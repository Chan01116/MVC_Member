<%@page import="mvc.vo.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import = "java.util.*" %>
<%

ArrayList<BoardVo> alist = (ArrayList<BoardVo>)request.getAttribute("alist");

%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link href="../css/style2.css" rel = "stylesheet">

</head>
<body>
	<h3>글목록</h3>
	<hr id = "top">
	<form>
	<div id = "searchBar" style = ""><input type="text" list="countries">
			<datalist id="countries"><option value="제목" selected>제목</datalist>
	<input type = "search" value = "" > <button type = "button" name = "btns" id = "btns" onclick = "check();">검색</button></div>
	<hr id = "mid">
	<table>
		<td id = "NoTop">No</td>
		<td id = "TitleTop">제목</td>
		<td id = "WriterTop">작성자</td>
		<td id = "HitTop">조회</td>
		<td id = "WritedayTop">날짜</td>
	</table>
	<table>
	<%	
		for(BoardVo bv : alist){ %>
	<tr> 
		<td><%=bv.getBidx()%></td>
		<td><%=bv.getSubject()%></td>
		<td><%=bv.getWriter()%></td>
		<td><%=bv.getRecom()%></td>
		<td><%=bv.getWriteday().substring(0, 10) %></td>
	
	</tr>
	<%} %>
	
	
	
	
	</table>
		<button type = "button" name = "btnwrite" id = "btnwrite"> <a href= "<%=request.getContextPath()%>/board/Board_Write.aws">글쓰기</button></a>
		<div><td onclick = "move();">1</td><td onclick = "move();">2</td><td onclick = "move();">3</td><td onclick = "move();">4</td><td onclick = "move();">5</td>
		<td onclick = "move();">6</td><td onclick = "move();">7</td><td onclick = "move();">8</td><td onclick = "move();">9</td><td onclick = "move();">10</td></div>
		
		
	</form>
</body>
</html>