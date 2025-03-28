package com.spring.JspringProject.service;

import java.util.List;

import com.spring.JspringProject.vo.ComplaintVo;

public interface AdminService {

	int setMemberLevelChange(int level, int idx);

	int getMemberTotRecCnt(int level);

	int setBoardComplaintInput(ComplaintVo vo);

	void setBoardTableComplaintOk(int partIdx);

	String setLevelSelectCheck(String idxSelectArray, int levelSelect);

	List<ComplaintVo> getComplaintList();

	int setContentChange(int contentIdx, String contentSw);

	int setContentDelete(int contentIdx, String part);

	int setComplaintDelete(int partIdx, String part);

	int setComplaintProcess(int partIdx, String complaintSw);

	void setComplaintProcessOk(int idx, String complaintSw);

}
