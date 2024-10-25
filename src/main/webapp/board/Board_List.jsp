<%@page import="mvc.vo.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import = "java.util.*" %>
<%
ArrayList<BoardVo> alist = (ArrayList<BoardVo>)request.getAttribute("alist");
//System.out.println("alist ==>" +alist);
PageMaker pm = (PageMaker)request.getAttribute("pm");


int totalCount = pm.getTotalCount();



String keyword = pm.getScri().getKeyword();
String searchType = pm.getScri().getSearchType();

String param = "keyword="+keyword+"&searchType="+searchType+"";
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
	<form class ="search" name ="frm" action = "<%=request.getContextPath() %>/board/Baord_List.aws" method = "get">
	<select name ="searchType">
	<option value = "subject">제목</option>
	<option value = "writer">작성자</option>
	</select>
		<input type = "text" name = "keyword" > <button  type="submit" class="btn">검색</button>
	<hr id = "mid">
	<table>
		<td id = "NoTop">No</td>
		<td id = "TitleTop">제목</td>
		<td id = "WriterTop">작성자</td>
		<td id = "HitTop">조회</td>
		<td id = "ViewRecom">추천</td>
		<td id = "WritedayTop">날짜</td>
	</table>
	<table>
	<%	
	
		int num = totalCount - (pm.getScri().getPage()-1)*pm.getScri().getPerPageNum();  // 이거 잘모르겠음
		
		for(BoardVo bv : alist){ 
			
			String lvlStr = ""; 
			for(int i=1; i<=bv.getLevel_();i++){
				lvlStr = lvlStr + "&nbsp;&nbsp;"; 
				if(i==bv.getLevel_()){
					lvlStr = lvlStr+"ㄴ";
					
				}
			}
		
		
		%>
	<tr> 
		<td><%=num%></td>
		<td class = "title">
		<%=lvlStr %>
		<a href = "<%=request.getContextPath() %> /board/Board_Contents.aws?bidx=<%=bv.getBidx()%>"><%=bv.getSubject()%></a></td>
		<td><%=bv.getWriter()%></td>
		<td><%=bv.getViewcnt()%></td>
		<td><%=bv.getRecom()%></td>
		<td><%=bv.getWriteday().substring(0, 10) %></td>
	
	</tr>
	<%
	
		num = num-1;
		
		
		}
		
		
		%>
	
	
	
	
	</table>
		<button type = "button" name = "btnwrite" id = "btnwrite"> <a href = "<%=request.getContextPath()%>/board/Board_Write.aws">글쓰기</a></button>
		<div class ="page">
		<ul>
		<%if(pm.isPrev()== true){ %>
		<li><a href = "/board/Board_List.aws?page=<%=pm.getStartPage()-1%>&<%=param%>">◀</a></li>
		<%} %>
		
		
		<%for(int i = pm.getStartPage(); i <= pm.getEndPage(); i++){ %>
		<li <%if(i ==pm.getScri().getPage()){ %>class = "on"<%} %>> <a href = "/board/Board_List.aws?page=<%=i%>&<%=param%>"><%=i %></a></li>
		<%} %>
		<%if(pm.isNext()==true&&pm.getEndPage()>0){ %>
		<li><a href = "/board/Board_List.aws?page=<%=pm.getEndPage()+1%>&<%=param%>">▶</a></li>
		<%} %>
		</ul>
		</div>
	</form>
</body>
</html>