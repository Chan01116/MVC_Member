<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<HTML>
 <HEAD>
  <TITLE> memberJoin.html </TITLE>
  <style>
  input[type=text]:focus, input[type=password]:focus{
  	font-size : 120%;
  
  }
  header{
  	text-align : center;
  	font-size : 32px;
  }
  
  footer{
  	position : absolute;
  	top : 98%;
  	text-align : center;
  	left: 50%;
  	transform: translateX(-50%);
  	color: white;
  }
  
  table{
  	margin: 0 auto;
    border-collapse: collapse;
    background-color : rgba(255,255,255,0.4);
    position : absolute;
    top: 20%;
    left: 70%;
  }
  
  
  div {
  	background-image : url("../images/pexels-pixabay-210012.jpg");
	margin: 0; /* 기본 여백 제거 */
    height: 100vh; /* 전체 높이를 화면에 맞게 설정 */
    background-size: cover; /* 배경 이미지를 요소 크기에 맞게 조절 */
    background-position: center; /* 배경 이미지 중앙 정렬 */
    background-repeat: no-repeat; /* 배경 이미지 반복 방지 */
  }
  
  
  
  </style>
  
  
  
  
  
 </HEAD>

 <BODY>

<header>User Login</header> 

 <div>
 <section>
	<article>
	<form name="frm" action = ".test0920_result.html" method="post">
		<table border = "1">
			<tr><td>아이디</td><td><input type="text" name="memberId" style = "width:100px" maxlength="30"></td></tr>
			<tr><td>비밀번호</td><td><input type="password" name="memberPwd" style = "width:100px" maxlength="30"></td></tr>
		
		
		<tr>
			<td colspan = 2 style = "text-align:center">
			<input type = "submit" name= "btn" value="로그인">
		</td>
		</tr>
		<tr></tr>
		<td><a href = "www.naver.com">아이디 찾기</a></td><td>비밀번호 찾기</td>

		</table>
	</form>
	</article>
 </section>

 <aside></aside>
<footer>made by HC</footer> 

  </div>
 </BODY>
</HTML>
