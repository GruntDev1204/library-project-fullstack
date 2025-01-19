ALTER TABLE transactions
    CHANGE COLUMN total single_price DECIMAL(15,2) DEFAULT 0;
