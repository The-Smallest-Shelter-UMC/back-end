package umc_sjs.smallestShelter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.Post;
import umc_sjs.smallestShelter.model.CreatePostReq;
import umc_sjs.smallestShelter.model.CreatePostRes;
import umc_sjs.smallestShelter.model.GetPostRes;
import umc_sjs.smallestShelter.response.BaseException;

import static umc_sjs.smallestShelter.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    // 게시글(피드) 생성
    public CreatePostRes create(Long animalIdx, CreatePostReq createPostReq) throws BaseException{
        // 동물 유효 검증해야함.
        // 유저 유효또한 검증해야함.

        Animal animal = postRepository.findAnimalByIdx(animalIdx); //나중에 끌고와야 할 것 같음.

        Post post = Post.createPost(createPostReq.getImgUrl(), createPostReq.getContent(), animal);

        try {
            postRepository.create(post);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }

        return new CreatePostRes(post.getIdx());
    }

    // 게시물 조회
    public GetPostRes findById(Long animlIdx) throws BaseException{
        // 동물 idx에 대해서도 검증이 필요하긴 함.

        try{
            Post post =  postRepository.findById(animlIdx);

            return new GetPostRes(post.getIdx(), post.getImgUrl(), post.getContent());
        } catch (NullPointerException e){ // null값. 즉 해당하는 게시물이 없다는 것.
            throw new BaseException(POST_NOT_EXIST);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
