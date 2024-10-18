<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글내용</title>
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
	<div><th id = "contentsTitle">장애학생들을 위한 특별한 피아노</th> <td id = "contentsTitle">(조회수 :  <%-- <%= %> --%>)</td><br>
	<%-- <td><%= %> <%= %></td> --%>
	</div>
	<hr id = "mid">
	<%-- <div><%= %></div> --%>
	<hr id = "mid">
	<img alt="" src="" onclick = "upload();">
	<hr id = "battom">
	<div> <button type = "button" id = "contentsBtn">수정</button><button type = "button" id = "contentsBtn">삭제</button><button type = "button" id = "contentsBtn">답변</button><button type = "button" id = "contentsBtn">목록</button></div>
	
	<div>admin</div>
	<div><input type = "text" name ="reply"> <a href = "" id = "reply" onclick = "check();">댓글쓰기</a></div>






</body>
</html>