package jdbc.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JdbcUpdate {
	public static void main(String[] args) {
		
		/*
		 * 업데이트도 insert와 동일하게 처리합니다.
		 * 
		 * 회원번호, 이름, 주소를 입력받아서 해당 회원의 이름과 주소를 변경해주세요.
		 * 
		 * 만약에 회원번호가 없으면 "회원번호가 없습니다"를 출력하면 됩니다.
		 */
		
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uid = "HR";
		String upw = "HR";
		
		String checkSql = "SELECT * FROM MEMBERS WHERE MNO = ?";
	
				
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url,uid,upw);

			System.out.println("수정할 회원번호>");
			int mno = sc.nextInt();
			sc.nextLine();
			
			pstmt = conn.prepareStatement(checkSql);
			pstmt.setInt(1, mno);
			ResultSet rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				System.out.println("회원번호가 존재하지 않습니다");
				return;
			}
			System.out.println("이름>");
			String name = sc.nextLine();
			System.out.println("주소");
			String address = sc.nextLine();
			
			String sql = "UPDATE MEMBERS SET NAME = ?, ADDRESS = ? WHERE MNO = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setInt(3, mno);
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                System.out.println("회원 정보가 성공적으로 수정되었습니다.");
            } else {
                System.out.println("업데이트에 실패했습니다.");
            }

            rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e) {
			} 
		}
		sc.close();
		
	}
}
