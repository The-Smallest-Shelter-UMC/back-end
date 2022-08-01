package umc_sjs.smallestShelter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.Post;
import umc_sjs.smallestShelter.dto.post.*;
import umc_sjs.smallestShelter.repository.PostRepository;
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
            postRepository.save(post);
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
            // 해당하는 게시글이 없는 경우
            if(post == null){
                throw new BaseException(POST_NOT_EXIST);
            }

            return new GetPostRes(post.getIdx(), post.getImgUrl(), post.getContent());
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 게시물 수정
    @Transactional
    public UpdatePostRes update(Long postIdx, UpdatePostReq updatePostReq) throws BaseException{
        // 동물 idx에 대해서도 검증이 필요하긴 함.
        // 유저에 대해서도 검증 필요함.

        // 수정 전 게시물
        Post beforePost = postRepository.findById(postIdx);
        // 해당 게시물이 존재하지 않는경우
        if(beforePost == null){
            throw new BaseException(POST_NOT_EXIST);
        }
        // 수정 후 게시물
        Post afterPost = beforePost.updatePost(updatePostReq.getImgUrl(), updatePostReq.getContent());

        // 게시물 수정
        try{
            postRepository.save(afterPost);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }

        return new UpdatePostRes(afterPost.getIdx());
    }

    // 게시물 삭제
    @Transactional
    public void delete(Long postIdx) throws BaseException{

        Post post = postRepository.findById(postIdx);
        if(post == null){
            throw new BaseException(POST_NOT_EXIST);
        }

        try{
            postRepository.delete(post);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
