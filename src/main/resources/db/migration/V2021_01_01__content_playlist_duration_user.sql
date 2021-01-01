CREATE TABLE duration_table (
    id serial PRIMARY KEY,
    duration bigint NOT NULL,
    content_id VARCHAR(100) NOT NULL,
    video_id VARCHAR(100)  NOT NULL,
    user_name VARCHAR(100)  NOT NULL,
    FOREIGN KEY (content_id) REFERENCES content_table (content_id),
    FOREIGN KEY (video_id) REFERENCES playlist_table (video_id),
    FOREIGN KEY (user_name) REFERENCES user_table (user_name)
);