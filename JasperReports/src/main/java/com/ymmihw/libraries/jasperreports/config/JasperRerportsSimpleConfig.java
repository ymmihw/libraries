package com.ymmihw.libraries.jasperreports.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import com.ymmihw.libraries.jasperreports.SimpleReportExporter;
import com.ymmihw.libraries.jasperreports.SimpleReportFiller;

@Configuration
public class JasperRerportsSimpleConfig {

  @Bean
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
        .addScript("classpath:employee-schema.sql").build();
  }

  @Bean
  public SimpleReportFiller reportFiller() {
    return new SimpleReportFiller();
  }

  @Bean
  public SimpleReportExporter reportExporter() {
    return new SimpleReportExporter();
  }

}
