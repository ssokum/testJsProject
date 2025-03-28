package com.spring.JspringProject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.JspringProject.vo.ComplaintVo;

public interface AdminDao {

	int setMemberLevelChange(@Param("level") int level, @Param("idx") int idx);

	int getMemberTotRecCnt(@Param("level") int level);

	int setBoardComplaintInput(@Param("vo") ComplaintVo vo);

	void setBoardTableComplaintOk(@Param("partIdx") int partIdx);

	void setMemberLevelCheck(@Param("idx") int idx, @Param("level") int levelSelect);

	List<ComplaintVo> getComplaintList();

	int setContentChange(@Param("contentIdx") int contentIdx, @Param("contentSw") String contentSw);

	int setContentDelete(@Param("contentIdx") int contentIdx, @Param("part") String part);

	int setComplaintDelete(@Param("partIdx") int partIdx);

	int setComplaintProcess(@Param("partIdx") int partIdx, @Param("complaintSw") String complaintSw);

	void setComplaintProcessOk(@Param("idx") int idx, @Param("complaintSw") String complaintSw);

}
