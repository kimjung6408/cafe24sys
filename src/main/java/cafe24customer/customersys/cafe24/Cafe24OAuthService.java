package cafe24customer.customersys.cafe24;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(Cafe24Config.class)
public class Cafe24OAuthService {
	
	@Autowired
	Cafe24Config cafe24Config;
	
	@Autowired
	CodeChallengeGenerator codeChallengeGenerator;
	
	
	public String getAuthorizationCodeRedirectUri(String mall_id)
	{
		return "https://";
	}

}
