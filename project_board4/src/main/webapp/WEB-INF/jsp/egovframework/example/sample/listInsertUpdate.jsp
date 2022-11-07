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

<c:set var="registerFlag" value="${sampleVO.code == 0 ? 'create' : 'modify'}" />
<title>
	<c:if test="${registerFlag == 'create'}">
		<spring:message code="button.create" />
	</c:if> <c:if test="${registerFlag == 'modify'}">
		<spring:message code="button.modify" />
	</c:if>
</title>

</head>
<script type="text/javaScript" language="javascript" defer="defer">
	// 파일 관련 function
	$(document).ready (function () {                
		$('.btnAdd').click (function () {                                        
    		$('.buttons').append (                        
        		'<input type="file" name="multiFile" required> <input type="button" class="btnRemove" value="삭제"><br>'                    
    		); // end append                            
    		$('.btnRemove').on('click', function () { 
       	 		$(this).prev().remove (); // remove the textbox
       	 		$(this).next ().remove (); // remove the <br>
       	 		$(this).remove (); // remove the button
    		});
		}); // end click
		$('.fileRemove').on('click', function () { 
			
			var id_check = $(this).attr("id");
			/* location.href = 'deleteFile.do?fileNo=' + id_check; */
   	 		$(this).prev().remove (); // remove the textbox
   	 		$(this).next ().remove (); // remove the <br>
   	 		$(this).remove (); // remove the button
   	 		   	 		
   	 		$('[id=listForm] #fileNo').val(id_check);
   	 		$('#listForm').attr('action', "${pageContext.request.contextPath}/deleteFile.do");
			$('#listForm')[0].submit();
		});
	}); // end ready
	
	// 등록
	function insert() {
		document.listForm.action = "<c:url value='/insert_data.do'/>";
		document.listForm.submit();
	}	
	// 수정
	function update() {
		document.listForm.action = "<c:url value='/update_data.do'/>";
		document.listForm.submit();
	}	
	// 취소 버튼
	$(document).ready(function(){
		$('#btn_close').click(function () {
			<c:if test="${registerFlag == 'create'}"> // 글 등록 상태이면 메인화면으로 
				$('#listForm').attr('action', "${pageContext.request.contextPath}/listUp.do");
				$('#listForm')[0].submit();
			</c:if>
			<c:if test="${registerFlag == 'modify'}"> // 글 수정 상태이면 상세화면으로
				$('#listForm').attr('action', "${pageContext.request.contextPath}/listDetail.do");
				$('#listForm')[0].submit();
			</c:if>			
	    });
    });
</script>
<body>
	${inputFormData} <br>
	${sampleVO } <br>
		
	<form:form commandName="sampleVO" id="listForm" name="listForm"
		method="post" enctype="multipart/form-data">
		
		<input type="hidden" id="code" name="code" value="${inputFormData.code}">
		<input type="hidden" id="searchKeyword" name="searchKeyword" value="${inputFormData.searchKeyword}">
		<input type="hidden" id="searchCondition" name="searchCondition" value="${inputFormData.searchCondition}">
		<input type="hidden" id="sort" name="sort" value="${inputFormData.sort}">
		<input type="hidden" id="pageIndex" name="pageIndex" value="${inputFormData.pageIndex}">
		
		
		<input type="hidden" id="fileNo" name="fileNo" value="${inputFormData.fileNo}">
		
		<div class="container">
			<div id="title">
				<h3>
					<c:if test="${registerFlag == 'create'}">
						<spring:message code="button.create" />
					</c:if>
					<c:if test="${registerFlag == 'modify'}">
						<spring:message code="button.modify" />
					</c:if>
				</h3>
			</div>

			<div class="panel panel-default">
				<div class="panel-body">
					<div class="form-group">
						<label class="control-label col-sm-2" for="title">제목 :</label>
						<div class="col-sm-10">

							<form:input path="title" maxlength="30" class="form-control"/>
							&nbsp;
							<form:errors path="title" />
							
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="name">작성자 :</label>
						<div class="col-sm-10">
							<form:input path="name" maxlength="30" class="form-control"/>
							&nbsp;
							<form:errors path="name" />						
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="content">내용:</label>
						<div class="col-sm-10">
							<form:textarea path="content" rows="5" cols="58" class="form-control"/>
							&nbsp;
							<form:errors path="content" />							
						</div>
					</div>					
					
					<div class="form-group">
						<label class="control-label col-sm-2">첨부파일 :</label>
						<div class="col-sm-10">
							<c:forEach var="result" items="${fileList}" varStatus="status">
								<input type="text" value="${result.originFileName}" readonly>
								<input type="button" class="fileRemove" id="${result.fileNo }" value="삭제"> <br>
							</c:forEach>
							<input type="button" class="btnAdd" value="파일 추가" style="float: right">							
							<div class="buttons">
								<!-- 파일 추가 클릭시 새로운 input 추가할 위치 -->
							</div>
						</div>
					</div>
				</div>
				<div class="panel-footer" align="right">
					<c:if test="${registerFlag == 'create'}">
						<button type="button" onclick="javascript:insert();">등록</button>											
					</c:if>
					<c:if test="${registerFlag == 'modify'}">
						<button type="button" onclick="javascript:update();">수정</button>										
					</c:if>					
					<button type="button" id="btn_close" name="btn_close">취소</button>
				</div>

			</div>
		</div>
	</form:form>

</body>
</html>