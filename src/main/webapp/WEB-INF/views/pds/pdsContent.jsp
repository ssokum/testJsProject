<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<% pageContext.setAttribute("newLine", "\n"); %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>pdsContent.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <link rel="stylesheet" type="text/css" href="${ctp}/css/linkOrange.css"/>
  <script>
    'use strict';
    
    // 처음 접속시는 '리뷰보이기'버튼이 감춰지고, '리브가리기'버튼과 '리뷰박스'를 보이게 한다.
    $(function(){
    	$("#reviewShowBtn").hide();
    	$("#reviewHideBtn").show();
    	$("#reviewBox").show();
    });
    
    // 리뷰 보이기
    function reviewShow() {
    	$("#reviewShowBtn").hide();
    	$("#reviewHideBtn").show();
    	$("#reviewBox").show();
    }
    
    // 리뷰 가리기
    function reviewHide() {
    	$("#reviewShowBtn").show();
    	$("#reviewHideBtn").hide();
    	$("#reviewBox").hide();
    }
    
    // 다운로드수 증가처리
    function downNumCheck(idx) {
    	$.ajax({
    		url  : "${ctp}/pds/pdsDownNumCheck",
    		type : "post",
    		data : {idx : idx},
    		success:function(res) { location.reload(); },
    		error  :function() { alert("전송오류!!"); }
    	});
    }
    
    // 별점 부여하기
    function reviewCheck() {
    	let star = starForm.star.value;
    	let review =$("#review").val();
    	
    	if(star == "") {
    		alert("별점을 부여하세요");
    		return false;
    	}
    	
    	let query = {
    			part    : 'pds',
    			partIdx : ${vo.idx},
    			mid     : '${sMid}',
    			nickName: '${sNickName}',
    			star    : star,
    			content : review
    	}
    	
    	$.ajax({
    		url  : "${ctp}/review/reviewInputOk",
    		type : "post",
    		data : query,
    		success:function(res) {
    			alert(res);
    			location.reload(); 
    		},
    		error  :function() { alert("전송오류!!"); }
    	});
    }
  </script>
  <style>
    #starForm fieldset {
      direction: rtl;
    }
    #starForm input[type=radio] {
      display: none;
    }
    #starForm label {
      font-size: 1.6em;
      color: transparent;
      text-shadow: 0 0 0 #f0f0f0;
    }
    #starForm label:hover {
      text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);
    }
    #starForm label:hover ~ label {
      text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);
    }
    #starForm input[type=radio]:checked ~ label {
      text-shadow: 0 0 0 rgba(250, 200, 0, 0.98);
    }
    
    #reviewReplyForm {
      font-size: 11pt;
    }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2 class="text-center">자료실 내용 상세보기</h2>
  <br/>
  <table class="table table-bordered text-center border-secondary-subtle">
    <tr>
      <th class="table-secondary">올린이</th><td>${vo.nickName}</td>
      <th class="table-secondary">올린날짜</th><td>${vo.FDate}</td>
    </tr>
    <tr>
      <th class="table-secondary">파일명</th>
      <td>
        <c:set var="fNames" value="${fn:split(vo.FName,'/')}"/>
        <c:set var="fSNames" value="${fn:split(vo.FSName,'/')}"/>
        <c:forEach var="fName" items="${fNames}" varStatus="st">
          <a href="${fSNames[st.index]}" download="${fName}" onclick="downNumCheck(${vo.idx})">${fName}</a><br/>
        </c:forEach>
        (<fmt:formatNumber value="${vo.FSize/1024}" pattern="#,##0" />KByte)
      </td>
      <th class="table-secondary">다운횟수</th><td>${vo.downNum}</td>
    </tr>
    <tr>
      <th class="table-secondary">분류</th><td>${vo.part}</td>
      <th class="table-secondary">접속IP</th><td>${vo.hostIp}</td>
    </tr>
    <tr>
      <th class="table-secondary">제목</th><td colspan="3" class="text-start">${vo.title}</td>
    </tr>
    <tr>
      <th class="table-secondary">상세내역</th><td colspan="3" class="text-start" style="height:250px">${fn:replace(vo.content, newLine, '<br/>')}</td>
    </tr>
  </table>
  <div class="text-center">
    <input type="button" value="돌아가기" onclick="location.href='pdsList?pag=${pag}&pagSize=${pageSize}&part=${part}';" class="btn btn-warning"/>
  </div>
  <hr class="border">
  <div>
    <form name="starForm" id="starForm">
      <fieldset style="border:0px;">
        <div class="text-left viewPoint m-0 b-0">
          <input type="radio" name="star" value="5" id="star1"><label for="star1">★</label>
          <input type="radio" name="star" value="4" id="star2"><label for="star2">★</label>
          <input type="radio" name="star" value="3" id="star3"><label for="star3">★</label>
          <input type="radio" name="star" value="2" id="star4"><label for="star4">★</label>
          <input type="radio" name="star" value="1" id="star5"><label for="star5">★</label>
          : 별점을 선택해 주세요 ■
        </div>
      </fieldset>
      <div class="m-0 p-0">
        <textarea rows="3" name="review" id="review" class="form-control mb-1" placeholder="별점 후기를 남겨주시면 100포인트를 지급합니다."></textarea>
      </div>
      <div>
        <input type="button" value="별점/리뷰등록" onclick="reviewCheck()" class="btn btn-primary btn-sm form-control"/>
      </div>
    </form>
  </div>
  <hr class="border">
  <div class="row mb-3">
    <div class="col">
      <input type="button" value="리뷰보이기" id="reviewShowBtn" onclick="reviewShow()" class="btn btn-success"/>
      <input type="button" value="리뷰가리기" id="reviewHideBtn" onclick="reviewHide()" class="btn btn-warning"/>
    </div>
    <div class="col text-end">
      <b>리뷰평점 : <fmt:formatNumber value="${reviewAvg}" pattern="#,##0.0" /> </b>
    </div>
  </div>
  <div id="reviewBox">
    <c:forEach var="vo" items="${rVos}" varStatus="st">
      <div class="border p-3">
	      <div class="row">
		      <div class="col ms-3">
		        <b>${vo.nickName}</b>
		        <c:if test="${sMid == vo.mid || sLevel == 0}"><a href="javascript:reviewDelete(${vo.idx})" title="리뷰삭제" class="badge bg-danger">x</a></c:if>
		        <a href="#" onclick="" title="댓글달기" data-bs-toggle="modal" data-bs-target="myModalReply" class="badge bg-secondary">▤</a>
		      </div>
		      <div class="col text-end me-3">
		        <c:forEach var="i" begin="1" end="${vo.star}" varStatus="st">
		          <font color="gold">★</font>
		        </c:forEach>
		        <c:forEach var="i" begin="1" end="${5 - vo.star}" varStatus="st">
		          <font>☆</font>
		        </c:forEach>
		      </div>
	      </div>
	      <div class="row">
	        <div class="col ms-3">${fn:replace(vo.content, newLine, '<br/>')}</div>
	      </div>
      </div>
      <br/>
      <!-- 리뷰에 댓글달기 -->
      <c:if test="${!empty reVos}">
        <div class="d-flex text-secondary">
          <div class="mt-2 ms-3">└─▶ </div>
          <div class="mt-2 ms-2">${reVos.replyNickName}
            <span style="font-size:11px">${fn:substring(vo.replyRDate,0,10)}</span>
            <c:if test="${reVos.replyMid == sMid || sLevel == 0}"><a href="#" title="댓글삭제" class="badge badge-danger">x</a></c:if>
            <br/>${reVos.replyContent}
          </div>
        </div>
      </c:if>
      <br/><br/>
    </c:forEach>
  </div>
  <hr class="border">
  <!-- 사진이 있으면 보여주기 -->
  <div class="text-center">
    <c:forEach var="fSName" items="${fSNames}" varStatus="st">
      <br/>${fNames[st.index]}<br/>
      <c:set var="ext" value="${fn:toLowerCase(fn:substring(fSName,fn:length(fSName)-3, fn:length(fSName)))}"/>
      <c:if test="${ext == 'jpg' || ext == 'gif' || ext == 'png'}">
        <img src="${ctp}/pds/${fSName}" width="85%"/><br/>
      </c:if>
    </c:forEach>
    <hr class="border">
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>