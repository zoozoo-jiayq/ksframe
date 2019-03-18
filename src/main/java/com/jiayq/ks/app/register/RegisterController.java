package com.jiayq.ks.app.register;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiayq.ks._frame.base.BaseController;
import com.jiayq.ks._frame.security.LoginUser;
import com.jiayq.ks._frame.security.LoginUserService;
import com.jiayq.ks._frame.utils.MD5;

@Controller
@RequestMapping("/register")
public class RegisterController extends BaseController {
	

	@Resource
	private LoginUserService userService;
	
	@RequestMapping("/index")
	public String register() {
		return "register";
	}
	
	/**
	 * @param phone
	 * @return
	 */
	@RequestMapping("checkphone")
	@ResponseBody
	public Object checkphone(String phone) {
		List<LoginUser> users = userService.findByPhone(phone);
		return users.size() == 0;
	}
	
	/**
	 * @param username
	 * @return
	 */
	@RequestMapping("checkusername")
	@ResponseBody
	public Object checkusername(String username) {
		List<LoginUser> users = userService.findByUsername(username);
		return users.size() == 0 ;
	}
	
	@RequestMapping("checknickname")
	@ResponseBody
	public Object checknickname(String nickName) {
		List<LoginUser> users = userService.findByNickName(nickName);
		return users.size() == 0 ;
	}
	
	@RequestMapping(value="submit",method = RequestMethod.POST)
	@ResponseBody
	public Object submit(LoginUser user) {
		String encryPwd = MD5.encrypt(user.getPassword());
		user.setPassword(encryPwd);
		userService.save(user);
		return SUCCESS();
	}
	
}
