-- // merge training and testing tables

DO $$ BEGIN
	IF (
		SELECT COUNT(*) 
		FROM testing_data
	) > 0 THEN
		CREATE TABLE deprecated_testing_data (
			id bigint PRIMARY KEY,
			"character" char NOT NULL,
			compressed_drawing boolean[]
		);
		INSERT INTO deprecated_testing_data
		SELECT 
			id,
			"character",
			compressed_drawing
		FROM
			testing_data;
	END IF;
	IF (
		SELECT COUNT(*) 
		FROM training_data
	) > 0 THEN
		CREATE TABLE deprecated_training_data (
			id bigint PRIMARY KEY,
			"character" char NOT NULL,
			compressed_drawing boolean[]
		);
		INSERT INTO deprecated_training_data
		SELECT 
			id,
			"character",
			compressed_drawing
		FROM
			training_data;
	END IF;
END
$$ language plpgsql;

INSERT INTO training_data (
	"character",
	compressed_drawing
)
SELECT
	"character",
	compressed_drawing
FROM
	testing_data;
	
DROP TABLE testing_data;
	

-- // @undo

CREATE TABLE testing_data (
	id bigserial PRIMARY KEY,
	"character" char NOT NULL,
	compressed_drawing boolean[]
);
	
DELETE FROM training_data;

DO $$ BEGIN
	IF EXISTS (
	   SELECT FROM information_schema.tables 
	   WHERE table_schema = 'public'
	   AND table_name = 'deprecated_testing_data'
	) THEN
		INSERT INTO testing_data (
			"character",
			compressed_drawing
		)
		SELECT
			"character",
			compressed_drawing
		FROM
			deprecated_testing_data;
		DROP TABLE deprecated_testing_data;
	END IF;
	IF EXISTS (
	   SELECT FROM information_schema.tables 
	   WHERE table_schema = 'public'
	   AND table_name = 'deprecated_training_data'
	) THEN
		INSERT INTO training_data (
			"character",
			compressed_drawing
		)
		SELECT
			"character",
			compressed_drawing
		FROM
			deprecated_training_data;
		DROP TABLE deprecated_training_data;
	END IF;
END
$$ language plpgsql;
