package umc_sjs.smallestShelter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import umc_sjs.smallestShelter.domain.PrivateMember;
import umc_sjs.smallestShelter.domain.Role;
import umc_sjs.smallestShelter.dto.JoinDto;
import umc_sjs.smallestShelter.repository.PrivateMemberRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PrivateMemberService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PrivateMemberRepository privateMemberRepository;

    @Transactional
    public void join(JoinDto joinDto) {

        PrivateMember privateMember = PrivateMember.builder()
                .idx(joinDto.getIdx())
                .name(joinDto.getName())
                .userName(joinDto.getUserName())
                .password(bCryptPasswordEncoder.encode(joinDto.getPassword()))
                .phoneNumber(joinDto.getPhoneNumber())
                .profileImgUrl(joinDto.getProfileImgUrl())
                .email(joinDto.getEmail())
                .address(joinDto.getAddress())
                .createDate(joinDto.getCreateDate())
                .role(Role.PRIVATE).build();

        privateMemberRepository.save(privateMember);
    }
}

