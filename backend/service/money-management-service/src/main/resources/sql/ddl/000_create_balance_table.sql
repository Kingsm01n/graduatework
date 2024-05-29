CREATE TABLE balance (
   id UUID,
   name VARCHAR(255),
   current_value DOUBLE PRECISION,
   user_id UUID,
   CONSTRAINT pk_balance PRIMARY KEY (id)
);