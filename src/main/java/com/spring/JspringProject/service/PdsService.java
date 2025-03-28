package com.spring.JspringProject.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.JspringProject.vo.PdsVo;

public interface PdsService {

	List<PdsVo> getPdsList(int startIndexNo, int pageSize, String part, String search, String searchString);

	int setPdsInput(MultipartHttpServletRequest mFName, PdsVo vo);

	int setPdsDelete(int idx, String fSName, HttpServletRequest request);

	int setPdsDownNumPlus(int idx);

	PdsVo getPdsContent(int idx);

	String pdsTotalDown(HttpServletRequest request, int idx);

}
