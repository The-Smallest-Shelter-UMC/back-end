package umc_sjs.smallestShelter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.Post;
import umc_sjs.smallestShelter.model.CreatePostReq;
import umc_sjs.smallestShelter.model.CreatePostRes;
import umc_sjs.smallestShelter.response.BaseException;

import static umc_sjs.smallestShelter.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;


    @Transactional
    public CreatePostRes create(Long animalIdx, CreatePostReq createPostReq) throws BaseException{
        // 동물 유효 검증해야함.
        // 유저 유효또한 검증해야함.

        Animal animal = postRepository.findAnimalByIdx(animalIdx); //나중에 끌고와야 할 것 같음.

        Post post = Post.createPost(createPostReq.getImgUrl(), createPostReq.getContext(), animal);

        try {
            postRepository.create(post);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }

        return new CreatePostRes(post.getIdx());
    }


}
