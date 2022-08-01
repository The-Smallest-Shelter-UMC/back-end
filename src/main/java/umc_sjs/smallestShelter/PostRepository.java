package umc_sjs.smallestShelter;

import org.springframework.stereotype.Repository;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.Post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public void create(Post post){
        em.persist(post);
    }

    public Animal findAnimalByIdx(Long animalIdx){
        return em.find(Animal.class, animalIdx);
    }
}
