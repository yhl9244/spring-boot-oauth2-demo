package top.shenluw.auth;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 * @author Shenluw
 * 创建日期：2018/6/27 11:47
 */
@RestController
@RequestMapping("api")
public class ApiController {

	@RequestMapping("user")
	public Authentication getUser(Authentication authentication) {
		return authentication;
	}

	@RequestMapping("time")
	public Instant getTime() {
		return Instant.now();
	}

}
