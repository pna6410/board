<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="<c:url value='/css/bootstrap/css/bootstrap.min.css'/>">
<script src="<c:url value='/js/jquery-3.6.1.min.js'/>"></script>
<script src="<c:url value='/css/bootstrap/js/bootstrap.min.js'/>"></script>
<title>로그인</title>
<script>
    $(document).ready(function(){
        $("#btnLogin").click(function(){
            // 태크.val() : 태그에 입력된 값
            // 태크.val("값") : 태그의 값을 변경 
            var userId = $("#userId").val();
            var userPw = $("#userPw").val();
            if(userId == ""){
                alert("아이디를 입력하세요.");
                $("#userId").focus(); // 입력포커스 이동
                return; // 함수 종료
            }
            if(userPw == ""){
                alert("비밀번호를 입력하세요.");
                $("#userPw").focus();
                return;
            }
            // 폼 내부의 데이터를 전송할 주소
            document.listForm.action="<c:url value='/loginCheck.do'/>"
            // 제출
            document.listForm.submit();
        });
    });
    
 	// 취소 버튼(메인 화면으로 돌아가는 버튼)
	$(document).ready(function(){
		$('#btn_close').click(function () {
			$('#listForm').attr('action', "${pageContext.request.contextPath}/listUp.do");
			$('#listForm')[0].submit();
	    });
    });
</script>
</head>
<body>
${inputFormData }
	<form:form commandName="searchVO" id="listForm" name="listForm"	method="post">
	
		<input type="hidden" id="searchKeyword" name="searchKeyword" value="${inputFormData.searchKeyword}">
		<input type="hidden" id="searchCondition" name="searchCondition" value="${inputFormData.searchCondition}">
		<input type="hidden" id="sort" name="sort" value="${inputFormData.sort}">
		<input type="hidden" id="pageIndex" name="pageIndex" value="${inputFormData.pageIndex}">
	
	
		<c:if test="${msg == 'success' }">
			<div style="color: blue">
				회원가입에 성공했습니다.
			</div>
		</c:if>

		<h2>로그인</h2>		
			<table border="1">
				<tr>
					<td>아이디</td>
					<td><input name="userId" id="userId"></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="userPw" id="userPw"></td>					
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button type="button" id="btnLogin">로그인</button>
						<c:if test="${msg == 'failure' }">
							<div style="color: red">
								아이디 또는 비밀번호가 일치하지 않습니다.
							</div>
						</c:if>
						<%-- <c:if test="${msg == 'logout' }">
							<div style="color: red">
								로그아웃되었습니다.
							</div>
						</c:if> --%>
					</td>
				</tr>				
			</table>
			<button type="button" id="btn_close" name="btn_close">목록</button>
	</form:form>
</body>
</html>