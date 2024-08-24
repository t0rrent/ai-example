-- // alter ai_model table

ALTER TABLE ai_model
ADD type_name text;

UPDATE ai_model
SET type_name = 'type_1';

ALTER TABLE ai_model
ALTER COLUMN type_name
SET NOT NULL;

-- // @undo

ALTER TABLE ai_model
DROP COLUMN type_name;
