package com.spring.JspringProject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.JspringProject.service.AdminService;
import com.spring.JspringProject.service.MemberService;
import com.spring.JspringProject.vo.ComplaintVo;
import com.spring.JspringProject.vo.MemberVo;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	MemberService memberService;
	
	@GetMapping("/adminMain")
	public String adminMainGet() {
		return "admin/adminMain";
	}
	
	@GetMapping("/adminLeft")
	public String adminLeftGet() {
		return "admin/adminLeft";
	}
	
	@GetMapping("/adminContent")
	public String adminContentGet() {
		return "admin/adminContent";
	}
	
	@GetMapping("/member/memberList")
	public String memberListGet(Model model, HttpSession session,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name="level", defaultValue = "99", required = false) int level
		) {
		int totRecCnt = adminService.getMemberTotRecCnt(level);
		int totPage = (totRecCnt % pageSize) == 0 ? (totRecCnt / pageSize) : (totRecCnt / pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		
		int blockSize = 3;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage - 1) / blockSize;
		List<MemberVo> vos = memberService.getMemberList(startIndexNo, pageSize, level);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStartNo", curScrStartNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		model.addAttribute("level", level);
		
		return "admin/member/memberList";
	}
	
	// 선택된 회원 레벨 등급 변경처리
	@ResponseBody
	@RequestMapping(value = "/memberLevelChange", method = RequestMethod.POST)
	public String memberLevelChangePost(int level, int idx) {
		//int res = adminService.setMemberLevelChange(level, idx);
		return adminService.setMemberLevelChange(level, idx) + "";
	}
	
	// 선택한 회원 전체적으로 등급 변경하기
	@ResponseBody
	@RequestMapping(value = "/member/memberLevelSelectCheck", method = RequestMethod.POST)
	public String memberLevelSelectCheckPost(String idxSelectArray, int levelSelect) {
		return adminService.setLevelSelectCheck(idxSelectArray, levelSelect);
	}
	
	// 개별회원정보 상세보기
	@RequestMapping(value = "/memberInfor/{idx}", method = RequestMethod.GET)
	public String memberInforGet(Model model, @PathVariable int idx) {
		MemberVo vo = memberService.getMemberIdxSearch(idx);
		model.addAttribute("vo", vo);
		
		return "admin/member/memberInfor";
	}
	
	// 신고 리스트 출력
	@RequestMapping(value = "/complaint/complaintList", method = RequestMethod.GET)
	public String complaintListGet(Model model) {
		List<ComplaintVo> vos = adminService.getComplaintList();
		model.addAttribute("vos", vos);
		
		return "admin/complaint/complaintList";
	}
	
	/*
	// 신고글 감추기/보이기/
	@ResponseBody
	@RequestMapping(value = "/complaint/contentChange", method = RequestMethod.POST)
	public String contentChangePost(int contentIdx, String contentSw) {
		return adminService.setContentChange(contentIdx, contentSw) + "";
	}
	
	// 신고글 삭제하기
	@ResponseBody
	@RequestMapping(value = "/complaint/contentDelete", method = RequestMethod.POST)
	public String contentDeletePost(int contentIdx, String part) {
		return adminService.setContentDelete(contentIdx, part) + "";
	}
	*/
	
	// 신고글 '보이기/감추기/삭제하기'
	// 신고 처리하기(신고취소(S)/신고가리기(H)/삭제하기(D))
	// complaintSw : H(감추기-board테이블의 complaint필드값을 'HI' , complaint테이블의 progress필드값을 '처리완료(H)')
	// complaintSw : S(보이기-신고해제-board테이블의 complaint필드값을 'NO' , complaint테이블의 progress필드값을 '처리완료(S)')
	// complaintSw : D(삭제하기-board테이블의 해당레코드 삭제처리 , complaint테이블의 progress필드값을 '처리완료(D)')
	@ResponseBody
	@RequestMapping(value = "/complaint/complaintProcess", method = RequestMethod.POST)
	public String complaintProcessPost(int idx, String part, int partIdx, String complaintSw) {
		int res = 0;
		if(complaintSw.equals("D")) {		// 신고글 삭제처리(board테이블의 신고글만 삭제처리한다)
			res = adminService.setComplaintDelete(partIdx, part);
			complaintSw = "처리완료(D)";
		}
		else {  // complaintSw가 'H'면 board테이블의 complaint필드값을 'HI'로, 'S'면 board테이블의 complaint필드값을 'NO'로 처리한다.
			if(complaintSw.equals("H"))	{
				res = adminService.setComplaintProcess(partIdx, "HI");
				complaintSw = "처리완료(H)";
			}
			else {
				res = adminService.setComplaintProcess(partIdx, "NO");
				complaintSw = "처리완료(S)";
			}
		}
		
		if(res != 0) adminService.setComplaintProcessOk(idx, complaintSw);	// 앞의 작업완료후 처리된 결과를 complaint테이블의 progress필드에 complaintSw값으로 처리하기(update) 
		
		return res + "";
	}
	
}
