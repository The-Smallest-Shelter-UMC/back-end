package umc_sjs.smallestShelter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import umc_sjs.smallestShelter.domain.OrganizationMember;
import umc_sjs.smallestShelter.domain.Role;
import umc_sjs.smallestShelter.dto.user.JoinDto;
import umc_sjs.smallestShelter.repository.OrganizationRepository;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class OrganizationService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OrganizationRepository organizationRepository;

    @Transactional
    public void join(JoinDto joinDto) {

        OrganizationMember organization = OrganizationMember.builder()
                .name(joinDto.getName())
                .userName(joinDto.getUsername())
                .password(bCryptPasswordEncoder.encode(joinDto.getPassword()))
                .organizationName(joinDto.getOrganizationName())
                .phoneNumber(joinDto.getPhoneNumber())
                .profileImgUrl(joinDto.getProfileImgUrl())
                .email(joinDto.getEmail())
                .address(joinDto.getAddress())
                .createDate(joinDto.getCreateDate())
                .role(Role.ORGANIZATION).build();

        organizationRepository.save(organization);
    }
}
