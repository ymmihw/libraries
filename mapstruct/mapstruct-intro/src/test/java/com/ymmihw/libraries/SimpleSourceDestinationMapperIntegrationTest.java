package com.ymmihw.libraries;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.ymmihw.libraries.mapper.SimpleSourceDestinationMapper;
import com.ymmihw.libraries.model.SimpleDestination;
import com.ymmihw.libraries.model.SimpleSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBootConfig.class)
public class SimpleSourceDestinationMapperIntegrationTest {

  @Autowired
  SimpleSourceDestinationMapper simpleSourceDestinationMapper;

  @Test
  public void givenSourceToDestination_whenMaps_thenCorrect() {
    SimpleSource simpleSource = new SimpleSource();
    simpleSource.setName("SourceName");
    simpleSource.setDescription("SourceDescription");

    SimpleDestination destination = simpleSourceDestinationMapper.sourceToDestination(simpleSource);

    assertEquals(simpleSource.getName(), destination.getName());
    assertEquals(simpleSource.getDescription(), destination.getDescription());
  }

  @Test
  public void givenDestinationToSource_whenMaps_thenCorrect() {
    SimpleDestination destination = new SimpleDestination();
    destination.setName("DestinationName");
    destination.setDescription("DestinationDescription");

    SimpleSource source = simpleSourceDestinationMapper.destinationToSource(destination);

    assertEquals(destination.getName(), source.getName());
    assertEquals(destination.getDescription(), source.getDescription());
  }

}
