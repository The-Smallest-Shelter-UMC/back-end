package umc_sjs.smallestShelter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.Post;
import umc_sjs.smallestShelter.repository.AnimalRepository;
import umc_sjs.smallestShelter.service.PostService;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.dto.post.CreatePostReq;
import umc_sjs.smallestShelter.dto.post.CreatePostRes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class GanaServiceTest {

    @Autowired private PostService postService;
    @Autowired private AnimalRepository animalRepository;
    @PersistenceContext
    private EntityManager em;

    @Test
    public void 게시물등록(){
        // given
        Long animalIdx = 1L;

        try{
            Post post = postService.create(animalIdx, "/img", "내용내용");
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

        Assertions.assertThat(animal).isNull(); //널값 확인. 에러가 뜨지는 않음.

        assertThrows(EmptyResultDataAccessException.class, () -> {
            Animal findAnimal = animalRepository.findAnimalById(1000L);
        });
    }
    
    @Test
    public void 동물수정후_테이블확인(){
        Animal animal = em.find(Animal.class, 2L);

        System.out.println("animal.getPostList() = " + animal.getPostList());
    }

    @Test
    public void 게시물조회(){
        Post post = em.find(Post.class, 4L);
    }

    @Test
    public void 이넘타입() {

    }
}
