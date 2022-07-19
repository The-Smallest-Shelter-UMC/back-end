package umc_sjs.smallestShelter.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Long join(Member member){
        em.persist(member);
        return member.getId();
    }

    @Transactional
    public Member findById(Long id){
        Member findMember = em.find(Member.class, id);
        return findMember;
    }
}
