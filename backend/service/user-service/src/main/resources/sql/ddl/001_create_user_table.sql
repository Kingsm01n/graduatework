CREATE TABLE "user" (
  id UUID NOT NULL,
   email VARCHAR(255),
   nickname VARCHAR(255),
   password VARCHAR(255),
   phone_number VARCHAR(255),
   role VARCHAR(255),
   enabled BOOLEAN NOT NULL,
   invitation_id UUID,
   CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE "user" ADD CONSTRAINT FK_USER_ON_INVITATION FOREIGN KEY (invitation_id) REFERENCES invitation (id);