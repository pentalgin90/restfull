package ca.home.novacom.restfull.repository;

import ca.home.novacom.restfull.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
