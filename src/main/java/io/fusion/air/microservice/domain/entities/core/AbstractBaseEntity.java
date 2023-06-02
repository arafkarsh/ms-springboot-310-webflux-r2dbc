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
package io.fusion.air.microservice.domain.entities.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.relational.core.mapping.Column;


/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public class AbstractBaseEntity {

    @Column("isActive")
    private boolean isActive;

    @Column("version")
    private int version;

    private AuditLog auditLog = new AuditLog();

    /**
     * Init Audit Log
     */
    @JsonIgnore
    public void initAudit() {
        this.isActive = true;
        this.auditLog.initAudit();
    }

    /**
     * Set the Updated By User and Updated By Time (Current Time)
     */
    @JsonIgnore
    public void setUpdatedBy() {
        this.auditLog.setUpdatedBy();
    }

    /**
     * De-Activate Record
     */
    @JsonIgnore
    public void deActivate() {
        isActive = false;
    }

    /**
     * Activate Record
     */
    @JsonIgnore
    public void activate() {
        isActive = true;
    }

    /**
     * Returns if the Record is Active
     * @return
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Returns the version of the Record
     * @return
     */
    public int getVersion() {
        return version;
    }

    /**
     * Returns the Audit Log for the following fields
     * 1. Created Time
     * 2. Created By
     * 3. Updated Time
     * 4. Updated By
     * @return
     */
    public AuditLog getAuditLog() {
        return auditLog;
    }
}
