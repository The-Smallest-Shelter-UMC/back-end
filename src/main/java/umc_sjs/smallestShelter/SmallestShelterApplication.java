package umc_sjs.smallestShelter;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

@SpringBootApplication
public class SmallestShelterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmallestShelterApplication.class, args);
	}

}
