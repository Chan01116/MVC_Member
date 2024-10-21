<%@page import="mvc.vo.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import = "java.util.*" %>
<%

ArrayList<BoardVo> alist = (ArrayList<BoardVo>)request.getAttribute("alist");
//System.out.println("alist ==>" +alist);
PageMaker pm = (PageMaker)request.getAttribute("pm");




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
		<div class ="page">
		<ul>
		<%if(pm.isPrev()== true){ %>
		<li><a href = "/board/board_List.aws?page=<%=pm.getStartPage()-1%>">◀</a></li>
		<%} %>
		
		
		<%for(int i = pm.getStartPage(); i <= pm.getEndPage(); i++){ %>
		<li <%if(i ==pm.getCri().getPage()){ %>class = "on"<%} %>> <a href = "/board/board_List.aws?page=<%=i%>"><%=i %></a></li>
		<%} %>
		<%if(pm.isNext()==true&&pm.getEndPage()>0){ %>
		<li><a href = "/board/board_List.aws?page=<%=pm.getEndPage()+1%>">▶</a></li>
		<%} %>
		</ul>
		</div>
	</form>
</body>
</html>