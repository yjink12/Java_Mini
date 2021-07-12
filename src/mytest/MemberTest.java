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
			System.out.println("1. ȸ������ | 2. ȸ������ | 3. ȸ������ | 4. ȸ������ | 5. ��ü���� | 6. ����");
			System.out.println("�̿��Ͻ� ���񽺸� �����ϼ���");
			int num=Integer.parseInt(sc.nextLine());
			
			String id=null;
			
			switch(num) {
			case 1:
				//�����Է�
				System.out.println("���̵� �Է��ϼ���");
				id=sc.nextLine();
				if(dao.isCheckId(id)) {
					System.out.println("���� ���̵� �ֽ��ϴ�");
			
				}else {
					System.out.println("�н����带 �Է��ϼ���");
					String pwd=sc.nextLine();
					System.out.println("�̸��� �Է��ϼ���");
					String name=sc.nextLine();
					System.out.println("�̸����� �Է��ϼ���");
					String email=sc.nextLine();
					int result=dao.insert(id, pwd, name, email);	//insert row 0 -> nothing update
					if(result>=1)
						System.out.println("�߰��Ϸ�");
					else
						System.out.println("�߰�����");
				}
				break;
			case 2:
				//���� ����
				System.out.println("������ ���̵� �Է��ϼ���");
				id=sc.nextLine();
				if(!dao.isCheckId(id)) {
					System.out.println("������ ���̵� �����ϴ�");
				}else {
					System.out.println("���ο� �н����带 �Է��ϼ���");
					String pwd=sc.nextLine();
					System.out.println("���ο� �̸����� �Է��ϼ���");
					String email=sc.nextLine();
					int result=dao.update(id, pwd, email);
					if(result>=1)
						System.out.println("���� �Ϸ�");
					else 
						System.out.println("���� ����");
				}
				break;
			case 3:
				//���� ����
				System.out.println("������ ���̵� �Է��ϼ���");
				id=sc.nextLine();
				if(!dao.isCheckId(id)) {
					System.out.println("������ ���̵� �����ϴ�");
				}else {
					int result=dao.delete(id);
					if(result>=1)
						System.out.println("�����Ϸ�");
					else
						System.out.println("���� ����");
				}
				break;
			case 4:
				//���� ����
				System.out.println("��ȸ�� ���̵� �Է��ϼ���");
				id=sc.nextLine();
				if(!dao.isCheckId(id)) {
					System.out.println("��ȸ�� ���̵� �����ϴ�");
				}else {
					dao.read(id);
				}
				break;
					
			case 5:
				//��ü ���� ����
				ArrayList<MemberDTO> result=dao.getAll();
				for(MemberDTO item:result)
					System.out.printf("%d %s %s %s %s %s\n"
							, item.getMno(), item.getId(), item.getPwd()
							, item.getName(), item.getEmail(), item.getJoindate());
				break;
			case 6:
				System.out.println("���α׷��� �����Ͻðڽ��ϱ�? y or n");
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
