package shop.mtcoding.blog;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		String encPassword1 = BCrypt.hashpw("1234", BCrypt.gensalt());
		System.out.println("encPassword1 : " + encPassword1);
		boolean isValid = BCrypt.checkpw("12345", encPassword1);
		System.out.println(isValid);
		SpringApplication.run(BlogApplication.class, args);
	}

}
