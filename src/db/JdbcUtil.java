package db;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class JdbcUtil {
// DB���� ���� �� ���� �޼ҵ���� ������ Ŭ���� - ��� �޼ҵ�� public static���� �����
	public static Connection getConnection() {
	// DB�� �����Ͽ� ���ؼ� ��ü�� �����ϴ� �޼ҵ�	
		Connection conn = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQLDB");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			// ������ �ڵ����� commit �Ǵ� ���� ���� ������� Ʈ����� ���۽�Ŵ 
		} catch(Exception e) {
			System.out.println("DB ���� ����!!!!!!!!!!!");
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn) {
		try {
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}		// Connection ��ü�� �ݾ� DB���� ������ �����ִ� �޼ҵ�
	}
	
	public static void close(Statement stmt) {
	// PreparedStatement�� CallabledStatement�� Statement�� ��ӹ����Ƿ� ���� close()�޼ҵ尡 �ʿ� ����
		try {
			stmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}		// Statement ��ü�� �ݾ��ִ� �޼ҵ�
	}
	
	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
		}		// ResultSet ��ü�� �ݾ��ִ� �޼ҵ�
	}
	
	public static void commit(Connection conn) {
	// transaction�� commit ��Ű�� �޼ҵ�
		try {
			conn.commit();
			System.out.println("���� ����");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
	// transaction�� rollback ��Ű�� �޼ҵ�
		try {
			conn.rollback();
			System.out.println("���� ����");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
