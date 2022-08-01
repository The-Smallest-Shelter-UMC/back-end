package umc_sjs.smallestShelter.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.service.PostService;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.model.CreatePostReq;
import umc_sjs.smallestShelter.model.CreatePostRes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired private PostService postService;
    @PersistenceContext
    private EntityManager em;

    @Test
    public void 게시물등록(){
        // given
        Long animalIdx = 1L;
        CreatePostReq createPostReq = new CreatePostReq("/img", "내용내용");

        try{
            CreatePostRes createPostRes = postService.create(animalIdx, createPostReq);
            System.out.println("createPostRes.getPostIdx() = " + createPostRes.getPostIdx());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void 동물찾기(){
        Animal animal = em.find(Animal.class, 1L);
        System.out.println("animal.getName() = " + animal.getName());
    }

    @Test
    public void 동물찾기_없는동물조회(){
        Animal animal = em.find(Animal.class, 1110L);

        Assertions.assertThat(animal).isNull();
    }
}
