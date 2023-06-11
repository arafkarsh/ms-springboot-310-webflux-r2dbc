
CREATE TABLE IF NOT EXISTS carts_tx (
    uuid uuid NOT NULL PRIMARY KEY,
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

INSERT INTO carts_tx (uuid, createdby, createdtime, updatedby, updatedtime, isactive, version, customerid, productid, productname, price, quantity)
VALUES ('22273a09-ee9e-4e5a-98fa-6bcfcfa97b49', 'john.doe', '2023-05-28 20:26:12.919', 'john.doe', '2023-05-28 20:26:12.919', true, 0, '123', '789', 'Pencil', 10.00, 3.00);

INSERT INTO carts_tx (uuid, createdby, createdtime, updatedby, updatedtime, isactive, version, customerid, productid, productname, price, quantity)
VALUES ('7b54e398-711a-4820-a32c-81c7dfab1ab1', 'john.doe', '2023-05-28 20:26:12.919', 'john.doe', '2023-05-28 20:26:12.919', true, 0, '123', '678', 'Pen', 30.00, 2.00);

CREATE TABLE IF NOT EXISTS products_m (
    uuid uuid NOT NULL PRIMARY KEY,
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

INSERT INTO products_m (uuid, createdby, createdtime, updatedby, updatedtime, isactive, version, productdetails, productlocationzipcode, productname, price)
VALUES ('22273a09-ee9e-4e5a-98fa-6bcfcfa97b49', 'john.doe', '2023-05-28 20:26:12.919', 'john.doe', '2023-05-28 20:26:12.919', true, 0, 'iPhone 10, 64 GB', '12345', 'iPhone 10', 60000.00);

INSERT INTO products_m (uuid, createdby, createdtime, updatedby, updatedtime, isactive, version, productdetails, productlocationzipcode, productname, price)
VALUES ('7b54e398-711a-4820-a32c-81c7dfab1ab1', 'john.doe', '2023-05-28 20:26:12.925', 'john.doe', '2023-05-28 20:26:12.925', true, 0, 'iPhone 11, 128 GB', '12345', 'iPhone 11', 70000.00);

INSERT INTO products_m (uuid, createdby, createdtime, updatedby, updatedtime, isactive, version, productdetails, productlocationzipcode, productname, price)
VALUES ('14879f35-9b10-4b75-8641-280731161aee', 'john.doe', '2023-05-28 20:26:12.925', 'john.doe', '2023-05-28 20:26:12.925', true, 0, 'Samsung Galaxy s20, 256 GB', '12345', 'Samsung Galaxy s20', 80000.00);

INSERT INTO products_m (uuid, createdby, createdtime, updatedby, updatedtime, isactive, version, productdetails, productlocationzipcode, productname, price)
VALUES ('2b4d83d6-0063-429e-9d27-94aacb6abba6', 'john.doe', '2023-05-28 20:31:14.267', 'john.doe', '2023-05-28 20:31:14.267', true, 0, 'Google Pixel 7, 128 GB SSD, 16GB RAM', '98321', 'Google Pixel 7', 60000.00);

INSERT INTO products_m (uuid, createdby, createdtime, updatedby, updatedtime, isactive, version, productdetails, productlocationzipcode, productname, price)
VALUES ('273c4812-40cb-4939-ae22-f4ad89d7a0ec', 'john.doe', '2023-05-28 20:32:40.006', 'john.doe', '2023-05-28 20:32:40.006', true, 0, 'Samsung Galaxy S22, 16GB RAM, 128 GB SDD', '46123', 'Samsung Galaxy S22', 65000.00);

INSERT INTO products_m (uuid, createdby, createdtime, updatedby, updatedtime, isactive, version, productdetails, productlocationzipcode, productname, price)
VALUES ('eef67186-ff2a-42b9-809e-93536d0c1076', 'john.doe', '2023-05-28 23:11:41.662', 'john.doe', '2023-05-28 23:11:41.662', true, 0, 'Google Pixel 6, 8GB RAM, 64GB SSD', '12345', 'Google Pixel 6', 50000.00);

CREATE TABLE IF NOT EXISTS country_t (
    cid integer NOT NULL PRIMARY KEY,
    countryId integer NOT NULL,
    countryCode character varying(255) NOT NULL,
    countryName character varying(255) NOT NULL,
    countryOfficialName character varying(255)
);

INSERT INTO country_t (cid, countryId, countryCode, countryName, countryOfficialName) VALUES (1, 1, 'USA', 'America', 'United States of America');
INSERT INTO country_t (cid, countryId, countryCode, countryName, countryOfficialName) VALUES (2, 250, 'FRA', 'France', 'The French Republic');
INSERT INTO country_t (cid, countryId, countryCode, countryName, countryOfficialName) VALUES (3, 76, 'BRA', 'Brazil', 'The Federative Republic of Brazil');
INSERT INTO country_t (cid, countryId, countryCode, countryName, countryOfficialName) VALUES (4, 380, 'ITA', 'Italy', 'Italy');
INSERT INTO country_t (cid, countryId, countryCode, countryName, countryOfficialName) VALUES (5, 380, 'IND', 'India', 'Republic of India');
INSERT INTO country_t (cid, countryId, countryCode, countryName, countryOfficialName) VALUES (6, 124, 'CAN', 'Canada', 'Canada');
