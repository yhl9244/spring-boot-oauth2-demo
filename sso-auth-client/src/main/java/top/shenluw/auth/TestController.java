package top.shenluw.auth;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shenluw
 * 创建日期：2018/3/21 17:20
 */
@RestController
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	private ResClient resClient;

	@Autowired
	public TestController(Decoder decoder, Encoder encoder, Client client, Contract contract, RequestInterceptor requestInterceptor) {
		this.resClient = Feign.builder().client(client)
				.encoder(encoder)
				.decoder(decoder)
				.contract(contract)
				.requestInterceptor(requestInterceptor)
				.target(ResClient.class, "http://sso-res");
	}


	@GetMapping("/user")
	public Authentication getUser(Authentication authentication) {
		log.info("auth : {}", authentication);
		return authentication;
	}

	@GetMapping("/authTime")
	public String getAuthTime() {
		return resClient.resTime();
	}

}
