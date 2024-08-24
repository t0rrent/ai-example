-- // alter training_data table

ALTER TABLE training_data
ADD date_added timestamp default (now() at time zone 'utc');

UPDATE training_data
SET date_added = (now() at time zone 'utc');

ALTER TABLE training_data
ALTER COLUMN date_added
SET NOT NULL;

-- // @undo

ALTER TABLE training_data
DROP COLUMN date_added;
