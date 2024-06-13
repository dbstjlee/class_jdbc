package ch05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchExample {

	public static void main(String[] args) {

		String url = "jdbc:mysql://localhost:3306/demo3?serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "asd123";
		Connection conn = null; // . 연산자를 사용하지만 객체가 없어 null 값 넣기

		try {
			// MySQL 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 자바 표준 API
			conn = DriverManager.getConnection(url, user, password);

			// 트랜잭션 처리
			conn.setAutoCommit(false); // 수동 커밋으로 변경

			// 배치 처리 --> User 가상 테이블에 한 번에 사용자 3명을 넣어보자.
			String sql = " INSERT INTO user(name, email) VALUES(?, ?) ";
			PreparedStatement pstmt = conn.prepareStatement(sql); // 쿼리문 뽑아냄

			// 1번 사용자 처리
			pstmt.setString(1, "유저1");
			pstmt.setString(2, "유저1@nate.com");
			// pstmt.executeUpdate();
			// 배치 처리 하기 위해 호출할 메서드가 필요
			pstmt.addBatch(); // 그냥 넣어두면 위 구문이 어딘가에 저장됨.

			// 2번 사용자 처리
			pstmt.setString(1, "유저2");
			pstmt.setString(2, "유저2@nate.com");
			pstmt.addBatch();

			// 3번 사용자 처리
			pstmt.setString(1, "유저3");
			pstmt.setString(2, "유저3@nate.com");
			pstmt.addBatch();

			int[] rowCounts = pstmt.executeBatch();
			// executeBatch()의 리턴 타입 = int[]
			// 몇 개의 row가 반환이 되었는지

			conn.commit(); // 물리적인 저장 장치에 영구히 반영하겠다.

			System.out.println("배치 처리 완료 : " + rowCounts.length);
			// rowCounts.length => 3

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}// end of main
}
