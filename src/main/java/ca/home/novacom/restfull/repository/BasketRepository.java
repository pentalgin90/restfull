package ca.home.novacom.restfull.repository;

import ca.home.novacom.restfull.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}
