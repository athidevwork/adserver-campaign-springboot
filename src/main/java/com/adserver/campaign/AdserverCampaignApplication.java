package com.adserver.campaign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationProperties
public class AdserverCampaignApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdserverCampaignApplication.class, args);
	}
	
	@Bean
	public JettyEmbeddedServletContainerFactory  jettyEmbeddedServletContainerFactory() {
	    JettyEmbeddedServletContainerFactory jettyContainer = 
	      new JettyEmbeddedServletContainerFactory();
	     
	    jettyContainer.setPort(8081);
	    jettyContainer.setContextPath("/adserver");
	    return jettyContainer;
	}
	
	/*@Bean
	public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
	    UndertowEmbeddedServletContainerFactory factory = 
	      new UndertowEmbeddedServletContainerFactory();
	     
	    factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {
	        @Override
	        public void customize(io.undertow.Undertow.Builder builder) {
	            builder.addHttpListener(8081, "0.0.0.0");
	        }
	    });
	     
	    return factory;
	}*/
}
