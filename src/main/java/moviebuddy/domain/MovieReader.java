package moviebuddy.domain;

import java.util.List;

public interface MovieReader {  // 새로운 인터페이스를 만듬 
	List<Movie>loadMovies();

}
