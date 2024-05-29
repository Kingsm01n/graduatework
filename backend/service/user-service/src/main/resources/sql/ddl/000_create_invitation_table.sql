CREATE TABLE invitation (
  id UUID NOT NULL,
   email VARCHAR(255),
   email_confirmed BOOLEAN NOT NULL,
   phone_number VARCHAR(255),
   phone_number_confirmed BOOLEAN NOT NULL,
   CONSTRAINT pk_invitation PRIMARY KEY (id)
);