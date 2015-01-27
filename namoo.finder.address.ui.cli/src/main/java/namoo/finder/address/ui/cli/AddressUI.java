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
		
		int i=0;
		
		System.out.println("우편번호 검색을 시작합니다.");
		System.out.print("찾으실 도로명을 입력해주세요: ");
		
		String tmpStreet = scanner.nextLine();
		
		List<Address> addressList = addressService.readAddress(tmpStreet);
		
		System.out.println("--------------주소 검색 결과--------------");
		if(addressList == null || addressList.isEmpty()){
			System.out.println("검색 결과가 없습니다.");
			scanner.close();
			System.exit(0);
		}
		for (Address address : addressList) {
			System.out.println(i + ": " + address.getSi() + " " + address.getGu() 
					+ " " + address.getStreet() + " " + address.getDetails());
			i++;
		}
		System.out.print("해당 번호를 입력해주세요: ");
		int num = scanner.nextInt();
		
		Address address = addressService.checkAddress(addressList, num);
		System.out.println("--------------우편번호 검색 결과--------------");
		System.out.println("우편번호: " + address.getPostcode());
		
		scanner.close();
	}
	public static void main(String[] args){
		
		new AddressUI().startFindAddress();
	}
}
