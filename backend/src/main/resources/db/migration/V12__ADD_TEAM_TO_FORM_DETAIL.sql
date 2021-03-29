ALTER TABLE form_detail
ADD COLUMN team_id INTEGER NOT NULL DEFAULT 1 REFERENCES team(id);

CREATE INDEX idx_fd_team_id ON form_detail(team_id);