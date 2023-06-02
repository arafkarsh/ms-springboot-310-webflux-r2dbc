package io.fusion.air.microservice.adapters.filters;


import io.fusion.air.microservice.adapters.security.AuthorizeRequestAspect;
import io.fusion.air.microservice.utils.Utils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
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
    public static void returnHeaders(ServerHttpRequest request, ServerHttpResponse response) {

        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(token != null) {
            response.getHeaders().add(HttpHeaders.AUTHORIZATION, token);
        }
        String refresh = request.getHeaders().getFirst(AuthorizeRequestAspect.REFRESH_TOKEN);
        if(refresh != null) {
            response.getHeaders().add(AuthorizeRequestAspect.REFRESH_TOKEN, refresh);
        }
        String txToken = request.getHeaders().getFirst("TX-TOKEN");
        if (txToken != null) {
            response.getHeaders().add("TX-TOKEN", txToken);
        }
    }

    /**
     * Extract Headers
     * @param request
     * @return
     */
    public static HttpHeaders extractHeaders(ServerHttpRequest request) {
        HttpHeaders headers = new HttpHeaders();
        /**
        Enumeration<String> hNames = request.getHeaderNames();
        while(hNames.hasMoreElements()) {
            String name = hNames.nextElement();
            headers.addIfAbsent(name, request.getHeader(name));
            System.out.println(name + " = " + request.getHeader(name));
        }
         */
        return headers;
    }

    /**
     * Extract Tokens
     * @param request
     * @return
     */
    public static HttpHeaders extractTokens(ServerHttpRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);
        if(request != null) {
            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (token != null) {
                headers.addIfAbsent(HttpHeaders.AUTHORIZATION, token);
            }
            String refresh = request.getHeaders().getFirst(AuthorizeRequestAspect.REFRESH_TOKEN);
            if (refresh != null) {
                headers.addIfAbsent(AuthorizeRequestAspect.REFRESH_TOKEN, refresh);
            }
            String txToken = request.getHeaders().getFirst("TX-TOKEN");
            if (txToken != null) {
                headers.addIfAbsent("TX-TOKEN", txToken);
            }
        }
        return headers;
    }
}
