package world.hkk.firstProject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Manage {
	DVD dvd[]=new DVD[50];
	int count=5;
	boolean stop=false;
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	Date time=new Date();

	Manage(){
		dvd[0]=new DVD("�ڰ���ʿ","48.8",true,"����","2017-12-15",5);
		dvd[1]=new DVD("�Ǽʴ�Խ","36.6",false,"  ","          ",1);
		dvd[2]=new DVD("�ؿ̶���","59.8",true,"����","2018-02-28",2);
		dvd[3]=new DVD("������Ƭ","37.5",false,"  ","          ",0);
		dvd[4]=new DVD("���οռ�","45.9",false,"  ","          ",2);
	}
	void init(){//��ʼ�����˵�
		System.out.println("\n------��ӭʹ��DVD����ϵͳ------\n"
				+ "------------���˵�------------\n"
				+ "1,�鿴����DVD\n2,�鿴�ѽ��DVD\n3,�鿴δ���DVD\n4,"
				+ "ɾ��DVD\n5,���DVD\n6,���DVD\n7,�黹DVD\n8,DVD���а�\n"
				+ "9,�˳�\n��ѡ���Ӧ�Ĳ���(1/2/3/4/5/6/7/8/9):");
	}
	int searchIndex(String name){//����ӰƬ�������±�
		int index=-1;
		for(int i=0;i<count;++i)if(dvd[i].getName().equals(name))index=i;
		return index;
	}
	void list(boolean special){//1,�鿴����DVD
		System.out.println("\nӰƬ\t\t�۸�\t״̬\t������\t�������\t\t���Ĵ���");
		for(int i=0;i<count;++i)dvd[i].showAll();
		if(special)isContinue();
	}
	void lendList(boolean special){//2,�鿴�ѽ��DVD
		System.out.println("\nӰƬ\t\t�۸�\t������\t�������\t\t���Ĵ���");
		for(int i=0;i<count;++i)dvd[i].showOnlyIsLend();
		if(special)isContinue();
	}
	void notLendList(boolean special){//3,�鿴δ���DVD
		System.out.println("\nӰƬ\t\t�۸�\t���Ĵ���");
		for(int i=0;i<count;++i)dvd[i].showOnlyNotLend();
		if(special)isContinue();
	}
	void delete(){//4,ɾ��DVD
		System.out.println("------------ɾ��DVD----------");
		boolean first=true;
		while(true){
			if(!first){
				System.out.println("DVD�б�����:");
				list(false);
				System.out.println("\n��ο��˱�����:");
			}else System.out.println("������DVDӰƬ��:");
			int index=searchIndex(input());
			if(index==-1){
				first=false;
				System.out.print("�������ӰƬ��������!");
				continue;
			}
			else if(dvd[index].isLend()){
				System.out.println("��DVD�ѱ����,��Ҫɾ��?(����yȷ��)");
				if(input().equals("y")){
					for(int i=index;i<count-1;++i)dvd[i]=dvd[i+1];
					--count;
				}else {
					first=true;
					continue;
				}
			}else {
				for(int i=index;i<count-1;++i)dvd[i]=dvd[i+1];
				--count;
			}
			int tmp=miniMenu("ɾ��");
			if(tmp==1){
				first=true;
				continue;
			}
			if(tmp==2)break;
			if(tmp==3)stop=true;break;
		}
	}
	void add(){//5,���DVD
		System.out.println("------------���DVD----------");
		boolean first=true;
		while(true){
			if(!first){
				System.out.println("DVD�б�����:");
				list(false);
				System.out.println("\n��ο��˱�����:");
			}
			else System.out.println("������DVDӰƬ��:");
			String name=input();
			int index=searchIndex(name);
			if(index!=-1){
				first=false;
				System.out.print("��Ҫ��ӵ�DVD�Ѵ���!");
				continue;
			}
			System.out.println("������ӰƬ�۸�:");
			String price=input();
			++count;
			dvd[count-1]=new DVD(name,price);
			int tmp=miniMenu("���");
			if(tmp==1){
				first=true;
				continue;
			}
			if(tmp==2)break;
			if(tmp==3)stop=true;break;
		}
	}
	void rent(){//6,����
		System.out.println("------------����DVD----------");
		boolean first=true;
		while(true){
			if(!first){
				notLendList(false);
				System.out.println("\n��ο��˱�����ӰƬ��:");
			}
			else System.out.println("������DVDӰƬ��:");
			String name=input();
			int index=searchIndex(name);
			if(index==-1){
				first=false;
				System.out.println("��ӰƬ������!����Ϊ����δ���DVD:");
				continue;
			}
			if(dvd[index].isLend()){
				first=false;
				System.out.println("����ʧ��!��ӰƬ�ѱ�����!����Ϊ����δ���DVD:");
				continue;
			}
			System.out.println("����������������:");
			dvd[index].setTenant(input());//��������
			dvd[index].setLend(true);//״̬
			++dvd[index].rentTimes;//���Ĵ���
			dvd[index].setLendDate(sdf.format(time));//����ʱ��
			int tmp=miniMenu("����");
			if(tmp==1){
				first=true;
				continue;
			}
			if(tmp==2)break;
			if(tmp==3)stop=true;break;
		}
	}
	void pay() throws Exception{//7,�黹
		System.out.println("------------�黹DVD----------");
		boolean first=true;
		while(true){
			if(!first){
				lendList(false);
				System.out.println("\n��ο��˱�����ӰƬ��:");
			}
			else System.out.println("������DVDӰƬ��:");
			int index=searchIndex(input());
			if(index==-1){
				first=false;
				System.out.println("��ӰƬ������!����Ϊ���н��DVD:");
				continue;
			}
			if(!dvd[index].isLend()){
				first=false;
				System.out.println("��ӰƬδ���,����黹!����Ϊ���н��DVD:");
				continue;
			}
			dvd[index].setLend(false);
			dvd[index].setTenant("  ");
			Date t=sdf.parse(dvd[index].getLendDate());//������ʱ���ڶ���!!!!!!!!!!!!!!!!!!
			dvd[index].setLendDate("          ");
			long days=(time.getTime()-t.getTime())/(24*60*60*1000);
			System.out.println("���Ϊ��"+(days+1)+"Ԫ");
			int tmp=miniMenu("�黹");
			if(tmp==1){
				first=true;
				continue;
			}
			if(tmp==2)break;
			if(tmp==3)stop=true;break;
		}
	}
	void rankList(){//8,���а�
		int tmp[]=new int[count];
		for(int i=0;i<count;++i)tmp[i]=dvd[i].rentTimes;
		for(int i=1;i<=count-1;++i)for(int j=0;j<count-i;++j){
			if(tmp[j]==tmp[j+1])tmp[j+1]=-1;
			if(tmp[j]<tmp[j+1])tmp[j]=tmp[j]+tmp[j+1]-(tmp[j+1]=tmp[j]);
		}
		System.out.println("\nӰƬ\t\t�۸�\t״̬\t������\t�������\t\t���Ĵ���");
		for(int i=0;i<count;++i)for(int j=0;j<count;++j)if(dvd[j].rentTimes==tmp[i])dvd[j].showAll();
		isContinue();
	}
	void isContinue(){//�Ƿ����
		System.out.println("\n�Ƿ����?(y/����)");
		if(!input().equals("y")){
			System.out.println("\n----------ϵͳ���˳�----------");
			stop=true;
		}
	}
	int miniMenu(String op){//С�˵�
		System.out.println(op+"�ɹ�!");
		System.out.println("\n��������Ҫ�������еĲ���:");
		System.out.println("(1)����"+op+"    (2)�������˵�    (����)�˳�ϵͳ");
		String menu=input();
		if(menu.equals("1"))return 1;
		else if(menu.equals("2"))return 2;
		else{
			System.out.println("\n----------ϵͳ���˳�----------");
			stop=true;
		}return 3;
	}
	String input(){//�������
		@SuppressWarnings("resource")
		Scanner sc=new Scanner(System.in);
		return sc.next();
	}
}
