package com.ymmihw.libraries.tomcat;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by adi on 1/10/18.
 */
@WebServlet(name = "com.ymmihw.libraries.tomcat.MyServlet", urlPatterns = {"/my-servlet"})
public class MyServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setStatus(HttpServletResponse.SC_OK);
    resp.getWriter().write("test");
    resp.getWriter().flush();
    resp.getWriter().close();
  }
}
