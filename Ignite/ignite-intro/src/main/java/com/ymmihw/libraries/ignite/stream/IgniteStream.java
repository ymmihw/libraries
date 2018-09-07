package com.ymmihw.libraries.ignite.stream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.cache.processor.MutableEntry;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;
import org.apache.ignite.stream.StreamTransformer;
import com.google.gson.Gson;
import com.ymmihw.libraries.ignite.model.Employee;

public class IgniteStream {

  private static final Gson GSON = new Gson();

  public static void main(String[] args) throws Exception {

    // Ignition.setClientMode(true);
    Ignite ignite = Ignition.start();

    IgniteCache<Integer, Employee> cache = ignite.getOrCreateCache(CacheConfig.employeeCache());
    IgniteDataStreamer<Integer, Employee> streamer = ignite.dataStreamer(cache.getName());
    streamer.allowOverwrite(true);

    streamer.receiver(StreamTransformer.from((e, arg) -> {
      Employee employee = (Employee) arg[0];
      employee.setEmployed(true);
      e.setValue(employee);
      return employee;
    }));

    Path path = Paths.get(IgniteStream.class.getClassLoader().getResource("employee.txt").toURI());

    Files.lines(path).forEach(line -> {
      Employee employee = GSON.fromJson(line, Employee.class);
      streamer.addData(employee.getId(), employee);
    });
    streamer.flush();
  }

}
