package umc_sjs.smallestShelter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostReq {

    private String imgUrl; //이미지 url
    private String content; //게시글 내용
}
