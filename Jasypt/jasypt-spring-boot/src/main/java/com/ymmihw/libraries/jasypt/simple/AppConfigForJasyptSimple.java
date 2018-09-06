package com.ymmihw.libraries.jasypt.simple;

import org.springframework.context.annotation.Configuration;
import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;

@Configuration
@EncryptablePropertySource(value = "encryptedv2.properties")
public class AppConfigForJasyptSimple {
}
