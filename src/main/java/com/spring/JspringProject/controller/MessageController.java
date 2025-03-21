package com.spring.JspringProject.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

	@RequestMapping(value = "/message/{msgFlag}", method = RequestMethod.GET)
	public String getMessage(Model model, @PathVariable String msgFlag,
			HttpSession session,
			@RequestParam(name="mid", defaultValue = "", required = false) String mid,
			@RequestParam(name="idx", defaultValue = "0", required = false) int idx
		) {
		
		if(msgFlag.equals("userInputOk")) {
			model.addAttribute("message", "회원에 가입되었습니다.");
			model.addAttribute("url", "user/userMain");
		}
		else if(msgFlag.equals("userInputNo")) {
			model.addAttribute("message", "회원에 가입실패~~");
			model.addAttribute("url", "user/userInput");
		}
		else if(msgFlag.equals("user2InputOk")) {
			model.addAttribute("message", "회원에 가입되었습니다.");
			model.addAttribute("url", "user2/userMain");
		}
		else if(msgFlag.equals("user2InputNo")) {
			model.addAttribute("message", "회원에 가입실패~~");
			model.addAttribute("url", "user2/userInput");
		}
		else if(msgFlag.equals("userIdDuplication")) {
			model.addAttribute("message", "이미 사용중인 아이디 입니다.\\n다른 아이디로 가입하세요.");
			model.addAttribute("url", "user/userInput");
		}
		else if(msgFlag.equals("user2IdDuplication")) {
			model.addAttribute("message", "이미 사용중인 아이디 입니다.\\n다른 아이디로 가입하세요.");
			model.addAttribute("url", "user2/userInput");
		}
		else if(msgFlag.equals("userSearchNo")) {
			model.addAttribute("message", "검색한 회원이 없습니다.");
			model.addAttribute("url", "user/userSearch");
		}
		else if(msgFlag.equals("user2SearchNo")) {
			model.addAttribute("message", "검색한 회원이 없습니다.");
			model.addAttribute("url", "user2/userSearch");
		}
		else if(msgFlag.equals("userDeleteOk")) {
			model.addAttribute("message", "회원을 삭제처리 하였습니다.");
			model.addAttribute("url", "user/userList");
		}
		else if(msgFlag.equals("userDeleteNo")) {
			model.addAttribute("message", "회원삭제 실패");
			model.addAttribute("url", "user/userList");
		}
		else if(msgFlag.equals("user2DeleteOk")) {
			model.addAttribute("message", "회원을 삭제처리 하였습니다.");
			model.addAttribute("url", "user2/userList");
		}
		else if(msgFlag.equals("user2DeleteNo")) {
			model.addAttribute("message", "회원삭제 실패");
			model.addAttribute("url", "user2/userList");
		}
		else if(msgFlag.equals("userUpdateOk")) {
			model.addAttribute("message", "회원정보 수정 완료");
			model.addAttribute("url", "user/userList");
		}
		else if(msgFlag.equals("userUpdateNo")) {
			model.addAttribute("message", "회원정보 수정 실패~~");
			model.addAttribute("url", "user/userList");
		}
		else if(msgFlag.equals("user2UpdateOk")) {
			model.addAttribute("message", "회원정보 수정 완료");
			model.addAttribute("url", "user2/userList");
		}
		else if(msgFlag.equals("user2UpdateNo")) {
			model.addAttribute("message", "회원정보 수정 실패~~");
			model.addAttribute("url", "user/userList");
		}
		else if(msgFlag.equals("guestInputOk")) {
			model.addAttribute("message", "방명록에 글이 등록되었습니다.");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("guestInputNo")) {
			model.addAttribute("message", "방명록에 글이 등록 실패~~");
			model.addAttribute("url", "guest/guestInput");
		}
		else if(msgFlag.equals("adminOk")) {
			model.addAttribute("message", "관리자 로그인 성공!!");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("adminNo")) {
			model.addAttribute("message", "관리자 로그인 실패~~");
			model.addAttribute("url", "guest/admin");
		}
		else if(msgFlag.equals("adminLogout")) {
			model.addAttribute("message", "관리자 로그아웃 성공!!");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("guestDeleteOk")) {
			model.addAttribute("message", "게시글이 삭제 되었습니다.");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("guestDeleteNo")) {
			model.addAttribute("message", "게시글이 삭제 실패~~");
			model.addAttribute("url", "guest/guestList");
		}
		else if(msgFlag.equals("fileUploadOk")) {
			model.addAttribute("message", "파일이 업로드 되었습니다.");
			model.addAttribute("url", "study/fileUpload/fileUpload");
		}
		else if(msgFlag.equals("fileUploadNo")) {
			model.addAttribute("message", "파일이 업로드 실패~~");
			model.addAttribute("url", "study/fileUpload/fileUpload");
		}
		else if(msgFlag.equals("mailSendOk")) {
			model.addAttribute("message", "메일을 성공적으로 발송 했습니다.");
			model.addAttribute("url", "study/mail/mailForm");
		}
		else if(msgFlag.equals("idCheckNo")) {
			model.addAttribute("message", "아이디가 중복되었습니다.\\n확인하시고 다시 가입하세요.");
			model.addAttribute("url", "member/memberJoin");
		}
		else if(msgFlag.equals("memberJoinOk")) {
			model.addAttribute("message", "회원 가입되셨습니다.\\n로그인후 사용하세요.");
			model.addAttribute("url", "member/memberLogin");
		}
		else if(msgFlag.equals("memberJoinNo")) {
			model.addAttribute("message", "회원 가입실패~~\\n다시 회원가입 해주세요");
			model.addAttribute("url", "member/memberJoin");
		}
		else if(msgFlag.equals("memberLoginOk")) {
			model.addAttribute("message", mid + " 회원님 로그인 되셨습니다.");
			model.addAttribute("url", "member/memberMain");
		}
		else if(msgFlag.equals("memberLoginNo")) {
			model.addAttribute("message", "로그인 실패~ 다시 로그인해 주세요.");
			model.addAttribute("url", "member/memberLogin");
		}
		else if(msgFlag.equals("memberLogoutOk")) {
			model.addAttribute("message", "로그아웃 되었습니다.");
			model.addAttribute("url", "member/memberLogin");
		}
		else if(msgFlag.equals("pwdCheckNo")) {
			model.addAttribute("message", "비밀번호가 틀립니다. 확인해 주세요.");
			model.addAttribute("url", "member/pwdCheck/d");
		}
		else if(msgFlag.equals("pwdCheckNoP")) {
			model.addAttribute("message", "비밀번호가 틀립니다. 확인해 주세요.");
			model.addAttribute("url", "member/pwdCheck/p");
		}
		else if(msgFlag.equals("pwdCheckNoU")) {
			model.addAttribute("message", "비밀번호가 틀립니다. 확인해 주세요.");
			model.addAttribute("url", "member/pwdCheck/u");
		}
		else if(msgFlag.equals("memberDeleteCheck")) {
			model.addAttribute("message", "탈퇴처리 되었습니다.");
			session.invalidate();
			model.addAttribute("url", "member/memberLogin");
		}
		else if(msgFlag.equals("pwdChangeOk")) {
			model.addAttribute("message", "회원 비밀번호를 변경하였습니다.\\n다시 로그인해 주세요.");
			session.invalidate();
			model.addAttribute("url", "member/memberLogin");
		}
		else if(msgFlag.equals("pwdChangeNo")) {
			model.addAttribute("message", "회원 비밀번호를 변경 실패~~");
			model.addAttribute("url", "member/pwdCheck/p");
		}
		else if(msgFlag.equals("nickCheckNo")) {
			model.addAttribute("message", "이미 사용중인 닉네임 입니다.");
			model.addAttribute("url", "member/memberUpdate");
		}
		else if(msgFlag.equals("memberUpdateOk")) {
			model.addAttribute("message", "회원 정보가 수정되었습니다.");
			model.addAttribute("url", "member/memberMain");
		}
		else if(msgFlag.equals("memberUpdateNo")) {
			model.addAttribute("message", "회원 정보 수정실패~~");
			model.addAttribute("url", "member/memberUpdate");
		}
		else if(msgFlag.equals("boardInputOk")) {
			model.addAttribute("message", "게시글이 등록되었습니다.");
			model.addAttribute("url", "board/boardList");
		}
		else if(msgFlag.equals("boardInputNo")) {
			model.addAttribute("message", "게시글 등록실패~~");
			model.addAttribute("url", "board/boardInput");
		}
		else if(msgFlag.equals("boardDeleteOk")) {
			model.addAttribute("message", "게시글을 삭제 하였습니다.");
			model.addAttribute("url", "board/boardList");
		}
		else if(msgFlag.equals("boardDeleteNo")) {
			model.addAttribute("message", "게시글 삭제 실패~~");
			model.addAttribute("url", "board/boardContent?idx="+idx);
		}
		else if(msgFlag.equals("boardUpdateOk")) {
			model.addAttribute("message", "게시글이 수정 되었습니다.");
			model.addAttribute("url", "board/boardList");
		}
		else if(msgFlag.equals("boardUpdateNo")) {
			model.addAttribute("message", "게시글 수정 실패");
			model.addAttribute("url", "board/boardContent?idx="+idx);
		}
		
		return "include/message";
	}
	
}
