package shop.mtcoding.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.JoinDTO;

// 현재 어노테이션 사용해서 자동으로 떠있는 것
// BoardController, UserController, UserRepository 내가 직접 띄운거
// EntityManager, HttpSession - 스피링이 띄어줌 
@Repository
public class UserRepository {

  @Autowired
  private EntityManager em;

  @Transactional
  public void save(JoinDTO joinDTO) {
    Query query = em
        .createNativeQuery("insert into user_tb(username, password, email) values(:username, :password, :email)");
    query.setParameter("username", joinDTO.getUsername());
    query.setParameter("password", joinDTO.getPassword());
    query.setParameter("email", joinDTO.getEmail());
    query.executeUpdate();
  }

}
