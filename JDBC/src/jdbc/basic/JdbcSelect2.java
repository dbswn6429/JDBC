package jdbc.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JdbcSelect2 {
	public static void main(String[] args) {
		
		/*
		 * 회원의 id를 입력받아서, 해당 id에 회원 정보만 출력하는 jdbc프로그램을 작엇
		 * id가 없으면, "id는 없습니다"로 출력합니다.
		 */
		
		Connection conn = null; //연결객체
		PreparedStatement pstmt = null; //sql을 실행하기 위한 객체
		ResultSet rs = null; //sql을 실행한 결과를 반환 받을 객체
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uid = "HR";
		String upw = "HR";
		
		Scanner sc = new Scanner(System.in);
		System.out.println("조회할 회원 NO를 입력하세요>");
		String inputNo = sc.nextLine(); 
		
		String sql = "SELECT * FROM MEMBERS WHERE MNO = ?";
		
		try {
			//1.드라이버 클래스
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2. conn
			conn = DriverManager.getConnection(url, uid, upw);
			//3. pstmt
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputNo);
			//3. 실행
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println("회원정보:");
				System.out.println("회원번호:" + rs.getString("MNO"));
				System.out.println("이름:" + rs.getString("NAME"));
				System.out.println("주소:" + rs.getString("ADDRESS"));
				System.out.println("날짜:" + rs.getDate("regdate"));
			} else {
				System.out.println("회원번호는 없습니다");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			} catch (Exception e2) {
			}
			sc.close();
		}
	}
}
