-- // create ai_model table

CREATE TABLE ai_model (
	id bigserial PRIMARY KEY,
	model text NOT NULL,
	f1_score numeric NOT NULL
);

-- // @undo

DROP TABLE ai_model;
