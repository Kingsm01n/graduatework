CREATE TABLE category (
   id UUID NOT NULL,
   name VARCHAR(255),
   user_id UUID,
   CONSTRAINT pk_category PRIMARY KEY (id)
);