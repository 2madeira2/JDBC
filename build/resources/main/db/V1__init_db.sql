CREATE TABLE nomenclature
(
    id serial NOT NULL,
    name character varying(30),
    internal_code bigint,
    CONSTRAINT id_pkey PRIMARY KEY (id)
);
CREATE TABLE organization
(
    name character varying(30) NOT NULL,
    "INN" bigint,
    checking_account bigint,
    CONSTRAINT "orgName_pkey" PRIMARY KEY (name)
);
CREATE TABLE waybill
(
    waybill_num serial NOT NULL,
    waybill_date date,
    org_sender character varying(30) NOT NULL,
    CONSTRAINT waybill_num_pkey PRIMARY KEY (waybill_num),
    CONSTRAINT org_sender_fkey FOREIGN KEY (org_sender)
        REFERENCES public.organization (name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);
CREATE TABLE waybill_position
(
    position serial NOT NULL,
    price bigint,
    nomenclature bigint NOT NULL,
    amount bigint,
    waybill bigint NOT NULL,
    CONSTRAINT position_pkey PRIMARY KEY (position),
    CONSTRAINT nomenclature_fkey FOREIGN KEY (nomenclature)
        REFERENCES public.nomenclature (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT waybill_fkey FOREIGN KEY (waybill)
        REFERENCES public.waybill (waybill_num) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);