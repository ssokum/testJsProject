package com.spring.JspringProject.dao;

import java.util.List;

import com.spring.JspringProject.vo.UserVo;


// 사용하지 않았음... ServiceImpl에서 바로 UserDaoImpl객체를 참조하고 있다.
public interface UserDao {

	UserVo getUserIdSearch(String mid);

	int setUserInput(UserVo vo);

	UserVo getUserSearchPart(UserVo vo);

	List<UserVo> getUserList();

	int setUserDeleteOk(int idx);

	UserVo getSearchIdx(int idx);

	int setUserUpdate(UserVo vo);

	
}
