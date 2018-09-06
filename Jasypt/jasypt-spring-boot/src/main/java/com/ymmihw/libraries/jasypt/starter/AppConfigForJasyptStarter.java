package com.ymmihw.libraries.jasypt.starter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "encrypted.properties")
public class AppConfigForJasyptStarter {
}
