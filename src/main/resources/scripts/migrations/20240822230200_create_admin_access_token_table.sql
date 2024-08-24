-- // create admin_access_token table

CREATE TABLE admin_access_token (
   	onerow_id bool PRIMARY KEY DEFAULT true,
	token text NOT NULL,
	CONSTRAINT onerow_uni CHECK (onerow_id)
);

-- // @undo

DROP TABLE admin_access_token;
