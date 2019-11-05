-- psql -h PSQL_HOST -p 5432 -U postgres jrvstrading -f schema.sql
-- Drop table
-- DROP TABLE IF EXISTS public.security_order cascade;

ALTER TABLE public.security_order
    ALTER COLUMN ts TYPE timestamp;

/*

CREATE TABLE public.security_order
(
  id         serial  NOT NULL,
  account_id int4    NOT NULL,
  status     varchar NOT NULL,
  ticker     varchar NOT NULL,
  "size"     int4    NOT NULL,
  price      float8  NULL,
  notes      varchar NULL,
  ts      timestamptz NOT NULL DEFAULT current_timestamp,
  CONSTRAINT security_order_pk PRIMARY KEY (id),
  CONSTRAINT security_order_account_fk FOREIGN KEY (account_id) REFERENCES account (id),
  CONSTRAINT security_order_quote_fk FOREIGN KEY (ticker) REFERENCES quote (ticker)
);
*/
