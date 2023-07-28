package shop.mtcoding.blog.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.repository.UserRepository;

@Controller
public class UserController {

  @Autowired
  private UserRepository userRepository;

  // 정상인
  @PostMapping("/join")
  public String join(JoinDTO joinDTO) {
    if (joinDTO.getUsername() == null || joinDTO.getUsername().isEmpty()) {
      return "redirect:/40x";
    }
    if (joinDTO.getUsername() == null || joinDTO.getUsername().isEmpty()) {
      return "redirect:/40x";
    }
    if (joinDTO.getUsername() == null || joinDTO.getUsername().isEmpty()) {
      return "redirect:/40x";
    }
    userRepository.save(joinDTO); // 핵심 기능

    return "redirect:/loginForm";
  }

  @GetMapping("/joinForm")
  public String joinForm() {
    return "user/joinForm";
  }

  @GetMapping("/loginForm")
  public String loginForm() {
    return "user/loginForm";
  }

  @GetMapping("/updateForm")
  public String updateForm() {
    return "user/updateForm";
  }

  @GetMapping("/logout")
  public String logout() {
    return "redirect:/";
  }
}

// @PostMapping("/join")
// public String join(HttpServletRequest request) throws IOException {
// // username=ssar&password=1234&email=ssar@nate.com
// BufferedReader br = request.getReader();

// // 버퍼가 소비됨.
// String body = br.readLine();

// // 버퍼가 값이 없어서, 못꺼냄.
// String username = request.getParameter("username");

// System.out.println("body : " + body);
// System.out.println("username : " + username);

// return "redirect:/loginForm";
// }

// DS(컨트롤러 메서드 찾기, 바디데이터 파싱)
// DS가 바디데이터를 파싱 안하고 컨트롤러 메서드만 찾는 상황

// @PostMapping("/join")
// public String join(HttpServletRequest request) {
// // username=ssar&password=1234&email=ssar@nate.com
// String username = request.getParameter("username");
// String password = request.getParameter("password");
// String email = request.getParameter("email");
// System.out.println("username : " + username);
// System.out.println("password : " + password);
// System.out.println("email : " + email);
// return "redirect:/loginForm";
// }