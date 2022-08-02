package umc_sjs.smallestShelter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import umc_sjs.smallestShelter.domain.PrivateMember;
import umc_sjs.smallestShelter.domain.Role;
import umc_sjs.smallestShelter.domain.User;
import umc_sjs.smallestShelter.dto.JoinDto;
import umc_sjs.smallestShelter.repository.PrivateMemberRepository;
import umc_sjs.smallestShelter.repository.UserRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void join(JoinDto joinDto) {

        User user = User.builder()
                .name(joinDto.getName())
                .username(joinDto.getUsername())
                .password(bCryptPasswordEncoder.encode(joinDto.getPassword()))
                .phoneNumber(joinDto.getPhoneNumber())
                .profileImgUrl(joinDto.getProfileImgUrl())
                .email(joinDto.getEmail())
                .address(joinDto.getAddress())
                .createDate(joinDto.getCreateDate())
                .role(joinDto.getRole()).build();

        userRepository.save(user);
    }
}

