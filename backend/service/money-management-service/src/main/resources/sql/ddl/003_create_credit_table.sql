CREATE TABLE credit (
  id UUID NOT NULL,
   name VARCHAR(255),
   current_value VARCHAR(255),
   needed_value VARCHAR(255),
   credit_type VARCHAR(255),
   user_id UUID,
   CONSTRAINT pk_credit PRIMARY KEY (id)
);