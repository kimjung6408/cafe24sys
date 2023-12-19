package cafe24customer.customersys.cafe24;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionDataService {
	
	
	@Autowired
	private SessionRepository sessionRepo;
	
	
	public void saveCodeVerifier(String sessionId, String codeVerifier)
	{
        SessionData sessionData = sessionRepo.findById(sessionId)
                .orElse(new SessionData());
        
        		sessionData.setSessionId(sessionId);
        		sessionData.setCodeVerifier(codeVerifier);
        		// set other data...

        		sessionRepo.save(sessionData);
	}
	
	public void saveMall_id(String sessionId, String mall_id)
	{
        SessionData sessionData = sessionRepo.findById(sessionId)
                .orElse(new SessionData());
        
        		sessionData.setSessionId(sessionId);
        		sessionData.setMall_id(mall_id);
        		// set other data...

        		sessionRepo.save(sessionData);
	}
	
	public String readCodeVerifier(String sessionId)
	{
        SessionData sessionData = sessionRepo.findById(sessionId)
                .orElse(new SessionData());
        
        		return sessionData.getCodeVerifier();
	}
	
	public String getMall_id(String sessionId)
	{
        SessionData sessionData = sessionRepo.findById(sessionId)
                .orElse(new SessionData());
        
        		return sessionData.getMall_id();
		
		
	}

}
