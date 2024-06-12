package ch04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionExample {

	// 자동(기본값 - true), 수동 커밋(변경 사항 저장)
	public static void main(String[] args) {

		// 드라이버 -> MySQL 개발자들이 자바 코드로 작성한 클래스의 묶음(.jar)
		// ver 8.0 이상
		String url = "jdbc:mysql://localhost:3306/m_board?serverTimezone=Asia/Seoul";
		String id = "root";
		String password = "asd123";

		// 구현체를 사용하기 위해서
		// 클래스 Class <-- 최상위 Object 안에 있음
		// 동적 바인딩 처리 => 코드 작성 시점 = 컴파일 시점(문자열)
		// -> 클래스 Class 이용 -> 런타임 시점의 이 패키지를 데이터 타입으로 가지고 옴.
		try {
			// mysql 드라이버(구현클래스) 메모리에 로드
			Class.forName("com.mysql.cj.jdbc.Driver");

			// try catch resource 문법
			try (Connection conn = DriverManager.getConnection(url, id, password)) {

				conn.setAutoCommit(false); // 수동 커밋 모드 설정
				String sqlInsert = " insert into user (username, password, email, userRole, address, createDate)"
						+ "values(?, ?, ?, ?, ?, now())";
				// 공백 주의 ""; => " "; (공백 띄운 후에 작성하기)

				PreparedStatement psmt1 = conn.prepareStatement(sqlInsert);
				psmt1.setString(1, "김영희");
				psmt1.setString(2, "asd123");
				psmt1.setString(3, "b@nate.com");
				psmt1.setString(4, "user");
				psmt1.setString(5, "부산시진구");
				psmt1.executeUpdate();

				String sqlUpdate = "UPDATE user SET email = ? WHERE username = ?";
				PreparedStatement psmt2 = conn.prepareStatement(sqlUpdate);
				psmt2.setString(1, "c@naver.com");
				psmt2.setString(2, "김유신");
				psmt2.executeUpdate();

				// 수동 커밋 모드를 설정했다면 직접 commit()을 실행해야
				// 물리적인 저장장치에 영구히 반영이 된다.
				conn.commit();

//				if (true) {
//					conn.commit(); // commit 되기 전에 빠져버려서 오류 발생
//				} else {
//					conn.rollback(); //데이터 변경 사항이 취소 -> 데이터 이전 상태로 복구
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}// end of main

}
