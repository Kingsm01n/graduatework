CREATE TABLE transaction (
  id UUID NOT NULL,
   name VARCHAR(255),
   value DOUBLE PRECISION,
   user_id UUID,
   date TIMESTAMP WITHOUT TIME ZONE,
   balance_id UUID,
   credit_id UUID,
   category_id UUID,
   CONSTRAINT pk_transaction PRIMARY KEY (id)
);

ALTER TABLE transaction ADD CONSTRAINT FK_TRANSACTION_ON_BALANCE FOREIGN KEY (balance_id) REFERENCES balance (id);

ALTER TABLE transaction ADD CONSTRAINT FK_TRANSACTION_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE transaction ADD CONSTRAINT FK_TRANSACTION_ON_CREDIT FOREIGN KEY (credit_id) REFERENCES credit (id);