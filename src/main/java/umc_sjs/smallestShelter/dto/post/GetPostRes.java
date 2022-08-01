package umc_sjs.smallestShelter.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPostRes {

    private Long postIdx; //게시물 idx
    private String imgUrl; //게시물 이미지
    private String content; //게시물 내용
}
