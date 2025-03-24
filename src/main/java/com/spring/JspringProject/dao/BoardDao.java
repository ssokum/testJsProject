package com.spring.JspringProject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.JspringProject.vo.BoardReplyVo;
import com.spring.JspringProject.vo.BoardVo;

public interface BoardDao {

	int getBoardTotRecCnt();

	List<BoardVo> getBoardList(@Param("startIndexNo") int startIndexNo, 
			@Param("pageSize") int pageSize, @Param("search") String search, @Param("searchString") String searchString);

	BoardVo getBoardContent(@Param("idx") int idx);

	void setBoardReadNumPlus(@Param("idx") int idx);

	int setBoardInputOk(@Param("vo") BoardVo vo);

	int setboardDelete(@Param("idx") int idx);

	int setBoardUpdate(@Param("vo") BoardVo vo);

	int getBoardTotRecCntSeach(@Param("search") String search, @Param("searchString") String searchString);

	int setBoardGoodCheck1(@Param("idx") int idx);

	int setBoardGoodCheck2(@Param("idx") int idx, @Param("goodCnt") int goodCnt);

	BoardVo getPrevNextSearch(@Param("idx") int idx, @Param("prevNext")  String prevNext);

	List<BoardReplyVo> getBoardReply(@Param("idx") int idx);

	int setBoardReplyInput(@Param("vo") BoardReplyVo vo);

}
