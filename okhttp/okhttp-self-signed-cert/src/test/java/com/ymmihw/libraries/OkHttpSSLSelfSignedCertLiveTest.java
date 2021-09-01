package com.ymmihw.libraries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Execute <code>spring-security-web-boot-2</code> module before running this live test
 *
 * @see com.baeldung.ssl.HttpsEnabledApplication
 */
public class OkHttpSSLSelfSignedCertLiveTest {

  private final String HTTPS_WELCOME_URL = "https://localhost:8443/welcome";

  private OkHttpClient.Builder builder;

  @BeforeEach
  public void init() {
    builder = new OkHttpClient.Builder();
  }

  @Test
  public void whenHTTPSSelfSignedCertGET_thenException() throws IOException {
    Assertions.assertThrows(
        SSLHandshakeException.class,
        () -> {
          builder.build().newCall(new Request.Builder().url(HTTPS_WELCOME_URL).build()).execute();
        });
  }

  @Test
  public void givenTrustAllCerts_whenHTTPSSelfSignedCertGET_thenException()
      throws GeneralSecurityException, IOException {
    final TrustManager TRUST_ALL_CERTS =
        new X509TrustManager() {
          @Override
          public void checkClientTrusted(
              java.security.cert.X509Certificate[] chain, String authType)
              throws CertificateException {}

          @Override
          public void checkServerTrusted(
              java.security.cert.X509Certificate[] chain, String authType)
              throws CertificateException {}

          @Override
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[] {};
          }
        };
    final SSLContext sslContext = SSLContext.getInstance("SSL");
    sslContext.init(null, new TrustManager[] {TRUST_ALL_CERTS}, new java.security.SecureRandom());
    builder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) TRUST_ALL_CERTS);
    Assertions.assertThrows(
        SSLPeerUnverifiedException.class,
        () -> {
          builder.build().newCall(new Request.Builder().url(HTTPS_WELCOME_URL).build()).execute();
        });
  }

  @Test
  public void givenTrustAllCertsSkipHostnameVerification_whenHTTPSSelfSignedCertGET_then200OK()
      throws GeneralSecurityException, IOException {
    final TrustManager TRUST_ALL_CERTS =
        new X509TrustManager() {
          @Override
          public void checkClientTrusted(
              java.security.cert.X509Certificate[] chain, String authType)
              throws CertificateException {}

          @Override
          public void checkServerTrusted(
              java.security.cert.X509Certificate[] chain, String authType)
              throws CertificateException {}

          @Override
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[] {};
          }
        };
    final SSLContext sslContext = SSLContext.getInstance("SSL");
    sslContext.init(null, new TrustManager[] {TRUST_ALL_CERTS}, new java.security.SecureRandom());
    builder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) TRUST_ALL_CERTS);
    builder.hostnameVerifier(
        new HostnameVerifier() {
          @Override
          public boolean verify(String hostname, SSLSession session) {
            return true;
          }
        });
    Response response =
        builder.build().newCall(new Request.Builder().url(HTTPS_WELCOME_URL).build()).execute();
    assertEquals(200, response.code());
    assertNotNull(response.body());
    assertEquals("<h1>Welcome to Secured Site</h1>", response.body().string());
  }
}
