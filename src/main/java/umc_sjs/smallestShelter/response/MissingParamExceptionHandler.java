package umc_sjs.smallestShelter.response;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class MissingParamExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<BaseResponse> MissingRequestParamException(MissingServletRequestParameterException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse(BaseResponseStatus.EMPTY_URL_VALUE));

    }

    @ExceptionHandler(com.auth0.jwt.exceptions.TokenExpiredException.class)
    protected ResponseEntity<BaseResponse> TokenExpiredException(TokenExpiredException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse(BaseResponseStatus.INVALID_JWT));
    }
}
