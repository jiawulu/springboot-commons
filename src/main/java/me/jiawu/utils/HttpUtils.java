package me.jiawu.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;

import com.alibaba.fastjson.JSON;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author wuzhong on 2017/12/30.
 * @version 1.0
 */
@Component
public class HttpUtils {

    @Autowired
    private RestTemplate restTemplate;

    public HttpEntity<String> post(String restUrl, Object postData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonString = "";
        if (postData instanceof String) {
            jsonString = (String)postData;
        } else {
            jsonString = JSON.toJSONString(postData);
        }

        HttpEntity<?> entity = new HttpEntity(jsonString, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restUrl);
        return this.restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, entity, String.class);
    }

    public HttpEntity<String> request(String restUrl, HttpMethod method, Object postData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonString = "";
        if (postData instanceof String) {
            jsonString = (String)postData;
        } else {
            jsonString = JSON.toJSONString(postData);
        }
        HttpEntity<?> entity = new HttpEntity(jsonString, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(restUrl);
        return this.restTemplate.exchange(builder.build().encode().toUri(), method, entity, String.class);
    }

    @Configuration
    public static class RestTemplateConfig {
        @Bean
        public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
            RestTemplate restTemplate = new RestTemplate(factory);

            StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
            stringHttpMessageConverter.setWriteAcceptCharset(false);
            for (int i = 0; i < restTemplate.getMessageConverters().size(); i++) {
                if (restTemplate.getMessageConverters().get(i) instanceof StringHttpMessageConverter) {
                    restTemplate.getMessageConverters().remove(i);
                    restTemplate.getMessageConverters().add(i, stringHttpMessageConverter);
                    break;
                }
            }

            restTemplate.getInterceptors().add(new LoggingInterceptor());
            return restTemplate;
        }

        @Bean
        public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setReadTimeout(5000);
            factory.setConnectTimeout(15000);
            return factory;
        }

        /**
         * Allows logging outgoing requests and the corresponding responses.
         * Requires the use of a {@link org.springframework.http.client.BufferingClientHttpRequestFactory} to log
         * the body of received responses.
         */
        public static class LoggingInterceptor implements ClientHttpRequestInterceptor {

            protected Logger logger = LoggerFactory.getLogger(getClass());

            protected Logger requestLogger = LoggerFactory.getLogger("org.springframework.web.http.request");
            protected Logger responseLogger = LoggerFactory.getLogger("org.springframework.web.http.response");

            private volatile boolean loggedMissingBuffering;

            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                throws IOException {
                logRequest(request, body);
                ClientHttpResponse response = execution.execute(request, body);
                logResponse(request, response);
                return response;
            }

            protected void logRequest(HttpRequest request, byte[] body) {
                if (requestLogger.isDebugEnabled()) {
                    StringBuilder builder = new StringBuilder(String.valueOf(request.getMethod())).append(" to ")
                        .append(request.getURI());

                    if (body.length > 0 && hasTextBody(request.getHeaders())) {
                        String bodyText = new String(body, determineCharset(request.getHeaders()));
                        builder.append("\r\nparams:\r\n\t").append(bodyText);
                    }

                    appendHeaders(builder, request.getHeaders());

                    requestLogger.info(builder.toString());

                }
            }

            protected void logResponse(HttpRequest request, ClientHttpResponse response) {
                if (responseLogger.isDebugEnabled()) {
                    try {
                        StringBuilder builder = new StringBuilder("Received ")
                            .append(response.getRawStatusCode()).append(" ").append(" from ").append(request.getURI());

                        HttpHeaders responseHeaders = response.getHeaders();
                        appendHeaders(builder, responseHeaders);

                        //添加了，业务就读不到数据了
                        //builder.append("\r\nresponse body:\r\n\t").append(IOUtils.toString(response.getBody()));

                        responseLogger.debug(builder.toString());
                    } catch (IOException e) {
                        responseLogger.warn("Failed to log response for {} request to {}", request.getMethod(),
                                            request.getURI(), e);
                    }
                }
            }

            private void appendHeaders(StringBuilder builder, HttpHeaders responseHeaders) {
                builder.append("\r\n").append("headers:");
                for (String key : responseHeaders.keySet()) {
                    builder.append("\r\n\t").append(
                        key + " :" + StringUtils.join(new List[] {responseHeaders.get(key)}));
                }
            }

            protected boolean hasTextBody(HttpHeaders headers) {
                MediaType contentType = headers.getContentType();
                if (contentType != null) {
                    String subtype = contentType.getSubtype();
                    return "text".equals(contentType.getType()) || "xml".equals(subtype) || "json".equals(subtype);
                }
                return false;
            }

            protected Charset determineCharset(HttpHeaders headers) {
                MediaType contentType = headers.getContentType();
                if (contentType != null) {
                    try {
                        Charset charSet = contentType.getCharSet();
                        if (charSet != null) {
                            return charSet;
                        }
                    } catch (UnsupportedCharsetException e) {
                        // ignore
                    }
                }
                return StandardCharsets.UTF_8;
            }

        }
    }

}
