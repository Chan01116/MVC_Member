<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@page import="mvc.vo.BoardVo" %>
    
    <%
    BoardVo bv = (BoardVo)request.getAttribute("bv");  // 강제형변환 양쪽형을 맞춰준다
    
    %>
    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글내용</title>
<link href = "../css/style2.css" rel = "stylesheet">

<script>
function check(){
	  
	  let reply = document.getElementsByName("reply");
	
	
	  
	  if(reply[0].value == ""){
		  alert("댓글내용을 입력해주세요");
		  reply.focus();
		  return;
	 	
	  }
	  var fm = document.bd;
	 
	  fm.method = "post";
	  fm.submit();
	  return;
}



</script>
</head>
<body name = "bd">
	<h3>글내용</h3>
	<hr id = "top">
	<div><th id = "contentsTitle"><%=bv.getSubject() %>(조회수:<%=bv.getViewcnt() %>)</th> <br>
		</div>
		<div>
		<%=bv.getContents()%>
		</div>
		
		
	<hr id = "mid">
	<hr id = "mid">
	<%if(bv.getFilename()!=null){ %>
	<img alt="" src="" onclick = "upload();">
	첨부파일입니다
	<%} %>
	<hr id = "battom">
	<div> <button type = "button" id = "contentsBtn" > <a href = "<%=request.getContextPath() %>/board/Board_Modify.aws?bidx=<%=bv.getBidx()%>">수정</a></button><button type = "button" id = "contentsBtn">삭제</button><button type = "button" id = "contentsBtn">답변</button><button type = "button" id = "contentsBtn">목록</button></div>
	
	<div>admin</div>
	<div><input type = "text" name ="reply"> <a href = "" id = "reply" onclick = "check();">댓글쓰기</a></div>






</body>
</html>