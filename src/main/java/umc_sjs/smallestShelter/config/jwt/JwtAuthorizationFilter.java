package umc_sjs.smallestShelter.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import umc_sjs.smallestShelter.config.auth.PrincipalDetails;
import umc_sjs.smallestShelter.domain.User;
import umc_sjs.smallestShelter.repository.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("AuthorizationFilter들어옴");

        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);

        if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }
        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        String username =
                JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken)
                        .getClaim("username").asString();

        System.out.println("username :::::::::::::::: " + username);

        if (username != null){
            User user = userRepository.findByUsername(username);

            System.out.println("user :::::::::::::::: " + user);

            PrincipalDetails principalDetails = new PrincipalDetails(user);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            PrincipalDetails principalDetails1 = (PrincipalDetails) authentication.getPrincipal();

            System.out.println("authentication:::::::::::::::" + principalDetails1.getUser().getRole());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }
    }
}