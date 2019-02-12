package com.jiayq.ks._frame.freemarker;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jiayq.ks._frame.utils.Variant;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class TagFrame implements TemplateDirectiveModel {

	private final String TITLE = "title";
	private final String MENU = "menu";
	
	/* 
	 * arg1:{
	 * 		title:页面标题,
	 * 		menu:当前选中的菜单
	 * }
	 */
	@Override
	public void execute(Environment arg0, Map arg1, TemplateModel[] arg2, TemplateDirectiveBody arg3)
			throws TemplateException, IOException {
		// TODO Auto-generated method stub
		String title =  Variant.valueOf(arg1.get(TITLE)).stringValue("XXXX管理系统");
		String menu = Variant.valueOf(arg1.get(MENU)).stringValue("");
		arg3.render(arg0.getOut());
	}

}
