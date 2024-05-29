CREATE TABLE budget (
  id UUID NOT NULL,
   name VARCHAR(255),
   current_value DOUBLE PRECISION,
   needed_value DOUBLE PRECISION,
   user_id UUID,
   category_id UUID,
   CONSTRAINT pk_budget PRIMARY KEY (id)
);

ALTER TABLE budget ADD CONSTRAINT FK_BUDGET_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);