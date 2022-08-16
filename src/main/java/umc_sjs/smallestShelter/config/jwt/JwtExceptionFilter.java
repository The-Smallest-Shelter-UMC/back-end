package umc_sjs.smallestShelter.config.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import umc_sjs.smallestShelter.response.BaseResponse;
import umc_sjs.smallestShelter.response.BaseResponseStatus;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response);
        }

    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");

        BaseResponse<Object> baseResponse = new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
        response.getWriter().write(baseResponse.getMessage());
    }
}
