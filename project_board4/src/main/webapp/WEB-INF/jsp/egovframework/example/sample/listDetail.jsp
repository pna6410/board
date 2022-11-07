<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet"
	href="<c:url value='/css/bootstrap/css/bootstrap.min.css'/>">
<script src="<c:url value='/js/jquery-3.6.1.min.js'/>"></script>
<script src="<c:url value='/css/bootstrap/js/bootstrap.min.js'/>"></script>
<title>상세 화면</title>
</head>
<script type="text/javaScript" language="javascript" defer="defer">
	/* function data_update(){
		document.listForm.action = "<c:url value='/listUpdate.do?code=${resultList[0].code}'/>";
		document.listForm.submit();
	} */
	
	/* function data_delete(){
		document.listForm.action = "<c:url value='/delete_data.do?code=${resultList[0].code}'/>";
		document.listForm.submit();
	} */
	
	$(document).ready (function () {
		$('.replyRemove').on('click', function () {			
			var id_check = $(this).attr("id");
   	 		//location.href = 'deleteReply.do?replyNo=' + id_check;
   	 		
   	 		$('[id=listForm] #replyNo').val(id_check);
	 		$('#listForm').attr('action', "${pageContext.request.contextPath}/deleteReply.do");
			$('#listForm')[0].submit();
		});
	});
	
	// 수정 버튼
	function data_update() {		
		$('#listForm').attr('action', "${pageContext.request.contextPath}/listUpdate.do");		
		$('#listForm')[0].submit();
	}
	// 삭제 버튼
	function data_delete() {		
		$('#listForm').attr('action', "${pageContext.request.contextPath}/delete_data.do");		
		$('#listForm')[0].submit();
	}	
	
	// 취소 버튼(메인 화면으로 돌아가는 버튼)
	$(document).ready(function(){
		$('#btn_close').click(function () {
			$('#listForm').attr('action', "${pageContext.request.contextPath}/listUp.do");
			$('#listForm')[0].submit();
	    });
    });
	
</script>
<body>


${resultList} <br>
${inputFormData } <br>
${inputFormData.searchKeyword} <br>
${inputFormData.pageIndex}
	<form:form commandName="sampleVO" id="listForm" name="listForm"
		method="post" action="addReply.do">
				
		<input type="hidden" id="code" name="code" value="${inputFormData.code}">
		<input type="hidden" id="searchKeyword" name="searchKeyword" value="${inputFormData.searchKeyword}">
		<input type="hidden" id="searchCondition" name="searchCondition" value="${inputFormData.searchCondition}">
		<input type="hidden" id="sort" name="sort" value="${inputFormData.sort}">
		<input type="hidden" id="pageIndex" name="pageIndex" value="${inputFormData.pageIndex}">
		
		<input type="hidden" id="fileNo" name="fileNo" value="${inputFormData.fileNo}">	
		<input type="hidden" id="replyNo" name="replyNo" value="${inputFormData.replyNo}">

		<div class="container">
			<h1>상세 화면</h1>
			<div class="panel panel-default">
				<div class="panel-body">
					<table class="table table-hover">
						<thead>
							<tr>
								<th align="center">코드</th>
								<th align="center">제목</th>
								<th align="center">작성자</th>
								<th align="center">작성일</th>
								<th align="center">내용</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${resultList}" varStatus="status">
								<tr>
									<td align="center" class="listtd" style="word-break:break-all; width:10%"><c:out
											value="${result.code}" />&nbsp;</td>
									<td align="center" class="listtd" style="word-break:break-all; width:10%"><c:out
											value="${result.title}" />&nbsp;</td>
									<td align="center" class="listtd" style="word-break:break-all; width:10%"><c:out
											value="${result.name}" />&nbsp;</td>
									<td align="center" class="listtd" style="word-break:break-all; width:10%"><c:out
											value="${result.createDate}" />&nbsp;</td>
									<td align="center" class="listtd" style="word-break:break-all; width:50%"><c:out
											value="${result.content}" />&nbsp;</td>
								</tr>
							</c:forEach>							
						</tbody>
						
						<tr>
							<th align="center">첨부파일</th>
						</tr>
						<c:forEach var="result" items="${fileList}" varStatus="status">
							<td align="center" class="listtd"><a
									href="downloadFile.do?fileNo=<c:out value='${result.fileNo}'/>"><c:out
											value="${result.originFileName}" />&nbsp;</a></td>
						</c:forEach>						
					</table>
					
					
					<form >
					<table  class="table table-hover">
						<thead>
							<tr>
								<th align="center">댓글</th>								
							</tr>						
						</thead>
						
						<tbody>
							<tr>
								<td>								
									<div>
										<c:choose>
										<c:when test="${sessionScope.userId == null}">
											<label style="float: left;">작성자 : &nbsp;</label>
						    				<input type="text" name="replyWriter" id="replyWriter" style="float: left;">											
								    	</c:when>
					    				<c:otherwise>
					    					<input type="hidden" name="replyWriter" id="replyWriter" style="float: left;" value="${sessionScope.userName}">
					    				</c:otherwise>
					    				</c:choose>
										<br>
										<textarea name="replyContent" id="replyContent" style="float: center; width: 100%;" required></textarea>
										<br>
										<button type="submit" style="float: right;">댓글 작성</button>
									</div>
								</td>
							</tr>						
							<c:forEach var="result" items="${replyList}" varStatus="status">
								<tr>
								<td align="center" class="listtd"  style="float: left;">
									<c:out value="${result.replyWriter}" />&nbsp; 
									<c:out value="${result.replyDate}" />&nbsp; <br>
									<c:out value="${result.replyContent}" />&nbsp;
									<input type="button" class="replyRemove" id="${result.replyNo }" style="text-align:right"value="삭제"> 
									<br>
								</td>
								</tr>
							</c:forEach>						
						</tbody>
					</table>
					</form>
				</div>

				<div class="panel-footer"  align="center">				
					<button type="button" onclick="javascript:data_update();">수정</button>
					<button type="button" onclick="javascript:data_delete();">삭제</button>					
					
					<button type="button" id="btn_close" name="btn_close">목록</button>					
				</div>
				
			</div>
		</div>
	</form:form>
</body>
</html>