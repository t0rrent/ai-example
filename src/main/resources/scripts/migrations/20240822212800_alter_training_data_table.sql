-- // alter training_data table

ALTER TABLE training_data
ADD contributor_id bigint REFERENCES contributors(id);
 
UPDATE training_data
SET contributor_id = (
	SELECT id
	FROM contributors
	WHERE ip_address = '127.0.0.1'
);

ALTER TABLE training_data
ALTER COLUMN contributor_id
SET NOT NULL;

-- // @undo

ALTER TABLE training_data
DROP COLUMN contributor_id;
