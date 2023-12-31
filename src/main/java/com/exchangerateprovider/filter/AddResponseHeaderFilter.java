package com.exchangerateprovider.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.extern.slf4j.Slf4j;

import static com.exchangerateprovider.util.CheckSumUtil.getChecksumCRC32;

/**
 * На каждый запрос по пути "/rate/**" добавляет
 * в каждый заголовок CRC32 тела ответа
 */
@Component
@WebFilter
@Slf4j
public class AddResponseHeaderFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);

        byte[] responseBody = responseWrapper.getContentAsByteArray();

        responseWrapper.addHeader("Digest", String.valueOf(getChecksumCRC32(responseBody)));
        responseWrapper.copyBodyToResponse();
    }
}