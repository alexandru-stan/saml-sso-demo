package es.ssodemo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

	
	 @GetMapping("/home")
	 public Object home() {
		 
		
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 
		 return authentication.getCredentials();
	 }
	 
	 


	
}
