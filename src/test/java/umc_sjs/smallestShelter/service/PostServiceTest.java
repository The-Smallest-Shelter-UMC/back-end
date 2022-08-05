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
import static umc_sjs.smallestShelter.response.BaseResponseStatus.ANIMAL_NOT_EXIST;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PostServiceTest {

    @Autowired private PostService postService;
//    @Autowired private AnimalRepository animalRepository;
    @PersistenceContext
    private EntityManager em;

    private List<Animal> animals;

    @BeforeEach
    public void 동물_전체조회(){
        animals = em.createQuery("select a from Animal a", Animal.class)
                .getResultList();
    }

    @Test
    public void 게시물생성(){

        try{
            Animal animal = animals.get(0);

            Post post = postService.create(animal.getIdx(), "/img1", "게시물 내용");

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
}