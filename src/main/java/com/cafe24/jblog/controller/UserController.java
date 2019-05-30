package com.cafe24.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.service.UserService;
import com.cafe24.jblog.vo.BlogVO;
import com.cafe24.jblog.vo.UserVO;

@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	//회원가입 페이지 이동
	@RequestMapping("/join")
	public String join() {
		return "user/join";
	}
	
	//회원가입 처리
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute UserVO uvo,
					   @RequestParam (value="title", required=true, defaultValue="") String title,
					   @RequestParam (value="logo") MultipartFile logo) {
		

		//1) 회원가입
		boolean flag = userService.join(uvo);
		if(!flag) return "redirect:/user/join";
		
		//2) 블로그 자동 생성
		String url = userService.restore(logo);
		
		BlogVO bvo = new BlogVO();
		bvo.setUserId(uvo.getId());
		bvo.setTitle(title);
		bvo.setLogo(url);
			
		userService.makeBlog(bvo);
		
		return "user/joinsuccess";
	}
	
	//로그인 페이지 이동
	@RequestMapping("/login")
	public String login() {
		return "user/login";
	}
	
	//회원정보 수정
	
}