package cafe24customer.customersys.cafe24;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import cafe24customer.customersys.user.CustomUser;
import cafe24customer.customersys.user.CustomUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
@EnableConfigurationProperties(Cafe24Config.class)
public class Cafe24OAuthService {
	
	@Autowired
	Cafe24Config cafe24Config;
	
	@Autowired
	Cafe24UserRepository cafe24UserRepo;
	
	@Autowired
	CustomUserRepository customUserRepository;
	
	@Autowired
	CodeChallengeGenerator codeChallengeGenerator;
	
	@Autowired
	SessionDataService sessionDataService;
	
	
	public String getAuthorizationCodeRedirectUri(String mall_id, HttpServletRequest request) throws NoSuchAlgorithmException
	{
		HttpSession session=request.getSession();
		String sessionId=session.getId();
		
		String codeVerifier=codeChallengeGenerator.generateCodeVerifier();
		String codeChallenge=codeChallengeGenerator.generateCodeChallenge(codeVerifier);
		
		sessionDataService.saveCodeVerifier(sessionId, codeVerifier);
		sessionDataService.saveMall_id(sessionId, mall_id);
		
		System.out.println("prev code verifier1:"+codeChallenge);
		
		
		
		return "https://"+mall_id+".cafe24api.com/api/v2/oauth/authorize?response_type=code&client_id="
				+cafe24Config.getClient_id()
				+"&state=app_start"
				+"&redirect_uri="+cafe24Config.getCode_redirect_uri()
				+"&scope="+cafe24Config.getScope()
				+"&code_challenge="+codeChallenge
				+"&code_challenge_method=S256";
		
	
	}
	
	 public boolean isAccessible(String authorization_code, String csrf_token, HttpServletRequest client_request) throws IOException, InterruptedException
	 {
		 HttpSession session=client_request.getSession();
		 String sessionId=session.getId();
		 System.out.println("access token getter session id :"+sessionId);
		 
		 	String clientId = cafe24Config.getClient_id();
	        String clientSecret = cafe24Config.getClient_secret();
	        String authorizationCode = authorization_code;
	        String redirectUri = cafe24Config.getAccess_redirect_uri();
	        
	        //검증 로직 필요한 부분
	        String codeVerifier = sessionDataService.readCodeVerifier(sessionId); // Only if PKCE is used
	        String mallId = sessionDataService.getMall_id(sessionId);

	        // Base64 encode the client credentials
	        String encodedCredentials = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

	        // Prepare the request body
	        String requestBody = "grant_type=authorization_code" +
	                "&code=" + authorizationCode +
	                "&redirect_uri=" + redirectUri +
	                "&code_verifier=" + codeVerifier+
	                "&code_challenge_method=S256"; // Include this only if PKCE is used
	        
	        System.out.println("post code verifier"+codeVerifier);

	        // Create the HTTP client and request
	        HttpClient httpClient = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create("https://" + mallId + ".cafe24api.com/api/v2/oauth/token"))
	                .header("Authorization", "Basic " + encodedCredentials)
	                .header("Content-Type", "application/x-www-form-urlencoded")
	                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                .build();

	        // Send the request
	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

	        // Output the response
	        System.out.println("Response status code: " + response.statusCode());
	        System.out.println("Response body: " + response.body());
        	System.out.println("authentication info : "+SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());	    
		 
	        if(response.statusCode()==200)
	        {
	        	ObjectMapper mapper=new ObjectMapper();
	        	mapper.registerModule(new JavaTimeModule());
	        	//Create Or Read Cafe24User from repository
	        	Cafe24User cafe24User=mapper.readValue(response.body(), Cafe24User.class);
	        	cafe24User.setSessionId(sessionId);
	        	
	        	//Create Or Read User from UserRepository
	        	cafe24UserRepo.findByUserId(cafe24User.getUserId()).ifPresent(
	        			repoUser->{
	        				cafe24UserRepo.delete(repoUser);
	        				
	        			}
	        			
	        			);
	        	
	        	cafe24UserRepo.save(cafe24User);
	        	
	        	//Set user's name and roles to session and authentication object
	        	CustomUser user=customUserRepository.findByUsername(cafe24User.getUserId());
	        	
	        	if(user==null)
	        	{
	        		user = new CustomUser();
	        		
	        		Set<String> roles=new HashSet();
	        		roles.add("ROLE_USER");
	        		
	        		user.setUsername(cafe24User.getUserId());
	        		user.setRoles(roles);
	        		user.setSessionId(sessionId);
	        		
	        		customUserRepository.save(user);
	        	}
	        	else
	        	{
	        		user.setSessionId(sessionId);
	        		customUserRepository.save(user);
	        	}
	        	
	        	Collection<GrantedAuthority> authorities = user.getRoles().stream()
	        		    .map(SimpleGrantedAuthority::new)
	        		    .collect(Collectors.toSet());
	        	
	        	Authentication new_auth=new UsernamePasswordAuthenticationToken(user, encodedCredentials, authorities);
	        	SecurityContextHolder.getContext().setAuthentication(new_auth);
	        	System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
	        	
	        	//권한 정보 저장 및 유지
	        	session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
	        	
	        	
	        	return true;
	        }
	        else
	        {
	        	return false;
	        }
		 
		 
	 }

}
