package com.suman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HoController {
	
	

	@RequestMapping("/")
	public String showLogout() {
		System.out.println("in home page..");

		return "index";
	}

}
