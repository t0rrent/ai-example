-- // create contributors_detail_view

CREATE VIEW contributors_detail_view AS
SELECT
	id,
	ip_address,
	banned,
	(
		SELECT date_added
		FROM training_data
		WHERE contributor_id = contributors.id
  		ORDER BY date_added DESC
  		LIMIT 1
	) AS last_contribution_date
FROM contributors;

-- // @undo

DROP VIEW contributors_detail_view;
