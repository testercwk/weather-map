package com.github.testercwk.weather.map.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpService.class);

    public String callOpenWeather(String url) throws IOException {
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(url);

        HttpResponse response = httpclient.execute(httpget);

        return EntityUtils.toString(response.getEntity());
    }
}
