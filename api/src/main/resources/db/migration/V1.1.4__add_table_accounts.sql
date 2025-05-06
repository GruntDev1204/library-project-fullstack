CREATE TABLE accounts
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    user_name   VARCHAR(255)          NOT NULL,
    password    VARCHAR(255)          NULL,
    status      VARCHAR(255)          NULL,
    customer_id BIGINT                NOT NULL,
    CONSTRAINT pk_accounts PRIMARY KEY (id)
);

ALTER TABLE accounts
    ADD CONSTRAINT uc_f752cf255e5841fc748933368 UNIQUE (`user_name`);

ALTER TABLE accounts
    ADD CONSTRAINT FK_ACCOUNTS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customers (id);