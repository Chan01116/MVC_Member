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
  
  <script>
  //아이디 비밀번호 유효성 검사
  function check(){
	  //이름으로 객체찾기
	  let memberid = document.getElementsByName("memberid");
	  let memberpwd = document.getElementsByName("memberpwd");
	  //alert(memberid[0].value);
	  //alert(memberpwd[0].value);
	  
	  if(memberid[0].value == ""){
		  alert("아이디를 입력해주세요");
		  memberid[0].focus();
		  return;
	  }else if(memberpwd[0].value == ""){
		  alert("비밀번호를 입력해주세요");
		  memberpwd[0].focus();
		  return;
		  
	  }
	  var fm = document.frm;
	  fm.action = "<%=request.getContextPath()%>/member/memberLoginAction.aws";  //가상경로지정 action은 처리하는 의미
	  fm.method = "post";
	  fm.submit();
	  return;
  }
  
  </script>
  
  
  
 </HEAD>

 <BODY>

<header>User Login</header> 

 <div>
 <section>
	<article>
	<form name="frm" >
		<table border = "1">
			<tr><td>아이디</td><td><input type="text" name="memberid" style =  "width:100px" maxlength="30"></td></tr>
			<tr><td>비밀번호</td><td><input type="password" name="memberpwd" style = "width:100px" maxlength="30"></td></tr>
		
		
		<tr>
			<td colspan = 2 style = "text-align:center">
			<input type = "button" name= "btn" value="로그인" onclick = "check();">
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
