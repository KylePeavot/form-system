/*
 Master for the form_response_detail table
 */
CREATE TABLE form_response(
    id INT AUTO_INCREMENT PRIMARY KEY,
    form_detail_id INT NOT NULL REFERENCES form_detail(id),
    assigner_team_member_id INT NOT NULL REFERENCES team_member(id),
    assigner_team_detail_id INT NOT NULL REFERENCES team_detail(id),
    assigned_to VARCHAR(4000) NOT NULL,
    assigned_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX idx_frd_frd_id ON form_response(form_detail_id);
CREATE INDEX idx_frd_atm_id ON form_response(assigner_team_member_id);
CREATE INDEX idx_frd_atd_id ON form_response(assigner_team_detail_id);