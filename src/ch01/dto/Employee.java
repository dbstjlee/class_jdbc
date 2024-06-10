package ch01.dto;

import lombok.Getter;
import lombok.Setter;

// DTO 설계하고 
// 값을 담아서 . 연산자를 사용해 보시오. 
@Setter
@Getter
public class Employee {

	public int id;
	public String name;
	public String department; 

}
