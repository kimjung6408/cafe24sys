package cafe24customer.customersys.features.adminside;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cafe24customer.customersys.cafe24.Cafe24Config;
import cafe24customer.customersys.cafe24.Cafe24User;
import cafe24customer.customersys.cafe24.Cafe24UserRepository;

@Service
public class BoardService {
	
	@Autowired
	Cafe24Config cafe24Config;
	
	@Autowired
	Cafe24UserRepository cafe24userRepo;
	
	
    public String getBoardList(String userId) {
    	
    	RestTemplate restTemplate=new RestTemplate();
    	
    	Cafe24User user=cafe24userRepo.findByUserId(userId).orElse(null);
    	
    	if(user==null) return null;
    	
    	
        String url = "https://" + user.getMallId() + ".cafe24api.com/api/v2/admin/boards";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + user.getAccessToken());
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("X-Cafe24-Api-Version", cafe24Config.getVersion());

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));

        // GET 요청 보내기
        String response = restTemplate.exchange(requestEntity, String.class).getBody();

        return response;
    }
    
    public String getReviews(String userId)
    {
    	RestTemplate restTemplate=new RestTemplate();
    	
    	Cafe24User user=cafe24userRepo.findByUserId(userId).orElse(null);
    	
    	if(user==null) return null;
    	
    	
        String url = "https://" + user.getMallId() + ".cafe24api.com/api/v2/admin/boards/4/articles";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + user.getAccessToken());
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("X-Cafe24-Api-Version", cafe24Config.getVersion());

        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(url));

        // GET 요청 보내기
        String response = restTemplate.exchange(requestEntity, String.class).getBody();

        return response;
    	
    }

}
