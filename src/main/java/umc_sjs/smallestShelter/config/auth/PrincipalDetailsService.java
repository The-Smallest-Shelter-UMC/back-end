/*
package umc_sjs.smallestShelter.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import umc_sjs.smallestShelter.dto.JoinDto;
import umc_sjs.smallestShelter.repository.JoinDtoRepository;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final JoinDtoRepository joinDtoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JoinDto joinDto = joinDtoRepository.findByUserName(username);

        return new PrincipalDetails(joinDto);
    }
}*/
