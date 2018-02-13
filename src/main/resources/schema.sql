CREATE TABLE player(
  id INT SERIAL NOT NULL,
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE category(
  id INT SERIAL NOT NULL,
  name VARCHAR(20) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE score(
  id INT SERIAL NOT NULL,
  player_id INT NOT NULL,
  category_id INT NOT NULL,
  level INT NOT NULL,
  xp INT8 NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT score_player_fkey
    FOREIGN KEY (player_id) REFERENCES player(id)
    ON UPDATE CASCADE ON DELETE CASCADE ,
  CONSTRAINT score_category_fkey
    FOREIGN KEY (category_id) REFERENCES category(id)
    ON UPDATE CASCADE ON DELETE CASCADE
);