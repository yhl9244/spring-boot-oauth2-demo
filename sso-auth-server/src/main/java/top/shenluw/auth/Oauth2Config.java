package top.shenluw.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author Shenluw
 * 创建日期：2018/3/21 15:02
 */
@Configuration
public class Oauth2Config extends AuthorizationServerConfigurerAdapter implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(Oauth2Config.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("sso.jks"), "ssostorepass".toCharArray());
        accessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("ssotest" , "ssopass".toCharArray()));
        return accessTokenConverter;
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(jwtTokenStore())
                /* 使用jwtTokenStore是必须配置tokenEnhancer， 不然返回的不是jwt类型的token，而是由DefaultAccessTokenConverter生成的 */
                .tokenEnhancer(accessTokenConverter());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 给测试环境添加预置的client
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        clientDetailsService.setPasswordEncoder(passwordEncoder);
        try {
            clientDetailsService.loadClientByClientId("testclient");
        } catch (ClientRegistrationException e) {
            BaseClientDetails details = new BaseClientDetails();
            details.setClientId("testclient");
            details.setClientSecret("testclient");
            details.setScope(Arrays.asList("test", "test2"));
            details.setAutoApproveScopes(Arrays.asList("test"));
            details.setAuthorizedGrantTypes(Arrays.asList("authorization_code", "refresh_token"));
            clientDetailsService.addClientDetails(details);
        }
        log.info("add default client complete");
    }
}
