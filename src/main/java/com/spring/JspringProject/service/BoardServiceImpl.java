package com.spring.JspringProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.JspringProject.dao.BoardDao;
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
	public List<BoardVo> getBoardList(int startIndexNo, int pageSize) {
		return boardDao.getBoardList(startIndexNo, pageSize);
	}

	@Override
	public BoardVo getBoardContent(int idx) {
		return boardDao.getBoardContent(idx);
	}

	@Override
	public void setBoardReadNumPlus(int idx) {
		return;
	}

	@Override
	public int setBoardInputOk(BoardVo vo) {
		return boardDao.setBoardInputOk(vo);
	}

	
}
