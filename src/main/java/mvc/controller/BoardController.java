package mvc.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mvc.dao.BoardDao;
import mvc.vo.BoardVo;

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
		if(location.equals("boardList.aws")) {  // 가상경로
			BoardDao bd = new BoardDao();
			ArrayList<BoardVo> alist = bd.boardSelectAll();
			//System.out.println("alist==>"+alist); //객체주소가 나오면 객체가 생성된것을 짐작할 수있다
			
			request.setAttribute("alist", alist);
			
			
			paramMethod ="F";
			url = request.getContextPath()+"/board/Board_List.jsp";   // 실제 내부경로
			
			
		}
		if(location.equals("Board_Write.aws")) {  // 가상경로
			BoardDao bd = new BoardDao();
			ArrayList<BoardVo> alist = bd.boardSelectAll();
			//System.out.println("alist==>"+alist); //객체주소가 나오면 객체가 생성된것을 짐작할 수있다
			
			request.setAttribute("alist", alist);
			
			
			paramMethod ="F";
			url = request.getContextPath()+"/board/Board_Write.jsp";   // 실제 내부경로
			
			
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
