<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<HTML>
 <HEAD>
  <TITLE> memberJoin </TITLE> 
  
  <link href = "../css/style.css" type = "text/css" rel = "stylesheet">
  <script src="https://code.jquery.com/jquery-latest.min.js"></script>  <!-- CDN(컨텐츠 딜리버리 네트워크)주소 -->
 <script type="text/javascript">
 
 // 버튼을 눌렀을때 check 함수 작동
  
 function check(){
	var fm = document.frm; // frm : form객체의 이름
	const email = /[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$/i;
	
	 if (fm.memberid.value == ""){
		 alert("아이디를 입력해주세요");
		 fm.memberid.focus();
		 return;
	 }else if(fm.btn.value == "N"){
		 alert("아이디 중복체크를 해주세요.");
		 fm.memberid.focus();
		 return;
	 }else if (fm.memberpwd.value == ""){
		 alert("비밀번호를 입력해주세요");
		 fm.memberpwd.focus();
		 return;
	 }else if (fm.memberpwd2.value == ""){
		 alert("비밀번호확인을 입력해주세요");
		 fm.memberpwd2.focus();
		 return;
	 }else if (fm.memberpwd.value !== fm.memberpwd2.value){
		 fm.memberpwd2.value="";
		 fm.memberpwd2.focus();
		 alert("비밀번호가 일치하지 않습니다.");
		 return;
	 }else if (fm.membername.value == ""){
		 alert("이름을 입력해주세요");
		 fm.membername.focus();
		 return;
	 }else if (fm.memberemail.value == ""){
		 alert("이메일을 입력해주세요");
		 fm.memberemail.focus();
		 return;
	 }else if(email.test(fm.memberemail.value)==false){
		 alert("이메일형식이 올바르지 않습니다.");
		 fm.memberemail.value="";
		 fm.memberemail.focus();
		 return;
	 }else if (fm.memberephone.value == ""){
		 alert("전화번호를 입력해주세요");
		 fm.memberephone.focus();
		 return;
	 }else if (fm.memberebirth.value == ""){
		 alert("생년월일을 입력해주세요");
		 fm.memberebirth.focus();
		 return;
	 }else if(hobbyCheck() == false) {
		 alert("취미를 1개이상 선택해주세요");
		 return;
	 }
	 //alert("이동할 정보등록할 차례입니다.");
	 
	 var ans = confirm("저장하시겠습니까?");
	 if(ans == true){
		 
		 
		 //가상경로를 사용해서 쓸 예정  가짜경로 형식은 :  /기능/세부기능.aws
		 fm.action = "<%=request.getContextPath()%>/member/memberJoinAction.aws";
		 fm.method = "post";
		 fm.submit();
	 }
	 return;  // 리턴값을 안쓰면 그냥 멈춤 종료
 }
 
 function hobbyCheck(){
	 
	 var arr = document.frm.memberhobby;  // 문서객체안에 폼객체 안에 input객체 선언
	 var flag = false;           // 체크유무 초기값 false선언
	 
	 for(var i = 0; i<arr.length; i++){    // 선택한 여러값을 반복해서 출력
		 if(arr[i].checked == true){       // 하나라도 선택했다면 true값 리턴
			 flag = true;
			 break;
		 }
		 
	 }
	 return flag;
	 }
	  
	 /* if(flag == false){
	 alert("취미를 1개이상 선택해주세요");
	 return false; }*/
 
	 $(document).ready(function(){
		 
		 $("#btn").click(function(){
			 //alert("중복체크버튼 클릭");
			 let memberId = $("#memberid").val();
			 if(memberId == ""){
				 alert("아이디를 입력해 주세요");
				 return;
			 }
			 
			 
			 $.ajax({
				 type : "post",     //전송방식
				 url : "<%=request.getContextPath()%>/member/memberIdCheck.aws",
				 dataType : "json",     // json타입은 문서에서 {"키값" : "value값","키갑2" : "value값2"}
				 data : {"memberId" : memberId },
				 success : function(result){    // 결과가 넘어와서 성공했을 때 받는 영역
				 	 
					 //alert("전송성공 테스트");
				 	 //alert("길이는"+result.length);
				 	 //alert("cnt값은"+result.cnt);
				 	 if(result.cnt == 0){
				 		 alert("사용할수 있는 아이디입니다.");
				 		$("#btn").val("Y");
				 	 }else{
				 		 alert("사용할수 없는 아이디입니다.");
				 		$("#memberid").val("");  //입력한 아이디 지우기
				 		 
				 	 }
				 	 
				 
				 },
				 error : function(){   // 결과가 실패했을 때 받는 영역
					 
					 alert("전송실패 테스트");
					 
				 }
				 
				 
			 });
				 			 
		 });
		 
	  });
	 
	 
	 
	 
	 
	 
	 
 </script>
 </HEAD>
 <BODY>
 <header><a href = "./memberJoin.jsp">회원가입 페이지></a></header>
 <nav><a href="./memberLogin.jsp" style = "text-decoration : none">로그인하기</a></nav> 
 <section>
	<article>
	<form name="frm" >
		<table border = "1">
			<tr><th class = "idcolor">아이디</th>
				<td>
				<input type="text" id = "memberid" name="memberid" style = "width:100px" maxlength="30" placeholder = "아이디를 입력하세요">
				<button type = "button" name = "btn" id = "btn" value = "N"> 아이디 중복체크 </button>
				</td>
			</tr>
			<tr><th class = "idcolor">비밀번호</th><td><input type="password" name="memberpwd" style = "width:100px" maxlength="30"></td></tr>
			<tr><th>비밀번호 확인</th><td><input type="password" name="memberpwd2" style = "width:100px" maxlength="30"></td></tr>
			<tr><th id="name">이름</th><td><input type="text" name="membername" style = "width:100px" maxlength="30"></td></tr>
			<tr><th>이메일</th><td><input type="email" name="memberemail" style = "width:100px" maxlength="30"></td></tr>
			<tr><th>연락처</th><td><input type="number" name="memberephone" style = "width:100px" maxlength="30"></td></tr>
		<tr><th>주소</th><td>
		
		<select name = "membereaddr" style = "width:100px">
		
			<option value = "서울">서울</option>
			<option value = "대전" selected>대전</option>
			<option value = "부산">부산</option>
			<option value = "인천">인천</option>
			
		</select></td></tr>
		
		<tr><th>성별</th>
		<td>
		<input type="radio" name="membergender" id = "select1" value = "M"><label for="select1">남성</label>
		<input type="radio" name="membergender" id = "select2" value = "F" checked><label for="select2">여성</label>
		</td></tr>
		
		<tr><th>생년월일</th><td><input type="number" name="memberebirth" style = "width:100px" maxlength="8">예)20240920</td></tr>
		
		<tr><th>취미</th><td>
			<input type="checkbox" name="memberhobby" id = "check1" value = "야구"><label for="check1"></label>야구
			<input type="checkbox" name="memberhobby" id = "check2" value = "농구"><label for="check2"></label>농구
			<input type="checkbox" name="memberhobby" id = "check3" value = "축구"><label for="check3"></label>축구	
		</td></tr>
		
		
		<tr>
			<td colspan = 2 style = "text-align:center">
			<button type="button" onclick = "check();">
			저장하기
			</button>
			
			
			<!-- <input type = "submit" name= "btn" value="회원정보 저장하기">
			<input type = "reset" name= "btn" value="초기화"> -->
		</td>
		</tr>

		</table>
	</form>
	</article>
 </section>

 <aside></aside>
<footer>made by HC</footer> 
 
 </BODY>
</HTML>
