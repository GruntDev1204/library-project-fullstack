CREATE TABLE transactions (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      customer_id BIGINT NOT NULL,
      book_id BIGINT NOT NULL,
      number_of_books BIGINT NOT NULL,
      transaction_date TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
      expired_date TIMESTAMP NOT NULL,
      status VARCHAR(255) DEFAULT 'PENDING',
      CONSTRAINT chk_status CHECK (status IN ('PENDING', 'BORROWED', 'RETURNED')),
      FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
      FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);
