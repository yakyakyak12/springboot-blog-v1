package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

/*
   프론트엔드 개발자에게 보여주는 것 
 * 회원가입 API
 *  1. URL : http://localhost:8080/join
 *  2. method : POST
 *  3. 요청 body : username=값(String)&password=값(String)&email=값(String)
 *  4. MIME타입 : x-www-form-urlencoded
 *  5. 응답 : view를 응답함.
 */

@Getter
@Setter
public class JoinDTO {
  private String username;
  private String password;
  private String email;
}
