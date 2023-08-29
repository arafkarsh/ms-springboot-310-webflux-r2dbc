/**
 * (C) Copyright 2023 Araf Karsh Hamid
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
package io.fusion.air.microservice.server.models;

import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public class RequestMap extends LinkedHashMap<String, Object>  {


    public RequestMap() {
        super();
        this.addHttpCode( 500);
        this.addHttpMessage( "Internal-Server-Error");
        super.put("ReqId", UUID.randomUUID().toString());
        this.addIPAddress("NA");
        addPort("NA");
        addURI("NA");
        addProtocol("NA");
        addUser("john.doe");
        addService("NA");
    }

    public RequestMap addHttpCode(Integer _code) {
        if(_code != null && _code > 0) {
            put("code", _code);
        }
        return this;
    }

    public RequestMap addHttpMessage(String _message) {
        if(_message != null && !_message.isBlank()) {
            put("mesg", _message);
        }
        return this;
    }

    public RequestMap addIPAddress(String ip) {
        if(ip != null && !ip.isBlank()) {
            put("IP", ip);
        }
        return this;
    }

    public RequestMap addPort(String port) {
        if(port != null && !port.isBlank()) {
            put("Port", port);
        }
        return this;
    }

    public RequestMap addURI(String uri) {
        if(uri != null && !uri.isBlank()) {
            put("URI", uri);
        }
        return this;
    }

    public RequestMap addProtocol(String protocol) {
        if(protocol != null && !protocol.isBlank()) {
            put("Protocol", protocol);
        }
        return this;
    }

    public RequestMap addService(String service) {
        if(service != null && !service.isBlank()) {
            put("Service", service);
        }
        return this;
    }

    /**
     * Update the logged in User ID
     * @param user
     */
    public RequestMap addUser(String user) {
        if(user != null && !user.isBlank()) {
            put("user", user);
        }
        return this;
    }
}

