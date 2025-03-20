package com.spring.JspringProject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.JspringProject.vo.UserVo;

public interface User2Dao {

	UserVo getUserIdSearch(@Param("mid") String mid);

	//int setUserInput(@Param("vo") UserVo vo);
	int setUserInput(UserVo vo);

	UserVo getUserSearchPart(@Param("vo") UserVo vo);

	List<UserVo> getUserList();

	int setUserDeleteOk(@Param("idx") int idx);

	UserVo getSearchIdx(@Param("idx") int idx);

	//int setUserUpdate(@Param("vo") UserVo vo);
	int setUserUpdate(UserVo vo);

	int getUserCount();

	List<UserVo> getUserOrderList(@Param("order") String order);

}
