package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"validators", "utils", "exceptions", "service", "db"})
public class Config {
}
