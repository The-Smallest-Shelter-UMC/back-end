package umc_sjs.smallestShelter.config.jwt;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import umc_sjs.smallestShelter.response.BaseResponse;
import umc_sjs.smallestShelter.response.BaseResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        System.out.println("authException " + accessDeniedException.getMessage());

        BaseResponse<String> baseResponse = new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        MediaType jsonMimeType = MediaType.APPLICATION_JSON;

        jsonConverter.write(baseResponse, jsonMimeType, new ServletServerHttpResponse(response));

    }
}
