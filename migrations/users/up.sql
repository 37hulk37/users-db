CREATE TABLE users (
	user_id serial,
	name text UNIQUE NOT NULL,
	
	PRIMARY KEY (user_id)
);