package umc_sjs.smallestShelter.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.*;

import umc_sjs.smallestShelter.dto.SearchAnimalReq;
import umc_sjs.smallestShelter.dto.getAnimalDetailDto.RecommandAnimalDto;
import umc_sjs.smallestShelter.dto.getAnimalDto.GetAnimalDto;
import umc_sjs.smallestShelter.dto.getAnimalDto.GetAnimalRes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Random;

@Repository
@Transactional
public class AnimalRepository {

    @PersistenceContext
    private EntityManager em;

    private Random random = new Random();

    public Long saveAnimal(Animal joinAnimal, List<String> illnessNameList){

        for (String illnessName : illnessNameList) {
            Illness illness = new Illness(illnessName);
            illness.modifyAnimal(joinAnimal);
            em.persist(illness);
        }

        em.persist(joinAnimal);

        return joinAnimal.getIdx();
    }

    public OrganizationMember findOrganizationMember(Long userIdx) {
        OrganizationMember findOrganization = em.find(OrganizationMember.class, userIdx);
        return findOrganization;
    }

    public GetAnimalRes getAnimals(int page, GetAnimalRes getAnimalRes) {

        List<GetAnimalDto> animalList = em.createQuery("select new umc_sjs.smallestShelter.dto.getAnimalDto.GetAnimalDto(a.idx, a.name, a.mainImgUrl, a.species, a.gender, a.isAdopted, a.age) from Animal a order by a.createDate desc", GetAnimalDto.class)
                .setFirstResult(page * 12)
                .setMaxResults(12)
                .getResultList();

        Long animalCount = em.createQuery("select count(a) from Animal a", Long.class)
                .getSingleResult();

        getAnimalRes.setAnimal(animalList);
        getAnimalRes.setPageNumber((animalCount.intValue()/12));

        return getAnimalRes;
    }

    public Animal findAnimalById(Long anmIdx) {

        Animal findAnimal = em.createQuery("select a from Animal a left join fetch a.illnessList where a.idx =: anmIdx", Animal.class)
                .setParameter("anmIdx", anmIdx)
                .getSingleResult();

        return findAnimal;
    }

    public List<Post> findPostById(Long anmIdx) {
        List<Post> postList = em.createQuery("select p from Post p where p.animal.idx =: anmIdx", Post.class)
                .setParameter("anmIdx", anmIdx)
                .getResultList();

        return postList;
    }

    public void deleteAnimal(Long anmIdx){

        em.remove(anmIdx);

        /*Animal findAnimal = findAnimalById(anmIdx);
        em.remove(findAnimal);*/
    }

    public List<RecommandAnimalDto> getRecommendAnimals(Long anmIdx) {

        Long animalCount = em.createQuery("select count(a) from Animal a", Long.class)
                .getSingleResult();

        random.setSeed(System.currentTimeMillis());
        int randomNumber = random.nextInt(animalCount.intValue());

        List<RecommandAnimalDto> resultList = em.createQuery("select new umc_sjs.smallestShelter.dto.getAnimalDetailDto.RecommandAnimalDto(a.idx, a.mainImgUrl) from Animal a " +
                        "where a.isAdopted = false ", RecommandAnimalDto.class)
                .setFirstResult(randomNumber)
                .setMaxResults(12)
                .getResultList();

        System.out.println("randomNumber = " + randomNumber);

        for (RecommandAnimalDto recommandAnimalDto : resultList) {
            System.out.println("recommandAnimalDto = " + recommandAnimalDto.getRecommandAnmIdx());
        }

        return resultList;
    }

    public GetAnimalRes searchAnimal(int page, SearchAnimalReq searchAnimalReq, GetAnimalRes getAnimalRes) {

        String query = null;
        String countQuery = null;

        if (searchAnimalReq.getAgeBoundary() == AgeBoundary.PUPPY) {
            query = "select new umc_sjs.smallestShelter.dto.getAnimalDto.GetAnimalDto(a.idx, a.name, a.mainImgUrl, a.species, a.gender, a.isAdopted, a.age) from Animal a " +
                    "where a.species =: species and a.gender =: gender and a.age.year = 0 and a.isAdopted =: isAdopted" +
                    " order by a.createDate desc";

            countQuery = "select count(a) from Animal a " +
                    "where a.species =: species and a.gender =: gender and a.age.year = 0 and a.isAdopted =: isAdopted";
        }

        else if (searchAnimalReq.getAgeBoundary() == AgeBoundary.JUNIOR) {
            query = "select new umc_sjs.smallestShelter.dto.getAnimalDto.GetAnimalDto(a.idx, a.name, a.mainImgUrl, a.species, a.gender, a.isAdopted, a.age) from Animal a " +
                    "where a.species =: species and a.gender =: gender and a.age.year >= 1 and a.age.year <= 2 and a.isAdopted =: isAdopted" +
                    " order by a.createDate desc";

            countQuery = "select count(a) from Animal a " +
                    "where a.species =: species and a.gender =: gender and a.age.year >= 1 and a.age.year <= 2 and a.isAdopted =: isAdopted";
        }

        else if (searchAnimalReq.getAgeBoundary() == AgeBoundary.ADULT) {
            query = "select new umc_sjs.smallestShelter.dto.getAnimalDto.GetAnimalDto(a.idx, a.name, a.mainImgUrl, a.species, a.gender, a.isAdopted, a.age) from Animal a " +
                    "where a.species =: species and a.gender =: gender and a.age.year >= 3 and a.age.year <= 8 and a.isAdopted =: isAdopted" +
                    " order by a.createDate desc";

            countQuery = "select count(a) from Animal a " +
                    "where a.species =: species and a.gender =: gender and a.age.year >= 3 and a.age.year <= 8 and a.isAdopted =: isAdopted";
        }

        else if (searchAnimalReq.getAgeBoundary() == AgeBoundary.SENIOR) {
            query = "select new umc_sjs.smallestShelter.dto.getAnimalDto.GetAnimalDto(a.idx, a.name, a.mainImgUrl, a.species, a.gender, a.isAdopted, a.age) from Animal a " +
                    "where a.species =: species and a.gender =: gender and a.age.year >= 9 and a.isAdopted =: isAdopted" +
                    " order by a.createDate desc";

            countQuery = "select count(a) from Animal a " +
                    "where a.species =: species and a.gender =: gender and a.age.year >= 9 and a.isAdopted =: isAdopted";
        }

        System.out.println("query = " + query);

        List<GetAnimalDto> animalList = em.createQuery(query, GetAnimalDto.class)
                .setParameter("species", searchAnimalReq.getSpecies())
                .setParameter("gender", searchAnimalReq.getGender())
                .setParameter("isAdopted", searchAnimalReq.getIsAdopted())
                .setFirstResult(page * 12)
                .setMaxResults(12)
                .getResultList();

        Long animalCount = em.createQuery(countQuery, Long.class)
                .setParameter("species", searchAnimalReq.getSpecies())
                .setParameter("gender", searchAnimalReq.getGender())
                .setParameter("isAdopted", searchAnimalReq.getIsAdopted())
                .getSingleResult();

        getAnimalRes.setAnimal(animalList);
        getAnimalRes.setPageNumber((animalCount.intValue()/12));

        return getAnimalRes;
    }
}
