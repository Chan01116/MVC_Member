<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mvc.vo.BoardVo" %>
<%@ include file="/common/loginCheck.jsp"%>
    
    <script src="https://code.jquery.com/jquery-latest.min.js"></script>
    <%
    BoardVo bv = (BoardVo)request.getAttribute("bv");  // 강제형변환 양쪽형을 맞춰준다
    
    
    String memberName = "";
    if(session.getAttribute("memberName")!=null){
    	memberName = (String)session.getAttribute("memberName");
    	
    	
    }
    
    %>
    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글내용</title>
<link href= "../css/style2.css" rel = "stylesheet">

<script>
//제이쿼리는 함수명이 앞으로
// jquery 로 만드는 함수
// read밖에 생성
$.boardCommentList = function(){
    $.ajax({
        type: "get",
        url: "<%=request.getContextPath()%>/comment/commentList.aws?bidx=<%=bv.getBidx()%>",
        dataType: "json",
        success: function(result){
            console.log(result);  // 결과 로그 출력
            let commentList = $("#commentList");
            commentList.empty();
            for(let i=0; i<result.length; i++){
                let comment = result[i];
                let commentHtml = "<div><strong>" + comment.cwriter + "</strong> (" + comment.writeday + ")<br>" + comment.ccontents + "</div>";
                commentList.append(commentHtml);
            }
        },
        error: function(xhr, status, error){
            console.log(xhr.responseText);  // 에러 내용 로그 출력
            alert("댓글 로딩 실패: " + error);
        }
    });
}

$(document).ready(function(){
	$.boardCommentList();
	
	$("#btn").click(function(){
		alert("추천버튼 클릭");		
	
		$.ajax({
			type :  "get",    //전송방식
			url : "<%=request.getContextPath()%>/board/boardRecom.aws?bidx=<%=bv.getBidx()%>",
			dataType : "json",       // json타입은 문서에서  {"키값" : "value값","키값2":"value값2"}
			success : function(result){   //결과가 넘어와서 성공했을 받는 영역
				alert("전송성공 테스트");	
			
				var str ="추천("+result.recom+")";			
				$("#btn").val(str);			
			},
			error : function(){  //결과가 실패했을때 받는 영역						
				alert("전송실패");
			}			
		});			
	});	
	
	
	
	
	$("#cmtBtn").click(function(){
		
		
		let loginCheck = "<%=session.getAttribute("midx")%>";
		if(loginCheck == ""|| loginCheck == "null" || loginCheck == null){
			alert("로그인을 해주세요");
			return;
		}
		
		
		let cwriter = $("#cwriter").val()
		let ccontents = $("#ccontents").val()
		
		if(cwriter == "" ){
			alert("작성자를 입력해주세요");
			$("#cwriter").focus();
			return;
			
			
		}else if(ccontents == ""){
			alert("내용을 입력해주세요");
			$("#ccontents").focus();
			return;
		}
		
		
		
		$.ajax({
			type :  "post",    //전송방식
			url : "<%=request.getContextPath()%>/comment/commentWriteAction.aws",
			data : {"cwriter" : cwriter, 
				    "ccontents" : ccontents, 
				    "bidx" :"<%=bv.getBidx()%>", 
				    "midx" : "<%=session.getAttribute("midx")%>"},       // json타입은 문서에서  {"키값" : "value값","키값2":"value값2"}
				        
			dataType : "json",	        
			success : function(result){   //결과가 넘어와서 성공했을 받는 영역
				alert("전송성공 테스트");	
			
				var str ="("+result.value+")";			
				alert(str);	
			},
			error : function(){  //결과가 실패했을때 받는 영역						
				alert("전송실패");
			}			
		});			
    });
	
	

	
	
});



</script>
</head>
<body name = "bd">
	<h3>글내용</h3>
	<hr id = "top">
	<div><th id = "contentsTitle"><%=bv.getSubject() %>(조회수:<%=bv.getViewcnt() %>)</th><br>
	<input type="button" id="btn" value="추천(<%=bv.getRecom() %>)">
	
		</div>
		<div>
		<%=bv.getContents()%>
		</div>
		
		
	<hr id = "mid">
	<hr id = "mid">
	<%if(bv.getFilename()==null|| bv.getFilename().equals("")){}else{ %>
	<img src="<%=request.getContextPath() %>/images/<%=bv.getFilename() %>">
	<%} %>
	<p>
	<a href = "<%=request.getContextPath() %>/board/Board_Download.aws?filename=<%=bv.getFilename() %>" class = "fileDown">
	첨부파일 다운로드
	</a></p>
	
	<hr id = "battom">
	<div> <a class = "btn aBtn" id = "contentsBtn"  href = "<%=request.getContextPath() %>/board/Board_Modify.aws?bidx=<%=bv.getBidx()%>">수정</a>
	<a class = "btn aBtn" id = "contentsBtn" href = "<%=request.getContextPath() %>/board/Board_Delete.aws?bidx=<%=bv.getBidx()%>">삭제</a>
	<a class = "btn aBtn" id = "contentsBtn" href = "<%=request.getContextPath() %>/board/Board_Reply.aws?bidx=<%=bv.getBidx()%>">답변</a>
	<a class="btn aBtn" href="<%=request.getContextPath() %>/board/Board_List.aws">목록</a></div>
	
	<input type = "text" id = "cwriter" name = "cwriter" value = "<%=memberName%>" readonly = "readonly" style = "width:100px;">
	<input type = "text" id = "ccontents" name ="ccontnents">
	<button type = "button" id = "cmtBtn" >댓글쓰기</button>
	<div id="commentList"></div>





</body>
</html>