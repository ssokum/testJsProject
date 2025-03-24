package com.spring.JspringProject.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.JspringProject.common.PageNavigation;
import com.spring.JspringProject.service.BoardService;
import com.spring.JspringProject.vo.BoardReplyVo;
import com.spring.JspringProject.vo.BoardVo;
import com.spring.JspringProject.vo.PageVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@Autowired
	PageNavigation PageNavigation;
	
	/*
	@RequestMapping(value = "/boardList", method = RequestMethod.GET)
	public String boardListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
		) {
		int totRecCnt = boardService.getBoardTotRecCnt();
		int totPage = (totRecCnt % pageSize) == 0 ? (totRecCnt / pageSize) : (totRecCnt / pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		
		int blockSize = 3;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage - 1) / blockSize;
		List<BoardVo> vos = boardService.getBoardList(startIndexNo, pageSize);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStartNo", curScrStartNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		
		return "board/boardList";
	}
*/
	
	@RequestMapping(value = "/boardList", method = RequestMethod.GET)
	public String boardListGet(Model model,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name="search", defaultValue = "", required = false) String search,
			@RequestParam(name="searchString", defaultValue = "", required = false) String searchString
		) {
		//	int totRecCnt = boardService.getBoardTotRecCnt(pag, pageSize, section, part ,searchString );	//페이지번호, 한 페이지 분량, 분류, 상세 분류 , 검색어
		PageVo pageVo = PageNavigation.getTotRecCnt(pag, pageSize, "board", search ,searchString );		
		
		List<BoardVo> vos = boardService.getBoardList(pageVo.getStartIndexNo(), pageVo.getPageSize(), search, searchString);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVo", pageVo);
		
		return "board/boardList";
	}

	// 게시글 입력폼 보기
	@RequestMapping(value = "/boardInput", method = RequestMethod.GET)
	public String boardInputGet() {
		return "board/boardInput";
	}
	
	// 게시글 입력 처리
	@RequestMapping(value = "/boardInput", method = RequestMethod.POST)
	public String boardInputPost(BoardVo vo) {
		if(vo.getContent().indexOf("src=\"/") != -1) {
			boardService.imgCheck(vo.getContent());	
		}
		
		vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
		
		int res = boardService.setBoardInputOk(vo);
		
		if(res != 0) return "redirect:/message/boardInputOk";
		else return "redirect:/message/boardInputNo";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/boardContent", method = RequestMethod.GET)
	public String boardContentGet(Model model, int idx,  HttpSession session,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name="search", defaultValue = "", required = false) String search,
			@RequestParam(name="searchString", defaultValue = "", required = false) String searchString) {
		// 글 조회수 증가처리
		//boardService.setBoardReadNumPlus(idx);
		// 중복 방지
		List<String> boardNum = (List<String>) session.getAttribute("sDuplicate");
		if(boardNum == null) boardNum = new ArrayList<String>();
		String tmpNum = "board" + idx;
		if(!boardNum.contains(tmpNum)) {
			boardService.setBoardReadNumPlus(idx);
			boardNum.add(tmpNum);
		}
		session.setAttribute("sDuplicate", boardNum);
		
		BoardVo vo = boardService.getBoardContent(idx);
		
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("search", search);
		model.addAttribute("searchString", searchString);
		
		// 이전글/다음글 가져오기
		BoardVo prevVo = boardService.getPrevNextSearch(idx, "prev");
		BoardVo nextVo = boardService.getPrevNextSearch(idx, "next");
		
		model.addAttribute("prevVo", prevVo);
		model.addAttribute("nextVo", nextVo);
		
		// 댓글 추가
		List<BoardReplyVo> replyVos = boardService.getBoardReply(idx);
		model.addAttribute("replyVos", replyVos);
		
		return "board/boardContent";
	}
	
	//게시글 삭제 처리
	@RequestMapping(value = "/boardDelete", method = RequestMethod.GET)
	public String boardDeleteGet(int idx) {
		//게시글에 사진이 존재한다면 실제 파일을 board폴더에서 삭제시킨다.
		BoardVo vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1) {
			boardService.imgDelete(vo.getContent());	
		}
		
		//사진 작업 완료 후 DB에 저장된 실제 정보 레코드를 삭제 처리
		int res = boardService.setboardDelete(idx);
		
		if(res != 0) return "redirect:/message/boardDeleteOk";
		else return "redirect:/message/boardDeleteNo?idx="+idx;
	}
	
	// 게시글 수정 폼 보기
	@RequestMapping(value = "/boardUpdate", method = RequestMethod.GET)
	public String boardUpdateGet(Model model, int idx,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name="search", defaultValue = "", required = false) String search,
			@RequestParam(name="searchString", defaultValue = "", required = false) String searchString
			) {
		//수정 처리 시, 수정 폼을 호출할 때 현재 게시글에 그림이 존재한다면 그림 파일 모두를 ckeditor 폴더로 복사시켜둔다.
		BoardVo vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1) {
			boardService.imgBackup(vo.getContent());	
		}
		
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("search", search);
		model.addAttribute("searchString", searchString);
		
		
		return "board/boardUpdate";
	}
	
	// 게시글 수정처리
	@RequestMapping(value = "/boardUpdate", method = RequestMethod.POST)
	public String boardUpdatePost(Model model, BoardVo vo,
			@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name="search", defaultValue = "", required = false) String search,
			@RequestParam(name="searchString", defaultValue = "", required = false) String searchString
			) {
		// 수정된 자료가 원본 자료와 완전히 동일하다면 수정할 필요x
		BoardVo dbVo = boardService.getBoardContent(vo.getIdx());
		
		// content의 내용이 변경되었다면, 그림 파일 처리 유무를 결정함
		if(!dbVo.getContent().equals(vo.getContent())) {
			// 1. 기존 board폴더의 그림이 존재했다면, 원본 그림을 모두 삭제 처리함 
			if(dbVo.getContent().indexOf("src=\"/") != -1) {
				boardService.imgDelete(dbVo.getContent());	
			}	
			
			// 2. 삭제 후 'board' 폴더를 'ckeditor' 폴더로 경로 변경
			vo.setContent(vo.getContent().replace("/data/board/", "/data/ckeditor/"));;
			
			// 1, 2번 작업을 마치면 처음 글을 올릴 떄와 똑같은 상황으로 처리
			if(vo.getContent().indexOf("src=\"/") != -1) {
				boardService.imgCheck(vo.getContent());	
			}
			
			// 이미지 복사 작업을 마쳤으면 'ckeditor' 폴더에서 'board' 폴더로 경로 변경
			vo.setContent(vo.getContent().replace("/data/ckeditor/", "/data/board/"));
		}
		int res = boardService.setBoardUpdate(vo);
		
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("search", search);
		model.addAttribute("searchString", searchString);
		
		if(res != 0) return "redirect:/message/boardUpdateOk";
		else return "redirect:/message/boardUpdateOk?idx="+vo.getIdx();
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody	//<-- Ajax 처리 시 사용
	@RequestMapping(value="/boardGoodCheck1", method = RequestMethod.POST)
	public String boardGoodCheck1Post(HttpSession session,int idx) {
		//return boardService.setBoardGoodCheck1(idx)+"";
		String res = "0";
		
		// 중복 방지
		List<String> goodNum = (List<String>) session.getAttribute("sDuplicate");
		if(goodNum == null) goodNum = new ArrayList<String>();
		String tmpNum = "boardGood" + idx;
		if(!goodNum.contains(tmpNum)) {
			boardService.setBoardGoodCheck1(idx);
			goodNum.add(tmpNum);
			res = "1";
		}
		session.setAttribute("sDuplicate", goodNum);
		
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody	//<-- Ajax 처리 시 사용
	@RequestMapping(value="/boardGoodCheck2", method = RequestMethod.POST)
	public String boardGoodCheck2Post(HttpSession session,int idx, int goodCnt) {
		return boardService.setBoardGoodCheck2(idx, goodCnt) + "";
	}
	
	@ResponseBody	
	@RequestMapping(value="/boardReplyInput", method = RequestMethod.POST)
	public String boardReplyInput(BoardReplyVo vo) {
		return boardService.setBoardReplyInput(vo) + "";
	}
}
