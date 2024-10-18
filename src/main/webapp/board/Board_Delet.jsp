<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글삭제</title>
<script>
function check(){
	  
	  let password = document.getElementsByName("password");
	  	
	  
	  if(password[0].value == ""){
		  alert("비밀번호를 입력해주세요");
		  password.focus();
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
	<h3>글삭제</h3>
	<hr>
	<div>비밀번호 <input type = "password" name = "password"></div>
	<hr>
	<div><button type = "button" id = "save" onclick = "check();">저장</button> <button type = "button" id = "cancle">취소</button> </div>
	


</body>
</html>