package umc_sjs.smallestShelter.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.Post;
import umc_sjs.smallestShelter.repository.AnimalRepository;
import umc_sjs.smallestShelter.repository.UserRepository;
import umc_sjs.smallestShelter.response.BaseException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static umc_sjs.smallestShelter.response.BaseResponseStatus.*;

@SpringBootTest
//@Transactional
@Rollback(value = false)
class PostServiceTest {

    @Autowired private PostService postService;
//    @Autowired private AnimalRepository animalRepository;
    @PersistenceContext
    private EntityManager em;

    private List<Animal> animals;
    private List<Post> posts;
    private String imgUrl = "https://www.missyusa.com/fileServer/ImageServer/upload/talk6/20209/2020090722130066402.jpeg";

    private Long animalIdx;
    private Long postIdx;
    private Animal animal;
    private Post post;

    @BeforeEach
    public void 동물_전체조회(){
        animals = em.createQuery("select a from Animal a", Animal.class)
                .getResultList();
        animal = animals.get(0);
        animalIdx = animal.getIdx();
    }

    @BeforeEach
    public void 게시물_전체조회(){
        posts = em.createQuery("select p from Post p", Post.class)
                .getResultList();
        post = posts.get(0);
        postIdx = post.getIdx();
    }

    @Test
    public void 게시물생성(){

        try{
            Animal animal = animals.get(0);

            Post post = postService.create(animal.getIdx(), imgUrl, "게시물 내용");

            Assertions.assertThat(post.getContent()).isEqualTo("게시물 내용");
        } catch (BaseException e){
            Assertions.fail("게시물 생성 실패: " + e.getStatus().getMessage());
        } catch (Exception e){
            e.printStackTrace();
            Assertions.fail("게시물 생성 실패 in Exception");
        }
    }

    @Test
    @Rollback(value = true)
    public void 게시물생성_X_없는동물(){
        try {
            Post post = postService.create(-1L, "/img1", "테스트 게시물 in 게시물생성_X_없는동물");
        } catch (BaseException e){
            Assertions.assertThat(e.getStatus()).isSameAs(ANIMAL_NOT_EXIST);
        } catch (Exception e){
            e.printStackTrace();
            Assertions.fail("에러!");
        }
    }

    @Test
    public void 게시물조회(){
        try {
            Post post = posts.get(0);

            Post findPost = postService.getPost(post.getIdx(), post.getAnimal().getIdx());

            Assertions.assertThat(findPost.getIdx()).isEqualTo(post.getIdx());
        } catch (BaseException e){
            Assertions.fail("게시물 단건 조회 실패: " + e.getStatus().getMessage());
        } catch (Exception e){
            e.printStackTrace();
            Assertions.fail("에러!");
        }
    }

    @Test
    @Rollback(value = true)
    public void 게시물조회_X_없는게시물조회(){
        try {
            postService.getPost(-1L, animalIdx);
        } catch (BaseException e){
            Assertions.assertThat(e.getStatus()).isSameAs(POST_NOT_EXIST);
        } catch (Exception e){
            e.printStackTrace();
            Assertions.fail("에러!");
        }
    }

    @Test
    @Rollback(value = true)
    public void 게시글조회_X_일치하지않는동물idx(){
        try{
            postService.getPost(postIdx, -1L);
        } catch (BaseException e){
            Assertions.assertThat(e.getStatus()).isSameAs(POSTIDX_ANIMALIDX_ILLEGAL);
        } catch (Exception e){
            e.printStackTrace();
            Assertions.fail("에러!");
        }
    }

    @Test
    public void 게시글삭제(){
        try {
            postService.delete(post.getIdx(), post.getAnimal().getIdx());
        } catch (BaseException e){
            Assertions.fail("게시글 삭제 실패: " + e.getStatus().getMessage());
        } catch (Exception e){
            Assertions.fail("에러!");
        }
    }

    @Test
    @Rollback(value = true)
    public void 게시글삭제_x_없는게시물(){
        try{
            postService.delete(-1L, animalIdx);
        } catch (BaseException e){
            Assertions.assertThat(e.getStatus()).isEqualTo(POST_NOT_EXIST);
        } catch (Exception e){
            Assertions.fail("에러!");
        }
    }

    @Test
    @Rollback(value = true)
    public void 게시물삭제_x_일치하지않는동물idx(){
        try {
            postService.delete(postIdx, -1L);
        } catch (BaseException e){
            Assertions.assertThat(e.getStatus()).isEqualTo(POSTIDX_ANIMALIDX_ILLEGAL);
        } catch (Exception e){
            Assertions.fail("에러!");
        }
    }


    @Test
    public void 게시물수정(){
        try {
            postService.update(postIdx, post.getAnimal().getIdx(), "/updateImgUrl", "서비스에서 게시물 내용 수전ㅇ");
        } catch (BaseException e){
            Assertions.fail("게시물 수정 실패: " + e.getStatus().getMessage());
        } catch (Exception e){
            Assertions.fail("에러!");
        }
    }

    @Test
    public void 게시물수정_x_없는게시물(){
        try {
            postService.update(-1L, animalIdx, "/noUpdateImgURl", "서비스에서 게시물 수정 불가");
        } catch (BaseException e){
            Assertions.assertThat(e.getStatus()).isEqualTo(POST_NOT_EXIST);
        } catch (Exception e){
            Assertions.fail("에러!");
        }
    }

    @Test
    public void 게시물수정_x_일치하지않는동물idx(){
        try {
            postService.update(postIdx, -1L, "/noUpdateImgUrl", "서비스에서 게시물 수정 불가");
        } catch (BaseException e){
            Assertions.assertThat(e.getStatus()).isEqualTo(POSTIDX_ANIMALIDX_ILLEGAL);
        } catch (Exception e){
            Assertions.fail("에러!");
        }
    }
}