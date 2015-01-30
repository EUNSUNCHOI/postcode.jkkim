package namoo.finder.address.persist.file.store_seek;

public class IndexSorter {

	public void sortIndex(){
		//인덱스 정렬
/*				
		if(indexList.size() < 2){   ==> 시간 겁나오래걸림...
			String csvIndex = createIndexCSVFromAddress(address);
			indexList.add(csvIndex);
		}
		else{					
			//'zipcode'는 제외한 우편번호 비교
			for (int i=1 ; i<indexList.size() ; i++) {
				String csvIndex = indexList.get(i);
				String[] data = csvIndex.split(CSV_SEPERATOR);
				String sortedPostcode = data[0];
										
				if(Integer.parseInt(address.getPostcode())
						< Integer.parseInt(sortedPostcode)) {
					
					indexList.add(i+1, indexList.get(i));
					indexList.add(i, createCSVFromAddress(address));
					break;
				}
				if(i == indexList.size()-1){
					indexList.add(createCSVFromAddress(address));
					break;
				}
			}
		}
		
			for (String csvIndex : indexList) {
				fw_index.write(csvIndex);
			}
*/
	}
}
