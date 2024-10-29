package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mvc.dbcon.Dbconn;
import mvc.vo.BoardVo;
import mvc.vo.Criteria;
import mvc.vo.SearchCriteria;

public class BoardDao {
	
	private Connection conn; // 전역적으로 쓴다 연결객체를
	private PreparedStatement pstmt;  // 전역객체
	public BoardDao() {  // 생성자를 만든다 왜? DB연결하는 Dbconn 객체를 생성할려고..... 생성해야 MYSQL에 접속이 가능해서
		Dbconn db = new Dbconn(); //conn 객체생성
		this.conn = db.getConnetcion();
			
		
	}
	
	
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri) {
		int page = scri.getPage(); // 페이지 번호
		int perPageNum = scri.getPerPageNum(); //화면노출갯수
		
		//키워드가 존재한다면 like구문을 활용한다
		
		
		String str = "";
		String keyword = scri.getKeyword();
		String searchType = scri.getSearchType();
		
		if(!scri.getKeyword().equals("")) {
			
			str = "and "+searchType+" like concat('%','"+keyword+"','%') ";
			
		}		
		
		ArrayList<BoardVo> alist = new ArrayList<BoardVo>();  // ArrayList 컬렉션 객체에 BoardVo를 담겠다 BoardVo안에는 컬럼값을 담겠다 
		    	
	    
	    	String sql = "select * from board where delyn='N'"+str+" order by originbidx desc, depth asc limit ?,?";
	    	ResultSet rs = null; // DB값을 가져오기 위한 전용클래스
	    	
	    	
	    	try {
	    	pstmt = conn.prepareStatement(sql);
	    	pstmt.setInt(1, (page-1)*perPageNum);
	    	pstmt.setInt(2, perPageNum);
	    	
	    	rs = pstmt.executeQuery();
	    	
	    	while(rs.next()) { // 커서가 다음르로 이동해서 첫글이 있느냐 물어보고 true면 진행
	    		int bidx = rs.getInt("bidx");
	    		String boardSubject = rs.getString("subject");
	    		String boardContents = rs.getString("contents");
	    		String boardWriter = rs.getString("writer");
	    		String boardWriteday = rs.getString("writeday");
	    		int boardRecom = rs.getInt("recom");
	    		int boardViewcnt = rs.getInt("viewcnt");
	    		int boardLevel_ = rs.getInt("level_");
	    		
	    		BoardVo bv = new BoardVo();  // 첫행부터 mv에 옮겨담기
	    		bv.setBidx(bidx);
	    		bv.setSubject(boardSubject);
	    		bv.setContents(boardContents);
	    		bv.setWriter(boardWriter);
	    		bv.setWriteday(boardWriteday);
	    		bv.setRecom(boardRecom);
	    		bv.setViewcnt(boardViewcnt);
	    		bv.setLevel_(boardLevel_);
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
			
	
	
	
			//개시물 전체 갯수 구하기
			public int boardTotalCount(SearchCriteria scri) {
				
				
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
	
	
		public int boardInsert(BoardVo bv) {
			
			int value = 0;
			
			String subject = bv.getSubject();
			String contents = bv.getContents();
			String writer = bv.getWriter();
			String password = bv.getPassword();
			int midx = bv.getMidx();
			String filename = bv.getFilename();
			String ip = bv.getIp();					
			String sql = "INSERT INTO board(originbidx,depth,level_,SUBJECT,contents,writer,password,midx,filename,ip)"
					+ "value(null,0,0,?,?,?,?,?,?,?)";
			
			String sql2 = "update board set originbidx = (select A.maxbidx from (SELECT max(bidx) as maxbidx from board)A)"
					+"where bidx = (select A.maxbidx from (SELECT max(bidx) as maxbidx from board)A)";
			
			try {
				
			conn.setAutoCommit(false);  // 수동커밋으로 하겠다는 뜻
				
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,subject);
			pstmt.setString(2,contents);
			pstmt.setString(3,writer);
			pstmt.setString(4,password);
			pstmt.setInt(5,midx);
			pstmt.setString(6,filename);
			pstmt.setString(7,ip);
			int exec = pstmt.executeUpdate();  //실행되면 1 아니면 0
			
			pstmt = conn.prepareStatement(sql2);
			int exec2 = pstmt.executeUpdate();   //실행되면 1 아니면 0
			
			conn.commit();  // 일괄처리 커밋
			
			value = exec+exec2;
			
			}catch(SQLException e) {
				
				try {
					conn.rollback(); //실행중 오류 발생시 롤백처리
				}catch(SQLException e1) {
					e1.printStackTrace();
				}
				
				
				
				e.printStackTrace();
				
				
			}finally {
				try {  
				
				pstmt.close();
				//conn.close();
				}catch(SQLException e) {
	    			e.printStackTrace();
	    			
	    		}
				
				
			}
			return value;
		}
		
		public BoardVo boardSelectOne(int bidx) {
			//1 형식부터만든다
			BoardVo bv = null;
			//2 사용할 쿼리를 준비한다
			
			String sql = "SELECT * FROM board WHERE delyn = 'N' and bidx = ?";
			
			//3 conn 연결객체에서 쿼리실행 구문 클래스를 불러온다
			ResultSet rs = null;
			try {
			pstmt = conn.prepareStatement(sql);  // pstmtm는 전역변수로 선언했다 (PreparedStatement객체)
			pstmt.setInt(1, bidx);    // 첫번째 물음표에 매개변수 bidx값을 담아서 구문을 완성한다
 			rs = pstmt.executeQuery(); // 쿼리를 실행해서 결과값을 컬럼전용클래스인 ResultSet 객체에 담는다
			
 			if(rs.next()==true) {  // rs.next()는 커서를 다음줄로 이동시킨다. 맨처음 커서는 상단에 위치되어 있다.
 				//값이 존재한다면 BoardVo 객체에 꺼내서 담는다
 				String subject = rs.getString("subject");
 				String contents = rs.getString("contents");
 				String writer = rs.getString("writer");
 				String writeday = rs.getString("writeday");
 				int viewcnt = rs.getInt("viewcnt");
 				int recom = rs.getInt("recom");
 				String filename = rs.getString("filename");
 				
 				int rtnBidx = rs.getInt("bidx");
 				int originbidx = rs.getInt("originbidx");
 				int depth = rs.getInt("depth");
 				int level_ = rs.getInt("level_");
 				
 			//System.out.println("rtnBidx"+rtnBidx);
				//System.out.println("originbidx"+originbidx);
 				
 				//System.out.println("depth"+depth);
				//System.out.println("level_"+level_);
				
 				String password = rs.getString("password");
 				
 				bv = new BoardVo();  // 객체생성해서 지역변수 bv로 담아서 리턴해서 가져간다
 				bv.setSubject(subject);
 				bv.setContents(contents);
 				bv.setWriter(writer);
 				bv.setWriteday(writeday);
 				bv.setViewcnt(viewcnt);
 				bv.setRecom(recom);
 				bv.setFilename(filename);
 				bv.setBidx(rtnBidx);
 				bv.setOriginbidx(originbidx);
 				bv.setDepth(depth);
 				bv.setLevel_(level_);
 				bv.setPassword(password);
 			}
 			
 			
			}catch(SQLException e){
				e.printStackTrace();
			}finally {
				try {  
				rs.close();
				pstmt.close();
				conn.close();
				}catch(SQLException e) {
	    			e.printStackTrace();
	    			
	    		}
				
				
			}
			
			return bv;
		}
		
		public BoardVo boardToModify(int bidx) {
			//1 형식부터만든다
			BoardVo bv = null;
			//2 사용할 쿼리를 준비한다
			
			String sql = "SELECT * FROM board WHERE delyn = 'N' and bidx = ?";
			
			//3 conn 연결객체에서 쿼리실행 구문 클래스를 불러온다
			ResultSet rs = null;
			try {
			pstmt = conn.prepareStatement(sql);  // pstmtm는 전역변수로 선언했다 (PreparedStatement객체)
			pstmt.setInt(1, bidx);    // 첫번째 물음표에 매개변수 bidx값을 담아서 구문을 완성한다
 			rs = pstmt.executeQuery(); // 쿼리를 실행해서 결과값을 컬럼전용클래스인 ResultSet 객체에 담는다
			
 			if(rs.next()==true) {  // rs.next()는 커서를 다음줄로 이동시킨다. 맨처음 커서는 상단에 위치되어 있다.
 				//값이 존재한다면 BoardVo 객체에 꺼내서 담는다
 				String subject = rs.getString("subject");
 				String contents = rs.getString("contents");
 				String writer = rs.getString("writer");
 				String writeday = rs.getString("writeday");
 				int viewcnt = rs.getInt("viewcnt");
 				int recom = rs.getInt("recom");
 				String filename = rs.getString("filename");
 				int rtnBidx = rs.getInt("bidx");
 				int originbidx = rs.getInt("originbidx");
 				int depth = rs.getInt("depth");
 				int level_ = rs.getInt("level_");
 				
 				
 				bv = new BoardVo();  // 객체생성해서 지역변수 bv로 담아서 리턴해서 가져간다
 				bv.setSubject(subject);
 				bv.setContents(contents);
 				bv.setWriter(writer);
 				bv.setWriteday(writeday);
 				bv.setViewcnt(viewcnt);
 				bv.setRecom(recom);
 				bv.setFilename(filename);
 				bv.setBidx(originbidx);
 				bv.setOriginbidx(originbidx);
 				bv.setDepth(depth);
 				bv.setLevel_(level_);
 				
 			}
 			
 			
			}catch(SQLException e){
				e.printStackTrace();
			}finally {
				try {  
				rs.close();
				pstmt.close();
				conn.close();
				}catch(SQLException e) {
	    			e.printStackTrace();
	    			
	    		}
				
				
			}
			
			return bv;
		}
		
		
		
		//게시물 수정하기
		public int boardUpdate(BoardVo bv) {
		
			int value = 0;
			
			String sql = "update board set subject = ?, contents=?,writer=?,modifyday=now() where bidx=? and password =?";			
			
			try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bv.getSubject());
			pstmt.setString(2, bv.getContents());
			pstmt.setString(3, bv.getWriter());
			pstmt.setInt(4, bv.getBidx());
			pstmt.setString(5, bv.getPassword());
			value = pstmt.executeUpdate();
			
			
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
		
		
		public int boardViewCntUpdate(int bidx) {
			
			int value = 0;
			
			String sql = "UPDATE board SET viewcnt = viewcnt+1 WHERE bidx=?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bidx);
				value = pstmt.executeUpdate(); //성공하면 1 실패하면0
			
				
			}catch(SQLException e) {
				e.printStackTrace();
				
			}finally {
				try { 
				pstmt.close();
				}catch(SQLException e) {
	    			e.printStackTrace();  //conn을 끊으면 다음 메소드가 작동하지 않으므로 끊지 않는다
	    			
	    		}
								
			}
						
			return value;
		}
		
		
		
		public int boardRecomUpdate(int bidx) {
			
			int value = 0;
			int recom = 0;
			
			
			
			String sql = "UPDATE board SET recom = recom+1 WHERE bidx=?";
			String sql2 = "select recom from board where bidx=?";
			ResultSet rs = null;
			
			try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bidx);
			value = pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, bidx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				recom = rs.getInt("recom");
				
			
			
			
			}
			
			
			
			
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
			
			
			return recom;   // 설명다시 필요함
		}
		
		
		public int boardDelete(int bidx, String password) {
			
			int value = 0;
			
			String sql = "UPDATE board SET delyn = 'Y' WHERE bidx =? and password=?";
			
			try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,bidx);
			pstmt.setString(2,password);
			
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
		
		public int boardReply(BoardVo bv) {
			
			int value = 0;
			int maxbidx = 0;
			
			String sql = "update board set depth = depth+1 where originbidx = ? and depth > ?";
			String sql2 = "insert into board (originbidx,depth,level_,subject,contents,writer,midx,filename,password,ip) "
					 + "values(?,?,?,?,?,?,?,?,?,?)";
			
			String sql3 = "select max(bidx) as maxbidx from board where originbidx = ?";
			
			try {
				
				conn.setAutoCommit(false);  // 수동커밋으로 하겠다는 뜻
					
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,bv.getOriginbidx());
				pstmt.setInt(2,bv.getDepth());
				int exec = pstmt.executeUpdate();  //실행되면 1 아니면 0
				
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, bv.getOriginbidx());
				pstmt.setInt(2, bv.getDepth()+1);
				//System.out.println("다오뎁스"+bv.getDepth());
				pstmt.setInt(3, bv.getLevel_()+1);
				//System.out.println("다오레벨"+bv.getLevel_());
				pstmt.setString(4, bv.getSubject());
				pstmt.setString(5, bv.getContents());
				pstmt.setString(6, bv.getWriter());
				pstmt.setInt(7, bv.getMidx());
				pstmt.setString(8, bv.getFilename());
				pstmt.setString(9, bv.getPassword());
				pstmt.setString(10, bv.getIp());
				
				int exec2 = pstmt.executeUpdate();   //실행되면 1 아니면 0
				
				ResultSet rs = null;
				pstmt = conn.prepareStatement(sql3);
				pstmt.setInt(1, bv.getOriginbidx());
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					maxbidx = rs.getInt("maxbidx");
					
					
				}
				
				conn.commit();  // 일괄처리 커밋
				
				//value = exec+exec2;
				
				}catch(SQLException e) {
					
					try {
						conn.rollback(); //실행중 오류 발생시 롤백처리
					}catch(SQLException e1) {
						e1.printStackTrace();
					}
					
					
					
					e.printStackTrace();
					
					
				}finally {
					try {  
					
					pstmt.close();
					conn.close();
					}catch(SQLException e) {
		    			e.printStackTrace();
		    			
		    		}
					
					
				}
			
			
			return maxbidx;
		}
		


}
