package com.jiayq.ks.app.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.catalina.core.StandardContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiayq.ks._frame.base.BaseController;
import com.jiayq.ks._frame.utils.StringUtils;
import com.jiayq.ks.app.consu.Consu;
import com.jiayq.ks.app.consu.ConsuService;
import com.jiayq.ks.app.consuapply.ConsuApply;
import com.jiayq.ks.app.consuapply.ConsuApplyService;
import com.jiayq.ks.app.fee.Fee;
import com.jiayq.ks.app.fee.FeeService;
import com.jiayq.ks.app.feeapply.FeeApply;
import com.jiayq.ks.app.feeapply.FeeApplyService;
import com.jiayq.ks.app.product.Product;
import com.jiayq.ks.app.product.ProductService;
import com.jiayq.ks.app.productapply.ProductApply;
import com.jiayq.ks.app.productapply.ProductApplyService;

@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {

	@Resource
	private ProductApplyService productApplyService;
	@Resource
	private ProductService productService;
	@Resource
	private FeeApplyService feeApplyService;
	@Resource
	private ConsuApplyService consuApplyService;
	@Resource
	private FeeService feeService;
	@Resource
	private ConsuService consuService;
	
	//默认查询最近5天的
	@RequestMapping("product")
	public Object product(Model model) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -5);
		model.addAttribute("begin", c.getTime());
		return "report/product";
	}
	//默认查询最近5天的
	@RequestMapping("fee")
	public Object fee(Model model) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -5);
		model.addAttribute("begin", c.getTime());
		return "report/fee";
	}
	//默认查询最近5天的
		@RequestMapping("consu")
		public Object consu(Model model) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -5);
			model.addAttribute("begin", c.getTime());
			return "report/consu";
		}
	
	/**
	 * @param beginyear
	 * @param beginmonth
	 * @param endyear
	 * @param endmonth
	 * @param productIds
	 * @return {
	 * 			   data:{
	 * 					"石子":[1,2],
	 * 					"炮眼":[2,3]
	 * 			   },
	 * 			   x:["2019-1","2019-2"]
	 * 		   }
	 */
	@ResponseBody
	@RequestMapping("productMonthAmount")
	public Object productMonthAmount(int beginyear,int beginmonth,int endyear,int endmonth,String productIds) {
		Date begin  = new Date(beginyear-1900,beginmonth-1,1);
		Date end = new Date(endyear-1900,endmonth-1,1);
		Calendar c = Calendar.getInstance();
		c.setTime(end);
		int days = c.getMaximum(Calendar.DAY_OF_MONTH);
		end = new Date(endyear-1900,endmonth-1,days); 
		Map<String,Object> r1 = new HashMap<>();
		
		if(StringUtils.isNotEmpty(productIds)) {
			Map<String,List<Double>> data = new HashMap<>();
			r1.put("x", initByMonth(begin, end).stream().map(i->i.getDate()).collect(Collectors.toList()));
			r1.put("data", data);
			List<ProductApply> applys = productApplyService.findByProjectIdAndDays(getCurrentUser().getProjectId(), begin, end);
			
			//key:产品类型，value,依次按天的产量
			//初始化result
			Map<String,List<ReportAmount>> result = new HashMap<>();
			for(String id:productIds.split(",")) {
				List<ReportAmount> initmonths = initByMonth(begin, end);
				result.put(id, initmonths);
			}
			
			//赋值
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			for(Iterator<ProductApply> it = applys.iterator(); it.hasNext();) {
				ProductApply pa = it.next();
				String applyDate = sdf.format(pa.getApplyDate());
				List<ReportAmount> amounts = result.get(pa.getProductId());
				if(amounts!=null) {
					for(ReportAmount productAmount:amounts) {
						if(productAmount.getDate().equals(applyDate)) {
							productAmount.addAmount(pa.getAmount());
						}
					}
				}
			}
			
			//转化输出
			for(Iterator<Map.Entry<String, List<ReportAmount>>> it = result.entrySet().iterator();it.hasNext();) {
				Map.Entry<String,List<ReportAmount>> e = it.next();
				String productId = e.getKey();
				List<ReportAmount> palist = e.getValue();
				Product p = productService.findById(productId).get();
				data.put(p.getName(), palist.stream().map(pa->pa.getAmount()).collect(Collectors.toList()));
			}
		}
		
		return SUCCESS(r1);
	}
	
	/**
	 * @param begin
	 * @param end
	 * @param productIds
	 * @return {
	 * 			   data:{
	 * 					"石子":[1,2],
	 * 					"炮眼":[2,3]
	 * 			   },
	 * 			   x:["2019-1-1","2019-1-2"]
	 * 		   }
	 */
	@ResponseBody
	@RequestMapping("productDayAmount")
	public Object productDayAmount(@DateTimeFormat(pattern = "yyyy-MM-dd")Date begin,@DateTimeFormat(pattern = "yyyy-MM-dd") Date end,String productIds) {
		Map<String,Object> r1 = new HashMap<>();
		
		if(StringUtils.isNotEmpty(productIds)) {
			Map<String,List<Double>> data = new HashMap<>();
			r1.put("data", data);
			//初始化X坐标
			r1.put("x", initByDays(begin, end).stream().map(i->i.getDate()).collect(Collectors.toList()));
			
			//key:产品类型ID，value:依次按天的产量，如果没有为0
			Map<String,List<ReportAmount>> result = new HashMap<>();
			List<ProductApply> applys = productApplyService.findByProjectIdAndDays(getCurrentUser().getProjectId(), begin, end);
			
			//初始化result
			for(String id:productIds.split(",")) {
				List<ReportAmount> initdays = initByDays(begin, end);
				result.put(id, initdays);
			}
			
			//赋值
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(Iterator<ProductApply> it = applys.iterator(); it.hasNext();) {
				ProductApply pa = it.next();
				String applyDate = sdf.format(pa.getApplyDate());
				List<ReportAmount> amounts = result.get(pa.getProductId());
				if(amounts!=null) {
					for(ReportAmount productAmount:amounts) {
						if(productAmount.getDate().equals(applyDate)) {
							productAmount.addAmount(pa.getAmount());
						}
					}
				}
			}
			
			//转化输出
			for(Iterator<Map.Entry<String, List<ReportAmount>>> it = result.entrySet().iterator();it.hasNext();) {
				Map.Entry<String,List<ReportAmount>> e = it.next();
				String productId = e.getKey();
				List<ReportAmount> palist = e.getValue();
				Product p = productService.findById(productId).get();
				data.put(p.getName(), palist.stream().map(pa->pa.getAmount()).collect(Collectors.toList()));
			}
		}
		
		
		
		return SUCCESS(r1);
	}
	
	/**
	 * @param begin
	 * @param end
	 * @param feeIds
	 * @return {
	 * 			   data:{
	 * 					"费用1":[1,2],
	 * 					"费用2":[2,3]
	 * 			   },
	 * 			   x:["2019-1-1","2019-1-2"]
	 * 		   }
	 */
	@ResponseBody
	@RequestMapping("feeDayAmount")
	public Object feeDayAmount(@DateTimeFormat(pattern = "yyyy-MM-dd")Date begin,@DateTimeFormat(pattern = "yyyy-MM-dd") Date end,String feeIds) {
		Map<String,Object> r1 = new HashMap<>();
		
		if(StringUtils.isNotEmpty(feeIds)) {
			Map<String,List<Double>> data = new HashMap<>();
			r1.put("data", data);
			//初始化X坐标
			r1.put("x", initByDays(begin, end).stream().map(i->i.getDate()).collect(Collectors.toList()));
			
			//key:费用类型ID，value:依次为费用额，如果没有为0
			Map<String,List<ReportAmount>> result = new HashMap<>();
			List<FeeApply> applys = feeApplyService.findApprovedHistory(getCurrentUser().getProjectId(), begin, end);
			
			//初始化result
			for(String id:feeIds.split(",")) {
				List<ReportAmount> initdays = initByDays(begin, end);
				result.put(id, initdays);
			}
			
			//赋值
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(Iterator<FeeApply> it = applys.iterator(); it.hasNext();) {
				FeeApply pa = it.next();
				String applyDate = sdf.format(pa.getApplyDate());
				List<ReportAmount> amounts = result.get(pa.getFeeTypeId());
				if(amounts!=null) {
					for(ReportAmount productAmount:amounts) {
						if(productAmount.getDate().equals(applyDate)) {
							productAmount.addAmount(pa.getMoney());
						}
					}
				}
			}
			
			//转化输出
			for(Iterator<Map.Entry<String, List<ReportAmount>>> it = result.entrySet().iterator();it.hasNext();) {
				Map.Entry<String,List<ReportAmount>> e = it.next();
				String feeId = e.getKey();
				List<ReportAmount> palist = e.getValue();
				Fee p = feeService.findById(feeId).get();
				data.put(p.getName(), palist.stream().map(pa->pa.getAmount()).collect(Collectors.toList()));
			}
		}
		return SUCCESS(r1);
	}
	
	@ResponseBody
	@RequestMapping("feeMonthAmount")
	public Object feeMonthAmount(int beginyear,int beginmonth,int endyear,int endmonth,String feeIds) {
		Date begin  = new Date(beginyear-1900,beginmonth-1,1);
		Date end = new Date(endyear-1900,endmonth-1,1);
		Calendar c = Calendar.getInstance();
		c.setTime(end);
		int days = c.getMaximum(Calendar.DAY_OF_MONTH);
		end = new Date(endyear-1900,endmonth-1,days); 
		Map<String,Object> r1 = new HashMap<>();
		
		if(StringUtils.isNotEmpty(feeIds)) {
			Map<String,List<Double>> data = new HashMap<>();
			r1.put("x", initByMonth(begin, end).stream().map(i->i.getDate()).collect(Collectors.toList()));
			r1.put("data", data);
			List<FeeApply> applys = feeApplyService.findApprovedHistory(getCurrentUser().getProjectId(), begin, end);
			
			//key:产品类型，value,依次按天的产量
			//初始化result
			Map<String,List<ReportAmount>> result = new HashMap<>();
			for(String id:feeIds.split(",")) {
				List<ReportAmount> initmonths = initByMonth(begin, end);
				result.put(id, initmonths);
			}
			
			//赋值
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			for(Iterator<FeeApply> it = applys.iterator(); it.hasNext();) {
				FeeApply pa = it.next();
				String applyDate = sdf.format(pa.getApplyDate());
				List<ReportAmount> amounts = result.get(pa.getFeeTypeId());
				if(amounts!=null) {
					for(ReportAmount productAmount:amounts) {
						if(productAmount.getDate().equals(applyDate)) {
							productAmount.addAmount(pa.getMoney());
						}
					}
				}
			}
			
			//转化输出
			for(Iterator<Map.Entry<String, List<ReportAmount>>> it = result.entrySet().iterator();it.hasNext();) {
				Map.Entry<String,List<ReportAmount>> e = it.next();
				String feeId = e.getKey();
				List<ReportAmount> palist = e.getValue();
				Fee p = feeService.findById(feeId).get();
				data.put(p.getName(), palist.stream().map(pa->pa.getAmount()).collect(Collectors.toList()));
			}
		}
		
		return SUCCESS(r1);
	}
	
	@ResponseBody
	@RequestMapping("consuDayAmount")
	public Object consuDayAmount(@DateTimeFormat(pattern = "yyyy-MM-dd")Date begin,@DateTimeFormat(pattern = "yyyy-MM-dd") Date end,String consuIds) {
		Map<String,Object> r1 = new HashMap<>();
		
		if(StringUtils.isNotEmpty(consuIds)) {
			Map<String,List<Double>> data = new HashMap<>();
			r1.put("data", data);
			//初始化X坐标
			r1.put("x", initByDays(begin, end).stream().map(i->i.getDate()).collect(Collectors.toList()));
			
			//key:费用类型ID，value:依次为费用额，如果没有为0
			Map<String,List<ReportAmount>> result = new HashMap<>();
			List<ConsuApply> applys = consuApplyService.findApprovedHistory(getCurrentUser().getProjectId(), begin, end);
			
			//初始化result
			for(String id:consuIds.split(",")) {
				List<ReportAmount> initdays = initByDays(begin, end);
				result.put(id, initdays);
			}
			
			//赋值
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(Iterator<ConsuApply> it = applys.iterator(); it.hasNext();) {
				ConsuApply pa = it.next();
				String applyDate = sdf.format(pa.getApplyDate());
				List<ReportAmount> amounts = result.get(pa.getConsuTypeId());
				if(amounts!=null) {
					for(ReportAmount productAmount:amounts) {
						if(productAmount.getDate().equals(applyDate)) {
							productAmount.addAmount(pa.getNums());
						}
					}
				}
			}
			
			//转化输出
			for(Iterator<Map.Entry<String, List<ReportAmount>>> it = result.entrySet().iterator();it.hasNext();) {
				Map.Entry<String,List<ReportAmount>> e = it.next();
				String consuId = e.getKey();
				List<ReportAmount> palist = e.getValue();
				Consu p = consuService.findById(consuId).get();
				data.put(p.getName(), palist.stream().map(pa->pa.getAmount()).collect(Collectors.toList()));
			}
		}
		return SUCCESS(r1);
	}
	
	@ResponseBody
	@RequestMapping("consuMonthAmount")
	public Object consuMonthAmount(int beginyear,int beginmonth,int endyear,int endmonth,String consuIds) {
		Date begin  = new Date(beginyear-1900,beginmonth-1,1);
		Date end = new Date(endyear-1900,endmonth-1,1);
		Calendar c = Calendar.getInstance();
		c.setTime(end);
		int days = c.getMaximum(Calendar.DAY_OF_MONTH);
		end = new Date(endyear-1900,endmonth-1,days); 
		Map<String,Object> r1 = new HashMap<>();
		
		if(StringUtils.isNotEmpty(consuIds)) {
			Map<String,List<Double>> data = new HashMap<>();
			r1.put("x", initByMonth(begin, end).stream().map(i->i.getDate()).collect(Collectors.toList()));
			r1.put("data", data);
			List<ConsuApply> applys = consuApplyService.findApprovedHistory(getCurrentUser().getProjectId(), begin, end);
			
			//key:产品类型，value,依次按天的产量
			//初始化result
			Map<String,List<ReportAmount>> result = new HashMap<>();
			for(String id:consuIds.split(",")) {
				List<ReportAmount> initmonths = initByMonth(begin, end);
				result.put(id, initmonths);
			}
			
			//赋值
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			for(Iterator<ConsuApply> it = applys.iterator(); it.hasNext();) {
				ConsuApply pa = it.next();
				String applyDate = sdf.format(pa.getApplyDate());
				List<ReportAmount> amounts = result.get(pa.getConsuTypeId());
				if(amounts!=null) {
					for(ReportAmount productAmount:amounts) {
						if(productAmount.getDate().equals(applyDate)) {
							productAmount.addAmount(pa.getNums());
						}
					}
				}
			}
			
			//转化输出
			for(Iterator<Map.Entry<String, List<ReportAmount>>> it = result.entrySet().iterator();it.hasNext();) {
				Map.Entry<String,List<ReportAmount>> e = it.next();
				String consuId = e.getKey();
				List<ReportAmount> palist = e.getValue();
				Consu p = consuService.findById(consuId).get();
				data.put(p.getName(), palist.stream().map(pa->pa.getAmount()).collect(Collectors.toList()));
			}
		}
		
		return SUCCESS(r1);
	}

	private List<ReportAmount> initByMonth(Date begin,Date end){
		List<ReportAmount> list = new ArrayList<>();
		Calendar b = Calendar.getInstance();
		b.setTime(begin);
		
		Calendar e = Calendar.getInstance();
		e.setTime(end);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		while(b.getTimeInMillis()<=e.getTimeInMillis()) {
			list.add(new ReportAmount(sdf.format(b.getTime()), 0d));
			b.add(Calendar.MONTH, 1);
		}
		return list;
	}
	
	private List<ReportAmount> initByDays(Date begin,Date end){
		List<ReportAmount> list = new ArrayList<>();
		Calendar b = Calendar.getInstance();
		b.setTime(begin);
		
		Calendar e = Calendar.getInstance();
		e.setTime(end);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		while(b.getTimeInMillis()<=e.getTimeInMillis()) {
			list.add(new ReportAmount(sdf.format(b.getTime()), 0d));
			b.add(Calendar.DATE, 1);
		}
		return list;
	}
	
	class ReportAmount{
		private String date;
		private double amount;
		
		public ReportAmount(String date, double amount) {
			super();
			this.date = date;
			this.amount = amount;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
		
		public void addAmount(double d) {
			this.amount+=d;
		}
		
		
	}
	
	
}
