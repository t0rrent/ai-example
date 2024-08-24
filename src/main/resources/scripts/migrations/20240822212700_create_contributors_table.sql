-- // create contributors table

CREATE TABLE contributors (
	id bigserial PRIMARY KEY,
	ip_address text NOT NULL UNIQUE,
	banned boolean NOT NULL DEFAULT FALSE
);

INSERT INTO contributors (
	ip_address
) VALUES (
	'127.0.0.1'
);

-- // @undo

DROP TABLE contributors;
