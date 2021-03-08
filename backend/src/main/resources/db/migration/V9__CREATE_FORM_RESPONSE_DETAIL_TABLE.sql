/*
 Stores the assignee and assigner of a form on a particular version of the form_response
 */
CREATE TABLE form_response_detail(
    id INT AUTO_INCREMENT PRIMARY KEY,
    form_response_id INT NOT NULL REFERENCES form_response(id),
    assigner_team_member_id INT NOT NULL REFERENCES team_member(id),
    assigner_team_detail_id INT NOT NULL REFERENCES team_detail(id),
    status_control BOOL NOT NULL,
    last_updated_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX idx_frd_fr_id ON form_response_detail(form_response_id);
CREATE INDEX idx_frd_atm_id ON form_response_detail(assigner_team_member_id);
CREATE INDEX idx_frd_atd_id ON form_response_detail(assigner_team_detail_id);