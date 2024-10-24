package mvc.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("/FrontController")
@MultipartConfig(  //멀티파일을 설정한다
		fileSizeThreshold =  1024 * 1024 * 1,  // 1MB
		maxFileSize =  1024 * 1024 * 10,  // 10MB
		maxRequestSize =  1024 * 1024*15, //15MB
		location =  "D:\\dev\\temp"  //임시로 보관하는 위치(물리적으로 만들어놔야 한다)
		)
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI(); // 전체주소 가져오기
		
		//      /member/memberJoinAction.aws
		
		String[] entity = uri.split("/");  // 스플릿으로 자르기 
		
		if(entity[1].equals("member")) {
			MemberController mc = new MemberController(entity[2]);
			mc.doGet(request, response);
						
		}else if(entity[1].equals("board")) {
			BoardController bc = new BoardController(entity[2]);
			bc.doGet(request, response);
						
		}
		
		
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
