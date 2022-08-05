package umc_sjs.smallestShelter.repository;

import org.springframework.stereotype.Repository;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.Post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    // 게시글(피드) 생성/수정
    public void save(Post post){
        if(post.getIdx() == null){ //게시물 생성
            em.persist(post);
        }
        else { //게시물 수정
            em.merge(post);
        }
    }

    // 게시물 idx로 조회
    public Post findById(Long postIdx){
        return em.find(Post.class, postIdx);
    }

    // 게시물 조회 + fetch join Animal
    public Post findPost(Long postIdx){
        return em.createQuery("select p from Post p join fetch p.animal a where p.idx=:postIdx", Post.class)
                .setParameter("postIdx", postIdx)
                .getSingleResult();
    }

    // 게시물 삭제
    public void delete(Post post){
        em.remove(post);
    }

    // 게시물 전체조회
    public List<Post> findAll(){
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }
}
