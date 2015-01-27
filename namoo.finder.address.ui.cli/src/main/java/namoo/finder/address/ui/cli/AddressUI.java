package namoo.finder.address.ui.cli;

import java.util.List;
import java.util.Scanner;

import namoo.finder.address.entity.Address;
import namoo.finder.address.persist.file.AddressFilePersist;
import namoo.finder.address.service.AddressService;
import namoo.finder.address.serviceLogic.AddressServiceLogic;

public class AddressUI {
	
	private AddressService addressService;
	private Scanner scanner;
	
	public AddressUI(){
		addressService = new AddressServiceLogic(new AddressFilePersist());
		scanner = new Scanner(System.in);
	}

	public void startFindAddress(){
		
		System.out.println("우편번호 검색을 시작합니다.");
		System.out.println("도로명으로 검색(서울시만 가능): 1");
		System.out.println("읍면동명으로 검색: 2");
		System.out.print("선택하세요: ");
		
		int choice = scanner.nextInt();
		scanner.nextLine();
		
		if(choice == 1){
			findAddressByStreet();
		}
		else if(choice == 2){
			findAddressByDong();
		}
		else{
			System.out.println("잘못입력하셨습니다.");
			System.exit(0);
		}
		
		scanner.close();
	}
	private void findAddressByDong() {
		// TODO Auto-generated method stub
		int i = 0;
		
		System.out.print("찾으실 읍면동명을 입력해주세요: ");
		
		String tmpDong = scanner.nextLine();
		
		List<Address> addressList = addressService.readAddressByDong(tmpDong);
		
		System.out.println("--------------주소 검색 결과--------------");
		if(addressList == null || addressList.isEmpty()){
			System.out.println("검색 결과가 없습니다.");
			scanner.close();
			System.exit(0);
		}
		for (Address address : addressList) {
			System.out.println(i + ": " + address.getSi() + " " + address.getGu() + " " 
					+ address.getDongAddress().getDong() + " " + address.getDongAddress().getDetails());
			i++;
		}
		System.out.print("해당 번호를 입력해주세요: ");
		int num = scanner.nextInt();
		
		Address address = addressService.checkAddress(addressList, num);
		System.out.println("--------------우편번호 검색 결과--------------");
		System.out.println("우편번호: " + address.getPostcode());
	}

	private void findAddressByStreet() {
		// TODO Auto-generated method stub
		int i = 0;
		
		System.out.print("찾으실 도로명을 입력해주세요: ");
		
		String tmpStreet = scanner.nextLine();
		
		List<Address> addressList = addressService.readAddressByStreet(tmpStreet);
		
		System.out.println("--------------주소 검색 결과--------------");
		if(addressList == null || addressList.isEmpty()){
			System.out.println("검색 결과가 없습니다.");
			scanner.close();
			System.exit(0);
		}
		for (Address address : addressList) {
			System.out.println(i + ": " + address.getSi() + " " + address.getGu() + " " 
					+ address.getStreetAddress().getStreet() + " " + address.getStreetAddress().getDetails());
			i++;
		}
		System.out.print("해당 번호를 입력해주세요: ");
		int num = scanner.nextInt();
		
		Address address = addressService.checkAddress(addressList, num);
		System.out.println("--------------우편번호 검색 결과--------------");
		System.out.println("우편번호: " + address.getPostcode());
	}

	public static void main(String[] args){
		
		new AddressUI().startFindAddress();
	}
}
