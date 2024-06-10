package ch01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ch01.dto.Employee;

public class MySQLJdbcExample {

	public static void main(String[] args) {
		

		// 준비물
		String url = "jdbc:mysql://localhost:3306/mydb2?serverTimezone=Asia/Seoul";
		String user = "root"; // 상용서비스에서 절대 루트 계정 사용 금지
		String password = "asd123";

		// 필요 데이터 타입
		// JDBC API 레벨(자바 개발자들이 개념화 시켜 놓은 클래스들이다.)
		// java.sql.Connection이라는 데이터 타입이 필요함.
		// 3개 전부 인터페이스
		Connection connection = null; // 연결을 관리하는 데이터 타입
		Statement statement = null; // 쿼리를 실행
		ResultSet resultSet = null; // 응답 받음

		// 1. MySQL 구현체를 사용하겠다는 설정을 해야 한다.
		// JDBC 드라이버 로드를 해줘야 함. (그 중 MySQL 드라이버 구현 클래스를 로드함)
		try {
			// 1. 메모리에 사용하는 드라이버(JDBC API를 구현한 클래스) 클래스를 띄운다.
			Class.forName("com.mysql.cj.jdbc.Driver"); // 컴파일 시점은 문자열

			// 2. 데이터 베이스의 연결 설정
			connection = DriverManager.getConnection(url, user, password);
			// 생성된 객체 connection에 넣어야 오류 안 뜸
			
			// 3. SQL 실행
			statement = connection.createStatement();
			// 2가지 기억하기(쿼리를 실행 시키는 메서드)
			resultSet = statement.executeQuery("SELECT * FROM employee limit = 1"); // select 실행 시 사용한다.
			// statement.executeUpdate(password); --> insert, Update, delete 사용

			// 구문 분석 -- 파싱

			// 4. 결과 처리
			// 5개의 다중 구문이 while 문을 통해 5번 반복해서 출력됨.
			List<Employee> list = new ArrayList<>();
			
			Employee employee = null;// null 값을 넣어준 후 객체 생성
			// while문 돌 때 덮어쓰기가 됨. 
			while (resultSet.next()) {
//				System.out.println("USER ID : " + resultSet.getInt("id"));
//				System.out.println("USER NAME : " + resultSet.getString("name"));
//				System.out.println("department : " + resultSet.getString("department"));
//				System.out.println("-------------------------------------");
				
				employee = new Employee();
				// resultSet.getInt("id") 이 결과를 employee.id 여기에 담음
				employee.id = resultSet.getInt("id"); 
				employee.name = resultSet.getString("name");
				employee.department = resultSet.getString("department");
				list.add(employee); // employee를 통으로 list에 붙임.
				employee.toString();
				
				for (Employee em : list) {
					System.out.println();
				}
				
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// end of main

} // end of class
