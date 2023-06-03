--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4
-- Dumped by pg_dump version 14.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: ms_schema; Type: SCHEMA; Schema: -; Owner: arafkarsh
--

CREATE SCHEMA ms_schema;


ALTER SCHEMA ms_schema OWNER TO arafkarsh;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: carts_tx; Type: TABLE; Schema: ms_schema; Owner: postgres
--

CREATE TABLE ms_schema.carts_tx (
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


ALTER TABLE ms_schema.carts_tx OWNER TO postgres;

--
-- Name: country_m; Type: TABLE; Schema: ms_schema; Owner: postgres
--

CREATE TABLE ms_schema.country_m (
    countryuuid uuid NOT NULL,
    countrycode character varying(255) NOT NULL,
    countryid integer NOT NULL,
    countryname character varying(255) NOT NULL,
    countryofficialname character varying(255)
);


ALTER TABLE ms_schema.country_m OWNER TO postgres;

--
-- Name: products_m; Type: TABLE; Schema: ms_schema; Owner: postgres
--

CREATE TABLE ms_schema.products_m (
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


ALTER TABLE ms_schema.products_m OWNER TO postgres;

--
-- Data for Name: carts_tx; Type: TABLE DATA; Schema: ms_schema; Owner: postgres
--

COPY ms_schema.carts_tx (uuid, createdby, createdtime, updatedby, updatedtime, isactive, version, customerid, productid, productname, price, quantity) FROM stdin;
22273a09-ee9e-4e5a-98fa-6bcfcfa97b49	john.doe	2023-05-28 20:26:12.919	john.doe	2023-05-28 20:26:12.919	t	0	123	789	Pencil	10.00	3.00
7b54e398-711a-4820-a32c-81c7dfab1ab1	john.doe	2023-05-28 20:26:12.919	john.doe	2023-05-28 20:26:12.919	t	0	123	678	Pen	30.00	2.00
\.


--
-- Data for Name: country_m; Type: TABLE DATA; Schema: ms_schema; Owner: postgres
--

COPY ms_schema.country_m (countryuuid, countrycode, countryid, countryname, countryofficialname) FROM stdin;
\.


--
-- Data for Name: products_m; Type: TABLE DATA; Schema: ms_schema; Owner: postgres
--

COPY ms_schema.products_m (uuid, createdby, createdtime, updatedby, updatedtime, isactive, version, productdetails, productlocationzipcode, productname, price) FROM stdin;
22273a09-ee9e-4e5a-98fa-6bcfcfa97b49	john.doe	2023-05-28 20:26:12.919	john.doe	2023-05-28 20:26:12.919	t	0	iPhone 10, 64 GB	12345	iPhone 10	60000.00
7b54e398-711a-4820-a32c-81c7dfab1ab1	john.doe	2023-05-28 20:26:12.925	john.doe	2023-05-28 20:26:12.925	t	0	iPhone 11, 128 GB	12345	iPhone 11	70000.00
14879f35-9b10-4b75-8641-280731161aee	john.doe	2023-05-28 20:26:12.925	john.doe	2023-05-28 20:26:12.925	t	0	Samsung Galaxy s20, 256 GB	12345	Samsung Galaxy s20	80000.00
2b4d83d6-0063-429e-9d27-94aacb6abba6	john.doe	2023-05-28 20:31:14.267	john.doe	2023-05-28 20:31:14.267	t	0	Google Pixel 7, 128 GB SSD, 16GB RAM	98321	Google Pixel 7	60000.00
273c4812-40cb-4939-ae22-f4ad89d7a0ec	john.doe	2023-05-28 20:32:40.006	john.doe	2023-05-28 20:32:40.006	t	0	Samsung Galaxy S22, 16GB RAM, 128 GB SDD	46123	Samsung Galaxy S22	65000.00
eef67186-ff2a-42b9-809e-93536d0c1076	john.doe	2023-05-28 23:11:41.662	john.doe	2023-05-28 23:11:41.662	t	0	Google Pixel 6, 8GB RAM, 64GB SSD	12345	Google Pixel 6	50000.00
\.


--
-- Name: carts_tx carts_tx_pkey; Type: CONSTRAINT; Schema: ms_schema; Owner: postgres
--

ALTER TABLE ONLY ms_schema.carts_tx
    ADD CONSTRAINT carts_tx_pkey PRIMARY KEY (uuid);


--
-- Name: country_m country_m_pkey; Type: CONSTRAINT; Schema: ms_schema; Owner: postgres
--

ALTER TABLE ONLY ms_schema.country_m
    ADD CONSTRAINT country_m_pkey PRIMARY KEY (countryuuid);


--
-- Name: products_m products_m_pkey1; Type: CONSTRAINT; Schema: ms_schema; Owner: postgres
--

ALTER TABLE ONLY ms_schema.products_m
    ADD CONSTRAINT products_m_pkey1 PRIMARY KEY (uuid);


--
-- PostgreSQL database dump complete
--

