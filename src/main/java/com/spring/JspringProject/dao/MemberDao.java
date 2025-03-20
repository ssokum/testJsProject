package com.spring.JspringProject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.JspringProject.vo.MemberVo;

public interface MemberDao {

	MemberVo getMemberIdCheck(@Param("mid") String mid);

	int setMemberJoinOk(@Param("vo") MemberVo vo);

	void setMemberInforUpdate(@Param("mid") String mid, @Param("point") int point);

	List<MemberVo> getMemberList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("level") int level);

	void setMemberDeleteCheck(@Param("mid") String mid);

	MemberVo getMemberNickCheck(@Param("nickName") String nickName);

	MemberVo getMemberIdxSearch(@Param("idx") int idx);

	int setMemberPwdChange(@Param("mid") String mid, @Param("pwd") String pwd);

	void setMemberTodayCntClear(@Param("mid") String mid);

	int setMemberUpdateOk(@Param("vo") MemberVo vo);

}
