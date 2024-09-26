	package kr.co.duck.application;
	
	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
	
	@SpringBootApplication
	@EnableJpaRepositories(basePackages = "kr.co.duck.repository")
	public class MusicQuizApplication {
		public static void main(String[] args) {
			SpringApplication.run(MusicQuizApplication.class, args);
		}
	}
