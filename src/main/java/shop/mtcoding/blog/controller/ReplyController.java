package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.ReplyWriteDTO;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.Reply;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.ReplyRepository;

// 컴포넌트 스캔 되면서 나타난 것들 
// UserController, BoardController, ReplyController, ErrorController - Autowired할 일이 없음 
// UserRepository, BoardRepository, ReplyRepository
// EntityManager, HttpSession

@Controller
public class ReplyController {

  @Autowired
  private HttpSession session;

  @Autowired
  private ReplyRepository replyRepository;

  @PostMapping("/reply/save")
  public String save(ReplyWriteDTO replyWriteDTO) {
    // comment(코멘트) 유효성 검사

    if (replyWriteDTO.getComment() == null || replyWriteDTO.getComment().isEmpty()) {
      return "redirect:/40x";
    }
    // int는 공백 체크가 되지 않는다.
    if (replyWriteDTO.getBoardId() == null) {
      return "redirect:/40x";
    }

    // 인증 검사
    User sessionUser = (User) session.getAttribute("sessionUser");
    if (sessionUser == null) {
      return "redirect:/loginForm";
    }
    // 댓글 쓰기
    replyRepository.save(replyWriteDTO, sessionUser.getId());
    return "redirect:/board/" + replyWriteDTO.getBoardId();
    // 상세보기로 리턴한다.
  }

  @PostMapping("/reply/{id}/delete")
  public String delete(@PathVariable Integer id, Integer boardId) {
    if (boardId == null) {
      return "redirect:/40x";
    }
    // 인증체크

    User sessionUser = (User) session.getAttribute("sessionUser");
    if (sessionUser == null) {
      return "redirect:/loginForm"; // 401
    }

    // 권한체크
    Reply reply = replyRepository.findById(id);
    if (reply.getUser().getId() != sessionUser.getId()) {
      return "redirect:/40x";
    }

    // 핵심로직
    replyRepository.deleteById(id);
    return "redirect:/board/" + boardId;
  }

}
