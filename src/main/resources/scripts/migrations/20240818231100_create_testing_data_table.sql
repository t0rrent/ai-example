-- // create training_data table

CREATE TABLE testing_data (
	id bigserial PRIMARY KEY,
	"character" char NOT NULL,
	compressed_drawing boolean[]
);

-- // @undo

DROP TABLE testing_data;
