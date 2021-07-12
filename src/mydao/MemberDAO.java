package mydao;
/*
 * 	insert update delete => PreparedStatement executeUpdate()
 *  => row -> int(row count) 
 * 	select => PreparedStatement executeQuery()
 * 	=> next() : 다음 자료를 가리킴 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mydto.MemberDTO;

public class MemberDAO {
	
	//조회하기
	public boolean isCheckId(String id) {
		//select
		Connection conn=getConnection();
		PreparedStatement pstmt=null;	//preparedStatement : 미리 컴파일된 SQL문을 나타내는 개체
		StringBuilder sb=new StringBuilder();
		sb.append(" select                  ");
		sb.append("         id              ");
		sb.append(" from 	Member2         ");
		sb.append(" where 	id=?            ");
		
		ResultSet rs=null;
		boolean result=false;
		try {
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);		//?에 관한 자료들을 외부에서 받아서 사용
			rs=pstmt.executeQuery();
			while(rs.next()) {
				result=true;
			}
		}catch (SQLException e) { 
			System.out.println(e);
		}finally {
			close(pstmt, conn);
		}
		return result;
	}
	
	//db 연결 메소드
	private Connection getConnection(){
		String className="oracle.jdbc.OracleDriver";
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String user="hr";
		String pwd="hr";
		
		//지역변수 Connection 
		Connection conn=null;
		try {
			Class.forName(className);
			conn=DriverManager.getConnection(url, user, pwd);	//DriverManager 연결방식
		}catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);
		}
		return conn;	
	}
	
	
	//insert 기능 메소드
	//insert 메소드 안에 db연결 메소드
	public int insert(String id, String pwd, String name, String email)
	{
		Connection conn=getConnection();
		PreparedStatement pstmt=null;
		StringBuilder sb=new StringBuilder();
		sb.append(" insert into Member2( mno, id, pwd, name, email, joindate ) ");
		sb.append(" values	(	memberseq.nextval, ?, ?, ?, ?, sysdate  )   ");
		//sequence 이용 => 자동으로 순서지정(번호 자동증가)
		int result=0;
		
		try {
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			result=pstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println(e);
		}finally {
			close(pstmt, conn);
		}
		return result;
	}
	
	//update 기능 메소드
	/*
	 * 	update 테이블명
	 * 	set 
	 * 		컬럼명1=값1 
	 * 		, 컬럼명2=값2
	 * 	where 조건
	 * 
	 */
	public int update(String id, String pwd, String email) {
		Connection conn=getConnection();
		PreparedStatement pstmt=null;
		StringBuilder sb=new StringBuilder();
		sb.append("   update     Member2         ");
		sb.append("   set                        ");
		sb.append("       		pwd=?            ");
		sb.append("            , email=?         ");
		sb.append("   where                      ");
		sb.append("            id=?             ");
		int result=0;
		
		try {
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, pwd);
			pstmt.setString(2, email);
			pstmt.setString(3, id);
			result=pstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println(e);
		}finally {
			close(pstmt, conn);
		}
		return result;
		
	}
	
	//delete 기능 메소드
	public int delete(String id) {
		Connection conn=getConnection();
		PreparedStatement pstmt=null;
		StringBuilder sb=new StringBuilder();
		
		sb.append("    delete             ");
		sb.append("    from Member2       ");
		sb.append("    where  id=?        ");
		
		int result=0;
		try {
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);
			result=pstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println(e);
		}finally {
			close(pstmt, conn);
		}
		return result;
	}
	
	//회원 한 명의 정보보기
	public void read(String id) {
		Connection conn=getConnection();
		PreparedStatement pstmt=null;
		StringBuilder sb=new StringBuilder();
		sb.append("  select                 ");
		sb.append("         mno             ");
		sb.append("         , id            ");
		sb.append("         , pwd           ");
		sb.append("         , name          ");
		sb.append("         , email         ");
		sb.append("         , joindate      ");
		sb.append("   from  Member2         ");
		sb.append("   where id=?            ");
		
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();	//select => executeQuery
			while(rs.next()) {	//다음 자료가 있는지 확인
				System.out.println("아이디: "+ rs.getString("id"));	//getString(String columnLabel)
				System.out.println("패스워드: "+ rs.getString("pwd"));
				System.out.println("이름: "+ rs.getString("name"));
				System.out.println("이메일: "+ rs.getString("email"));
				System.out.println("가입일자: "+ rs.getDate("joindate"));
			}
		}catch (SQLException e) {
			System.out.println(e);
		}finally{
			close(pstmt, conn);
		}
	}
	//select 기능 메소드
	//회원 전체의 정보 모두 보기
	
	public ArrayList<MemberDTO> getAll()
	{
		//db연결해서 자료를 받아야함
		//arraylist : db연결 후에 arraylist에 담아서 리턴
		Connection conn=getConnection();
		PreparedStatement pstmt=null;
		StringBuilder sb=new StringBuilder();
		sb.append("  select                 ");
		sb.append("         mno             ");
		sb.append("         , id            ");
		sb.append("         , pwd           ");
		sb.append("         , name          ");
		sb.append("         , email         ");
		sb.append("         , joindate      ");
		sb.append("   from  Member2         ");
		
		ResultSet rs=null;
		ArrayList<MemberDTO> list=new ArrayList<MemberDTO>();
		try {
			pstmt=conn.prepareStatement(sb.toString());
			rs=pstmt.executeQuery();
			while(rs.next()) {
			list.add(new MemberDTO(rs.getInt("mno")	//
					, rs.getString("id"), rs.getString("pwd")
					, rs.getString("name"), rs.getString("email"), rs.getString("joindate")));
			}
		}catch (SQLException e) {
			System.out.println(e);
		}finally {
			close(pstmt, conn);
		}
		return list;
		
	}
	
	//close 기능 메소드
	//close 기능은  dao에서만 사용 => private (외부 접근이 필요 없음)
	private void close(PreparedStatement pstmt, Connection conn) {
		if(pstmt!=null) try {pstmt.close();} catch (SQLException e) {}
		if(conn!=null) try {conn.close();} catch (SQLException e) {}
	}
	
}
