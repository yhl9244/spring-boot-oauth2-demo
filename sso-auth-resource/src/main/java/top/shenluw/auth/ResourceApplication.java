package top.shenluw.auth;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author Shenluw
 * 创建日期：2018/3/21 18:15
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Import({FeignClientsConfiguration.class})
public class ResourceApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ResourceApplication.class)
				.run(args);
	}

	/*这里并不需要配置JwtAccessTokenConverter，因为访问认证服务器返回的key就是公钥*/
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() throws IOException {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        Resource resource = new ClassPathResource("public.txt");
//        converter.setVerifierKey(StreamUtils.copyToString(resource.getInputStream(), Charset.forName("utf-8")));
//        return converter;
//    }

}
