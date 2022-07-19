package umc_sjs.smallestShelter;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.Member;
import umc_sjs.smallestShelter.repository.MemberRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(value = false)
    @Transactional
    public void testMember() throws Exception{
        //given
        Member member = new Member();
        member.setName("member1");
        //when
        Long id = memberRepository.join(member);
        Member findMember = memberRepository.findById(id);
        //then
        Assertions.assertThat(member).isEqualTo(findMember);
        Assertions.assertThat(member.getId()).isEqualTo(findMember.getId());
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());

    }
}