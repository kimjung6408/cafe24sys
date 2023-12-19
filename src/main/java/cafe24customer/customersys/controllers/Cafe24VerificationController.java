package cafe24customer.customersys.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cafe24customer.customersys.cafe24.Cafe24OAuthService;
import cafe24customer.customersys.cafe24.SessionDataService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class Cafe24VerificationController {
	
	@Autowired
	Cafe24OAuthService cafe24OAuthService;

	
	@GetMapping("/")
	public String main(@RequestParam(value="mall_id", required=true) String mall_id, HttpServletRequest request) throws NoSuchAlgorithmException
	{
		HttpSession session=request.getSession();
		
		String sessionId=session.getId();
		
		
		
		
		return "redirect:"+cafe24OAuthService.getAuthorizationCodeRedirectUri(mall_id, request);
	}
	
	@GetMapping("/oauth2/code")
	public String authorizeCode(@RequestParam(value="code") String authorization_code, @RequestParam(value="state") String csrf_token ,HttpServletRequest request) throws IOException, InterruptedException
	{
		if(cafe24OAuthService.isAccessible(authorization_code, csrf_token, request))
			return "redirect:/user";
		else
			return "redirect:/403";
	}
	
	
}
