package b635310;

import java.util.Arrays;

public class PerformanceTest {
	public static void main(String[] args) {
		
        Book[] books = new Book[1000000]; // 100만권의 책 배열 생성
        
        for (int i = 0; i < books.length; i++) // 객체별 id, 제목 설정
        {
            books[i] = new Book(i+1, "Book " + i+1);
        }
        
        // 책들을 id를 기준으로 오름차순 정렬
	    Arrays.sort(books, (b1, b2) -> Integer.compare(b1.getId(), b2.getId()));
	    
	    Book temp_book = books[999999];
	    
	    long startTime = System.nanoTime(); // 성능 테스트 시작 시간
	    BookSearch.search_bs(books, temp_book.getId()); // search_bs 함수 시행
	    long endTime = System.nanoTime(); // 성능 테스트 종료 시간
	    
        
	    System.out.println("search_bs 수행 시간: " + (endTime - startTime) + " 나노초");
    }

}
