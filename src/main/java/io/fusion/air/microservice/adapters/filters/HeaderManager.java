package io.fusion.air.microservice.adapters.filters;


import io.fusion.air.microservice.adapters.security.AuthorizeRequestAspect;
import io.fusion.air.microservice.utils.Utils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public class HeaderManager {

    /**
     * Returns Headers and Cookies
     *
     * @param request
     * @param response
     */
    public static void returnHeaders(HttpServletRequest request, HttpServletResponse response) {
        response.addCookie(Utils.createCookie(request, "SameSite", "Strict"));
        response.addCookie(Utils.createCookie(request, "JSESSIONID", UUID.randomUUID().toString()));

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(token != null) {
            response.addHeader(HttpHeaders.AUTHORIZATION, token);
        }
        String refresh = request.getHeader(AuthorizeRequestAspect.REFRESH_TOKEN);
        if(refresh != null) {
            response.addHeader(AuthorizeRequestAspect.REFRESH_TOKEN, refresh);
        }
        String txToken = request.getHeader("TX-TOKEN");
        if (txToken != null) {
            response.addHeader("TX-TOKEN", txToken);
        }
    }

    /**
     * Extract Headers
     * @param request
     * @return
     */
    public static HttpHeaders extractHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> hNames = request.getHeaderNames();
        while(hNames.hasMoreElements()) {
            String name = hNames.nextElement();
            headers.addIfAbsent(name, request.getHeader(name));
            System.out.println(name + " = " + request.getHeader(name));
        }
        return headers;
    }

    /**
     * Extract Tokens
     * @param request
     * @return
     */
    public static HttpHeaders extractTokens(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);
        if(request != null) {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (token != null) {
                headers.addIfAbsent(HttpHeaders.AUTHORIZATION, token);
            }
            String refresh = request.getHeader(AuthorizeRequestAspect.REFRESH_TOKEN);
            if (refresh != null) {
                headers.addIfAbsent(AuthorizeRequestAspect.REFRESH_TOKEN, refresh);
            }
            String txToken = request.getHeader("TX-TOKEN");
            if (txToken != null) {
                headers.addIfAbsent("TX-TOKEN", txToken);
            }
        }
        return headers;
    }
}
