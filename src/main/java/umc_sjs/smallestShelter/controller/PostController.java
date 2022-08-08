package umc_sjs.smallestShelter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc_sjs.smallestShelter.domain.Post;
import umc_sjs.smallestShelter.service.PostService;
import umc_sjs.smallestShelter.dto.post.*;
import umc_sjs.smallestShelter.response.BaseException;
import umc_sjs.smallestShelter.response.BaseResponse;

import static umc_sjs.smallestShelter.response.BaseResponseStatus.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final int MAX_CONTENT = 300;

    // 동물 게시물(피드) 등록
    // [POST] /post/join?animal_id
    @PostMapping("/join")
    public BaseResponse<CreatePostRes> createPost(@RequestParam("animal_id") Long animalIdx, @RequestBody CreatePostReq createPostReq){

        if(animalIdx == null){
            return new BaseResponse<>(URL_VALUE_EMPLY);
        }

        String imgUrl = createPostReq.getImgUrl(); // 게시글의 이미지
        String content = createPostReq.getContent(); // 게시글의 내용

        // 게시글의 이미지가 없으면
        if(imgUrl.isEmpty() || imgUrl == null){
            return new BaseResponse<>(POST_EMPTY_IMG);
        }
        // 게시물의 내용은 없어도 되지 않나..?
        // 대신 게시글의 글자수 제한은 필요할 듯함.
        if(content.length() > MAX_CONTENT){
            return new BaseResponse<>(POST_CONTENT_LENGTH_OVER);
        }

        try {
            // 게시물 생성
            Post post = postService.create(animalIdx, imgUrl, content);

            CreatePostRes createPostRes = new CreatePostRes(post.getIdx());
            return new BaseResponse<>(createPostRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

    }

    // 게시물 조회
    // [GET] /post?animal_id=&post_id=
    @GetMapping()
    public BaseResponse<GetPostRes> getPost(@RequestParam("animal_id") Long animalIdx, @RequestParam("post_id") Long postIdx){
        //animalIdx null 체크도 해줘야하나?
        if(animalIdx==null || postIdx==null){
            return new BaseResponse<>(URL_VALUE_EMPLY);
        }

        try{
            // 게시물 조회
            Post post = postService.getPost(postIdx, animalIdx);

            GetPostRes getPostRes = new GetPostRes(post.getIdx(), post.getImgUrl(), post.getContent());
            return new BaseResponse<>(getPostRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 게시물 수정
    // [PATCH] /post?animal_id=&post_id=
    @PatchMapping()
    public BaseResponse<UpdatePostRes> updatePost(@RequestParam("animal_id") Long animalIdx, @RequestParam("post_id") Long postIdx, @RequestBody UpdatePostReq updatePostReq){

        //animalIdx null 체크도 해줘야하나?
        if(animalIdx==null || postIdx==null){
            return new BaseResponse<>(URL_VALUE_EMPLY);
        }

        // 게시글의 이미지가 없으면
        if(updatePostReq.getImgUrl().isEmpty() || updatePostReq.getImgUrl() == null){
            return new BaseResponse<>(POST_EMPTY_IMG);
        }
        // 게시물의 내용은 없어도 되지 않나..?
        // 대신 게시글의 글자수 제한은 필요할 듯함.
        if(updatePostReq.getContent().length() > MAX_CONTENT){
            return new BaseResponse<>(POST_CONTENT_LENGTH_OVER);
        }

        try{
            // 게시물 수정
            Post post = postService.update(postIdx, animalIdx, updatePostReq.getImgUrl(), updatePostReq.getContent());

            UpdatePostRes updatePostRes = new UpdatePostRes(post.getIdx());
            return new BaseResponse<>(updatePostRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 게시물 삭제
    // [DELTE] /post?animal_id=&post_id=
    @DeleteMapping()
    public BaseResponse<String> deletePost(@RequestParam("animal_id") Long animalIdx, @RequestParam("post_id") Long postIdx){
        //animalIdx null 체크도 해줘야하나?
        if(animalIdx==null || postIdx==null){
            return new BaseResponse<>(URL_VALUE_EMPLY);
        }

        try{
            postService.delete(postIdx, animalIdx);
            return new BaseResponse<>("게시물 삭제에 성공했습니다.");
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
