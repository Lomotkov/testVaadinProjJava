-- Table: public.contacts

-- DROP TABLE public.contacts;

CREATE TABLE public.contacts
(
    id bigint NOT NULL DEFAULT nextval('contacts_id_seq'::regclass),
    email character varying(255) COLLATE pg_catalog."default",
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    phone_number character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT contacts_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.contacts
    OWNER to postgres;