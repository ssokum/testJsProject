package com.spring.JspringProject.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.JspringProject.dao.AdminDao;
import com.spring.JspringProject.dao.BoardDao;
import com.spring.JspringProject.vo.BoardVo;
import com.spring.JspringProject.vo.ComplaintVo;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDao adminDao;
	
	@Autowired
	BoardDao boardDao;

	@Override
	public int setMemberLevelChange(int level, int idx) {
		return adminDao.setMemberLevelChange(level, idx);
	}

	@Override
	public int getMemberTotRecCnt(int level) {
		return adminDao.getMemberTotRecCnt(level);
	}

	@Override
	public int setBoardComplaintInput(ComplaintVo vo) {
		return adminDao.setBoardComplaintInput(vo);
	}

	@Override
	public void setBoardTableComplaintOk(int partIdx) {
		adminDao.setBoardTableComplaintOk(partIdx);
	}

	@Override
	public String setLevelSelectCheck(String idxSelectArray, int levelSelect) {
		String[] idxSelectArrays = idxSelectArray.split("/");
		
		String str = "0";
		for(String idx : idxSelectArrays) {
			adminDao.setMemberLevelCheck(Integer.parseInt(idx), levelSelect);
			str = "1";
		}
		return str;
	}

	@Override
	public List<ComplaintVo> getComplaintList() {
		return adminDao.getComplaintList();
	}

	@Override
	public int setContentChange(int contentIdx, String contentSw) {
		return adminDao.setContentChange(contentIdx, contentSw);
	}

	// 신고글 삭제하기
	@Override
	public int setContentDelete(int contentIdx, String part) {
		if(part.equals("board")) {
			// board글에 사진이 있으면 사진 먼저 삭제처리한다.
			BoardVo vo = boardDao.getBoardContent(contentIdx);
			
			if(vo.getContent().indexOf("src=\"/") != -1) {
				//      0         1         2         3         4         4
				//      01234567890123456789012345678901234567890123456789012345678
				// <img src="/JspringProject/data/board/250321140356_2503.jpg" style="height:854px; width:1280px" />
				
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
				String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
				
				int position = 32;
				String nextImg = vo.getContent().substring(vo.getContent().indexOf("src=\"/") + position);
				boolean sw = true;
				
				while(sw) {
					String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
					
					String origFilePath = realPath + "board/" + imgFile;
					
					File delFile = new File(origFilePath);
					if(delFile.exists()) delFile.delete();
					
					if(nextImg.indexOf("src=\"/") == -1) sw = false;
					else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
				}
			}
		}
		
		return adminDao.setContentDelete(contentIdx, part);
	}

	@Override
	public int setComplaintDelete(int partIdx, String part) {
		// 게시글에 사진이 있을경우는 사진도 삭제 처리한다.
		if(part.equals(part)) {
			// board글에 사진이 있으면 사진 먼저 삭제처리한다.
			BoardVo vo = boardDao.getBoardContent(partIdx);
			
			if(vo.getContent().indexOf("src=\"/") != -1) {
				//      0         1         2         3         4         4
				//      01234567890123456789012345678901234567890123456789012345678
				// <img src="/javaclassS/data/board/250321140356_2503.jpg" style="height:854px; width:1280px" />
				
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
				String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
				
				int position = 28;
				String nextImg = vo.getContent().substring(vo.getContent().indexOf("src=\"/") + position);
				boolean sw = true;
				
				while(sw) {
					String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
					
					String origFilePath = realPath + "board/" + imgFile;
					
					File delFile = new File(origFilePath);
					if(delFile.exists()) delFile.delete();
					
					if(nextImg.indexOf("src=\"/") == -1) sw = false;
					else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
				}
			}
		}
		return adminDao.setComplaintDelete(partIdx);
	}

	@Override
	public int setComplaintProcess(int partIdx, String complaintSw) {
		return adminDao.setComplaintProcess(partIdx, complaintSw);
	}

	@Override
	public void setComplaintProcessOk(int idx, String complaintSw) {
		adminDao.setComplaintProcessOk(idx, complaintSw);
	}
	
}
