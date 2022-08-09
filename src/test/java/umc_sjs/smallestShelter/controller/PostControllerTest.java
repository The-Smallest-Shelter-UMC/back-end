package umc_sjs.smallestShelter.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.Post;
import umc_sjs.smallestShelter.dto.post.CreatePostReq;
import umc_sjs.smallestShelter.response.BaseException;
import umc_sjs.smallestShelter.response.BaseResponse;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Rollback(value = false)
class PostControllerTest {

    @Autowired private PostController postController;

    @PersistenceContext
    private EntityManager em;
    private List<Animal> animals;
    private List<Post> posts;
    private String imgUrl = "https://www.missyusa.com/fileServer/ImageServer/upload/talk6/20209/2020090722130066402.jpeg";
    private Animal animal;
    private Post post;

    @BeforeEach
    public void 동물_전체조회(){
        animals = em.createQuery("select a from Animal a", Animal.class)
                .getResultList();
        animal = animals.get(0);
    }

    @BeforeEach
    public void 게시물_전체조회(){
        posts = em.createQuery("select p from Post p", Post.class)
                .getResultList();
        post = posts.get(3);
    }


    @Test
    public void 게시글생성(){
        CreatePostReq createPostReq = new CreatePostReq(imgUrl, "테스트 게시물 내용");
        BaseResponse response = postController.createPost(animals.get(0).getIdx(), createPostReq);

//        System.out.println(response.getMessage());
        Assertions.assertThat(response.getIsSuccess()).isEqualTo(true);
    }

    @Test
    public void 게시글생성_X_없는동물(){
        CreatePostReq createPostReq = new CreatePostReq(imgUrl, "테스트 게시물 내용");
        BaseResponse response = postController.createPost(-1L, createPostReq);

        System.out.println(response.getMessage());
        Assertions.assertThat(response.getIsSuccess()).isEqualTo(false);
    }

    @Test
    @DisplayName("게시글생성X 이미지 없음")
    public void 게시글생성_X_이미지없음(){
        CreatePostReq createPostReq = new CreatePostReq("", "테스트 게시물 내용");
        BaseResponse response = postController.createPost(animals.get(0).getIdx(), createPostReq);

        System.out.println(response.getMessage());
        Assertions.assertThat(response.getIsSuccess()).isEqualTo(false);
    }

    @Test
    public void 게시글조회(){
        BaseResponse response = postController.getPost(post.getAnimal().getIdx(), post.getIdx());

        Assertions.assertThat(response.getIsSuccess()).isEqualTo(true);
    }

    @Test
    public void 게시글조회_x_없는게시물조회(){
        BaseResponse response = postController.getPost(1L, -1L);

        System.out.println(response.getMessage());
        Assertions.assertThat(response.getIsSuccess()).isEqualTo(false);
    }

    @Test
    public void 게시글조회_X_일치하지않는동물idx(){
        BaseResponse response = postController.getPost(-1L, post.getIdx());

        System.out.println(response.getMessage());
        Assertions.assertThat(response.getIsSuccess()).isEqualTo(false);
    }


}