package umc_sjs.smallestShelter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import umc_sjs.smallestShelter.domain.User;
import umc_sjs.smallestShelter.dto.user.GetPrivatePageRes;
import umc_sjs.smallestShelter.dto.user.JoinDto;
import umc_sjs.smallestShelter.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

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

    // 마이페이지 - 개인
    public GetPrivatePageRes privatePage(Long userIdx) {

        GetPrivatePageRes getPrivatePageRes = new GetPrivatePageRes();

        Optional<User> user = userRepository.findById(userIdx);

        System.out.println("UserService" + user.get());

        getPrivatePageRes.setUserIdx(user.get().getIdx());
        getPrivatePageRes.setName(user.get().getName());
        getPrivatePageRes.setPhoneNumber(user.get().getPhoneNumber());
        getPrivatePageRes.setAddress(user.get().getAddress());
        getPrivatePageRes.setEmail(user.get().getEmail());
        getPrivatePageRes.setRole(user.get().getRole().toString());
        getPrivatePageRes.setProfileImgUrl(user.get().getProfileImgUrl());

        return getPrivatePageRes;
    }
}

