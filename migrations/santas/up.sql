CREATE TABLE santas (
	id serial,
	rec_id integer NOT NULL,
	self_id integer NOT NULL,
	group_id integer NOT NULL,
	
	PRIMARY KEY (id),
	UNIQUE(group_id, self_id, rec_id),
	FOREIGN KEY (group_id)
		REFERENCES groups (group_id) ON DELETE CASCADE,
	FOREIGN KEY (rec_id)
		REFERENCES users (user_id) ON DELETE CASCADE,
	FOREIGN KEY (self_id)
		REFERENCES users (user_id) ON DELETE CASCADE
);