CREATE TABLE category_table (
    id serial PRIMARY KEY,
    category_name TEXT NOT NULL,
    category_display_name TEXT NOT NULL,
    category_id VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE content_table (
    id serial PRIMARY KEY,
    content_id VARCHAR(100) NOT NULL,
    content_display_name TEXT NOT NULL,
    content_description TEXT NOT NULL,
    content_thumbnail TEXT NOT NULL,
    duration VARCHAR(100) NOT NULL,
    pricing VARCHAR(100) NOT NULL,
    category_id VARCHAR(100) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category_table (category_id)
);