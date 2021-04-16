CREATE TABLE form_detail(
    id INT AUTO_INCREMENT PRIMARY KEY,
    form_id INT REFERENCES form(id),
    last_updated_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_updated_by VARCHAR(4000) NOT NULL,
    status_control BOOL NOT NULL
);
CREATE INDEX idx_fd_form_id ON form_detail(form_id);