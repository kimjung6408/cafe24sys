package cafe24customer.customersys.cafe24;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "cafe24")
@Getter
@Setter
public class Cafe24Config {

	String client_id;
	String client_secret;
	String redirect_uri;
	String scope;
}
