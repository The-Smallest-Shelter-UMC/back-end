package umc_sjs.smallestShelter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.Post;
import umc_sjs.smallestShelter.repository.AnimalRepository;
import umc_sjs.smallestShelter.repository.PostRepository;
import umc_sjs.smallestShelter.response.BaseException;

import java.util.List;

import static umc_sjs.smallestShelter.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final AnimalRepository animalRepository;

    @Transactional
    // 게시글(피드) 생성
    public Post create(Long animalIdx, String imgUrl, String content) throws BaseException{
        // 동물 찾기
        Animal animal;
        try {
            animal = animalRepository.findAnimalById(animalIdx);

        } catch (EmptyResultDataAccessException e){ // 해당하는 동물이 없을경우
            throw new BaseException(NON_EXISTING_ANIMAL);
        } catch (Exception e){
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }

        // 게시물 만들기
        Post post = Post.createPost(imgUrl, content, animal);

        try {
            // 게시물 저장
            postRepository.save(post);
            return post;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 게시물 조회
    public Post getPost (Long postIdx, Long animalIdx) throws BaseException{
        try{
            // 게시물 조회
            Post post = findPostOne(postIdx);

            checkPostLegal(post, animalIdx);

            return post;
        } catch (BaseException e){
            throw new BaseException(e.getStatus());
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 게시물 수정
    @Transactional
    public Post update(Long postIdx, Long animalIdx, String imgUrl, String content) throws BaseException{

        try{
            // 수정 전 게시물 조회
            Post beforUpdatePost = findPostOne(postIdx);

            checkPostLegal(beforUpdatePost, animalIdx);

            // 수정 후 게시물
            Post afterUpdatePost = beforUpdatePost.updatePost(imgUrl, content);

            // 게시물 수정
            postRepository.save(afterUpdatePost);
            return afterUpdatePost;
        } catch (BaseException e){
            throw new BaseException(e.getStatus());
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 게시물 삭제
    @Transactional
    public void delete(Long postIdx, Long animalIdx) throws BaseException{

        try{
            Post post = findPostOne(postIdx);

            checkPostLegal(post, animalIdx);
            // 게시물 삭제
            postRepository.delete(post);
        } catch (BaseException e){
            throw new BaseException(e.getStatus());
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 게시물 idx와 동물 idx가 일치하는지 확인
    private boolean checkPostLegal(Post post, Long animalIdx) throws BaseException{

        // 게시물의 주인(반려동물)이 요청값으로 넘어온 반려동물과 일치하지 않으면
        if(!post.checkLegal(animalIdx)){
            throw new BaseException(POSTIDX_ANIMALIDX_ILLEGAL);
        }

        return true;
    }

    // 게시물 찾기
    public Post findPostOne(Long postIdx) throws BaseException{
        try{
            return postRepository.findPost(postIdx);
        } catch (EmptyResultDataAccessException e){ // 해당하는 게시물이 없을경우
            throw new BaseException(POST_NOT_EXIST);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //건호 추가
    public List<Post> getAnimalPost(Long anmIdx) {
        List<Post> postList = postRepository.findPostById(anmIdx);
        return postList;
    }
}
