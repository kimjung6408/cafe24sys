package cafe24customer.customersys.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import cafe24customer.customersys.cafe24.Cafe24User;
import cafe24customer.customersys.features.adminside.BoardService;
import cafe24customer.customersys.user.CustomUser;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PageController {
	
	@Autowired
	BoardService boardService;
	
	
	@GetMapping("/user")
	public String userPage(HttpServletRequest request)
	{
		CustomUser user= (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(user.getUsername());
		
		System.out.println(boardService.getBoardList(user.getUsername()));
		
		return "userwelcome";
	}

}
