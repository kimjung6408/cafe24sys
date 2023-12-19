package cafe24customer.customersys.user;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CustomUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String username;
	
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name="role")
    @CollectionTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id")
    )
    private Set<String> roles;

	@Column(unique=true)
	private String sessionId;
}
