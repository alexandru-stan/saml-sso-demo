package es.ssodemo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.saml2.provider.service.metadata.OpenSamlMetadataResolver;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.RelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.Saml2MetadataFilter;
import org.springframework.security.saml2.provider.service.web.authentication.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import es.ssodemo.Security.SAML.CustomSAMLEntryPoint;
import es.ssodemo.Security.SAML.SamlAuthenticationSuccessHandler;
@Configuration
public class SecurityConfig {

	 public SecurityConfig(RelyingPartyRegistrationRepository relyingPartyRegistrationRepository) {
	        this.relyingPartyRegistrationRepository = relyingPartyRegistrationRepository;

	    }
	
	
	//INSTANCIAMOS EL REGISTRO DE IDPS CONFIGURADO EN SamlConfig.java
    private final RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;
//    private final Saml2MetadataResolver saml2MetadataResolver;
    
	

	 
	 @Bean
     SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	System.out.println("HOLA");
    	//DESHABILTAMOS CROSS SITE REQUEST FORGERY
    	http.csrf(e -> e.disable());
    	http.exceptionHandling(e -> e.authenticationEntryPoint(new CustomSAMLEntryPoint()));
    	//EL ÚNICO ENDPOINT AL QUE SE PODRÁ ACCEDER SIN AUTENTICACIÓN SERÁ EL RAÍZ.
    	http.authorizeHttpRequests(e -> {
    		e.requestMatchers("/").permitAll().anyRequest().authenticated();
    	});
    	
    	//INDICO QUE EL METODO DE AUTENTICACIÓN SERÁ SAML2
    	//EN CASO DE QUE EL USUARIO LOGRE AUTENTICARSE, SE INVOCARÁ EL BEAN SamlAuthenticationSuccessHandler
    
    	http.saml2Login(e -> e.successHandler(new SamlAuthenticationSuccessHandler()));
    	
    	//DEFINO UN RESOLVER PARA EL REPOSITORIO DE IDPs
    	var relyingPartyRegistrationResolver = new DefaultRelyingPartyRegistrationResolver(relyingPartyRegistrationRepository);
    	var metadataResolver = new OpenSamlMetadataResolver();
    	//DEFINO UN FILTRO DE SEGURIDAD, PASANDOLE EL RESOLVER QUE LLEVA LAS RELACIONES ENTRE IDP Y SP ASÍ COMO TAMBIÉN EL RESOLVER DE METADATOS DEL SP
    	Saml2MetadataFilter filter = new Saml2MetadataFilter((RelyingPartyRegistrationResolver) relyingPartyRegistrationResolver, metadataResolver);    	
    	
    	//AÑADO EL FILTRO A LA CADENA DE FILTROS.
    	http.addFilterBefore(filter, Saml2WebSsoAuthenticationFilter.class);
    	
    	return http.build();
    	
    
    }
	 
	 
	 
}