package umc_sjs.smallestShelter.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_idx")
    private Long idx;

    private String imgUrl;
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "animal_idx")
    private Animal animal;

    @CreationTimestamp
    private Timestamp createDate;

    public static Post createPost(String imgUrl, String content, Animal animal){
        Post post = new Post();
        post.setImgUrl(imgUrl);
        post.setContent(content);
        post.setAnimal(animal);

        return post;
    }
}
