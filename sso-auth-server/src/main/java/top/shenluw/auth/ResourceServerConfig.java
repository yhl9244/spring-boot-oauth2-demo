package top.shenluw.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 对auth服务启用资源服务，不然其他服务无法通过oauth2方式访问改服务api
 *
 * @author Shenluw
 * 创建日期：2018/6/27 16:40
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.
				antMatcher("/actuator/**")
				.authorizeRequests()
				.anyRequest()
				.permitAll()
				.and()
				.antMatcher("/api/**")
				.authorizeRequests()
				.anyRequest()
				.authenticated();
	}
}
