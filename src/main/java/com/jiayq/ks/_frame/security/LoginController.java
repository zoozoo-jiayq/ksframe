package com.jiayq.ks._frame.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jiayq.ks._frame.base.BaseController;

@Controller
public class LoginController extends BaseController {
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(ModelMap model){
		return "login";
	}
	
	
	@RequestMapping("/")
	public String home(ModelMap model){
		return "index";
	}
	
}
