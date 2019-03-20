package com.jiayq.ks._frame.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiayq.ks._frame.base.BaseController;
import com.jiayq.ks._frame.utils.SpringContextHolder;

@Controller
public class LoginController extends BaseController {
	
	public static final String LOGIN_USER_KEY = "login_user";
	
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(ModelMap model){
		return "login";
	}
	
	@ResponseBody
	@RequestMapping("loginHandler")
	public Object loginHandler() {
		request.getSession().setAttribute(LOGIN_USER_KEY, getCurrentUser());
		SpringContextHolder.getContext().publishEvent(new LoginedEvent(request.getSession()));
		return SUCCESS();
	}
	
	@ResponseBody
	@RequestMapping("loginerror")
	public Object loginerror() {
		
		return SUCCESS("loginerror");
	}
	
	@RequestMapping("/")
	public String home(ModelMap model){
		
		return "index";
	}
	
	
}
