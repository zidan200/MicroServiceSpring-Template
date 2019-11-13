package com.tsse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodafone.config.JwtAccessTokenCustomizer;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnProperty(prefix = "rest.security", value = "enabled", havingValue = "true")
@Import({SecurityProperties.class})
@Slf4j
public class SecurityConfig extends ResourceServerConfigurerAdapter {

  @Autowired
  private ResourceServerProperties resourceServerProperties;

  @Autowired
  private SecurityProperties securityProperties;

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId(resourceServerProperties.getResourceId());
  }

  @Override
  public void configure(final HttpSecurity http) throws Exception {

    http.headers()
        .frameOptions()
        .disable()
        .and()        
        .authorizeRequests()
        .antMatchers("/departments/*").permitAll()
        .antMatchers(securityProperties.getApiMatcher())
        
       // .hasAnyRole("READ_EMPLOYEE")
        
       // .anyRequest()
        
        .authenticated()
        
        ;
  }
  
  
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    if (null != securityProperties.getCorsConfiguration()) {
      source.registerCorsConfiguration("/**", securityProperties.getCorsConfiguration());
    }
    return source;
  }

  @Bean
  public JwtAccessTokenCustomizer jwtAccessTokenCustomizer(ObjectMapper mapper) {
    return new JwtAccessTokenCustomizer(mapper);
  }

  /*@Configuration
  //@ConditionalOnProperty(prefix = "security.oauth2.client", value = "grant-type", havingValue = "client_credentials")
  public static class OAuthRestTemplateConfigurer {

    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails details) {
      OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(details);
      //oAuth2RestTemplate.getInterceptors().add(new OAuthInterceptor());
      log.debug("Begin OAuth2RestTemplate: getAccessToken");
       To validate if required configurations are in place during startup 
      //oAuth2RestTemplate.getAccessToken();
      log.debug("End OAuth2RestTemplate: getAccessToken");
      return oAuth2RestTemplate;
    }
    
   
  }*/
}
