CREATE TABLE review_table (
    id serial PRIMARY KEY,
    rating integer NOT NULL,
    review_comment TEXT DEFAULT NULL,
    user_name VARCHAR(100)  NOT NULL,
    content_id VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_name) REFERENCES user_table (user_name),
    FOREIGN KEY (content_id) REFERENCES content_table (content_id)
);