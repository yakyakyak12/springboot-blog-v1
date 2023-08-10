package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.dto.UserUpdateDTO;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.UserRepository;

@Controller
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private HttpSession session; // request는 가방, session은 서랍

  // localhost:8080/check?username=ssar
  @GetMapping("/check")
  public ResponseEntity<String> check(String username) {
    User user = userRepository.findByUsername(username);
    if (user != null) {
      return new ResponseEntity<String>("유저네임이 중복 되었습니다", HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<String>("유저네임을 사용할 수 있습니다", HttpStatus.OK);
  }

  @PostMapping("/user/{id}/update")
  public String update(@PathVariable Integer id, UserUpdateDTO userUpdateDTO) {
    // System.out.println("테스트 : id : " + id);
    // System.out.println("테스트 : userUpdateDTO : " + userUpdateDTO);
    userRepository.update(userUpdateDTO, id);
    return "redirect:/loginForm";
  }

  @PostMapping("/login")
  public String login(LoginDTO loginDTO) {
    // validation check (유효성 검사)
    if (loginDTO.getUsername() == null || loginDTO.getUsername().isEmpty()) {
      return "redirect:/40x";
    }
    if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
      return "redirect:/40x";
    }
    // 핵심 기능
    // System.out.println("테스트 : username : " + loginDTO.getUsername());
    // System.out.println("테스트 : password : " + loginDTO.getPassword());
    try {
      User user = userRepository.findByUsername(loginDTO.getUsername());
      if (BCrypt.checkpw(loginDTO.getPassword(), user.getPassword())) {
        session.setAttribute("sessionUser", user);
        System.out.println("받은 값 : " + loginDTO.getPassword());
        System.out.println("받은 값 : " + user.getPassword());
        return "redirect:/";
      } else {
        return "redirect:/loginForm";
      }
    } catch (Exception e) {
      return "redirect:/exLogin";
    }

  }

  // 실무
  @PostMapping("/join")
  public String join(JoinDTO joinDTO) {
    // validation check (유효성 검사)
    if (joinDTO.getUsername() == null || joinDTO.getUsername().isEmpty()) {
      return "redirect:/40x";
    }
    if (joinDTO.getPassword() == null || joinDTO.getPassword().isEmpty()) {
      return "redirect:/40x";
    }
    if (joinDTO.getEmail() == null || joinDTO.getEmail().isEmpty()) {
      return "redirect:/40x";
    }

    // DB에 해당 username이 있는지 체크해보기
    User user = userRepository.findByUsername(joinDTO.getUsername());
    if (user != null) {
      return "redirect:/50x";
    }

    userRepository.save(joinDTO); // 핵심 기능
    return "redirect:/loginForm";
  }

  // ip주소 부여 : 10.5.9.200:8080 -> mtcoding.com:8080
  // localhost, 127.0.0.1
  // a태그 form태그 method=get
  @GetMapping("/joinForm")
  public String joinForm() {
    // templates/
    // .mustache
    // templates//user/joinForm.mustache
    return "user/joinForm"; // ViewResolver
  }

  @GetMapping("/loginForm")
  public String loginForm() {
    return "user/loginForm";
  }

  @GetMapping("/user/updateForm")
  public String updateForm(HttpServletRequest request) {
    User sessionUser = (User) session.getAttribute("sessionUser");
    if (sessionUser == null) {
      return "redirect:/loginForm";

    }
    User user = userRepository.findByUsername(sessionUser.getUsername());
    request.setAttribute("user", user);
    return "user/updateForm";
  }

  @GetMapping("/logout")
  public String logout() {
    session.invalidate(); // 세션 무효화 (내 서랍을 비우는 것)
    return "redirect:/";
  }

}