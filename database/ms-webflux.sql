
CREATE TABLE IF NOT EXISTS ms_schema.carts_tx (
    uuid uuid NOT NULL,
    createdby character varying(255) NOT NULL,
    createdtime timestamp(6) without time zone NOT NULL,
    updatedby character varying(255) NOT NULL,
    updatedtime timestamp(6) without time zone NOT NULL,
    isactive boolean,
    version integer,
    customerid character varying(255) NOT NULL,
    productid character varying(255) NOT NULL,
    productname character varying(32),
    price numeric(38,2) NOT NULL,
    quantity numeric(38,2) NOT NULL
);

ALTER TABLE ONLY ms_schema.carts_tx ADD CONSTRAINT carts_tx_pkey PRIMARY KEY (uuid);

CREATE TABLE IF NOT EXISTS ms_schema.products_m (
    uuid uuid NOT NULL,
    createdby character varying(255) NOT NULL,
    createdtime timestamp(6) without time zone NOT NULL,
    updatedby character varying(255) NOT NULL,
    updatedtime timestamp(6) without time zone NOT NULL,
    isactive boolean,
    version integer,
    productdetails character varying(64) NOT NULL,
    productlocationzipcode character varying(255),
    productname character varying(32),
    price numeric(38,2) NOT NULL
);

ALTER TABLE ONLY ms_schema.products_m ADD CONSTRAINT products_m_pkey PRIMARY KEY (uuid);

CREATE TABLE IF NOT EXISTS ms_schema.country_t (
    cid integer NOT NULL,
    countryId integer NOT NULL,
    countryCode character varying(255) NOT NULL,
    countryName character varying(255) NOT NULL,
    countryOfficialName character varying(255)
);

ALTER TABLE ONLY ms_schema.country_t ADD CONSTRAINT country_t_pkey PRIMARY KEY (cid);

INSERT INTO ms_schema.country_t (cid, countryId, countryCode, countryName, countryOfficialName) VALUES (1, 1, 'USA', 'America', 'United States of America');
INSERT INTO ms_schema.country_t (cid, countryId, countryCode, countryName, countryOfficialName) VALUES (2, 250, 'FRA', 'France', 'The French Republic');
INSERT INTO ms_schema.country_t (cid, countryId, countryCode, countryName, countryOfficialName) VALUES (3, 76, 'BRA', 'Brazil', 'The Federative Republic of Brazil');
INSERT INTO ms_schema.country_t (cid, countryId, countryCode, countryName, countryOfficialName) VALUES (4, 380, 'ITA', 'Italy', 'Italy');
INSERT INTO ms_schema.country_t (cid, countryId, countryCode, countryName, countryOfficialName) VALUES (5, 380, 'IND', 'India', 'Republic of India');
INSERT INTO ms_schema.country_t (cid, countryId, countryCode, countryName, countryOfficialName) VALUES (6, 124, 'CAN', 'Canada', 'Canada');
