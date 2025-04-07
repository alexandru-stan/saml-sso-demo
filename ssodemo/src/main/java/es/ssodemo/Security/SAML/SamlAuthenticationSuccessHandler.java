package es.ssodemo.Security.SAML;
import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class SamlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	Logger logger;
    private RedirectStrategy REDIRECT = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
  
    	AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
  
        
    }

    
    
    
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
       System.out.println();
    	REDIRECT.sendRedirect(request, response,(String) request.getSession().getAttribute("RelayState"));
    
    }
}