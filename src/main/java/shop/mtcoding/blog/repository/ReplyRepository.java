package shop.mtcoding.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.mtcoding.blog.dto.ReplyWriteDTO;
import shop.mtcoding.blog.model.Reply;

@Repository
public class ReplyRepository {

  @Autowired
  private EntityManager em;

  public List<Reply> findByBoardId(Integer boardId) {
    Query query = em.createNativeQuery("select * from reply_tb where board_id = :boardId", Reply.class);
    query.setParameter("boardId", boardId);
    return query.getResultList();
  }

  @Transactional
  public void save(ReplyWriteDTO replyWriteDTO, Integer id) {
    Query query = em
        .createNativeQuery(
            "insert into reply_tb(comment, board_id, user_id) values(:comment, :boardId, :userId)");

    query.setParameter("comment", replyWriteDTO.getComment());
    query.setParameter("boardId", replyWriteDTO.getBoardId());
    query.setParameter("userId", id);
    query.executeUpdate();

  }

}
