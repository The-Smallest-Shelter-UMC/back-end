package umc_sjs.smallestShelter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.dto.post.CreatePostReq;
import umc_sjs.smallestShelter.response.BaseResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
//@Rollback(value = false)
class PostControllerTest {

    @Autowired private PostController postController;

    @Test
    public void 게시글생성_X_없는동물(){
        CreatePostReq createPostReq = new CreatePostReq("/img", "테스트 게시물 내용");
        BaseResponse response = postController.createPost(100L, createPostReq);

        System.out.println(response.getMessage());
    }

    @Test
    public void 게시글생성(){
        CreatePostReq createPostReq = new CreatePostReq("/img", "테스트 게시물 내용");
        BaseResponse response = postController.createPost(1L, createPostReq);

//        System.out.println(response.getMessage());
    }
}