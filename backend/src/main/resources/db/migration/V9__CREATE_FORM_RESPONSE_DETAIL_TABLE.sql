/*
 Stores the assignee and assigner of a form on a particular version of the form_response
 */
CREATE TABLE form_response_detail(
    id INT AUTO_INCREMENT PRIMARY KEY,
    form_response_id INT NOT NULL REFERENCES form_response(id),
    status_control BOOL NOT NULL,
    last_updated_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX idx_frd_fr_id ON form_response_detail(form_response_id);