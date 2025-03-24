package com.spring.JspringProject.vo;

import lombok.Data;

//@Getter
//@Setter
//@ToString
@Data
public class BoardVo {
	private int idx;
	private String mid;
	private String nickName;
	private String title;
	private String content;
	private String hostIp;
	private String openSw;
	private String readNum;
	private String wDate;
	private String good;
	private String complaint;
	
	private int dateDiff;	//게시글 일자 경과 유무 체크
	private int hourDiff;	//게시글 24시간 경과 유무 체크
}
