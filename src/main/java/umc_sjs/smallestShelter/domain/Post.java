package umc_sjs.smallestShelter.domain;

<<<<<<< HEAD
=======
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
>>>>>>> 9367bffc6c6b558b8b73efd9efa32729296d4d65
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자 못쓰게 막아둠
@Getter
@Setter
@Entity
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_idx")
    private Long idx;

    // 게시물 이미지
    private String imgUrl;

    // 게시물 내용
    private String content;

    // 해당 게시물을 등록한 동물
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "animal_idx")
    private Animal animal;

    // 게시물이 만들어진 시간
    @CreationTimestamp
    private Timestamp createDate;

    /* 연관관계 편의 메소드 */
    public void setAnimal(Animal animal){
        this.animal = animal;
        animal.getPostList().add(this);
    }
    /* 연관관계 편의 메소드 끝 */

    /* 생성 메소드드 */
    public static Post createPost(String imgUrl, String content, Animal animal){
        Post post = new Post();
        post.setImgUrl(imgUrl);
        post.setContent(content);

        post.setAnimal(animal);

        return post;
    }

    /*  비지니스 로직 */
    // 게시물 수정
    public Post updatePost(String imgUrl, String content){
        this.setImgUrl(imgUrl);
        this.setContent(content);

        return this;
    }

    // 게시물의 주인(반려동물)이 요청값으로 넘어온 반려동물과 일치하는지 확인
    public boolean checkLegal(Long animalIdx){
        if(this.getAnimal().getIdx() == animalIdx){
            return true;
        }
        else {
            return false;
        }
    }
    /*  비지니스 로직 끝 */
}
