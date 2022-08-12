package umc_sjs.smallestShelter.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.post.PostController;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.Post;
import umc_sjs.smallestShelter.post.postDto.CreatePostReq;
import umc_sjs.smallestShelter.post.postDto.UpdatePostReq;
import umc_sjs.smallestShelter.response.BaseResponse;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
@Transactional
//@Rollback(value = true)
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
        post = posts.get(0);
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
    public void 게시글생성_X_이미지없음_null(){
        CreatePostReq createPostReq = new CreatePostReq(null, "테스트 게시물 내용");
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
        BaseResponse response = postController.getPost(post.getAnimal().getIdx() + 1, post.getIdx());

        System.out.println(response.getMessage());
        Assertions.assertThat(response.getIsSuccess()).isEqualTo(false);
    }

    @Test
    public void 게시물삭제(){
        게시글생성();

        BaseResponse response = postController.deletePost(post.getAnimal().getIdx(), post.getIdx());

        System.out.println(response.getMessage());
        Assertions.assertThat(response.getIsSuccess()).isEqualTo(true);
    }

    @Test
    public void 게시글삭제_x_없는게시물(){
        BaseResponse response = postController.deletePost(animal.getIdx(), -1L);

        System.out.println(response.getMessage());
        Assertions.assertThat(response.getIsSuccess()).isEqualTo(false);
    }

    @Test
    public void 게시물삭제_x_일치하지않는동물idx(){
        BaseResponse response = postController.deletePost(-1L, post.getIdx());

        System.out.println(response.getMessage());
        Assertions.assertThat(response.getIsSuccess()).isEqualTo(false);
    }

    @Test
    public void 게시물수정(){
        UpdatePostReq updatePostReq = new UpdatePostReq("/updateImgUrl", "컨트롤러 테스트코드에서 수정된 게시물");

        BaseResponse response = postController.updatePost(post.getAnimal().getIdx(), post.getIdx(), updatePostReq);

        Assertions.assertThat(response.getIsSuccess()).isEqualTo(true);
    }

    @Test
    public void 게시물수정_x_이미지없음(){
        UpdatePostReq updatePostReq = new UpdatePostReq("", "컨트롤러 테스트코드에서 수정된 게시물");

        BaseResponse response = postController.updatePost(post.getAnimal().getIdx(), post.getIdx(), updatePostReq);

        System.out.println(response.getMessage());
        Assertions.assertThat(response.getIsSuccess()).isEqualTo(false);
    }

    @Test
    public void 게시물수정_x_이미지없음_null(){
        UpdatePostReq updatePostReq = new UpdatePostReq(null, "컨트롤러 테스트코드에서 수정된 게시물");

        BaseResponse response = postController.updatePost(post.getAnimal().getIdx(), post.getIdx(), updatePostReq);

        System.out.println(response.getMessage());
        Assertions.assertThat(response.getIsSuccess()).isEqualTo(false);
    }

    @Test
    public void 게시물수정_x_없는게시물(){
        UpdatePostReq updatePostReq = new UpdatePostReq("/updateImgUrl", "컨트롤러 테스트코드에서 수정된 게시물");

        BaseResponse response = postController.updatePost(animal.getIdx(), -1L, updatePostReq);

        System.out.println(response.getMessage());
        Assertions.assertThat(response.getIsSuccess()).isEqualTo(false);
    }

    @Test
    public void 게시물수정_x_일치하지않는동물idx(){
        UpdatePostReq updatePostReq = new UpdatePostReq("/updateImgUrl", "컨트롤러 테스트코드에서 수정된 게시물");

        BaseResponse response = postController.updatePost(-1L, post.getIdx(), updatePostReq);

        System.out.println(response.getMessage());
        Assertions.assertThat(response.getIsSuccess()).isEqualTo(false);
    }
}