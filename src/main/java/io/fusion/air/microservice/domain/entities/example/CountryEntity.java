/**
 * (C) Copyright 2022 Araf Karsh Hamid
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
package io.fusion.air.microservice.domain.entities.example;

import io.fusion.air.microservice.utils.Utils;
import jakarta.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Objects;


/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@Table("country_t")
public class CountryEntity implements Serializable {

    @Id
    private int cid;

    @NotNull
    @Column("countryId")
    private int countryId;

    @NotNull
    @Column("countryCode")
    private String countryCode;

    @NotNull
    @Column("countryName")
    private String countryName;

    @Column("countryOfficialName")
    private String countryOfficialName;

    protected CountryEntity() {
    }

    /**
     * Create Country
     *
     * @param _pid
     * @param _countryNm
     * @param _countryOnm
     * @param _countryCd
     */
    public CountryEntity(int _pid, String _countryNm, String _countryOnm, String _countryCd) {
        countryId = _pid;
        countryName = _countryNm;
        countryOfficialName = _countryOnm;
        countryCode = _countryCd;
    }

    /**
     * Returns the Country ID
     * @return
     */
    public int getCid() {
        return cid;
    }

    /**
     * Get Country Code
     *
     * @return
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Get Country ID
     *
     * @return
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Get Country Name
     *
     * @return
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Get the Country Official Name
     *
     * @return
     */
    public String getCountryOfficialName() {
        return countryOfficialName;
    }

    /**
     * Returns Country ID + Country Name
     *
     * @return
     */
    public String toString() {
        // return countryId + "|" + countryName;
        return Utils.toJsonString(this);
    }

    /**
     * Returns the Hashcode
     *
     * @return
     */
    public int hashCode() {
        return Objects.hash(countryId, countryName);
    }

    /**
     * Checks Equality returns True if Objects are equal
     * Check Country ID and Country Name
     *
     * @param o
     * @return
     */
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CountryEntity countryEntity = (CountryEntity) o;
        return countryId == countryEntity.countryId && countryName.equals(countryEntity.countryName);
    }

}
