package com.spring.JspringProject.service;

import java.util.List;

import com.spring.JspringProject.vo.UserVo;

public interface User2Service {

	UserVo getUserIdSearch(String mid);

	int setUserInput(UserVo vo);

	UserVo getUserSearchPart(UserVo vo);

	List<UserVo> getUserList();

	int setUserDeleteOk(int idx);

	UserVo getSearchIdx(int idx);

	int setUserUpdate(UserVo vo);

	int getUserCount();

	List<UserVo> getUserOrderList(String order);

}
