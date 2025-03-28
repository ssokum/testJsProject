package com.spring.JspringProject.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.JspringProject.common.Pagination;
import com.spring.JspringProject.service.PdsService;
import com.spring.JspringProject.service.ReviewService;
import com.spring.JspringProject.vo.PageVo;
import com.spring.JspringProject.vo.PdsVo;
import com.spring.JspringProject.vo.ReviewReplyVo;
import com.spring.JspringProject.vo.ReviewVo;

@Controller
@RequestMapping("/pds")
public class PdsController {

	@Autowired
	PdsService pdsService;
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	Pagination pagination;
	
	@RequestMapping(value = "/pdsList", method = RequestMethod.GET)
	public String pdsListGet(Model model,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize
		) {
		PageVo pageVo = pagination.getTotRecCnt(pag, pageSize, "pds", part, "");
		List<PdsVo> vos = pdsService.getPdsList(pageVo.getStartIndexNo(), pageVo.getPageSize(),part,"","");
		
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("vos", vos);
		
		return "pds/pdsList";
	}
	
	@RequestMapping(value = "/pdsInput", method = RequestMethod.GET)
	public String pdsInputGet(Model model,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part) {
		model.addAttribute("part", part);
		return "pds/pdsInput";
	}
	
	@RequestMapping(value = "/pdsInput", method = RequestMethod.POST)
	public String pdsInputPost(Model model, MultipartHttpServletRequest mFName, PdsVo vo) {
		int res = pdsService.setPdsInput(mFName, vo);
		model.addAttribute("part", vo.getPart());
		
		if(res != 0) return "redirect:/message/pdsInputOk";
		else return "redirect:/message/pdsInputNo";
	}
	
	// 자료실 삭제처리
	@ResponseBody
	@RequestMapping(value = "/pdsDeleteCheck", method = RequestMethod.POST)
	public String pdsDeleteCheckPost(int idx, String fSName, HttpServletRequest request) {
		return pdsService.setPdsDelete(idx, fSName, request) + "";
	}
	
	// 파일 다운로드수 증가처리
	@ResponseBody
	@RequestMapping(value = "/pdsDownNumCheck", method = RequestMethod.POST)
	public String pdsDownNumCheckPost(int idx) {
		return pdsService.setPdsDownNumPlus(idx) + "";
	}

	// 자료실 상세보기
	@RequestMapping(value = "/pdsContent", method = RequestMethod.GET)
	public String pdsContentGet(Model model, int idx,
			@RequestParam(name="part", defaultValue = "전체", required = false) String part,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize
		) {
		PdsVo vo = pdsService.getPdsContent(idx);
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("part", part);
		
		// 리뷰 가져오기
		List<ReviewVo> rVos = reviewService.getPdsReview("pds", idx);
		model.addAttribute("rVos", rVos);
		
		// 리뷰 평점 구하기
		Double reviewAvg = reviewService.getPdsReviewAge("pds", idx)==null ? 0.0 : reviewService.getPdsReviewAge("pds", idx);
		model.addAttribute("reviewAvg", reviewAvg);
		
		// 리뷰 댓글 가져오기
		List<ReviewReplyVo> reVos = reviewService.getPdsReviewReplyVo("pds", idx);
		model.addAttribute("reVos", reVos);
		
		return "pds/pdsContent";
	}
	
	@SuppressWarnings("deprecation")
	@GetMapping("/pdsTotalDown")
	public String pdsTotalDownGet(HttpServletRequest request, int idx) throws IOException {
		// 다운로드수 증가처리
		pdsService.setPdsDownNumPlus(idx);
		
		// 여러개의 파일을 하나의 통합파일(zip)로 다운로드 처리, 파일명은 '제목.zip'
		String zipName = pdsService.pdsTotalDown(request, idx);
		
		return "redirect:/fileDownAction?path=pds&file="+java.net.URLEncoder.encode(zipName);
	}
	
	// 댓글 삭제처리
//	@ResponseBody
//	@RequestMapping(value = "/pdsReviewDelete", method = RequestMethod.POST)
//	public String pdsReviewDeletePost(int idx) {
//		return pdsService.setPdsDelete(idx, fSName, request) + "";
//	}
//	
}
