package com.ymmihw.libraries.yarg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.io.FileUtils;
import com.haulmont.yarg.formatters.factory.DefaultFormatterFactory;
import com.haulmont.yarg.loaders.factory.DefaultLoaderFactory;
import com.haulmont.yarg.loaders.impl.JsonDataLoader;
import com.haulmont.yarg.reporting.Reporting;
import com.haulmont.yarg.reporting.RunParams;
import com.haulmont.yarg.structure.Report;
import com.haulmont.yarg.structure.ReportBand;
import com.haulmont.yarg.structure.ReportOutputType;
import com.haulmont.yarg.structure.impl.BandBuilder;
import com.haulmont.yarg.structure.impl.ReportBuilder;
import com.haulmont.yarg.structure.impl.ReportTemplateBuilder;

public class App {
  public static void main(String[] args) throws IOException {
    ReportBuilder reportBuilder = new ReportBuilder();
    ReportTemplateBuilder reportTemplateBuilder =
        new ReportTemplateBuilder().documentPath("./src/main/resources/Letter.docx")
            .documentName("Letter.docx").outputType(ReportOutputType.docx).readFileFromPath();
    reportBuilder.template(reportTemplateBuilder.build());
    BandBuilder bandBuilder = new BandBuilder();
    String json = FileUtils.readFileToString(new File("./src/main/resources/Data.json"));
    ReportBand main =
        bandBuilder.name("Main").query("Main", "parameter=param1 $.main", "json").build();
    reportBuilder.band(main);
    Report report = reportBuilder.build();

    Reporting reporting = new Reporting();
    reporting.setFormatterFactory(new DefaultFormatterFactory());
    reporting.setLoaderFactory(new DefaultLoaderFactory().setJsonDataLoader(new JsonDataLoader()));
    OutputStream os = new FileOutputStream("1.docx");
    reporting.runReport(new RunParams(report).param("param1", json), os);
  }
}
