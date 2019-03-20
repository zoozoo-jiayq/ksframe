package com.jiayq.ks._frame.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.jiayq.ks._frame.security.UserDetailsAdapter;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class AuthCtlDirective implements TemplateDirectiveModel {

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
		SimpleScalar role = (SimpleScalar) arg1.get("role");
		UserDetailsAdapter uda =  (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String roleCode = role.getAsString();
		if(uda.hasAuth(roleCode)) {
			arg3.render(arg0.getOut());
		}else {
			arg3.render(new EmptyWriter(arg0.getOut()));
		}
		
	}
	
	class EmptyWriter extends Writer{
		
		private Writer writer;
		
		public EmptyWriter(Writer writer) {
			super();
			this.writer = writer;
		}

		@Override
		public void write(char[] cbuf, int off, int len) throws IOException {
			// TODO Auto-generated method stub
			if(writer!=null) {
				writer.write("");
			}
		}

		@Override
		public void flush() throws IOException {
			// TODO Auto-generated method stub
			if(writer!=null) {
				writer.flush();
			}
		}

		@Override
		public void close() throws IOException {
			// TODO Auto-generated method stub
			if(writer!=null) {
				writer.close();
			}
		}
		
	}

}
