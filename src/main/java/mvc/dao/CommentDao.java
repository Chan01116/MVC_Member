package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mvc.dbcon.Dbconn;
import mvc.vo.BoardVo;
import mvc.vo.CommentVo;
import mvc.vo.Criteria;
import mvc.vo.SearchCriteria;

public class CommentDao {
	
	private Connection conn; // 전역적으로 쓴다 연결객체를
	private PreparedStatement pstmt;  // 전역객체
	public CommentDao() {  // 생성자를 만든다 왜? DB연결하는 Dbconn 객체를 생성할려고..... 생성해야 MYSQL에 접속이 가능해서
		Dbconn db = new Dbconn(); //conn 객체생성
		this.conn = db.getConnetcion();
			
		
	}
	
	
	public ArrayList<CommentVo> commentSelectAll(int bidx) {
		  ArrayList<CommentVo> alist = new ArrayList<CommentVo>();
		
		  
		 		  
		  String sql = "select * from comment where delyn='N' and bidx = ? order by cidx desc"; 
		  ResultSet rs = null;		  // DB값을 가져오기 위한 전용클래스
		  
		  
		 try { 
		      pstmt = conn.prepareStatement(sql); 
		      pstmt.setInt(1, bidx);
		      rs = pstmt.executeQuery();
		  
		  while(rs.next()) { // 커서가 다음르로 이동해서 첫글이 있느냐 물어보고 true면 진행 int bidx =
		  int cidx = rs.getInt("cidx");
		  
		  String ccontents = rs.getString("ccontents");
		  String cwriter = rs.getString("cwriter");
		  String writeday = rs.getString("writeday");
		  String delyn = rs.getString("delyn");
		  int midx = rs.getInt("midx");
		  CommentVo cv = new CommentVo(); // 첫행부터 mv에 옮겨담기 bv.setBidx(bidx);
		  cv.setCidx(cidx);
		  cv.setCcontents(ccontents);
		  cv.setCwriter(cwriter);
		  cv.setWriteday(writeday);
		  cv.setDelyn(delyn);
		  cv.setMidx(midx);
		  alist.add(cv);
		  }
		  }catch(SQLException e) { 
			 e.printStackTrace();		  
		  } finally { 
			  try { rs.close(); 
		        pstmt.close();
		        conn.close();
	  }catch(SQLException e) { 
		  e.printStackTrace();
	  
	  } 
	  }    
		 return alist;
	}
	/*
			//개시물 전체 갯수 구하기
			public int boardTotalCount(SearchCriteria scri) {
					
				int value = 0;
				
				
				String str = "";
				String keyword = scri.getKeyword();
				String searchType = scri.getSearchType();
				
				if(!scri.getKeyword().equals("")) {
					
					str = "and "+searchType+" like concat('%','"+keyword+"','%') ";
					
				}		
												
				
				int value = 0;
				//1. 쿼리만들기
				String sql = "SELECT COUNT(*) as cnt FROM board WHERE delyn = 'N'"+str+" ";
				//2. conn 객체 안에 있는 구문클래스 호출하기 
				//3. DB컬럼값을 받는 전용클래스 ResultSet을 호출 (ResultSet 특징은 데이터를 그대로 복사하기 떄문에 전달이 빠름)
				ResultSet rs = null;
				try{
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					if(rs.next()) {//커서를 이동시켜서 첫줄로 옮긴다
						value = rs.getInt("cnt");  // 지역변수 value에 담아서 리턴해서 가져간다
						
					}
					
				}catch (SQLException e) {
					e.printStackTrace();
				
				}finally {
					try {  //각 개체도 소멸시키고 DB연결 끊는다
						rs.close();
					pstmt.close();
					//conn.close();
					}catch(SQLException e) {
		    			e.printStackTrace();
		    			
		    		}
				}
					
											
				return value; 
			}
	*/	
	
			public int commentInsert(CommentVo cv) {
				int value=0;
				
				String cwriter = cv.getCwriter();
				String ccontents =cv.getCcontents();
			
				String csubject = cv.getCsubject();
				int bidx = cv.getBidx();
				int midx = cv.getMidx();		
				String cip = cv.getCip();
				
				String sql="insert into comment(csubject,ccontents,cwriter,bidx,midx,cip)"
						    + " value(null,?,?,?,?,?)";		
				try {			
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, ccontents);
					pstmt.setString(2, cwriter);
					pstmt.setInt(3, bidx);
					pstmt.setInt(4, midx);
					pstmt.setString(5, cip);
					value = pstmt.executeUpdate();    //실행되면 1 안되면 0	
					
				} catch (SQLException e) {				
					e.printStackTrace();
				}finally {
					try {     // 각 객체도 소멸시키고 DB연결 끊는다			
						pstmt.close();
						conn.close();
					} catch (SQLException e) {			
						e.printStackTrace();
					}	
				}		
				
				return value;
			}
		
		
		
		
		public int commentDelete(int cidx) {
			
			int value = 0;
	
			String sql = "UPDATE COMMENT SET delyn = 'Y' WHERE cidx =?";
			
			try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,cidx);
						
			value = pstmt.executeUpdate();  //성공하면 1 실패하면0
			
			}catch(SQLException e) {
				e.printStackTrace();
				
			}finally {
				try {
				pstmt.close();
				conn.close();
				}catch(SQLException e) {
	    			e.printStackTrace();  
	    			
	    		}
			}								
			return value; 
		}
		
		
		


}
