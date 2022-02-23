package com.ymmihw.libraries;

import com.google.common.collect.Lists;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ShardingJdbcTest {
  @Test
  public void test() throws SQLException {

    // Configure actual data sources
    Map<String, DataSource> dataSourceMap = new HashMap<>();

    // Configure first data source
    {
      org.apache.tomcat.jdbc.pool.PoolProperties p =
          new org.apache.tomcat.jdbc.pool.PoolProperties();
      p.setDriverClassName("com.mysql.cj.jdbc.Driver");
      p.setUrl(
          "jdbc:mysql://172.20.72.133:3306/shardingjdbc_0?useSSL=false&createDatabaseIfNotExist=true&characterEncoding=utf-8&useUnicode=true");
      p.setUsername("root");
      p.setPassword("123456");
      dataSourceMap.put("ds0", new org.apache.tomcat.jdbc.pool.DataSource(p));
    }
    // Configure second data source
    {
      org.apache.tomcat.jdbc.pool.PoolProperties p =
          new org.apache.tomcat.jdbc.pool.PoolProperties();
      p.setDriverClassName("com.mysql.cj.jdbc.Driver");
      p.setUrl(
          "jdbc:mysql://172.20.72.133:3306/shardingjdbc_1?useSSL=false&createDatabaseIfNotExist=true&characterEncoding=utf-8&useUnicode=true");
      p.setUsername("root");
      p.setPassword("123456");
      dataSourceMap.put("ds1", new org.apache.tomcat.jdbc.pool.DataSource(p));
    }

    // Configure table rule for Order
    TableRuleConfiguration orderTableRuleConfig =
        new TableRuleConfiguration("t_order", "ds${0..1}.t_order_${0..1}");

    // Configure strategies for database + table sharding
    orderTableRuleConfig.setDatabaseShardingStrategyConfig(
        new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
    orderTableRuleConfig.setTableShardingStrategyConfig(
        new InlineShardingStrategyConfiguration("order_id", "t_order_${order_id % 2}"));

    // Configure table rule for Item
    TableRuleConfiguration orderItemTableRuleConfig =
        new TableRuleConfiguration("t_order_item", "ds${0..1}.t_order_item_${0..1}");

    // Configure strategies for database + table sharding
    orderItemTableRuleConfig.setDatabaseShardingStrategyConfig(
        new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
    orderItemTableRuleConfig.setTableShardingStrategyConfig(
        new InlineShardingStrategyConfiguration("order_id", "t_order_item_${order_id % 2}"));

    // Configure sharding rule
    ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
    shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);
    shardingRuleConfig.getTableRuleConfigs().add(orderItemTableRuleConfig);
    shardingRuleConfig
        .getBindingTableGroups()
        .addAll(Lists.newArrayList("t_order", "t_order_item"));

    String sql =
        "SELECT i.* FROM t_order o JOIN t_order_item i ON o.order_id=i.order_id WHERE o.user_id=? AND o.order_id=?";

    Properties properties = new Properties();
    properties.setProperty("sql.show", "true");
    DataSource dataSource =
        ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, properties);

    try (Connection conn = dataSource.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
      preparedStatement.setInt(1, 10);
      preparedStatement.setInt(2, 1001);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        while (rs.next()) {
          System.out.println(rs.getInt(1));
          System.out.println(rs.getInt(2));
        }
      }
    }
  }
}
