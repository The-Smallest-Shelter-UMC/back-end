package umc_sjs.smallestShelter.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import umc_sjs.smallestShelter.domain.User;
import umc_sjs.smallestShelter.user.UserRepository;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try{
            User userEntity = userRepository.findByUsername(username).orElseThrow();
            return new PrincipalDetails(userEntity);
        } catch (Exception exception) {
            throw new RuntimeException("없는 아이디거나 비밀번호가 틀렸습니다.");
        }
    }
}
