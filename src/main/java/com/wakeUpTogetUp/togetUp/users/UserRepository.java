package com.wakeUpTogetUp.togetUp.users;

import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    private final EntityManager em;
//    public void save(User user) {
//        em.persist(user);
//    }
//
//    //유저 한명 조회
//    public User findOne(Long id) {
//        return em.find(User.class, id);//두번째에 pk를 넣어주면 된다.
//    }
//
//    //sql과 차이점: 테이블이 아닌 객체를 대상으로 쿼리를 한다.
//    public List<User> findAll() {
//        return em.createQuery("select u from User u", User.class)//두번째:반환타입
//                .getResultList();
//    }
//
//    public List<User> findByNickName(String nickName) {
//        return em.createQuery("select u from User u where u.nickName = :nickName", User.class)//:name->파라미터 바인딩
//                .setParameter("nickName", nickName).getResultList();
//    }
}
