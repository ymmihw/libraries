package com.ymmihw.libraries;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterceptorIntegrationTest {

    private MockWebServer server;

    @BeforeEach
    public void beforeEach() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @AfterEach
    public void afterEach() throws IOException {
        server.shutdown();
    }

    @Test
    public void givenSimpleLoggingInterceptor_whenRequestSent_thenHeadersLogged() throws IOException {
        server.enqueue(new MockResponse().setBody("Hello Baeldung Readers!"));

        OkHttpClient client =
                new OkHttpClient.Builder().addNetworkInterceptor(new SimpleLoggingInterceptor()).build();

        Request request =
                new Request.Builder()
                        .url(server.url("/greeting"))
                        .header("User-Agent", "A Baeldung Reader")
                        .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code(), "Response code should be: ");
            assertEquals("Hello Baeldung Readers!", response.body().string(), "Body should be: ");
        }
    }

    @Test
    public void givenResponseInterceptor_whenRequestSent_thenCacheControlSetToNoStore()
            throws IOException {
        server.enqueue(new MockResponse().setBody("Hello Baeldung Readers!"));

        OkHttpClient client =
                new OkHttpClient.Builder()
                        .addInterceptor(getHttpLogger())
                        .addInterceptor(new CacheControlResponeInterceptor())
                        .build();

        Request request =
                new Request.Builder()
                        .url(server.url("/greeting"))
                        .header("User-Agent", "A Baeldung Reader")
                        .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code(), "Response code should be: ");
            assertEquals("Hello Baeldung Readers!", response.body().string(), "Body should be: ");
            assertEquals(
                    "no-store", response.header("Cache-Control"), "Response cache-control should be");
        }
    }

    @Test
    public void givenErrorResponseInterceptor_whenResponseIs500_thenBodyIsJsonWithStatus()
            throws IOException {
        server.enqueue(new MockResponse().setResponseCode(500).setBody("Hello Baeldung Readers!"));

        OkHttpClient client =
                new OkHttpClient.Builder()
                        .addInterceptor(getHttpLogger())
                        .addInterceptor(new ErrorResponseInterceptor())
                        .build();

        Request request =
                new Request.Builder()
                        .url(server.url("/greeting"))
                        .header("User-Agent", "A Baeldung Reader")
                        .build();

        try (Response response = client.newCall(request).execute()) {
            assertEquals(500, response.code(), "Response code should be: ");
            assertEquals(
                    "{\"status\":500,\"detail\":\"The response from the server was not OK\"}",
                    response.body().string(),
                    "Body should be: ");
        }
    }

    private HttpLoggingInterceptor getHttpLogger() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(Level.HEADERS);
        return logger;
    }
}
