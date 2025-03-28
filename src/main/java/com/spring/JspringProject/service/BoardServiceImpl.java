package com.spring.JspringProject.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.JspringProject.dao.BoardDao;
import com.spring.JspringProject.vo.BoardReplyVo;
import com.spring.JspringProject.vo.BoardVo;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDao boardDao;

	@Override
	public int getBoardTotRecCnt() {
		return boardDao.getBoardTotRecCnt();
	}

	@Override
	public List<BoardVo> getBoardList(int startIndexNo, int pageSize, String search, String searchString) {
		return boardDao.getBoardList(startIndexNo, pageSize, search, searchString);
	}

	@Override
	public BoardVo getBoardContent(int idx) {
		return boardDao.getBoardContent(idx);
	}

	@Override
	public void setBoardReadNumPlus(int idx) {
		boardDao.setBoardReadNumPlus(idx);
	}

	@Override
	public int setBoardInputOk(BoardVo vo) {
		return boardDao.setBoardInputOk(vo);
	}

	@Override
	public int setBoardDelete(int idx) {
		
		return boardDao.setBoardDelete(idx);
	}

	@Override
	public void imgCheck(String content) {
		//      0         1         2         3         4         4
		//      01234567890123456789012345678901234567890123456789012345678
		// <img src="/JspringProject/data/ckeditor/250321140356_2503.jpg" style="height:854px; width:1280px" />
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 35;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			
			String origFilePath = realPath + "ckeditor/" + imgFile;
			String copyFilePath = realPath + "board/" + imgFile;
			
			fileCopyCheck(origFilePath, copyFilePath);
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
	}

	// 파일 복사처리
	private void fileCopyCheck(String origFilePath, String copyFilePath) {
		try {
			FileInputStream fis = new FileInputStream(new File(origFilePath));
			FileOutputStream fos = new FileOutputStream(new File(copyFilePath));
			
			byte[] b = new byte[2048];
			int cnt = 0;
			while((cnt = fis.read(b)) != -1) {
				fos.write(b, 0, cnt);
			}
			fos.flush();
			fos.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 게시글에 사진포함되어 있을때 사진 삭제하기
	@Override
	public void imgDelete(String content) {
		//      0         1         2         3         4         4
		//      01234567890123456789012345678901234567890123456789012345678
		// <img src="/JspringProject/data/board/250321140356_2503.jpg" style="height:854px; width:1280px" />
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 32;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			
			String origFilePath = realPath + "board/" + imgFile;
			
			fileDelete(origFilePath);
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
	}

	// 파일 삭제처리
	private void fileDelete(String origFilePath) {
		File delFile = new File(origFilePath);
		if(delFile.exists()) delFile.delete();
	}

	@Override
	public void imgBackup(String content) {
		//      0         1         2         3         4         4
		//      01234567890123456789012345678901234567890123456789012345678
		// <img src="/JspringProject/data/board/250321140356_2503.jpg" style="height:854px; width:1280px" />
		// <img src="/JspringProject/data/ckeditor/250321140356_2503.jpg" style="height:854px; width:1280px" />
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 32;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0, nextImg.indexOf("\""));
			
			String origFilePath = realPath + "board/" + imgFile;
			String copyFilePath = realPath + "ckeditor/" + imgFile;
			
			fileCopyCheck(origFilePath, copyFilePath);
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
	}

	@Override
	public int setBoardUpdate(BoardVo vo) {
		return boardDao.setBoardUpdate(vo);
	}

	@Override
	public int setBoardGoodCheck1(int idx) {
		return boardDao.setBoardGoodCheck1(idx);
	}

	@Override
	public int setBoardGoodCheck2(int idx, int goodCnt) {
		return boardDao.setBoardGoodCheck2(idx, goodCnt);
	}

	@Override
	public BoardVo getPreNextSearch(int idx, String preNext) {
		return boardDao.getPreNextSearch(idx, preNext);
	}

	@Override
	public List<BoardReplyVo> getBoardReply(int idx) {
		return boardDao.getBoardReply(idx);
	}

	@Override
	public int setBoardReplyInput(BoardReplyVo vo) {
		return boardDao.setBoardReplyInput(vo);
	}

	@Override
	public int setBoardReplyDelete(int idx) {
		return boardDao.setBoardReplyDelete(idx);
	}

	@Override
	public int setBoardReplyUpdateCheckOk(BoardReplyVo vo) {
		return boardDao.setBoardReplyUpdateCheckOk(vo);
	}

	
}
