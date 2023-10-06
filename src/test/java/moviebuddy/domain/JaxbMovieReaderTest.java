package moviebuddy.domain;

import java.util.List;

public class JaxbMovieReaderTest {
	
	public static void main(String[] args) {
		
		// 객체 생성 
		JaxbMovieReader movieReader = new JaxbMovieReader();
		
		List<Movie>movies = movieReader.loadMovies();
		// movies.size() =>> XML문서에 등록된 영화 수와 동일한가?
		MovieFinderTest.assertEquals(1375, movies.size());
	}
}
