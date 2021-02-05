/* Create team table */
CREATE TABLE team (
    id INT AUTO_INCREMENT PRIMARY KEY,
    created_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(4000) NOT NULL
);

/* Create team detail table */
CREATE TABLE team_detail (
    id INT AUTO_INCREMENT PRIMARY KEY,
    team_id INT NOT NULL REFERENCES team(id),
    name VARCHAR(4000) NOT NULL,
    created_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by VARCHAR(4000) NOT NULL,
    status_control BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE INDEX idx_td_team_id ON team_detail(team_id);

/* Create team_member table */
CREATE TABLE team_member (
    id INT AUTO_INCREMENT PRIMARY KEY,
    team_detail_id INT NOT NULL REFERENCES team_detail(id),
    username VARCHAR(4000) NOT NULL,
    created_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by VARCHAR(4000) NOT NULL,
    status_control BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE INDEX idx_tm_team_detail_id ON team_member(team_detail_id);