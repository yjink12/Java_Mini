package mytest;

import java.util.ArrayList;
import java.util.Scanner;

import mydao.MemberDAO;
import mydto.MemberDTO;

public class MemberTest {

	public static void main(String[] args) {
		
		Scanner sc=new Scanner(System.in);
		MemberDAO dao=new MemberDAO();
		boolean run=true;
		while(run) {
			
			System.out.println("< Welcome Board >");
			System.out.println("1. 회원가입 | 2. 회원수정 | 3. 회원삭제 | 4. 회원보기 | 5. 전체보기 | 6. 종료");
			System.out.println("이용하실 서비스를 선택하세요");
			int num=Integer.parseInt(sc.nextLine());
			
			String id=null;
			
			switch(num) {
			case 1:
				//정보입력
				System.out.println("아이디를 입력하세요");
				id=sc.nextLine();
				if(dao.isCheckId(id)) {
					System.out.println("기존 아이디가 있습니다");
			
				}else {
					System.out.println("패스워드를 입력하세요");
					String pwd=sc.nextLine();
					System.out.println("이름을 입력하세요");
					String name=sc.nextLine();
					System.out.println("이메일을 입력하세요");
					String email=sc.nextLine();
					int result=dao.insert(id, pwd, name, email);	//insert row 0 -> nothing update
					if(result>=1)
						System.out.println("추가완료");
					else
						System.out.println("추가실패");
				}
				break;
			case 2:
				//정보 수정
				System.out.println("수정할 아이디를 입력하세요");
				id=sc.nextLine();
				if(!dao.isCheckId(id)) {
					System.out.println("수정할 아이디가 없습니다");
				}else {
					System.out.println("새로운 패스워드를 입력하세요");
					String pwd=sc.nextLine();
					System.out.println("새로운 이메일을 입력하세요");
					String email=sc.nextLine();
					int result=dao.update(id, pwd, email);
					if(result>=1)
						System.out.println("수정 완료");
					else 
						System.out.println("수정 실패");
				}
				break;
			case 3:
				//정보 삭제
				System.out.println("삭제할 아이디를 입력하세요");
				id=sc.nextLine();
				if(!dao.isCheckId(id)) {
					System.out.println("삭제할 아이디가 없습니다");
				}else {
					int result=dao.delete(id);
					if(result>=1)
						System.out.println("삭제완료");
					else
						System.out.println("삭제 실패");
				}
				break;
			case 4:
				//정보 보기
				System.out.println("조회할 아이디를 입력하세요");
				id=sc.nextLine();
				if(!dao.isCheckId(id)) {
					System.out.println("조회할 아이디가 없습니다");
				}else {
					dao.read(id);
				}
				break;
					
			case 5:
				//전체 정보 보기
				ArrayList<MemberDTO> result=dao.getAll();
				for(MemberDTO item:result)
					System.out.printf("%d %s %s %s %s %s\n"
							, item.getMno(), item.getId(), item.getPwd()
							, item.getName(), item.getEmail(), item.getJoindate());
				break;
			case 6:
				System.out.println("프로그램을 종료하시겠습니까? y or n");
				String yn=sc.nextLine();
				if(yn.contentEquals("y"))
					System.exit(0);
					break;
			default:
				break;
			}

		}
		
	}

}
