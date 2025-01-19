ALTER TABLE books
    ADD id_category BIGINT;

ALTER TABLE books
    ADD CONSTRAINT FK_BOOKS_ON_CATEGORY FOREIGN KEY (id_category) REFERENCES categories (id);