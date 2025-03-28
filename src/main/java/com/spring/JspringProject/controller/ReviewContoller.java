package com.spring.JspringProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.JspringProject.service.ReviewService;
import com.spring.JspringProject.vo.ReviewVo;

@Controller
@RequestMapping("/review")
public class ReviewContoller {

	@Autowired
	ReviewService reviewService;
	
	// 리뷰 등록하기
	@ResponseBody
	@PostMapping(value="/reviewInputOk", produces="application/text; charset=utf-8")
	public String reviewInputOkPost(ReviewVo vo) {
		int res = reviewService.setReviewInputOk(vo);
		if(res != 0) return "리뷰가 등록되었습니다.";
		else return "리뷰 등록실패";
	}
	
}
