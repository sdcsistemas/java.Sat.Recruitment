package sat.recruitment.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sat.recruitment.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
