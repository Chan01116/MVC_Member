package mvc.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import mvc.dao.BoardDao;
import mvc.vo.BoardVo;
import mvc.vo.Criteria;
import mvc.vo.PageMaker;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;


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
			  System.out.println("Board_WriteAction.aws");
			 
			 //저장될 위치
				
				 String savePath = "C:\\Users\\admin\\git\\aws0822\\mvc_programming\\src\\main\\webapp\\images\\";
				 System.out.println(savePath);
				 int fsize = (int) request.getPart("filename").getSize(); //15MB만 올린다 
				 System.out.println("fsize : "+fsize);
				 
				 String originFileName = "";
				 if(fsize!=0) {
					 Part filePart = (Part) request.getPart("filename");
					 System.out.println("filePart==>"+filePart);
					 
					 originFileName = getFileName(filePart);
					 System.out.println("originFileName==>"+originFileName);
					 System.out.println("저장되는 위치는? : "+savePath+originFileName);
					 
					 File file = new File(savePath + originFileName);
					 InputStream is = filePart.getInputStream();   // Stream : 흐름. InputStream : 들어가는 부분의 데이터 흐름
					 File fos = null;
					 
				 }
				 
				 
				
				 
			 
			 
			   
			 
			 
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
			 bd.boardViewCntUpdate(bidxInt);
			 BoardVo bv = bd.boardSelectOne(bidxInt);  // 생성한 메소드호출 (해당되는 bidx의 게시물 데이터 가져옴)
			 
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
			  
			 }else if(location.equals("boardRecom.aws")) {
				 
				 String bidx = request.getParameter("bidx");
				 int bidxInt = Integer.parseInt(bidx);
				 BoardDao bd = new BoardDao();
				 int recom = bd.boardRecomUpdate(bidxInt);
				 
				 
				 PrintWriter out = response.getWriter();
				 out.println("{\"recom\":\""+recom+"\" }");
				 
				 
				 
				 //다시설명
				 //paramMethod="S";
				 //url ="/board/Board_Contents.aws?bidx="+bidx;
			 }
		
		
				
		
		
				if(paramMethod.equals("F")) { RequestDispatcher rd =
				  request.getRequestDispatcher(url); rd.forward(request, response);
				  
				  }else if(paramMethod.equals("S")){ response.sendRedirect(url);
				  
				  }
		
		
		  
		 
		
				
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	
	
	public String getFileName(Part filePart) {
		
		for(String filePartData : filePart.getHeader("Content-Disposition").split(";")) {
			System.out.println(filePartData);
			
			if(filePartData.trim().startsWith("filename")) {
				return filePartData.substring(filePartData.indexOf("=")+1).trim().replace("\"" ,"");
				
				
			}
		}
		return null;
	}
	
	
	
	
	
	
	}
