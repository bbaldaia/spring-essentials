package bruno.spring.config;

import external.dependency.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfiguration {

    @Bean
    public Connection connectionMysql() {
        return new Connection("localhost", "MySQL", "Schiffer98");
    }

    public Connection connectionMongodb() {
        return new Connection("localhost", "MONGODB", "Schiffer98");
    }
}
