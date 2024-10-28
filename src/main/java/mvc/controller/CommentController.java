package mvc.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import mvc.dao.BoardDao;
import mvc.dao.CommentDao;
import mvc.vo.BoardVo;
import mvc.vo.CommentVo;
import mvc.vo.Criteria;
import mvc.vo.PageMaker;
import mvc.vo.SearchCriteria;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.ArrayList;



@WebServlet("/CommentController")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String location; //멤버변수(전역) 초기화 =>이동할 페이지
	public CommentController(String location) {
		this.location = location;		
	}
    	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * String paramMethod = ""; String url = "";
		 */
		
		
		
		if(location.equals("commentList.aws")) {  // 가상경로
			
			
			//System.out.println("commentList.aws");
			
			
			String bidx = request.getParameter("bidx");
			CommentDao cd = new CommentDao();
			ArrayList<CommentVo> alist = cd.commentSelectAll(Integer.parseInt(bidx));
			
			
			int cidx = 0;
			String cwriter = "";
			String ccontents = "";
			String writeday = "";
			String str = "";
			
			for(int i = 0; i <alist.size(); i++) {
				
				cidx = alist.get(i).getCidx();
				cwriter = alist.get(i).getCwriter();
				ccontents = alist.get(i).getCcontents();
				writeday = alist.get(i).getWriteday();
				
				str = str + "{\"cidx\":\""+cidx+"\",\"cwriter\":\""+cwriter+"\",\"ccontents\":\""+ccontents+"\",\"writeday\":\""+writeday+"\"},";
			}

			// 마지막 쉼표 제거
			if (str.length() > 0) {
			    str = str.substring(0, str.length() - 1);
			}
			
			/*
			 * // 선생님이 만든 마지막 쉼표제거 String cma = ""; for( i == alist.size()-1 ) { cma = "";
			 * 
			 * }else { cma=",";
			 * 
			 * }
			 */
			
			
			
			
			
			//{"a":"1","b":"2","c":"3"},{"a":"1","b":"2","c":"3"}
			
			
			PrintWriter out = response.getWriter();
			out.println("["+str+"]");
			
		 }else if(location.equals("commentWriteAction.aws")) {
			//System.out.println("commentWriteAction.aws");
			
			 			 
			String cwriter = request.getParameter("cwriter");
			//System.out.println("cwriter : "+cwriter);
			String ccontents = request.getParameter("ccontents");
			//System.out.println("ccontents :"+ccontents);
			String bidx = request.getParameter("bidx");
			//System.out.println("bidx : "+bidx);
			String midx = request.getParameter("midx");
			//System.out.println("midx : "+midx);
			//Commnet 객체생성
			
			CommentVo cv = new CommentVo();
			cv.setCwriter(cwriter);
			cv.setCcontents(ccontents);
			cv.setBidx(Integer.parseInt(bidx));
			cv.setMidx(Integer.parseInt(midx));
			
			
			
			CommentDao cd = new CommentDao();
			int value = cd.commentInsert(cv); 
			
			PrintWriter out = response.getWriter();
			
			String str = "{\"value\":\""+value+"\"}";
			out.println(str);
			
			
			
			
			
		 	 }else if(location.equals("commentDeleteAction.aws")) {
					/*
					 * String bidx = request.getParameter("bidx"); String password =
					 * request.getParameter("password");
					 * 
					 * //어디로 이동할것인가를 보여줘야함 BoardDao bd = new BoardDao(); int value =
					 * bd.boardDelete(Integer.parseInt(bidx), password); //결과 값이 0,1
					 * 
					 * paramMethod = "S"; if(value==1) { //성공 paramMethod = "S"; url =
					 * request.getContextPath()+"/board/Board_List.aws";
					 * 
					 * }else { url = request.getContextPath()+"/board/Board_Delete.aws?bidx="+bidx;
					 * }
					 */
				 	
			  }else if(location.equals("Board_Reply.aws")) {
			//System.out.println("들어옴?");
				String bidx = request.getParameter("bidx");
				  
				BoardDao bd = new BoardDao();  
				BoardVo bv = bd.boardSelectOne(Integer.parseInt(bidx));
				  
				 int originbidx = bv.getOriginbidx();
				 int depth = bv.getDepth();
				 int level_ = bv.getLevel_();
				 
				//System.out.println("depth"+depth);
				//System.out.println("level_"+level_);
				 
				 request.setAttribute("bidx",Integer.parseInt(bidx));
				 request.setAttribute("originbidx",originbidx);
				 request.setAttribute("depth",depth);
				 request.setAttribute("level_",level_);
				 
			  
			  }
				  
				  
				 		  
			  
			  
			  }
	
		
				
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	
	
	
public String getUserIp(HttpServletRequest request) throws Exception {
		
        String ip = null;
       // HttpServletRequest request = ((ServletRequestAttributes)ServletRequestContext.currentRequestAttributes()).getRequest();

        ip = request.getHeader("X-Forwarded-For");
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_CLIENT_IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("X-Real-IP"); 
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("X-RealIP"); 
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        }
        
        
        
        if(ip.equals("0:0:0:0:0:0:0:1")||ip.equals("127.0.0.1")) {
        	InetAddress address = InetAddress.getLocalHost();
        	ip = address.getHostName() + "/" + address.getHostAddress();
        	
        	
        }
        
		
		return ip;
	}
	
	
	
	
	
	
	}
