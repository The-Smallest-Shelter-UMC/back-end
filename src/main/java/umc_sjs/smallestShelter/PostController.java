package umc_sjs.smallestShelter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc_sjs.smallestShelter.model.CreatePostReq;
import umc_sjs.smallestShelter.model.CreatePostRes;
import umc_sjs.smallestShelter.response.BaseException;
import umc_sjs.smallestShelter.response.BaseResponse;

import static umc_sjs.smallestShelter.response.BaseResponseStatus.POST_EMPTY_IMG;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 동물 게시물(피드) 등록
    // [POST] /post/join?animal_id
    @PostMapping("/join")
    public BaseResponse<CreatePostRes> createPost(@RequestParam("animal_id") Long animalIdx, @RequestBody CreatePostReq createPostReq){
        if(createPostReq.getImgUrl().isEmpty()){
            return new BaseResponse<>(POST_EMPTY_IMG);
        }
        // 게시물의 내용은 없어도 되지 않나..?

        try {
            CreatePostRes createPostRes = postService.create(animalIdx, createPostReq);
            return new BaseResponse<>(createPostRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

    }
}
