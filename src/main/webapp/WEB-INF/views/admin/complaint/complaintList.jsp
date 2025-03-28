<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("CRLF", "\r\n"); %>
<% pageContext.setAttribute("LF", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>complaintList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
    'use strict';
    
    /*
    // 신고글 감추기
    function contentHide(contentIdx) {
    	let ans = confirm("해당 게시글을 감출까요?");
    	if(!ans) return false;
    	
    	$.ajax({
    		url  : "${ctp}/admin/complaint/contentChange",
    		type : "post",
    		data : {
    			contentIdx : contentIdx,
    			contentSw  : 'H',
    		},
    		success:function(res) {
    			if(res != "0") {
    				alert("해당 게시글을 화면에서 감추었습니다.");
    				location.reload();
    			}
    			else alert("작업 실패~~");
    		},
    		error : function() { alert("전송오류"); }
    	});
    }
    
    // 신고글 보이기
    function contentShow(contentIdx) {
    	let ans = confirm("해당 게시글을 보이게 할까요?");
    	if(!ans) return false;
    	
    	$.ajax({
    		url  : "${ctp}/admin/complaint/contentChange",
    		type : "post",
    		data : {
    			contentIdx : contentIdx,
    			contentSw  : 'S',
    		},
    		success:function(res) {
    			if(res != "0") {
    				alert("해당 게시글을 화면에서 다시 볼수 있습니다.");
    				location.reload();
    			}
    			else alert("작업 실패~~");
    		},
    		error : function() { alert("전송오류"); }
    	});
    }
    
    // 신고글 삭제하기
    function contentDelete(contentIdx, part) {
    	let ans = confirm("해당 게시글을 삭제 할까요?");
    	if(!ans) return false;
    	
    	$.ajax({
    		url  : "${ctp}/admin/complaint/contentDelete",
    		type : "post",
    		data : {
    			contentIdx : contentIdx,
    			part  : part
    		},
    		success:function(res) {
    			if(res != "0") {
    				alert("해당 게시글이 삭제 되었습니다.");
    				location.reload();
    			}
    			else alert("작업 실패~~");
    		},
    		error : function() { alert("전송오류"); }
    	});
    }
    */
    
  	// 신고 '보이기(해제)하기/신고 가리기/삭제하기'
  	function complaintProcess(idx, part, partIdx, complaintSw) {
  		if(complaintSw == 'S') {
  			let ans = confirm("현 게시물의 신고를 해제 하시겠습니까?");
	  		if(!ans) return false;
  		}
  		else if(complaintSw == 'H') {
  			let ans = confirm("현 게시물을 감출까요?");
	  		if(!ans) return false;
  		}
  		else if(complaintSw == 'D') {
  			let ans = confirm("현 게시물을 삭제할까요?");
	  		if(!ans) return false;
  		}
  		
  		let query = {
  			idx      : idx,
  			partIdx  : partIdx,
  			part  	 : part,
  			complaintSw : complaintSw
  		}
  		
  		$.ajax({
    		url  : "complaintProcess",
    		type : "post",
    		data : query,
    		success:function(res) {
    			if(res != "0") {
    				alert("처리 완료되었습니다.")
    				location.reload();
    			}
    			else alert("처리실패~");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
  	}
    
    // 모달을 통한 신상정보 출력
  	//function modalCheck(title, mid, nickName,content) {
  	function modalCheck(title, mid, nickName) {
  		$("#myModalInfor #modalTitle").text(title);
  		$("#myModalInfor #modalMid").text(mid);
  		$("#myModalInfor #modalNickName").text(nickName);
  		//$("#myModalInfor #modalContent").text(content);
  	}
  </script>
  <style>
    a {text-decoration: none}
    a:hover {
      text-decoration: underline;
      color: orange;
    }
  </style>
</head>
<body>
<p><br/></p>
<div class="container">
  <h2 class="text-center">신 고 리 스 트</h2>
  <table class="table table-hover text-center">
    <tr class="table-secondary">
      <th>번호</th>
      <th>분류</th>
      <th>글제목</th>
      <th>글쓴이</th>
      <th>신고자</th>
      <th>신고내역</th>
      <th>신고날짜</th>
      <th>표시여부</th>
    </tr>
    <c:set var="complaintCnt" value="${fn:length(vos)}"/>
    <c:forEach var="vo" items="${vos}" varStatus="st">
      <tr>
	    	<td>${complaintCnt}</td>
	    	<td>${vo.part}</td>
	    	<td>
	    	  <c:if test="${!empty vo.content}">
	      		<%-- <c:set var="content" value="${fn:replace(fn:replace(vo.content, '<', '&lt;'), '>', '&gt;')}" /> --%>
	      		<%-- <c:set var="content" value="${fn:replace(fn:replace(vo.content, CRLF, '<br/>'), LF, '<br/>')}" /> --%>
		    	  <%-- <a href="#" onclick="modalCheck('${vo.title}','${vo.mid}','${vo.nickName}','${content}')" data-bs-toggle="modal" data-bs-target="#myModalInfor">${vo.title}</a> --%>
		    	  <%-- 
		    	  <c:set var="content" value="${fn:escapeXml(vo.content)}"/>
		    	  <a href="#" onclick="modalCheck('${vo.title}','${vo.mid}','${vo.nickName}','${fn:escapeXml(content)}')" data-bs-toggle="modal" data-bs-target="#myModalInfor">${vo.title}</a>
		    	   --%>
		    	  <a href="#" onclick="modalCheck('${vo.title}','${vo.mid}','${vo.nickName}')" data-bs-toggle="modal" data-bs-target="#myModalInfor">${vo.title}</a>
	    	  </c:if>
	    	</td>
	    	<td>${vo.nickName}</td>
	    	<td>${vo.cpMid}</td>
	    	<td>${vo.cpContent}</td>
	    	<td>${vo.cpDate}</td>
	    	<td>
	    	  <%-- 
	    	  <c:if test="${vo.complaint == 'NO'}"><a href="#" data-bs-toggle="modal" data-bs-target="#myModal" class="badge bg-success">처리완료</a></c:if>
	    	  <c:if test="${vo.complaint != 'NO'}">
		    	  <c:if test="${vo.complaint == 'OK'}"><a href="javascript:contentHide(${vo.boardIdx})" class="badge bg-warning">감추기</a></c:if>
		    	  <c:if test="${vo.complaint == 'HI'}"><a href="javascript:contentShow(${vo.boardIdx})" class="badge bg-primary">보이기</a></c:if>
		    	  <c:if test="${vo.part == 'board'}"><a href="javascript:contentDelete('${vo.boardIdx}','${vo.part}')" class="badge bg-danger">삭제</a></c:if>
		    	  <c:if test="${vo.part == 'pds'}"><a href="javascript:contentDelete('${vo.pdsIdx}','${vo.part}')" class="badge bg-danger">삭제</a></c:if>
	    	  </c:if>
	    	  --%>
	        <c:if test="${vo.progress == '신고접수'}">
		        <a href="javascript:complaintProcess('${vo.idx}','${vo.part}','${vo.partIdx}','S')" class="badge bg-primary">신고해제</a>
		        <a href="javascript:complaintProcess('${vo.idx}','${vo.part}','${vo.partIdx}','H')" class="badge bg-warning">${vo.complaint == 'NO' ? '보이기' : '<font color=white>감추기</font>'}</a><br/>
		        <a href="javascript:complaintProcess('${vo.idx}','${vo.part}','${vo.partIdx}','D')" class="badge bg-danger">삭제</a>
	        </c:if>
	        <c:if test="${vo.progress != '신고접수'}">
	          <span class="badge bg-success">${vo.progress}</span>
	        </c:if>
	    	</td>
    	</tr>
    	<c:set var="complaintCnt" value="${complaintCnt - 1}"/>
    </c:forEach>
  </table>
</div>
<p><br/></p>

<div class="modal fade" id="myModal">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">처리 완료</h4>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body">
        해당게시글은 문제가 없기에 다시 화면에 표시되었습니다.
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<!-- 모달에 회원정보 출력하기 -->
<div class="modal fade" id="myModalInfor">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
    
      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">작성자 정보</h4>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      
      <!-- Modal body -->
      <div class="modal-body">
        작성자 아이디${vo.mid} : <span id="modalMid"></span><hr class="border border-secondary">
        글제목 : <span id="modalTitle"></span><br/>
        글내용 : <span id="modalContent"></span><hr class="border border-secondary">
        작성자 닉네임 : <span id="modalNickName"></span><br/>
      </div>
      
      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

</body>
</html>