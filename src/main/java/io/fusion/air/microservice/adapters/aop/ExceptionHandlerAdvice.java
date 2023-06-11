/**
 * (C) Copyright 2021 Araf Karsh Hamid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fusion.air.microservice.adapters.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fusion.air.microservice.domain.exceptions.*;
import io.fusion.air.microservice.domain.exceptions.SecurityException;
import io.fusion.air.microservice.domain.models.core.StandardResponse;
import io.fusion.air.microservice.server.config.ServiceConfiguration;
import io.fusion.air.microservice.utils.Utils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Component;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.PrintWriter;
import java.io.StringWriter;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@Component
// -2 is used to give precedence to this handler over the default webflux exception handlers.
@Order(-2)
public class ExceptionHandlerAdvice implements ErrorWebExceptionHandler {

    // Set Logger -> Lookup will automatically determine the class name.
    private static final Logger log = getLogger(lookup().lookupClass());

    // ServiceConfiguration
    @Autowired
    private ServiceConfiguration serviceConfig;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Handles All the exceptions and Creates Standard Response with Context Specific Error Codes
     *
     * @param exchange
     * @param ex
     * @return
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        StandardResponse stdResponse = throwError(ex);
        response.setStatusCode(stdResponse.getHttpStatus());

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(stdResponse);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    /**
     * Create Standard Error Response
     *
     * @param _exception
     * @param _errorCode
     * @param _httpStatus
     * @return
     */
    private StandardResponse createErrorResponse(Throwable _exception, String _errorCode, HttpStatus _httpStatus) {

        String errorPrefix = (serviceConfig != null) ? serviceConfig.getServiceAPIErrorPrefix() : "AK";
        String errorCode = errorPrefix+_errorCode;
        String message = _exception.getMessage();
        if(_exception instanceof AbstractServiceException) {
            AbstractServiceException ase = (AbstractServiceException)_exception;
            ase.setErrorCode(errorCode);
        }
        logException(errorCode,  _exception);
        StandardResponse stdResponse = Utils.createErrorResponse(null, errorPrefix, _errorCode, _httpStatus,  message);
        return stdResponse;
    }

    // ================================================================================================================
    // SERVER EXCEPTIONS: ERROR CODES 430 - 439
    // ================================================================================================================
    /**
     * Handle Runtime Exception
     * @param _runEx
     * @return
     */
    public StandardResponse throwError(RuntimeException _runEx) {
        return createErrorResponse(_runEx,"590",  HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle Any Exception
     * @param _runEx
     * @return
     */
    public StandardResponse throwError(Throwable _runEx) {
        return createErrorResponse(_runEx, "599",  HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ================================================================================================================
    // STANDARD EXCEPTIONS: ERROR CODES 400 - 409
    // ================================================================================================================
    /**
     * Access Denied Exception
     * @param _adEx
     * @return
     */
    public StandardResponse throwError(AccessDeniedException _adEx) {
        return createErrorResponse(_adEx, "403",  HttpStatus.FORBIDDEN);
    }

    /**
     * Request Rejected Exception
     * @param _adEx
     * @return
     */
    public StandardResponse throwError(RequestRejectedException _adEx) {
        return createErrorResponse(_adEx,"403",  HttpStatus.FORBIDDEN);
    }


    /**v
     * Exception if the Resource NOT Available!
     * @param _rnfEx
     * @return
     */
    public  StandardResponse throwError(ResourceException _rnfEx) {
        return createErrorResponse(_rnfEx,  "404", HttpStatus.NOT_FOUND);
    }

    /**v
     * Exception if the Resource IS NOT FOUND!
     * @param _rnfEx
     * @return
     */
    public  StandardResponse throwError(ResourceNotFoundException _rnfEx) {
        return createErrorResponse(_rnfEx,  "404", HttpStatus.NOT_FOUND);
    }

    // ================================================================================================================
    // SECURITY EXCEPTIONS: ERROR CODES 410 - 429
    // ================================================================================================================
    /**
     * Authorization Exception
     * @param _adEx
     * @return
     */
    public  StandardResponse throwError(SecurityException _adEx) {
        return createErrorResponse(_adEx,  "411",  HttpStatus.UNAUTHORIZED);
    }

    /**
     * Authorization Exception
     * @param _adEx
     * @return
     */
    public  StandardResponse throwError(AuthorizationException _adEx) {
        return createErrorResponse(_adEx,  "413",  HttpStatus.UNAUTHORIZED);
    }

    /**
     * JWT Token Extraction Exception
     * @param _adEx
     * @return
     */
    public  StandardResponse throwError(JWTTokenExtractionException _adEx) {
        return createErrorResponse(_adEx, "414",   HttpStatus.UNAUTHORIZED);
    }

    /**
     * JWT Token Expired Exception
     * @param _adEx
     * @return
     */
    public StandardResponse throwError(JWTTokenExpiredException _adEx) {
        return createErrorResponse(_adEx, "415",  HttpStatus.UNAUTHORIZED);
    }

    /**
     * JWT Token Subject Exception
     * @param _adEx
     * @return
     */
    public StandardResponse throwError(JWTTokenSubjectException _adEx) {
        return createErrorResponse(_adEx,"416",   HttpStatus.UNAUTHORIZED);
    }

    /**
     * JWT UnDefined Exception
     * @param _adEx
     * @return
     */
    public StandardResponse throwError(JWTUnDefinedException _adEx) {
        return createErrorResponse(_adEx,  "417",   HttpStatus.UNAUTHORIZED);
    }

    // ================================================================================================================
    // MESSAGING EXCEPTIONS: ERROR CODES 430 - 439
    // ================================================================================================================
    /**
     * Messaging Exception
     * @param _msgEx
     * @return
     */
    public StandardResponse throwError(MessagingException _msgEx) {
        return createErrorResponse(_msgEx,  "430",  HttpStatus.BAD_REQUEST);
    }

    // ================================================================================================================
    // DATABASE EXCEPTIONS: ERROR CODES 440 - 459
    // ================================================================================================================
    /**
     * Database Exception
     * @param _dbEx
     * @return
     */
    public StandardResponse throwError(DatabaseException _dbEx) {
        return createErrorResponse(_dbEx,  "440", HttpStatus.BAD_REQUEST);
    }

    /**
     * Unable to Save Exception
     * @param _utEx
     * @return
     */
    public StandardResponse throwError(UnableToSaveException _utEx) {
        return createErrorResponse(_utEx,  "452", HttpStatus.BAD_REQUEST);
    }

    // ================================================================================================================
    // BUSINESS EXCEPTIONS: ERROR CODES 460 - 489
    // ================================================================================================================
    /**
     * Business Exception
     * @param _buEx
     * @return
     */
    public StandardResponse throwError(BusinessServiceException _buEx) {
        return createErrorResponse(_buEx,  "460", HttpStatus.BAD_REQUEST);
    }

    /**
     * InputDataException
     * @param _idEx
     * @return
     */
    public StandardResponse throwError(InputDataException _idEx) {
        return createErrorResponse(_idEx, "461", HttpStatus.BAD_REQUEST);
    }

    /**
     * Mandatory Data Required Exception
     * @param _mdrEx
     * @return
     */
    public StandardResponse throwError(MandatoryDataRequiredException _mdrEx) {
        return createErrorResponse(_mdrEx,  "462", HttpStatus.BAD_REQUEST);
    }

    // ================================================================================================================
    // CONTROLLER EXCEPTIONS: ERROR CODES 490 - 499
    // ================================================================================================================
    /**
     * Controller Exception
     * @param _coEx
     * @return
     */
    public StandardResponse throwError(ControllerException _coEx) {
        return createErrorResponse(_coEx,  "490", HttpStatus.BAD_REQUEST);
    }

    // ================================================================================================================
    // LOG EXCEPTIONS
    // ================================================================================================================
    /**
     *
     * @param _status
     * @param e
     */
    private void logException(String _status, Throwable e) {
        log.info(getStackTraceAsString(e));
        log.info("2|EH|TIME=00|STATUS=Error: {}|CLASS={}|",_status, e.toString());
    }

    /**
     * Get the Stack Trace
     * @param e
     * @return
     */
    private String getStackTraceAsString(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
