package com.resume.parser.controller.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.resume.parser.sso.CookieUtil;
import com.resume.parser.sso.JwtUtil;

@Controller
public class ResumeController {
	
	private static final String jwtTokenCookieName = "JWT-TOKEN";

	@GetMapping("/home")
	public String home() {
		return "index";
	}
	
	@GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        JwtUtil.invalidateRelatedTokens(httpServletRequest);
        CookieUtil.clear(httpServletResponse, jwtTokenCookieName);
        return "redirect:/";
    }
}
