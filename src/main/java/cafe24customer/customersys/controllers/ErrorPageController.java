package cafe24customer.customersys.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController {
	
	
	@GetMapping("/403")
	public String error403()
	{
		System.out.println("sibal");
		return "403";
	}

}
