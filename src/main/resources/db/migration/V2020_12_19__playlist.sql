ALTER TABLE content_table ADD CONSTRAINT content_id_unique_constraint UNIQUE (content_id);

CREATE TABLE playlist_table (
    id serial PRIMARY KEY,
    video_id VARCHAR(100) UNIQUE NOT NULL,
    start_time bigint NOT NULL,
    title TEXT NOT NULL,
    src TEXT NOT NULL,
    type TEXT NOT NULL,
    thumbnail TEXT NOT NULL,
    description TEXT NOT NULL,
    duration VARCHAR(100) NOT NULL,
    display_duration VARCHAR(100) DEFAULT NULL,
    content_id VARCHAR(100) NOT NULL,
    FOREIGN KEY (content_id) REFERENCES content_table (content_id)
);