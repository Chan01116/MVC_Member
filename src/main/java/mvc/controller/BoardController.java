package mvc.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mvc.dao.BoardDao;
import mvc.vo.BoardVo;
import mvc.vo.Criteria;
import mvc.vo.PageMaker;

import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String location; //멤버변수(전역) 초기화 =>이동할 페이지
	public BoardController(String location) {
		this.location = location;		
	}
    	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String paramMethod = "";
		String url = "";
		if(location.equals("Board_List.aws")) {  // 가상경로
			
			String page = request.getParameter("page");
			if(page == null) page = "1";
			
			int pageInt = Integer.parseInt(page);
			Criteria cri = new Criteria();
			cri.setPage(pageInt);
			
			PageMaker pm = new PageMaker();
			pm.setCri(cri);                         // <-- PageMaker에 Criteria 담아서 가지고 다닌다
			
						
			BoardDao bd = new BoardDao();
			//페이징 처리하기 위한 전체 데이터 갯수 가져오기
			
			int boardCnt = bd.boardTotalCount();
			//System.out.println("게시물 수는? : " + boardCnt);
			pm.setTotalCount(boardCnt);            // <-- PageMaker에 전체 게시물수를 담아서 페이지계산
			
			
			
			ArrayList<BoardVo> alist = bd.boardSelectAll(cri);
			//System.out.println("alist==>"+alist); //객체주소가 나오면 객체가 생성된것을 짐작할 수있다
			
			request.setAttribute("alist", alist);  // 화면까지 가지고 가기 위해 request객체에 담는다
			request.setAttribute("pm", pm);       // forward방식으로 넘기기 때문에 공유가 가능하다
			
			paramMethod ="F";
			url = "/board/Board_List.jsp";   // 실제 내부경로
			
			
			
			
		}else if(location.equals("Board_Write.aws")) { // 가상경로 
		//System.out.println("board_Write");
		 
		
		 
		 
		 paramMethod ="F";  // 포워드방식은 내부에서 공유하는 것이기 때문에 내부에서 활동하고 이동한다 
		 url = "/board/Board_Write.jsp"; // 실제 내부경로
		 
		 
		 }else if(location.equals("Board_WriteAction.aws")) {
			// System.out.println("Board_WriteAction.aws");
			 //1 파라미터 값을 넘겨받는다
			 String subject = request.getParameter("subject");
			 String contents = request.getParameter("contents");
			 String writer = request.getParameter("writer");
			 String password = request.getParameter("password");
			 
			 HttpSession session = request.getSession();
			 int midx = Integer.parseInt(session.getAttribute("midx").toString()); //로그인할때 담았던 세션변수 midx값을 꺼낸다
			 
			 BoardVo bv = new BoardVo();
			 bv.setSubject(subject);
			 bv.setContents(contents);
			 bv.setWriter(writer);
			 bv.setPassword(password);
			 bv.setMidx(midx);
			 
			 
			 //2 DB처리한다
			 BoardDao bd = new BoardDao();
			 int value = bd.boardInsert(bv);
			 
			 if(value == 2) { //입력성공
				 paramMethod ="S";
				 url = request.getContextPath()+"/board/Board_List.aws";
				 
			 }else { //실패했으면
				 paramMethod ="S";
				 url = request.getContextPath()+"/board/Board_Write.aws";
				 
			 }
			 
			 //3 처리후 이동한다 sendRedirect
			  	/*
				 * paramMethod ="S"; url = request.getContextPath()+"/board/Board_List.aws";
				 */
			 
			 
			 
			 
		 }else if(location.equals("Board_Contents.aws")) {
			 //System.out.println("Board_Contents.aws");
			 
			 
			 //1. 넘어온값 받기
			 String bidx = request.getParameter("bidx");
			 //System.out.println("bidx-->"+bidx);
			 
			 int bidxInt = Integer.parseInt(bidx);
			 
			 				 			 
			 //2. 처리하기
			 BoardDao bd = new BoardDao();  // 객체생성하고
			 BoardVo bv = bd.boardSelectOne(bidxInt);  // 생성한 메소드호출
			 
			 request.setAttribute("bv", bv);  // 포워드방식이라 같은영역안에 있어서 공유해서 jsp페이지에서 꺼내쓸수있다
			 
			 
			 //3. 이동해서 화면보여주기
			 paramMethod ="F";  // 화면을 보여주기 위해서 같은 영역내부안에 jsp페이지를 보여준다
			 url = "/board/Board_Contents.jsp";
			 			 
		 }else if(location.equals("Board_Modify.aws")) {
			 //System.out.println("Board_Modify.aws");
			 		 
			 String bidx = request.getParameter("bidx");
			 //System.out.println("bidx-->"+bidx);
			 
			 int bidxInt = Integer.parseInt(bidx);
			 
			 				 			 
			 BoardDao bd = new BoardDao();  // 객체생성하고
			 BoardVo bv = bd.boardToModify(bidxInt);  // 생성한 메소드호출
			
			 request.setAttribute("bv", bv);  // 포워드방식이라 같은영역안에 있어서 공유해서 jsp페이지에서 꺼내쓸수있다
			 
			 
			 //3. 이동해서 화면보여주기
			 paramMethod ="F";  // 화면을 보여주기 위해서 같은 영역내부안에 jsp페이지를 보여준다
			 url = "/board/Board_Modify.jsp";
			 			 
		 }else if(location.equals("Board_ModifyAction.aws")) {
			// System.out.println("Board_ModifyAction.aws");
			 
			 String subject = request.getParameter("subject");
			 String contents = request.getParameter("contents");
			 String writer = request.getParameter("writer");
			 String password = request.getParameter("password");
			 String bidx = request.getParameter("bidx");
			 int bidxInt = Integer.parseInt(bidx);
			 
			 
			 BoardDao bd = new BoardDao();  
			 BoardVo bv = bd.boardSelectOne(bidxInt);
			 //비밀번호 체크
			 paramMethod ="S";
			 
			 if(password.equals(bv.getPassword())) {
				 //같으면
				 BoardDao bd2 = new BoardDao();
				 BoardVo bv2 = new BoardVo();
				 bv2.setSubject(subject);
				 bv2.setContents(contents);
				 bv2.setWriter(writer);
				 bv2.setPassword(password);
				 bv2.setBidx(bidxInt);
				 
				 int value = bd2.boardUpdate(bv2);
				 
				 
				 
				 if(value == 1) {
					 //System.out.println("들어왔는가?");
					 url = request.getContextPath()+"/board/Board_Contents.aws?bidx="+bidx; 
					 
				 }else {
					 //System.out.println("들어왔는가?");
					 
					 url = request.getContextPath()+"/board/Board_Modify.aws?bidx="+bidx;
				 }
				 				 				 				 
			 }else {
				 //비밀번호가 다르면
				 //System.out.println("비번다름");
				 url = request.getContextPath()+"/board/Board_Modify.aws?bidx="+bidx;
			 }
			  
			 }
		
		
				
		
		
				if(paramMethod.equals("F")) { RequestDispatcher rd =
				  request.getRequestDispatcher(url); rd.forward(request, response);
				  
				  }else if(paramMethod.equals("S")){ response.sendRedirect(url);
				  
				  }
		
		
		  
		 
		
				
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
