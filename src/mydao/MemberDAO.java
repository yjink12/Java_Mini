package mydao;
/*
 * 	insert update delete => PreparedStatement executeUpdate()
 *  => row -> int(row count) 
 * 	select => PreparedStatement executeQuery()
 * 	=> next() : ���� �ڷḦ ����Ŵ 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mydto.MemberDTO;

public class MemberDAO {
	
	//��ȸ�ϱ�
	public boolean isCheckId(String id) {
		//select
		Connection conn=getConnection();
		PreparedStatement pstmt=null;	//preparedStatement : �̸� �����ϵ� SQL���� ��Ÿ���� ��ü
		StringBuilder sb=new StringBuilder();
		sb.append(" select                  ");
		sb.append("         id              ");
		sb.append(" from 	Member2         ");
		sb.append(" where 	id=?            ");
		
		ResultSet rs=null;
		boolean result=false;
		try {
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);		//?�� ���� �ڷ���� �ܺο��� �޾Ƽ� ���
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
	
	//db ���� �޼ҵ�
	private Connection getConnection(){
		String className="oracle.jdbc.OracleDriver";
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String user="hr";
		String pwd="hr";
		
		//�������� Connection 
		Connection conn=null;
		try {
			Class.forName(className);
			conn=DriverManager.getConnection(url, user, pwd);	//DriverManager ������
		}catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);
		}
		return conn;	
	}
	
	
	//insert ��� �޼ҵ�
	//insert �޼ҵ� �ȿ� db���� �޼ҵ�
	public int insert(String id, String pwd, String name, String email)
	{
		Connection conn=getConnection();
		PreparedStatement pstmt=null;
		StringBuilder sb=new StringBuilder();
		sb.append(" insert into Member2( mno, id, pwd, name, email, joindate ) ");
		sb.append(" values	(	memberseq.nextval, ?, ?, ?, ?, sysdate  )   ");
		//sequence �̿� => �ڵ����� ��������(��ȣ �ڵ�����)
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
	
	//update ��� �޼ҵ�
	/*
	 * 	update ���̺��
	 * 	set 
	 * 		�÷���1=��1 
	 * 		, �÷���2=��2
	 * 	where ����
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
	
	//delete ��� �޼ҵ�
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
	
	//ȸ�� �� ���� ��������
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
			while(rs.next()) {	//���� �ڷᰡ �ִ��� Ȯ��
				System.out.println("���̵�: "+ rs.getString("id"));	//getString(String columnLabel)
				System.out.println("�н�����: "+ rs.getString("pwd"));
				System.out.println("�̸�: "+ rs.getString("name"));
				System.out.println("�̸���: "+ rs.getString("email"));
				System.out.println("��������: "+ rs.getDate("joindate"));
			}
		}catch (SQLException e) {
			System.out.println(e);
		}finally{
			close(pstmt, conn);
		}
	}
	//select ��� �޼ҵ�
	//ȸ�� ��ü�� ���� ��� ����
	
	public ArrayList<MemberDTO> getAll()
	{
		//db�����ؼ� �ڷḦ �޾ƾ���
		//arraylist : db���� �Ŀ� arraylist�� ��Ƽ� ����
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
	
	//close ��� �޼ҵ�
	//close �����  dao������ ��� => private (�ܺ� ������ �ʿ� ����)
	private void close(PreparedStatement pstmt, Connection conn) {
		if(pstmt!=null) try {pstmt.close();} catch (SQLException e) {}
		if(conn!=null) try {conn.close();} catch (SQLException e) {}
	}
	
}
