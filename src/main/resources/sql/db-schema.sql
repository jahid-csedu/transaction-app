CREATE TABLE IF NOT EXISTS transactions (
    trx_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(255) NOT NULL,
    trx_amount DECIMAL(19, 2) NOT NULL,
    description VARCHAR(255),
    trx_date DATE NOT NULL,
    trx_time TIME NOT NULL,
    customer_id VARCHAR(255) NOT NULL,
    version BIGINT NOT NULL,
    CONSTRAINT uq_transaction UNIQUE (account_number, trx_date, trx_time)
);

CREATE INDEX idx_customer_id ON transactions (customer_id);
CREATE INDEX idx_account_number ON transactions (account_number);