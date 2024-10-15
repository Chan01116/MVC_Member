<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
    
    
     <jsp:useBean id="mv" class="mvc.vo.MemberVo" scope="page"/>
     <%@ page import="mvc.dao.MemberDao" %>
     <jsp:setProperty name="mv" property="*"/>
   
    <%
    
  	String[] memberHobby = request.getParameterValues("memberhobby");
    String memberInHobby = "";
    for(int i=0; i < memberHobby.length; i++){
    	memberInHobby = memberInHobby + memberHobby[i]+",";    	
    }
    
    
    	MemberDao md = new MemberDao();
    
    int value = md.memberInsert(mv.getMemberid(),
        		mv.getMemberpwd(),
        		mv.getMembername(),
        		mv.getMembergender(),
        		mv.getMemberbirth(),
        		mv.getMemberaddr(),
        		mv.getMemberphone(),
        		mv.getMembereamil(),
        		memberInHobby);
    	
 
    // 매개변수에 인자값 대입해서 함수호출
    
    
     //value 값이 1이면 0이면 입력실패
     //1이면 성공했기 때문에 다른페이로 이동시키고 0이면 다시 회원가입 입력페이지로 간다
     
     String pageUrl = "";
     String msg = "";
     if(value==1){ //-> index .jsp파일은 web.xml웹설정파일에 기본등록되어있어서 생략가능
    	 msg = "회원가입 되었습니다";
    	 
    	pageUrl = request.getContextPath()+"/";   // request.getContextPath() : 프로젝트이름
    //	 response.sendRedirect(pageUrl);   // 전송방식 : sendRedirect는 요청받으면 다시 그쪽으로가라고 지시하는 방법
     
     }else{
    	 msg = "회원가입 오류발생하였습니다";
    	 pageUrl = request.getContextPath()+"/member/memberJoin.jsp";
    //	 response.sendRedirect(pageUrl);
     }
     
     
     
     
     
    // value가 0이면 미입력 1이면 입력됨
    %>
    <script>
    alert('<%=msg%>');
    //자바스크립트로 페이지 이동시킨다 document객체안에 location 객체안에 주소속성에 담아서
    document.location.href="<%=pageUrl%>";
    <%-- alert(<%=value%>); --%>
    
    
    </script>
    
    
    
    
    