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
import org.slf4j.MDC;
import org.springframework.data.relational.core.mapping.Column;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public class AbstractBaseEntity {

    /**
     * By Setting it to updatable false,
     * Record Audit log will always have the original data.
     * Record Created By User
     */
    @Column("createdBy")
    private String createdBy;

    /**
     * By Setting it to updatable false,
     * Record Audit log will always have the original data.
     * Record Created Time
     */
    @Column("createdTime")
    private java.sql.Timestamp createdTime;

    /**
     * Set the updated by user.
     */
    @Column("updatedBy")
    private String updatedBy;

    /**
     * Set the Updated By Time by the user.
     */
    @Column("updatedTime")
    private java.sql.Timestamp updatedTime;

    @Column("isActive")
    private boolean isActive;

    @Column("version")
    private int version;

    /**
     * Init Audit Log
     */
    @JsonIgnore
    public void initAudit() {
        isActive        = true;
        createdTime 	= new Timestamp(new Date().getTime());
        createdBy       = MDC.get("user") == null ? "Admin" : MDC.get("user");
        updatedBy       = createdBy;
        updatedTime		= new Timestamp(new Date().getTime());
    }

    /**
     * Set the Updated By User and Updated By Time (Current Time)
     */
    @JsonIgnore
    public void setUpdatedBy() {
        updatedTime	= new Timestamp(new Date().getTime());
        updatedBy	= MDC.get("user") == null ? "User" : MDC.get("user");
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
     * Get the Record Created Time Stamp
     *
     * @return Timestamp
     */
    public java.sql.Timestamp getCreatedTime() {
        return createdTime;
    }

    /**
     * Get the user who created this record.
     *
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Get the Record Updated Time
     *
     * @return Timestamp
     */
    public java.sql.Timestamp getUpdatedTime() {
        return updatedTime;
    }

    /**
     * Get the Record updated User ID
     *
     * @return String (UserId)
     */
    public String getUpdatedBy() {
        return updatedBy;
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

    public String toString() {
        return new StringBuilder()
                .append(createdTime).append("|")
                .append(createdBy).append("|")
                .append(updatedTime).append("|")
                .append(updatedBy)
                .toString();
    }
}
