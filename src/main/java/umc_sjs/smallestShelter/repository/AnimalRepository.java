package umc_sjs.smallestShelter.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.*;

import umc_sjs.smallestShelter.dto.animal.AdoptAnimalRes;
import umc_sjs.smallestShelter.dto.animal.LikeAnimalRes;
import umc_sjs.smallestShelter.dto.animal.SearchAnimalReq;
import umc_sjs.smallestShelter.dto.animal.getAnimalDetailDto.RecommandAnimalDto;
import umc_sjs.smallestShelter.dto.animal.getAnimalDto.GetAnimalDto;
import umc_sjs.smallestShelter.dto.animal.getAnimalDto.GetAnimalRes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Random;

import static umc_sjs.smallestShelter.domain.QAnimal.animal;

@Repository
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

    public User findUser(Long userIdx) {
        User findUser = em.find(User.class, userIdx);
        return findUser;
    }

    public GetAnimalRes getAnimals(int page, GetAnimalRes getAnimalRes) {

        List<GetAnimalDto> animalList = em.createQuery("select new umc_sjs.smallestShelter.dto.animal.getAnimalDto.GetAnimalDto(a.idx, a.name, a.mainImgUrl, a.species, a.gender, a.isAdopted, a.age) from Animal a order by a.createDate desc", GetAnimalDto.class)
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

    // 연지 추가
    public List<Animal> findByUserIdx(Long userIdx, int page) {
        List<Animal> animals = em.createQuery("select a from Animal a where a.uploadUser.idx =: userIdx order by a.createDate desc", Animal.class)
                .setParameter("userIdx", userIdx)
                .setFirstResult(page * 2)
                .setMaxResults(2)
                .getResultList();

        return animals;
    }

    public void deleteAnimal(Animal animal){

        em.remove(animal);
    }

    public List<RecommandAnimalDto> getRecommendAnimals(Long anmIdx) {

        Long animalCount = em.createQuery("select count(a) from Animal a", Long.class)
                .getSingleResult();

        random.setSeed(System.currentTimeMillis());
        int randomNumber = random.nextInt(animalCount.intValue());

        List<RecommandAnimalDto> resultList = em.createQuery("select new umc_sjs.smallestShelter.dto.animal.getAnimalDetailDto.RecommandAnimalDto(a.idx, a.mainImgUrl) from Animal a " +
                        "where a.isAdopted = false ", RecommandAnimalDto.class)
                .setFirstResult(randomNumber)
                .setMaxResults(12)
                .getResultList();

        System.out.println("randomNumber = " + randomNumber);
        System.out.println("resultList.size() = " + resultList.size());

        for (RecommandAnimalDto recommandAnimalDto : resultList) {
            System.out.println("recommandAnimalDto = " + recommandAnimalDto.getRecommandAnmIdx());
        }

        return resultList;
    }

    // 동물 검색 querydsl
    public GetAnimalRes searchAnimal(int page, SearchAnimalReq searchAnimalReq, GetAnimalRes getAnimalRes) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QAnimal qAnimal = new QAnimal("animal");

        List<GetAnimalDto> getAnimalDtoList = queryFactory
                .select(Projections.fields(GetAnimalDto.class,
                        animal.idx.as("animalIdx"),
                        animal.name,
                        animal.mainImgUrl.as("imgUrl"),
                        animal.species,
                        animal.gender,
                        animal.isAdopted,
                        animal.age))
                .from(animal)
                .where(ageBoundaryEq(searchAnimalReq.getAgeBoundary()),
                        speciesEq(searchAnimalReq.getSpecies()),
                        genderEq(searchAnimalReq.getGender()),
                        isAdoptedEq(searchAnimalReq.getIsAdopted()))
                .orderBy(animal.createDate.desc())
                .offset(page * 12)
                .limit(12)
                .fetch();

        int count = queryFactory
                .selectFrom(animal)
                .where(ageBoundaryEq(searchAnimalReq.getAgeBoundary()),
                        speciesEq(searchAnimalReq.getSpecies()),
                        genderEq(searchAnimalReq.getGender()),
                        isAdoptedEq(searchAnimalReq.getIsAdopted()))
                .orderBy(animal.createDate.desc())
                .fetch().size();
        
        getAnimalRes.setAnimal(getAnimalDtoList);
        getAnimalRes.setPageNumber(count/12);
        
        return getAnimalRes;
    }

    private BooleanExpression ageBoundaryEq(AgeBoundary ageBoundary) {
        if (ageBoundary == null) {
            return null;
        } else if (ageBoundary == AgeBoundary.PUPPY) {
            return animal.age.year.eq(0);
        } else if (ageBoundary == AgeBoundary.JUNIOR) {
            return animal.age.year.between(1, 2);
        } else if (ageBoundary == AgeBoundary.ADULT) {
            return animal.age.year.between(3, 8);
        } else  {
            return animal.age.year.goe(9);
        }
    }

    private BooleanExpression speciesEq(Species species) {
        if (species == null) {
            return null;
        }
        return animal.species.eq(species);
    }

    private BooleanExpression genderEq(Gender gender) {
        if (gender == null) {
            return null;
        }
        return animal.gender.eq(gender);
    }

    private BooleanExpression isAdoptedEq(Boolean isAdopted) {
        if (isAdopted == null) {
            return null;
        }
        return animal.isAdopted.eq(isAdopted);
    }

    // 동물 검색 querydsl 끝


    public AdoptAnimalRes setIsAdopt(Long anmIdx) {

        Boolean isAdopted = em.createQuery("select a.isAdopted from Animal a where a.idx =: anmIdx", Boolean.class)
                .setParameter("anmIdx", anmIdx)
                .getSingleResult();

        if (isAdopted == true) {
            em.createQuery("update Animal a set a.isAdopted = false where a.idx =: anmIdx")
                    .setParameter("anmIdx", anmIdx)
                    .executeUpdate();

            AdoptAnimalRes adoptAnimalRes = em.createQuery("select new umc_sjs.smallestShelter.dto.animal.AdoptAnimalRes(a.idx, a.isAdopted) from Animal a where a.idx =: anmIdx", AdoptAnimalRes.class)
                    .setParameter("anmIdx", anmIdx)
                    .getSingleResult();

            return adoptAnimalRes;
        }
        else{
            em.createQuery("update Animal a set a.isAdopted = true where a.idx =: anmIdx")
                    .setParameter("anmIdx", anmIdx)
                    .executeUpdate();

            AdoptAnimalRes adoptAnimalRes = em.createQuery("select new umc_sjs.smallestShelter.dto.animal.AdoptAnimalRes(a.idx, a.isAdopted) from Animal a where a.idx =: anmIdx", AdoptAnimalRes.class)
                    .setParameter("anmIdx", anmIdx)
                    .getSingleResult();

            return adoptAnimalRes;
        }
    }

    public LikeAnimalRes setFavoriteAnimal(Long userIdx, Long animalIdx, LikeAnimalRes likeAnimalRes) {

        List<FavoriteAnimal> resultList = em.createQuery("select fa from FavoriteAnimal fa where fa.animal.idx =: animalIdx and fa.likeUser.idx =: userIdx", FavoriteAnimal.class)
                .setParameter("animalIdx", animalIdx)
                .setParameter("userIdx", userIdx)
                .getResultList();

        if(resultList.size() == 0){
            FavoriteAnimal favoriteAnimal = new FavoriteAnimal();

            favoriteAnimal.modifyLikeUserAndAnimal(findUser(userIdx), findAnimalById(animalIdx));
            em.persist(favoriteAnimal);

            likeAnimalRes.setUserIdx(favoriteAnimal.getLikeUser().getIdx());
            likeAnimalRes.setAnimalIdx(favoriteAnimal.getAnimal().getIdx());
            likeAnimalRes.setLike(true);

            return likeAnimalRes;
        }

        else{
            FavoriteAnimal favoriteAnimal = resultList.get(0);

            Long findUserIdx = favoriteAnimal.getLikeUser().getIdx();
            Long findAnimalIdx = favoriteAnimal.getAnimal().getIdx();

            likeAnimalRes.setUserIdx(findUserIdx);
            likeAnimalRes.setAnimalIdx(findAnimalIdx);
            likeAnimalRes.setLike(false);

            em.remove(favoriteAnimal);

            return likeAnimalRes;
        }

    }
}
