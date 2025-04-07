package es.ssodemo.Security.SAML;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSAMLEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // Capture original target URL
        String targetUrl = request.getRequestURI();
        System.out.println(targetUrl+ " esto es el target url");

        // Save in session or as RelayState
        request.getSession().setAttribute("RelayState", targetUrl);

        // Delegate to default SAML2 Entry Point
        response.sendRedirect("https://dev-89210570.okta.com/app/dev-89210570_samlssodemo_1/exko5zsfk9SiPimHA5d7/sso/saml/");
    }
}