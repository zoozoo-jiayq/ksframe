package com.jiayq.ks._frame.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.google.gson.Gson;
import com.jiayq.ks._frame.security.LoginUser;
import com.jiayq.ks._frame.security.UserDetailsAdapter;
import com.jiayq.ks._frame.utils.Variant;

public class BaseController implements BaseUI {

private static Logger log = LoggerFactory.getLogger(BaseController.class);
	
	private final String AJAX_HEADER = "X-Requested-With";
	private final String PAGE_INDEX = "page_index";//页数，从0开始
	private final String PAGE_SIZE = "page_size";//每页多少条记录,默认20
	private final int DEFAULT_PAGE_SIZE = 20;
	private final String DEFAULT_SORT_PROPERTY = "insertTime";
	
	@Resource
	protected HttpServletRequest request;
	
	@Resource
	protected HttpServletResponse response;

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
		int page = Variant.valueOf(request.getParameter(PAGE_INDEX)).intValue(0);
		int pageSize = Variant.valueOf(request.getParameter(PAGE_SIZE)).intValue(DEFAULT_PAGE_SIZE);
		return PageRequest.of(page, pageSize, sort);
	}
	
	protected Pageable getPage() {
		return getPage(new Sort(Direction.DESC, DEFAULT_SORT_PROPERTY));
	}

	protected LoginUser getCurrentUser() {
		UserDetailsAdapter uda =  (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return uda.getUser();
	}

	
	public Object SUCCESS(Object data){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", "success");
		result.put("data", data);
		return result;
	}
	
	public Object SUCCESS(){
		return SUCCESS(null);
	}
	
	public Object ERROR(String desc){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", "fail");
		result.put("data", desc);
		return result;
	}
}
