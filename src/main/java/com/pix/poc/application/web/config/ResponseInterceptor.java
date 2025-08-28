package com.pix.poc.application.web.config;


import com.pix.poc.application.web.dto.response.PixResponse;
import com.pix.poc.application.web.dto.response.ResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseInterceptor implements ResponseBodyAdvice<Object> {

    private static final Logger log = LoggerFactory.getLogger(ResponseInterceptor.class);


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return PixResponse.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if (request.getURI().getPath().startsWith("/pix")) {
            if (body instanceof PixResponse dto) {
                if (dto.getTimestamp() == null) {
                    dto.setTimestamp(java.time.LocalDateTime.now());
                }

                if (dto.getType() == ResponseType.SUCCESS) {
                    log.info("Request successful: {}", dto.getTimestamp());
                }
            }
        }


        return body;
    }
}
