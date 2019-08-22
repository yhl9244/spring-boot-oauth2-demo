package top.shenluw.auth;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author Shenluw
 * 创建日期：2018/3/21 17:19
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Import({FeignClientsConfiguration.class})
public class SsoApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SsoApplication.class)
				.run(args);
	}
}
