package cafe24customer.customersys.cafe24;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SessionData {
	
	@Id
	private String sessionId;
	
	private String mall_id;
	private String codeVerifier;

}
