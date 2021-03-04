/*
 Stores the assignee and assigner of a form on a particular version of the form_response
 */
CREATE TABLE form_response_detail(
    id INT AUTO_GENERATE PRIMARY KEY,
    fr_id INT REFERENCES form_response(id),
    assignee_user VARCHAR2(4000) NOT NULL,
    assigner_team_member INT REFERENCES team_member(id),
    status_control BOOL NOT NULL,
    last_updated_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
)