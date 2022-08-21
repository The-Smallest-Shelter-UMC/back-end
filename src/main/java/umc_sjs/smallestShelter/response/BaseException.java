package umc_sjs.smallestShelter.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
public class BaseException extends Exception {
    private BaseResponseStatus status;

    public BaseException(BaseResponseStatus status){
        super(status.getMessage());
        this.status = status;
    }

    //public BaseException getStatus;
}
