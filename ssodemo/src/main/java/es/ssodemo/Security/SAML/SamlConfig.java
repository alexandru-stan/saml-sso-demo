package es.ssodemo.Security.SAML;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrations;


@Configuration
public class SamlConfig {

	//CON ESTE OBJETO CARGAREMOS EL XML CON LOS METADATOS DEL IDP
//	 private final DefaultResourceLoader resourceLoader = new DefaultResourceLoader
	
	 
	 
	 //ESTE BEAN LLEVARÁ TODOS LOS IDPs QUE QUERAMOS DEFINIR, EN ESTE CASO SOLO 1.
	 
	 @Bean
	    public RelyingPartyRegistrationRepository relyingPartyRegistrations(){
	     
		 //CONFIGURAMOS UN ASSERTING PARTY / IDENTITY PROVIDER (OKTA) PARA NUESTRO RELYING PARTY / SERVICE PROVIDER (ESTE SERVIDOR WEB.)
		 
	        var oktaRe = RelyingPartyRegistrations.fromMetadataLocation("classpath:metadata/metadata-idp-okta.xml") //CARGAMOS LOS METADATOS CON LA INFORMACIÓN DEL IDP.
	                .registrationId("okta") // LE ASIGNAMOS UN ID, ESTE PUEDE SER CUALQUIERA, PERO TIENE QUE COINCIDIR CON EL FINAL DEL ASSERTIONCONSUMERSERVICELOCATION
	                .assertionConsumerServiceLocation("https://0c31-109-227-155-131.ngrok-free.app/login/saml2/sso/okta") // A ESTA DIRECCIÓN SE NOS DEVOLVERÁ LA RESPUESTA SAML, EL ÚLTIMO SEGMENTO DE LA URL TIENE QUE COINCIDIR CON EL ID, POR EJEMPLO, SI EL ID ES "ALEX", LA RUTA TIENE QUE TERMINAR EN /ALEX
	                .entityId("dev.alex") //esto hace referencia al audience restriction configurado en el IDP.
	                .build();
	       
	        

	        return new InMemoryRelyingPartyRegistrationRepository(oktaRe);
	    }
	 
	 
	
	 
}
