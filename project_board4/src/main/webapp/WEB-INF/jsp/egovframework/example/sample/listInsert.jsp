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
<title>글 등록</title>
</head>
<script type="text/javaScript" language="javascript" defer="defer">
	$(document).ready(function() {
		$('.btnAdd').click(function() {
			$('.buttons').append(
				'<input type="file" name="multiFile" required> <input type="button" class="btnRemove" value="삭제"><br>'
			); // end append
			$('.btnRemove').on('click',function() {
				$(this).prev().remove(); // remove the textbox
				$(this).next().remove(); // remove the <br>
				$(this).remove(); // remove the button
			});
		}); // end click
	}); // end ready
</script>
<body>

	<form:form commandName="sampleVO" id="listForm" name="listForm"
		method="post" action="insert_data.do" enctype="multipart/form-data">

		<div class="container">
			<h1>글 등록</h1>
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="form-group">
						<label class="control-label col-sm-2" for="title">제목 :</label>
						<div class="col-sm-10">
							<input type="text" name="title" id="title" class="form-control"
								required>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="name">작성자 :</label>
						<div class="col-sm-10">
							<input type="text" name="name" id="name" class="form-control"
								required>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="content">내용:</label>
						<div class="col-sm-10">
							<textarea class="form-control" rows="5" id="content"
								name="content" maxlength="2000" required></textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2">첨부파일 :</label>
						<div class="col-sm-10">
							<input type="button" class="btnAdd" value="파일 추가"
								style="float: right">
							<div class="buttons">
								<!-- 파일 추가 클릭시 새로운 input 추가할 위치 -->
							</div>
						</div>
					</div>
				</div>
				<div class="panel-footer" align="right">
					<button type="submit">등록</button>
					<button type="button"
						onclick="location.href='http://localhost:8080/project_board4/listUp.do'">취소</button>
				</div>

			</div>
		</div>
	</form:form>

</body>
</html>