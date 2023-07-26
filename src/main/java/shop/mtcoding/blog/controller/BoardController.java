package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
  @GetMapping({ "/", "/board" })
  public String index() {
    return "index";
  }

  @GetMapping("/board/saveForm")
  public String saveForm() {
    return "board/saveForm";
  }

  @GetMapping("/board1")
  public String detailForm() {
    return "board/detailForm";
  }
}
