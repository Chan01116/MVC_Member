package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mvc.dbcon.Dbconn;
import mvc.vo.BoardVo;

public class BoardDao {
	
	private Connection conn; // 전역적으로 쓴다 연결객체를
	private PreparedStatement pstmt;
	public BoardDao() {  // 생성자를 만든다 왜? DB연결하는 Dbconn 객체를 생성할려고..... 생성해야 MYSQL에 접속이 가능해서
		Dbconn db = new Dbconn(); //conn 객체생성
		this.conn = db.getConnetcion();
			
		
	}
	
	
	public ArrayList<BoardVo> boardSelectAll() {
		ArrayList<BoardVo> alist = new ArrayList<BoardVo>();  // ArrayList 컬렉션 객체에 BoardVo를 담겠다 BoardVo안에는 컬럼값을 담겠다 
		    	
	    
	    	String sql = "select *from board order by originbidx desc, depth asc";
	    	ResultSet rs = null; // DB값을 가져오기 위한 전용클래스
	    	
	    	
	    	try {
	    	pstmt = conn.prepareStatement(sql);
	    	rs = pstmt.executeQuery();
	    	
	    	while(rs.next()) { // 커서가 다음르로 이동해서 첫글이 있느냐 물어보고 true면 진행
	    		int bidx = rs.getInt("bidx");
	    		String boardSubject = rs.getString("subject");
	    		String boardContents = rs.getString("contents");
	    		String boardWriter = rs.getString("writer");
	    		String boardWriteday = rs.getString("writeday");
	    		int boardRecom = rs.getInt("recom");
	    		
	    		BoardVo bv = new BoardVo();  // 첫행부터 mv에 옮겨담기
	    		bv.setBidx(bidx);
	    		bv.setSubject(boardSubject);
	    		bv.setContents(boardContents);
	    		bv.setWriter(boardWriter);
	    		bv.setWriteday(boardWriteday);
	    		bv.setRecom(boardRecom);
	    		alist.add(bv);				//ArrayList객체에 하나씩 추가하고 리턴한다
	    		    		
	    	}
	    	
	    	}catch(SQLException e) {
				e.printStackTrace();
				
			} finally {
				try {
					rs.close();
				pstmt.close();
				conn.close();
				}catch(SQLException e) {
	    			e.printStackTrace();
	    			
	    		}
			}
	    	   	     	
	    	return alist;
	    }
		


}
