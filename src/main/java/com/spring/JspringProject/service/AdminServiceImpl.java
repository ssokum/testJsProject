package com.spring.JspringProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.JspringProject.dao.AdminDao;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDao adminDao;

	@Override
	public int setMemberLevelChange(int level, int idx) {
		return adminDao.setMemberLevelChange(level, idx);
	}

	@Override
	public int getMemberTotRecCnt(int level) {
		return adminDao.getMemberTotRecCnt(level);
	}
	
}
