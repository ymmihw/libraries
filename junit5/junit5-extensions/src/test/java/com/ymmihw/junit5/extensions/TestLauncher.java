package com.ymmihw.junit5.extensions;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import java.io.PrintWriter;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

public class TestLauncher {
  public static void main(String[] args) {

    LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
        .selectors(selectClass("com.ymmihw.junit5.extensions.EmployeesTest"))
        .configurationParameter("junit.conditions.deactivate", "com.ymmihw.junit5.extensions.*")
        .configurationParameter("junit.extensions.autodetection.enabled", "true").build();

    Launcher launcher = LauncherFactory.create();
    SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();
    launcher.execute(request, new TestExecutionListener[] {summaryGeneratingListener});
    launcher.execute(request);

    summaryGeneratingListener.getSummary().printTo(new PrintWriter(System.out));
  }
}
