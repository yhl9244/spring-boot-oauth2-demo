package top.shenluw.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Shenluw
 * 创建日期：2018/6/27 11:57
 */
@FeignClient(value = "resClient", configuration = FeignClientConfig.class)
public interface ResClient {
	@RequestMapping("/time")
	String resTime();

}
