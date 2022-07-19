package umc_sjs.smallestShelter.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import umc_sjs.smallestShelter.controller.dto.MemberDto;
import umc_sjs.smallestShelter.domain.Member;
import umc_sjs.smallestShelter.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
@Getter
@Setter
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/member")
    public MemberDto testController(){
        Member member = new Member();
        member.setName("testMember");
        Long id = memberRepository.join(member);
        Member findMember = memberRepository.findById(id);

        MemberDto memberDto = new MemberDto();
        memberDto.setName(findMember.getName());
        return memberDto;
    }
}
