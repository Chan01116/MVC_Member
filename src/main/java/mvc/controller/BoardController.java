package mvc.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.dao.BoardDao;
import mvc.vo.BoardVo;
import mvc.vo.Criteria;
import mvc.vo.PageMaker;

import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String location;
	public BoardController(String location) {
		this.location = location;		
	}
    	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String paramMethod = "";
		String url = "";
		if(location.equals("board_List.aws")) {  // 가상경로
			
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
			url = request.getContextPath()+"/board/Board_List.jsp";   // 실제 내부경로
			
			
			if(paramMethod.equals("F")) { RequestDispatcher rd =
					  request.getRequestDispatcher(url); rd.forward(request, response);
					  
					  }else if(paramMethod.equals("S")){ response.sendRedirect(url);
					  
					  }
			
			
		}
		/*
		 * if(location.equals("Board_Write.aws")) { // 가상경로 BoardDao bd = new
		 * BoardDao(); ArrayList<BoardVo> alist = bd.boardSelectAll(cri);
		 * //System.out.println("alist==>"+alist); //객체주소가 나오면 객체가 생성된것을 짐작할 수있다
		 * 
		 * request.setAttribute("alist", alist);
		 * 
		 * 
		 * paramMethod ="F"; url = request.getContextPath()+"/board/Board_Write.jsp"; //
		 * 실제 내부경로
		 * 
		 * 
		 * }
		 */
		
		
		  
		 
		
				
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
