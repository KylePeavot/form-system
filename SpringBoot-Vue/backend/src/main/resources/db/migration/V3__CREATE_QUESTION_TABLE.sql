CREATE TABLE question(
    id INT AUTO_INCREMENT PRIMARY KEY,
    form_detail_id INT REFERENCES form_detail(id),
    created_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by VARCHAR(4000) NOT NULL
);
CREATE INDEX idx_q_form_detail_id ON question(form_detail_id);