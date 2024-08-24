-- // create training_data table

CREATE TABLE training_data (
	id bigserial PRIMARY KEY,
	"character" char NOT NULL,
	compressed_drawing boolean[]
);

-- // @undo

DROP TABLE training_data;
