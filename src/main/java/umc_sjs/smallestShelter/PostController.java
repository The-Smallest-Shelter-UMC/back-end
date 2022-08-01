package umc_sjs.smallestShelter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc_sjs.smallestShelter.model.CreatePostReq;
import umc_sjs.smallestShelter.model.CreatePostRes;
import umc_sjs.smallestShelter.model.GetPostRes;
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

        //animalIdx null 체크도 해줘야하나?

        // 게시글의 이미지가 없으면
        if(createPostReq.getImgUrl().isEmpty() || createPostReq.getImgUrl() == null){
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

    // 게시물 조회
    // [GET] /post?animal_id=&post_id=
    @GetMapping()
    public BaseResponse<GetPostRes> getPost(@RequestParam("animal_id") Long animalIdx, @RequestParam("post_id") Long postIdx){
        try{
            GetPostRes getPostRes = postService.findById(postIdx);

            return new BaseResponse<>(getPostRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
