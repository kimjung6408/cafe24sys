package cafe24customer.customersys.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface CustomUserRepository extends JpaRepository<CustomUser, String>{
	CustomUser findByUsername(String username);
}
