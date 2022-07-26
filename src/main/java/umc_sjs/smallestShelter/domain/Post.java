package umc_sjs.smallestShelter.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor
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
}
