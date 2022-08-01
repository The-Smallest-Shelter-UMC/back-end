/*
package umc_sjs.smallestShelter.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import umc_sjs.smallestShelter.config.auth.PrincipalDetails;
import umc_sjs.smallestShelter.dto.JoinDto;
import umc_sjs.smallestShelter.dto.LoginDto;
import umc_sjs.smallestShelter.repository.JoinDtoRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JoinDtoRepository joinDtoRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JoinDtoRepository joinDtoRepository) {
        super(authenticationManager);
        this.joinDtoRepository = joinDtoRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);

        if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        String username =
                JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken)
                        .getClaim("username").asString();

        if (username != null){

            JoinDto joinDto = joinDtoRepository.findByUserName(username);

            PrincipalDetails principalDetails = new PrincipalDetails(joinDto);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }
    }
*/
//}