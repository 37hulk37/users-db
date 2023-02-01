CREATE TABLE groups (
	group_id serial,
	gname text UNIQUE NOT NULL,
	is_closed boolean DEFAULT ('f'),
	
	PRIMARY KEY (group_id)
);