package moviebuddy.domain;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import moviebuddy.ApplicationException;

public class JaxbMovieReader implements MovieReader{

	@Override
	public List<Movie> loadMovies() {
		
	 // jaxb 컨텍스트 객체 생성 - jaxb 사용을 위해 작업함 
		try {
		final JAXBContext jaxb = JAXBContext.newInstance(MovieMetadata.class);
		final Unmarshaller unmarshaller = jaxb.createUnmarshaller(); // checkedexception 발생시킴 
		
		//클래스로더를 사용해 스트림으로 값을 받아오기 
		final InputStream content= ClassLoader.getSystemResourceAsStream("movie_metadata.xml");
		//source 데이터 필요 
		final Source source = new StreamSource(content);
		//unmarshaller 구성 
		final MovieMetadata metadata =(MovieMetadata) unmarshaller.unmarshal(source); //XML파일을 언마샬하여 자바용 파일로 부르기
		
		//MovieList 만들기 (메타데이터 관련 기능 추가)
		return metadata.toMovies();
		}catch(JAXBException error){
			throw new ApplicationException("failed to load movies data",error);
		}
	
	}
	
	@XmlRootElement(name = "moviemetadata")  // XML 의 Root Element가 어떤 것인지 표시(movie_metadata.xml)
	public static class MovieMetadata{
		
		private List<MovieData> movies; // movies 라는 속성 (영화정보)를 가짐 

		public List<MovieData> getMovies() {  // 게터와 세터 생성 
			return movies;
		}

		public void setMovies(List<MovieData> movies) {
			this.movies = movies;
		}
		
		public List<Movie> toMovies(){
			// moviemetadata 객체를 이용해서 구성해줌
			return movies.stream().map(MovieData::toMovie).collect(Collectors.toList());
		}

	}
	
	public static class MovieData{  //컴파일 오류 해결용 VO 모델 클래스 
		
		private String title;
		private List<String> genres;
		private String language;
		private String country;
		private int releasedYear;
		private String director;
		private List<String> actors;
		private URL imdbLink;
		private String watchedDate;
		
		public String getTitle() {  // 게터와 세터 생성 
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public List<String> getGenres() {
			return genres;
		}
		public void setGenres(List<String> genres) {
			this.genres = genres;
		}
		public String getLanguage() {
			return language;
		}
		public void setLanguage(String language) {
			this.language = language;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public int getReleasedYear() {
			return releasedYear;
		}
		public void setReleasedYear(int releasedYear) {
			this.releasedYear = releasedYear;
		}
		public String getDirector() {
			return director;
		}
		public void setDirector(String director) {
			this.director = director;
		}
		public List<String> getActors() {
			return actors;
		}
		public void setActors(List<String> actors) {
			this.actors = actors;
		}
		public URL getImdbLink() {
			return imdbLink;
		}
		public void setImdbLink(URL imdbLink) {
			this.imdbLink = imdbLink;
		}
		public String getWatchedDate() {
			return watchedDate;
		}
		public void setWatchedDate(String watchedDate) {
			this.watchedDate = watchedDate;
		}
		
		public Movie toMovie() {
			return Movie.of(title, genres, language, country, releasedYear, director, actors, imdbLink, watchedDate);
		}
		
		
	}
}
