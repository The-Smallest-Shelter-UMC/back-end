package umc_sjs.smallestShelter.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Age {

    private Integer year;
    private Integer month;
    private boolean isGuessed;

    public Age(Integer year, Integer month, boolean isGuessed) {
        this.year = year;
        this.month = month;
        this.isGuessed = isGuessed;
    }
}
