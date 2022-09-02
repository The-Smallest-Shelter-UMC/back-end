package umc_sjs.smallestShelter.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import umc_sjs.smallestShelter.domain.Post;
import umc_sjs.smallestShelter.post.postDto.*;
import umc_sjs.smallestShelter.response.BaseException;
import umc_sjs.smallestShelter.response.BaseResponse;

import static umc_sjs.smallestShelter.response.BaseResponseStatus.*;

@RestController
//@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final int MAX_CONTENT = 300;


    /**
     * 게시물 등록(생성)
     * [POST] /auth/organization/post/join?animal_id=
     * @param animalIdx 반려동물 인덱스
     * @param createPostReq 생성할 게시물의 dto
     * @return
     */
    @PostMapping("/auth/organization/post/join")
    public BaseResponse<CreatePostRes> createPost(@RequestParam(value = "animal_id") Long animalIdx, @RequestBody CreatePostReq createPostReq){

        // 게시글의 이미지가 없으면
        if(createPostReq.getImgUrl() == null || createPostReq.getImgUrl().isEmpty()){
            log.info("게시물 생성 실패: {} (animalIdx: {})", POST_EMPTY_IMG.getMessage(), animalIdx);
            return new BaseResponse<>(POST_EMPTY_IMG);
        }
        // 게시물의 내용이 없으면
        if(createPostReq.getContent() == null || createPostReq.getContent().isEmpty()){
            log.info("게시물 생성 실패: {} (animalIdx: {})", POST_EMPTY_CONTNET.getMessage(), animalIdx);
            return new BaseResponse<>(POST_EMPTY_CONTNET);
        }

        try {
            // 게시물 생성
            Post post = postService.create(animalIdx, createPostReq.getImgUrl(), createPostReq.getContent());

            CreatePostRes createPostRes = new CreatePostRes(post.getIdx());

            log.info("게시물 생성 성공 (animalIdx: {})", animalIdx);
            return new BaseResponse<>(createPostRes);
        } catch (BaseException e){
            log.info("게시물 생성 실패: {} (animalIdx: {})", e.getMessage(), animalIdx);
            return new BaseResponse<>(e.getStatus());
        } catch (Exception e){
            log.error("예상치 못한 게시물 생성 실패: {} (animalIdx: {})", e.getMessage(), animalIdx);
            return new BaseResponse<>(DATABASE_ERROR);
        }

    }


    /**
     * 게시물 조회
     * [GET] /post?animal_id=&post_id=
     * @param animalIdx 반려동물 인덱스
     * @param postIdx 게시물 인덱스
     * @return
     */
    @GetMapping("/post")
    public BaseResponse<GetPostRes> getPost(@RequestParam("animal_id") Long animalIdx, @RequestParam("post_id") Long postIdx){

        try{
            // 게시물 조회
            Post post = postService.get(postIdx, animalIdx);

            GetPostRes getPostRes = new GetPostRes(post.getIdx(), post.getImgUrl(), post.getContent());

            log.info("게시물 조회 성공 (postIdx: {})", postIdx);
            return new BaseResponse<>(getPostRes);
        } catch (BaseException e){
            log.info("게시물 조회 실패: {} (postIdx: {})", e.getMessage(), postIdx);
            return new BaseResponse<>(e.getStatus());
        } catch (Exception e){
            log.error("예상치 못한 게시물 조회 실패: {} (postIdx: {})", e.getMessage(), postIdx);
            return new BaseResponse<>(DATABASE_ERROR);
        }
    }


    /**
     * 게시물 수정
     * [PATCH] /auth/organization/post?animal_id=&post_id=
     * @param animalIdx 반려동물 인덱스
     * @param postIdx 게시물 인덱스
     * @param updatePostReq 수정할 게시글의 dto
     * @return
     */
    @PatchMapping("/auth/organization/post")
    public BaseResponse<UpdatePostRes> updatePost(@RequestParam("animal_id") Long animalIdx, @RequestParam("post_id") Long postIdx, @RequestBody UpdatePostReq updatePostReq){


        // 게시글의 이미지가 없으면
        if(updatePostReq.getImgUrl() == null || updatePostReq.getImgUrl().isEmpty()){
            log.info("게시물 수정 실패: {} (animalIdx: {})", POST_EMPTY_IMG.getMessage(), animalIdx);
            return new BaseResponse<>(POST_EMPTY_IMG);
        }
        // 게시물의 내용은 없으면
        if(updatePostReq.getContent() == null || updatePostReq.getContent().isEmpty()){
            log.info("게시물 수정 실패: {} (animalIdx: {})", POST_EMPTY_CONTNET.getMessage(), animalIdx);
            return new BaseResponse<>(POST_EMPTY_CONTNET);
        }

        try{
            // 게시물 수정
            Post post = postService.update(postIdx, animalIdx, updatePostReq.getImgUrl(), updatePostReq.getContent());

            UpdatePostRes updatePostRes = new UpdatePostRes(post.getIdx());

            log.info("게시물 수정 성공 (postIdx: {})", postIdx);
            return new BaseResponse<>(updatePostRes);
        } catch (BaseException e){
            log.info("게시물 수정 실패: {} (postIdx: {})", e.getMessage(), postIdx);
            return new BaseResponse<>(e.getStatus());
        } catch (Exception e){
            log.error("예상치 못한 게시물 수정 실패: {} (postIdx: {})", e.getMessage(), postIdx);
            return new BaseResponse<>(DATABASE_ERROR);
        }
    }


    /**
     * 게시물 삭제
     * [DELTE] /auth/organization/post?animal_id=&post_id=
     * @param animalIdx 반려동물 인덱스
     * @param postIdx 게시물 인덱스
     * @return
     */
    @DeleteMapping("/auth/organization/post")
    public BaseResponse<String> deletePost(@RequestParam("animal_id") Long animalIdx, @RequestParam("post_id") Long postIdx){

        try{
            // 게시물 삭제
            postService.delete(postIdx, animalIdx);

            log.info("게시물 삭제 성공 (postIdx: {})", postIdx);
            return new BaseResponse<>("게시물 삭제에 성공했습니다.");
        } catch (BaseException e){
            log.info("게시물 삭제 실패: {} (postIdx: {})", e.getMessage(), postIdx);
            return new BaseResponse<>(e.getStatus());
        } catch (Exception e){
            log.error("예상치 못한 게시물 삭제 실패: {} (postIdx: {})", e.getMessage(),postIdx);
            return new BaseResponse<>(DATABASE_ERROR);
        }
    }
}
