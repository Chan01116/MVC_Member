<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<script>
function check(){
	  
	  let title = document.getElementsByName("title");
	  let contents = document.getElementsByName("contents");
	  let writer = document.getElementsByName("writer");
	
	  
	  if(title[0].value == ""){
		  alert("제목을 입력해주세요");
		  title.focus();
		  return;
	  }else if(contents[0].value == ""){
		  alert("내용을 입력해주세요");
		  contents.focus();
		  return;
		  
	  }else if(writer[0].value == ""){
		  alert("작성자를 입력해주세요");
		  writer.focus();
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
<h3>글쓰기</h3>
<hr id = "tod">
	<div>제목 <input type ="text" name = "title"> </div>
	<hr id = "mid">
	<div>내용 <input type ="text" name = "contents"> </div>
	<hr id = "mid">
	<div>작성자 <input type ="text" name = "writer"> </div>
	<hr id = "mid">
	<div>비밀번호 <input type ="password"> </div>
	<hr id = "mid">
	<div>첨부파일 <button type ="button">파일선택</button></div>
	<hr id = "mid">
	<div> <button type = "button" id = "save" onclick = "check();">저장</button> <button type = "button" id = "cancle">취소</button> </div>
	<table>
		<td id = "NoTop">번호</td>
		<td id = "WriterTop">작성자</td>
		<td id = "TitleTop">내용</td>
		<td id = "WritedayTop">날짜</td>
	</table>


</body>
</html>