/*
package umc_sjs.smallestShelter.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Getter;
import lombok.Setter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import umc_sjs.smallestShelter.domain.Animal;
import umc_sjs.smallestShelter.domain.Hello;
import umc_sjs.smallestShelter.domain.QAnimal;
import umc_sjs.smallestShelter.domain.QHello;

import javax.persistence.*;
import java.util.List;

@SpringBootTest
@Transactional
public class QuerydslApplicationTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void contextloads() {
        Hello hello = new Hello();
        em.persist(hello);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QHello qHello = new QHello("hello");

        Hello result = query
                .selectFrom(qHello)
                .fetchOne();

        Assertions.assertThat(result).isEqualTo(hello);
        Assertions.assertThat(result.getId()).isEqualTo(hello.getId());
    }
}
*/
