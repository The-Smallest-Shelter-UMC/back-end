package umc_sjs.smallestShelter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import umc_sjs.smallestShelter.domain.User;
import umc_sjs.smallestShelter.dto.user.GetOrganizationPageRes;
import umc_sjs.smallestShelter.dto.user.GetPrivatePageRes;
import umc_sjs.smallestShelter.dto.user.JoinDto;
import umc_sjs.smallestShelter.dto.user.PatchUserReq;
import umc_sjs.smallestShelter.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;
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
                .organizationName(joinDto.getOrganizationName())
                .role(joinDto.getRole()).build();

        userRepository.save(user);
    }

    @Transactional(readOnly = true) // 마이페이지 - 개인
    public GetPrivatePageRes privatePage(Long userIdx) {

        GetPrivatePageRes getPrivatePageRes = new GetPrivatePageRes();

        Optional<User> user = userRepository.findById(userIdx);

        getPrivatePageRes.setUserIdx(user.get().getIdx());
        getPrivatePageRes.setName(user.get().getName());
        getPrivatePageRes.setPhoneNumber(user.get().getPhoneNumber());
        getPrivatePageRes.setAddress(user.get().getAddress());
        getPrivatePageRes.setEmail(user.get().getEmail());
        getPrivatePageRes.setRole(user.get().getRole().toString());
        getPrivatePageRes.setProfileImgUrl(user.get().getProfileImgUrl());

        return getPrivatePageRes;
    }

    @Transactional(readOnly = true) // 마이페이지 - 단체
    public GetOrganizationPageRes organizationPage(Long userIdx) {

        GetOrganizationPageRes getOrganizationPageRes = new GetOrganizationPageRes();

        Optional<User> user = userRepository.findById(userIdx);

        getOrganizationPageRes.setUserIdx(user.get().getIdx());
        getOrganizationPageRes.setName(user.get().getName());
        getOrganizationPageRes.setPhoneNumber(user.get().getPhoneNumber());
        getOrganizationPageRes.setAddress(user.get().getAddress());
        getOrganizationPageRes.setEmail(user.get().getEmail());
        getOrganizationPageRes.setRole(user.get().getRole().toString());
        getOrganizationPageRes.setProfileImgUrl(user.get().getProfileImgUrl());
        getOrganizationPageRes.setOrganizationName(user.get().getOrganizationName().toString());

        return getOrganizationPageRes;
    }

    @Transactional // 회원정보수정 - 개인
    public Long updatePrivate(Long userIdx, PatchUserReq patchUserReq) {
        Optional<User> user = userRepository.findById(userIdx);

        user.get().setName(patchUserReq.getName());
        user.get().setPhoneNumber(patchUserReq.getPhoneNumber());
        user.get().setAddress(patchUserReq.getAddress());
        user.get().setEmail(patchUserReq.getEmail());

        userRepository.save(user.get());

        return user.get().getIdx();
    }

    @Transactional // 회원정보수정 - 단체
    public Long updateOrganization(Long userIdx, PatchUserReq patchUserReq) {
        Optional<User> user = userRepository.findById(userIdx);

        user.get().setName(patchUserReq.getName());
        user.get().setPhoneNumber(patchUserReq.getPhoneNumber());
        user.get().setAddress(patchUserReq.getAddress());
        user.get().setEmail(patchUserReq.getEmail());

        userRepository.save(user.get());

        return user.get().getIdx();
    }
}

