package top.shenluw.auth;

import feign.RequestInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

/**
 * @author Shenluw
 * 创建日期：2018/6/27 12:27
 */
@Configuration
public class FeignClientConfig {

	@Bean
	@ConfigurationProperties(prefix = "security.oauth2.client")
	OAuth2ProtectedResourceDetails authorizationCodeResourceDetails() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	RequestInterceptor requestInterceptor(OAuth2ClientContext context, OAuth2ProtectedResourceDetails details) {
		return new OAuth2FeignRequestInterceptor(context, details);
	}

}
