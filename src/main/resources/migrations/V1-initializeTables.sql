CREATE TABLE IF NOT EXISTS bank.account
(
    account_id bigint NOT NULL DEFAULT nextval('bank."Account_account_id_seq"'::regclass),
    balance double precision NOT NULL DEFAULT 0.00,
    account_type character varying(255) COLLATE pg_catalog."default" NOT NULL DEFAULT 'Standard User'::character varying,
    created_at timestamp with time zone DEFAULT now(),
    customer_id bigint NOT NULL,
    status character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Account_pkey" PRIMARY KEY (account_id),
    CONSTRAINT fk_customer_id FOREIGN KEY (customer_id)
        REFERENCES bank.customer (customer_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

CREATE TABLE IF NOT EXISTS bank.customer
(
    customer_id bigint NOT NULL DEFAULT nextval('bank."Customer_customer_id_seq"'::regclass),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    country character varying(255) COLLATE pg_catalog."default" NOT NULL,
    state character varying(255) COLLATE pg_catalog."default" NOT NULL,
    city character varying(255) COLLATE pg_catalog."default" NOT NULL,
    customer_role character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Customer_pkey" PRIMARY KEY (customer_id),
    CONSTRAINT customer_customer_role_check CHECK (customer_role::text = ANY (ARRAY['ADMINISTRATOR'::character varying::text, 'STANDARD_CUSTOMER'::character varying::text, 'VIEW_CUSTOMER'::character varying::text])) NOT VALID
    )

CREATE TABLE IF NOT EXISTS bank.loan
(
    loan_id bigint NOT NULL DEFAULT nextval('bank."Loan_loan_id_seq"'::regclass),
    account_id bigint NOT NULL,
    loan_date timestamp(6) without time zone NOT NULL,
    amount double precision NOT NULL,
    CONSTRAINT "Loan_pkey" PRIMARY KEY (loan_id),
    CONSTRAINT "fk_Loan_Account" FOREIGN KEY (account_id)
    REFERENCES bank.account (account_id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    )

CREATE TABLE IF NOT EXISTS bank.transaction
(
    transaction_id bigint NOT NULL DEFAULT nextval('bank."Transaction_transaction_id_seq"'::regclass),
    amount double precision NOT NULL,
    transaction_date timestamp without time zone NOT NULL,
    status character varying(255) COLLATE pg_catalog."default" NOT NULL,
    transaction_type character varying(255) COLLATE pg_catalog."default" NOT NULL,
    from_account_id bigint,
    to_account_id bigint,
    completed_transaction_date timestamp(6) without time zone NOT NULL,
    create_transaction_date timestamp(6) without time zone NOT NULL,
    CONSTRAINT "Transaction_pkey" PRIMARY KEY (transaction_id),
    CONSTRAINT fkluqt8k2pa8d4gmggx4rhl5vgv FOREIGN KEY (to_account_id)
    REFERENCES bank.account (account_id) MATCH SIMPLE
                               ON UPDATE NO ACTION
                               ON DELETE NO ACTION,
    CONSTRAINT fkrff4jlxetafju1e5cks5mfcnk FOREIGN KEY (from_account_id)
    REFERENCES bank.account (account_id) MATCH SIMPLE
                               ON UPDATE NO ACTION
                               ON DELETE NO ACTION
    )