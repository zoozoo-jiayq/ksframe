package com.jiayq.ks._frame.base;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.google.gson.Gson;
import com.jiayq.ks._frame.security.LoginUser;
import com.jiayq.ks._frame.security.LoginUserService;
import com.jiayq.ks._frame.security.LoginedEvent;
import com.jiayq.ks._frame.security.UserDetailsAdapter;
import com.jiayq.ks._frame.utils.SpringContextHolder;
import com.jiayq.ks._frame.utils.Variant;

public class BaseController implements BaseUI {

private static Logger log = LoggerFactory.getLogger(BaseController.class);
	
	private final String AJAX_HEADER = "X-Requested-With";
	private final String PAGE_NUMBER = "page";//页数，从1开始
	private final String PAGE_SIZE = "rows";//每页多少条记录,默认10
	private final int DEFAULT_PAGE_SIZE = 10;
	private final int DEFAULT_PAGE_NUMBER = 1;
	protected final String DEFAULT_SORT_PROPERTY = "inserttime";
	
	@Resource
	protected HttpServletRequest request;
	
	@Resource
	protected HttpServletResponse response;
	@Resource
	private LoginUserService loginUserService;

	@ExceptionHandler(value = Exception.class)
	public String handleException(Exception e) throws ServletException, IOException{
		e.printStackTrace();
		log.error("error message =["+e.getMessage()+"]");
		String ajaxHeader = request.getHeader(AJAX_HEADER);
		if(StringUtils.isEmpty(ajaxHeader)){
			return "/error";
		}else{
			response.getWriter().write(new Gson().toJson(ERROR("server exception")));
			response.getWriter().flush();
		}
		return null;
	}
	
	protected Pageable getPage(Sort sort) {
		int page = Variant.valueOf(request.getParameter(PAGE_NUMBER)).intValue(DEFAULT_PAGE_NUMBER)-1;
		int pageSize = Variant.valueOf(request.getParameter(PAGE_SIZE)).intValue(DEFAULT_PAGE_SIZE);
		return PageRequest.of(page, pageSize, sort);
	}
	
	protected Pageable getPage() {
		return getPage(new Sort(Direction.DESC, DEFAULT_SORT_PROPERTY));
	}
	
	protected Pageable getMaxPage() {
		return PageRequest.of(0, Integer.MAX_VALUE,new Sort(Direction.DESC,DEFAULT_SORT_PROPERTY));
	}
	
	protected <T extends BaseModel> boolean isExist(String id,List<T> list) {
		boolean flag = false;
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getId().equals(id)) {
				return true;
			}
		}
		return flag;
	}

	protected LoginUser getCurrentUser() {
		UserDetailsAdapter uda =  (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return uda.getUser();
	}
	
	protected void merge(BaseModel dest,BaseModel source) {
		if(dest==null){
			dest = source;
		}
		PropertyDescriptor[] pds = ReflectUtils.getBeanProperties(source.getClass());
		for(int i=0; i<pds.length; i++){
			Method rm = pds[i].getReadMethod();
			Method wm = pds[i].getWriteMethod();
			try {
				Object value = rm.invoke(source, null);
				if(value!=null && wm!=null){
					wm.invoke(dest, value);
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	protected void updateCurrentUser(LoginUser user) {
		loginUserService.save(user);
		SpringContextHolder.getContext().publishEvent(new LoginedEvent(request.getSession()));
	}

	
	/**
	 * 自定义的数据格式
	 * @param data
	 * @return
	 */
	public Object SUCCESS(Object data){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", "success");
		result.put("data", data);
		return result;
	}
	
	/**
	 * 自定义的数据格式
	 * @return
	 */
	public Object SUCCESS(){
		return SUCCESS(null);
	}
	
	/**
	 * 自定义的数据格式
	 * @param desc
	 * @return
	 */
	public Object ERROR(String desc){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", "fail");
		result.put("data", desc);
		return result;
	}
	
	/**
	 * jquery easyUI支持的数据格式
	 * @param page
	 * @return
	 */
	public <T extends BaseModel> Object SUCCESS_GRID(Page<T> page) {
		Map<String,Object> r = new HashMap<>();
		r.put("total", page.getTotalElements());
		r.put("rows", page.getContent());
		return r;
	}
	
	public Object SUCCESS_GRID(long total,List rows) {
		Map<String,Object> r = new HashMap<>();
		r.put("total", total);
		r.put("rows", rows);
		return r;
	}
	
}
