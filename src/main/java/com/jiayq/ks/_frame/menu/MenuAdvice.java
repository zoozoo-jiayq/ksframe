package com.jiayq.ks._frame.menu;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class MenuAdvice {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private HttpServletRequest request;
	
	@Pointcut("@annotation(com.jiayq.ks._frame.menu.Menu)")
	public void initMenu(){}
	
	@Around("initMenu()")
	public Object aoundInitModule(ProceedingJoinPoint jointPoint) throws Throwable{
		Signature sig = jointPoint.getSignature();
	    MethodSignature msig = null;
        if (sig instanceof MethodSignature) {
	        msig = (MethodSignature) sig;
	        Object target = jointPoint.getTarget();
	        Menu m = target.getClass().getAnnotation(Menu.class);
	        if(m!=null) {
	        	
		        String parentMenu =m.value();
		        request.setAttribute("parentmenu", parentMenu);
		        
		        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
		        Menu mc = currentMethod.getAnnotation(Menu.class);
				if(mc!=null){
					String menu = mc.value();
					request.setAttribute("menu", menu);
				}
			}
        }
		return jointPoint.proceed();
	}
}
