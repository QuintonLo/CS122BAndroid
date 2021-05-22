DROP TABLE IF EXISTS fulltitle;
CREATE TABLE fulltitle(
	id VARCHAR(10),
    title text,
    PRIMARY KEY(id),
    FULLTEXT(title)
    );
INSERT INTO fulltitle (id, title) SELECT id, title FROM movies;
