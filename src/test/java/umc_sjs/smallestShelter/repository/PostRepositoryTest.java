package umc_sjs.smallestShelter.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.Post;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
class PostRepositoryTest {

    @Autowired private PostRepository postRepository;

    @Test
    public void 게시물조회_fetch(){
        Post post = postRepository.findPost(1L);
    }

    @Test
    public void 게시물전체조회_fetch(){
        List<Post> posts = postRepository.findPostAll();
    }
}