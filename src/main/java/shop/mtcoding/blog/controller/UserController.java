package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

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
