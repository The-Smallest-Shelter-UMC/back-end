package umc_sjs.smallestShelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.dto.JoinDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class JoinDtoRepository {

    @PersistenceContext
    private EntityManager em;

    public JoinDto findMemberByUserName(String userName){

        em.createQuery("select pm from PrivateMember )

    }

}
