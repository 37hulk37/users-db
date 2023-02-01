CREATE TABLE members (
	id serial,
	user_id integer NOT NULL,
	group_id integer NOT NULL,
	role text NOT NULL,
	
	CHECK (role IN ( 'Admin', 'User' )) 
	
	PRIMARY KEY (id),
	UNIQUE(group_id, user_id),
	FOREIGN KEY (group_id)
		REFERENCES groups (group_id) ON DELETE CASCADE,
	FOREIGN KEY (user_id)
		REFERENCES users (user_id) ON DELETE CASCADE,
);