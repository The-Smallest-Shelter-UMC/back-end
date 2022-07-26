package umc_sjs.smallestShelter;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.AnimalIllness;
import umc_sjs.smallestShelter.domain.Illness;
import umc_sjs.smallestShelter.domain.OrganizationMember;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class AnimalRepository {

    @PersistenceContext
    private EntityManager em;

    public Long saveAnimal(Animal joinAnimal, List<String> illnessNameList){

        for (String illnessName : illnessNameList) {
            Illness illness = new Illness(illnessName);
            AnimalIllness animalIllness = new AnimalIllness(illness);
            animalIllness.modifyAnimal(joinAnimal);
            em.persist(illness);
            em.persist(animalIllness);
        }

        em.persist(joinAnimal);

        return joinAnimal.getIdx();
    }

    public OrganizationMember findOrganizationMember(Long userIdx) {
        OrganizationMember findOrganization = em.find(OrganizationMember.class, userIdx);
        return findOrganization;
    }

    public List<Animal> getAnimals(int page) {

        List<Animal> animalList = em.createQuery("select a from Animal a order by a.createDate desc", Animal.class)
                .setFirstResult(page * 12)
                .setMaxResults(12)
                .getResultList();

        return animalList;
    }
}
