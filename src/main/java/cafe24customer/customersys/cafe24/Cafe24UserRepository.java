package cafe24customer.customersys.cafe24;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Cafe24UserRepository extends JpaRepository<Cafe24User, Long>{
	
	
	Optional<Cafe24User>  findByUserId(String userId);
	
	Cafe24User findBySessionId(String sessionId);
}
