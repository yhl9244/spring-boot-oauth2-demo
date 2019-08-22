package top.shenluw.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Shenluw
 * 创建日期：2018/3/21 14:41
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableAuthorizationServer
public class AuthenticationApplication {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationApplication.class);

	public static void main(String[] args) throws SQLException {
		initDatabase();
		new SpringApplicationBuilder(AuthenticationApplication.class)
				.run(args);
	}

    public static void initDatabase() throws SQLException {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setSqlScriptEncoding("utf-8");
        populator.addScript(new DefaultResourceLoader().getResource("schema.sql"));
        populator.populate(DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "123456"));
        log.info("database init complete");
    }
}
