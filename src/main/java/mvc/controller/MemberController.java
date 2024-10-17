package mvc.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mvc.dao.MemberDao;
import mvc.vo.MemberVo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/MemberController")       //서블릿 : 자바로 만든 웹페이지 (접속주소: /MemberController)
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private String location;  // 멤버변수(전역)초기화 => 이동할 페이지
	public MemberController(String location) {
        	this.location = location;        	 
    }
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		// 넘어온 모든 값은 여기에서 처리해서 분기한다  - controller 역할
		
	//	System.out.println("값이 넘어오나요?");
		//전체주소를 추출
		String uri = request.getRequestURI();
		//System.out.println("uri"+uri);   //   /mvc_programming/member/memberJoinAction.aws
		//String[] location = uri.split("/");
		
		String paramMethod = "";// 전송방식이 sendRedirect면 S forward방식이면 F
		String url = "";
		
		
		if (location.equals("memberJoinAction.aws")){    //4번째방의 값이 memberJoinAction.aws이면
			
			String memberId = request.getParameter("memberid");			
			String memberPwd = request.getParameter("memberpwd");			
			String memberPwd2 = request.getParameter("memberpwd2");
			String memberName = request.getParameter("membername");
			String memberEmail = request.getParameter("memberemail");
			String memberPhone = request.getParameter("memberphone");
			String memberAddr = request.getParameter("memberaddr");
			String memberGender = request.getParameter("membergender");
			String memberBirth = request.getParameter("memberbirth");	
			String[] memberHobby = request.getParameterValues("memberhobby");
			String memberInHobby="";
			for(int i=0;i<memberHobby.length;i++){
				memberInHobby = memberInHobby +memberHobby[i]+",";				
			}

			MemberDao md = new MemberDao();
			int value = md.memberInsert(
					         memberId,              //객체안에 생성해놓은 멤버 메소드를 호출해서 값을꺼낸다
							 memberPwd,
							 memberName,
							 memberGender,
							 memberBirth,
				    		 memberAddr,
				    		 memberPhone,
				    		 memberEmail,
				    		 memberInHobby);      
		    
		    
		    String msg ="";
		    HttpSession session = request.getSession(); //세션객체 활용
		    if (value==1){                                  // -> index.jsp파일은 web.xml웹설정파일에 기본등록되어있어서 생략가능
		    	
		    	msg="회원 가입되었습니다";
		    	session.setAttribute("msg",msg);
		    	url=request.getContextPath()+"/";  //request.getContextPath() : 프로젝트이름
		    	    	  //전송방식 sendRedirect는 요청받으면 다시 그쪽으로 가라고 지시하는 방법
		    }else{    
		    	msg="회원 가입 오류발생하였습니다";
		    	session.setAttribute("msg",msg);
		    	url=request.getContextPath()+"/member/memberJoin.jsp";
		    	  // 센드리다이렉트 방식 : 새롭게 다른 쪽으로 가라고지시
		    	} 			
			paramMethod = "S";  // 밑에서 sendRedirect 방식으로 처리한다
		   // System.out.println("msg는?" + msg);
		    
		}else if (location.equals("memberJoin.aws")) {
	    	//System.out.println("조인에 들어왔나?");
			
			url = "/member/memberJoin.jsp";
			  // 포워드 방식 : 내부안에서 넘겨서 토스하겠다는 뜻
	    	
			paramMethod = "F";  //하단에서 포워드로 처리합니다
			
	    }else if (location.equals("memberLogin.aws")) {
	    	//System.out.println("로그인에 들어왔나?");
			
			url = "/member/memberLogin.jsp";
			paramMethod = "F";
			  // 포워드 방식 : 내부안에서 넘겨서 토스하겠다는 뜻
	    
	    } else if(location.equals("memberLoginAction.aws")) {
	    	
	    	
	    	//System.out.println("memberLoginAction들어왔나?");
	    	
	    	String memberId = request.getParameter("memberid");
	    	String memberPwd = request.getParameter("memberpwd");
	    	MemberDao md = new MemberDao();
	    	MemberVo mv = md.memberLoginCheck(memberId, memberPwd);
	    	md.memberLoginCheck(memberId, memberPwd);
	    	//System.out.println("mv객체가 생겼나요?"+ mv);
	    	
	    	
	    	if(mv == null){
	    		
	    		url=request.getContextPath()+"/member/memberLogin.aws";
				paramMethod="S";
				//해당 주소로 다시 가세요 해당하는 값이 없을때
	    		
	    	}else {
	    		//해당되는 로그인 사용자가 있으면 세션에 회원정보 담아서 메인으로 가라
	    		
	    		String mid = mv.getMemberid();  // 아이디 꺼내기
		    	int midx = mv.getMidx();       // 회원번호 꺼내기
		    	String memberName = mv.getMembername(); //이름꺼내기
	    		
		    	HttpSession session = request.getSession();
		    	session.setAttribute("mid",mid);
		    	session.setAttribute("midx",midx);
		    	session.setAttribute("memberName",memberName);
		    	
		    	url=request.getContextPath()+"/";
				paramMethod="S";			
				//로그인 되었으면 메인으로 가세요
	    	}
	    	
	    	
	    	
	    	
	    }else if(location.equals("memberLogout.aws")) {
	    	//System.out.println("memberLogout");
	    	
	    	// 세션삭제
	    	HttpSession session = request.getSession();
	    	session.removeAttribute("mid");
	    	session.removeAttribute("midx");
	    	session.removeAttribute("memberName");
	    	session.invalidate();
	    	
	    	url=request.getContextPath()+"/";
			paramMethod="S";		
	    	
	    	
	    }else if(location.equals("memberList.aws")) {
	    	//System.out.println("memberList.aws");
	    	//1. 메소드 불러서 처리하는 코드를 만들어야 한다
	    	//2. 보여줄 페이지를 forward방식으로 보여준다  공유의 특성을 가지고 있다
	    		    
	    	MemberDao md = new MemberDao();  //객체생성
	    	ArrayList<MemberVo> alist = md.memberSelectAll();
	    	
	    	
	    	request.setAttribute("alist", alist);
	    	
	    	
	    	url= "/member/memberList.jsp";				
			paramMethod="F";		
	    	
	    }else if(location.equals("memberIdCheck.aws")) {
	    	System.out.println("memberIdCheck.aws");
	    	
	    	String memberId = request.getParameter("memberId");
	    	
	    	MemberDao mv = new MemberDao();
	    	int cnt = mv.memberIdCheck(memberId);
	    	
	    	//System.out.println("cnt : "+cnt);
	    	
	    	
	    	PrintWriter out = response.getWriter();
	    	out.println("{\"cnt\":\""+cnt+"\"}");
	    	
	    	
	    }
		
		
		         
		if(paramMethod.equals("F")) {
			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request, response);
			
		}else if(paramMethod.equals("S")){
			response.sendRedirect(url);
			
		}
		
		
		
	}	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
