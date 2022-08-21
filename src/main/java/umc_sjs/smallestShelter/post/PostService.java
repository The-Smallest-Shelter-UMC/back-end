package umc_sjs.smallestShelter.post;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.Post;
import umc_sjs.smallestShelter.animal.AnimalRepository;
import umc_sjs.smallestShelter.response.BaseException;

import javax.persistence.NoResultException;
import java.util.List;

import static umc_sjs.smallestShelter.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final AnimalRepository animalRepository;


    /**
     * 게시물 생성
     * @param animalIdx 반려동물 인덱스
     * @param imgUrl 게시물 이미지 url
     * @param content 게시물 내용
     * @return Post
     * @throws BaseException
     */
    @Transactional
    public Post create(Long animalIdx, String imgUrl, String content) throws BaseException{

        // 동물 찾기
        Animal animal;
        try {
            animal = animalRepository.findAnimalById(animalIdx);
        } catch (EmptyResultDataAccessException e){
            throw new BaseException(NON_EXISTING_ANIMAL);
        }


        // 게시물 만들기
        Post post = Post.createPost(imgUrl, content, animal);
        postRepository.save(post);

        return post;
    }

    /**
     * 게시물 조회
     * @param postIdx 게시물 인덱스
     * @param animalIdx 동물 인덱스
     * @return Post
     * @throws BaseException
     */
    public Post get(Long postIdx, Long animalIdx) throws BaseException{

        // 게시물 조회
        Post post = findPost(postIdx);

        // 게시물 idx와 동물 idx가 일치하는지 확인
        checkPostLegal(post, animalIdx);

        return post;
    }

    /**
     * 게시물 수정
     * @param postIdx 게시물 인덱스
     * @param animalIdx 반려동물 인덱스
     * @param imgUrl 수정할 이미지 url
     * @param content 수정할 내용
     * @return 수정된 Post
     * @throws BaseException
     */
    @Transactional
    public Post update(Long postIdx, Long animalIdx, String imgUrl, String content) throws BaseException{

        // 수정 전 게시물 조회
        Post beforUpdatePost = findPost(postIdx);

        // 게시물 idx와 동물 idx가 일치하는지 확인
        checkPostLegal(beforUpdatePost, animalIdx);

        // 수정 후 게시물
        Post afterUpdatePost = beforUpdatePost.updatePost(imgUrl, content);

        // 게시물 수정
        postRepository.save(afterUpdatePost);
        return afterUpdatePost;
    }

    /**
     * 게시물 삭제
     * @param postIdx 게시물 idx
     * @param animalIdx 반려동물 idx
     * @throws BaseException
     */
    @Transactional
    public void delete(Long postIdx, Long animalIdx) throws BaseException{

        // 게시물 조회
        Post post = findPost(postIdx);

        // 게시물 idx와 동물 idx가 일치하는지 확인
        checkPostLegal(post, animalIdx);

        // 게시물 삭제
        postRepository.delete(post);
    }


    /**
     * 게시물의 idx와 동물 idx가 일치하는지 확인
     * @param post 게시물
     * @param animalIdx 반려동물 idx
     * @return true if 게시물의 idx와 동물 idx가 일치, false if not
     * @throws BaseException 게시물의 주인(반려동물)이 요청값으로 넘어온 반려동물과 일치하지 않으면
     */
    private boolean checkPostLegal(Post post, Long animalIdx) throws BaseException{
        // 게시물의 주인(반려동물)이 요청값으로 넘어온 반려동물과 일치하지 않으면
        if(!post.checkLegal(animalIdx)){
            throw new BaseException(POSTIDX_ANIMALIDX_ILLEGAL);
        }

        return true;
    }


    /**
     * 게시물 idx로 게시물 찾기 (fetch join한 post임)
     * @param postIdx
     * @return Post
     * @throws BaseException 게시물이 존재하지 않을 경우
     */
    public Post findPost(Long postIdx) throws BaseException{
        try {
            return postRepository.findPost(postIdx);
        } catch (EmptyResultDataAccessException e){ // 해당하는 게시물이 없을경우
            throw new BaseException(POST_NOT_EXIST);
        }

    }

    //건호 추가
    public List<Post> getAnimalPost(Long anmIdx) {
        List<Post> postList = postRepository.findPostByAnimalId(anmIdx);
        return postList;

    }
}
