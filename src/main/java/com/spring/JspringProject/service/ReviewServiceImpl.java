package com.spring.JspringProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.JspringProject.dao.ReviewDao;
import com.spring.JspringProject.vo.ReviewReplyVo;
import com.spring.JspringProject.vo.ReviewVo;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewDao reviewDao;

	@Override
	public int setReviewInputOk(ReviewVo vo) {
		return reviewDao.setReviewInputOk(vo);
	}

	@Override
	public List<ReviewVo> getPdsReview(String part, int idx) {
		return reviewDao.getPdsReview(part, idx);
	}

	@Override
	public Double getPdsReviewAge(String part, int idx) {
		return reviewDao.getPdsReviewAge(part, idx);
	}

	@Override
	public List<ReviewReplyVo> getPdsReviewReplyVo(String part, int idx) {
		return reviewDao.getPdsReviewReplyVo(part, idx);
	}
	
}
