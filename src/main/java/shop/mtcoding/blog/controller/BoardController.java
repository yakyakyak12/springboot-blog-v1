package shop.mtcoding.blog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.mtcoding.blog.dto.WriteDTO;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.BoardRepository;

@Controller
public class BoardController {

  @Autowired
  private HttpSession session;

  @Autowired
  private BoardRepository boardRepository;

  @PostMapping("/board/{id}/delete")
  public String delete(@PathVariable Integer id) { // 1. PathVariable 값 받기
    // 2. 인증검사 해야함. (로그인 페이지 보내기)
    // session에 접근해서 sessionUser 키값을 가져오세요
    // null 이면, 로그인페이지로 보내고
    // null 아니면, 4번을 실행하세요.
    User sessionUser = (User) session.getAttribute("sessionUser");
    if (sessionUser == null) {
      return "redirect:/loginForm";
    }
    // 3. 권한 검사
    Board board = boardRepository.findById(id);
    ;
    if (board.getUser().getId() != sessionUser.getId()) {
      return "redirect:/40x";
    }
    // 4. 모델에 접근해서 삭제
    // boardRepository.deleteById(id)
    // delete from board_tb where id = :id
    boardRepository.deleteById(id);

    return "redirect:/";
  }

  // localhost:8080?page=1
  @GetMapping({ "/", "/board" })
  public String index(
      @RequestParam(defaultValue = "0") Integer page,
      HttpServletRequest request) {
    // 1. 유효성 검사 X
    // 2. 인증검사 X

    List<Board> boardList = boardRepository.findAll(page); // page = 1
    int totalCount = boardRepository.count(); // totalCount = 5
    int totalPage = totalCount / 3; // totalPage = 1
    if (totalCount % 3 > 0) {
      totalPage = totalPage + 1; // totalPage = 2
    }
    boolean last = totalPage - 1 == page;

    request.setAttribute("boardList", boardList);
    request.setAttribute("prevPage", page - 1);
    request.setAttribute("nextPage", page + 1);
    request.setAttribute("first", page == 0 ? true : false);
    request.setAttribute("last", last);
    request.setAttribute("totalPage", totalPage);
    request.setAttribute("totalCount", totalCount);

    return "index";
  }

  @PostMapping("/board/save")
  public String save(WriteDTO writeDTO) {
    // validation check (유효성 검사)
    if (writeDTO.getTitle() == null || writeDTO.getTitle().isEmpty()) {
      return "redirect:/40x";
    }
    if (writeDTO.getContent() == null || writeDTO.getContent().isEmpty()) {
      return "redirect:/40x";
    }

    // 인증체크
    User sessionUser = (User) session.getAttribute("sessionUser");
    if (sessionUser == null) {
      return "redirect:/loginForm";
    }

    boardRepository.save(writeDTO, sessionUser.getId());
    return "redirect:/";
  }

  @GetMapping("/board/saveForm")
  public String saveForm() {
    User sessionUser = (User) session.getAttribute("sessionUser");
    if (sessionUser == null) {
      return "redirect:/loginForm";
    }
    return "board/saveForm";
  }

  // localhost:8080/board/1
  // localhost:8080/board/50
  @GetMapping("/board/{id}")
  public String detail(@PathVariable Integer id, HttpServletRequest request) {
    User sessionUser = (User) session.getAttribute("sessionUser"); // 세션접근
    Board board = boardRepository.findById(id);

    boolean pageOwner = false;
    if (sessionUser != null) {
      pageOwner = sessionUser.getId() == board.getUser().getId(); // 비지니스 로직
    }

    request.setAttribute("board", board);
    request.setAttribute("pageOwner", pageOwner);
    return "board/detailForm";
  }
}