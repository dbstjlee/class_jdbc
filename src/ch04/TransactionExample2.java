package ch04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionExample2 {

	// 트랜잭션 : 하나의 거래를 안전하게 처리하도록 보장
	// 트랜잭션이 없으면 원자성이 적용 안됨.
	
	// 자동, 수동 커밋
	// 자동 커밋 : 커밋, 롤백 직접 호출 필요X, 명령 즉시 커밋함. -> 트랜잭션 구성X
	// => 원하는 기능 사용X
	// 수동 커밋 : 변경 사항을 모으기 위해 항상 트랜잭션 구성함.
	public static void main(String[] args) {

		String url = "jdbc:mysql://localhost:3306/m_board?serverTimezone=Asia/Seoul";
		String id = "root";
		String password = "asd123";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, id, password);
			conn.setAutoCommit(false); // 수동 커밋(true - 자동 커밋이 기본값)
			
			String sqlInsert = "insert into board(id, userId, title, content, readCount)"
					+"values(?, ?, ?, ?, ?)";
			PreparedStatement psmt1 = conn.prepareStatement(sqlInsert);
			psmt1.setInt(1, 6);
			psmt1.setInt(2, 6);
			psmt1.setString(3, "제목");
			psmt1.setString(4, "내용");
			psmt1.setInt(5, 100);
			psmt1.executeUpdate();
			
			String sqlInsert2 = "insert into reply(id, userId, boardId, content, createDate)"
					+"values(?, ?, ?, ?, now())";
			PreparedStatement psmt2 = conn.prepareStatement(sqlInsert2);
			psmt2.setInt(1, 10);
			psmt2.setInt(2, 1);
			psmt2.setInt(3, 5);
			psmt2.setString(4, "댓글");
			psmt2.executeUpdate();
			
			conn.commit(); // 수동 커밋(직접 실행)
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}// end of main

}
