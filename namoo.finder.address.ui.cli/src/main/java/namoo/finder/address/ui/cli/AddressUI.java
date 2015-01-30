package namoo.finder.address.ui.cli;

import java.util.List;
import java.util.Scanner;

import namoo.finder.address.entity.Address;
import namoo.finder.address.entity.DongAddress;
import namoo.finder.address.entity.StreetAddress;
import namoo.finder.address.persist.file.AddressFilePersist;
import namoo.finder.address.service.AddressService;
import namoo.finder.address.serviceLogic.AddressServiceLogic;

public class AddressUI {
	
	private AddressService addressService;
	private Scanner scanner;
	
	private static final String STREET_FILE_NAME = "address_street.csv";
	private static final String DONG_FILE_NAME = "address_dong.csv";
	
	public AddressUI(){
		addressService = new AddressServiceLogic(new AddressFilePersist());
		scanner = new Scanner(System.in);
	}

	public void startFindAddress(){
		//
		System.out.println("우편번호 검색을 시작합니다.");
		System.out.print("검색에 필요한 파일명을 입력해주세요(ex address_dong.txt): ");
		String tmpFileName = scanner.nextLine();
		
		// 입력된 파일경로에 파일이 있는지 체크 후 csv파일로 변환
		String fileName = addressService.changeFileToCSV(tmpFileName);
		
		if(STREET_FILE_NAME.equals(fileName)){
			System.out.println("도로명으로 주소 검색/추가 합니다.");
			System.out.println("도로명으로 주소 검색: 1");
			System.out.println("도로명으로 주소 추가: 2");
			System.out.println("우편번호로 주소 검색: 3");
			
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			switch(choice){
			case 1:
				readAddressByStreet();
				break;
				
			case 2:
				createAddressByStreet();
				break;
				
			case 3:
				readAddressByStreetPostcode();
				break;
				
			default:
				System.out.println("메뉴를 잘 못 입력하셨습니다.");
				break;
			}
		}	
		else if(DONG_FILE_NAME.equals(fileName)){
			System.out.println("읍면동명으로 주소 검색/추가 합니다.");
			System.out.println("읍면동명으로 주소 검색: 1");
			System.out.println("읍면동명으로 주소 추가: 2");
			System.out.println("우편번호로 주소 검색: 3");
			System.out.print("선택하세요: ");
			
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			switch(choice){
			case 1:
				readAddressByDong();
				break;
				
			case 2:
				createAddressByDong();
				break;
				
			case 3:
				readAddressByDongPostcode();
				break;
				
			default:
				System.out.println("메뉴를 잘 못 입력하셨습니다.");
				break;
			}
		}
		else{
			System.out.println("파일명을 잘 못 입력하셨습니다.");
		}

		scanner.close();
	}
	private void createAddressByDong() {
		//
		System.out.print("시도를 입력해주세요: ");
		String si = scanner.nextLine();
		
		System.out.print("구를 입력해주세요: ");
		String gu = scanner.nextLine();
		
		System.out.print("읍면동명을 입력해주세요: ");
		String dong = scanner.nextLine();
		
		System.out.print("나머지 주소를 입력해주세요: ");
		String details = scanner.nextLine();
		
		System.out.print("우편번호를 입력해주세요: ");
		String postcode = scanner.nextLine();
		
		Address address = new Address(si, gu, new DongAddress(dong, details), null, postcode);
		
		if(addressService.createAddressByDong(address)){
			System.out.println("새로운 주소가 추가되었습니다!");
		}
		else{
			System.out.println("잘못입력하셨습니다.");
		}
	}

	private void createAddressByStreet() {
		//
		System.out.print("시도를 입력해주세요: ");
		String si = scanner.nextLine();
		
		System.out.print("구를 입력해주세요: ");
		String gu = scanner.nextLine();
		
		System.out.print("도로명을 입력해주세요: ");
		String street = scanner.nextLine();
		
		System.out.print("나머지 주소를 입력해주세요: ");
		String details = scanner.nextLine();
		
		System.out.print("우편번호를 입력해주세요: ");
		String postcode = scanner.nextLine();
		
		Address address = new Address(si, gu, null, new StreetAddress(street, details), postcode);

		if(addressService.createAddressByStreet(address)){
			System.out.println("새로운 주소가 추가되었습니다!");
		}
		else{
			System.out.println("잘못입력하셨습니다.");
		}
	}

	private void readAddressByDong() {
		//
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

	private void readAddressByStreet() {
		//
		int i = 0;
		
		System.out.print("찾으실 도로명을 입력해주세요(서울시만 가능): ");
		
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

	public void readAddressByDongPostcode(){
		//
		int i=0;
		
		System.out.print("찾으실 주소의 우편번호를 입력해주세요: ");
		String postcode = scanner.nextLine();
		
		List<Address> addressList = addressService.readAddressByDongPostcode(postcode);
		
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
		
		System.out.println("--------------주소 검색 결과--------------");
		
		System.out.println("(우편번호: " + address.getPostcode() + ") " + address.getSi() + " " + address.getGu() + " " 
				+ address.getDongAddress().getDong() + " " + address.getDongAddress().getDetails());
		
	}
	
	public void readAddressByStreetPostcode(){
		//
		int i=0;
		
		System.out.print("찾으실 주소의 우편번호를 입력해주세요(ex 000000): ");
		String postcode = scanner.nextLine();
		
		List<Address> addressList = addressService.readAddressByStreetPostcode(postcode);
		
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
		
		System.out.println("--------------주소 검색 결과--------------");
		
		System.out.println("(우편번호: " + address.getPostcode() + ") " + address.getSi() + " " + address.getGu() + " " 
				+ address.getStreetAddress().getStreet() + " " + address.getStreetAddress().getDetails());
		
	}
	
	public static void main(String[] args){
		
		new AddressUI().startFindAddress();
	}
}
