package com.jiayq.ks._frame.base;

import org.springframework.ui.Model;

public interface BaseUI {

	default  String list(Model model) {
		return null;
	};

	default String form(Model model) {
		return null;
	};

	default String delete(Model model) {
		return null;
	};
}
