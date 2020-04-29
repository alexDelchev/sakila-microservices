--
-- PostgreSQL database dump
--

-- Dumped from database version 10.12 (Debian 10.12-2.pgdg90+1)
-- Dumped by pg_dump version 10.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Data for Name: aggregate; Type: TABLE DATA; Schema: public; Owner: sakila_store_write_service
--

COPY public.aggregate (aggregateid, type, version, lastupdate) FROM stdin;
1	com.example.sakila.module.store.StoreWriteModel	1	2020-04-29 03:09:56.69
2	com.example.sakila.module.store.StoreWriteModel	1	2020-04-29 03:10:41.626
3	com.example.sakila.module.staff.StaffWriteModel	1	2020-04-29 03:11:39.089
4	com.example.sakila.module.staff.StaffWriteModel	1	2020-04-29 03:12:37.169
\.

