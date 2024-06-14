package b635310;

import java.util.Arrays;

public class BookSearch {
	
	 public static Book search_bs(Book[] books, int bookId) {
	        
		    // 책들을 id를 기준으로 오름차순 정렬
		    Arrays.sort(books, (b1, b2) -> Integer.compare(b1.getId(), b2.getId()));
	        
		    // (1)부터 시작하여 (책 갯수)만큼 범위 설정
		    int left = 1;
	        int right = books.length;

	        // 범위를 수정해가며(좁혀가며) 이진 탐색
	        while (left <= right) {
	            
	        	int midIndex = left + (right - left) / 2; //중간 인덱스를 계산한다.
	            
	            if (books[midIndex].getId() == bookId) 
	            {
	                return books[midIndex];
	            } 
	            
	            else if (books[midIndex].getId() < bookId) // 찾고자 하는 bookId가 중간 인덱스 객체의 id보다 더 크다면 left 1 증가
	            {
	                left = midIndex + 1; 
	            }
	            
	            else // 찾고자 하는 bookId가 중간 인덱스 객체의 id보다 더 작다면 right 1 감소
	            {
	                right = midIndex - 1;
	            }
	        }

	        return null; // 책을 찾지 못했을 경우, null을 return 한다.
	    }
}
