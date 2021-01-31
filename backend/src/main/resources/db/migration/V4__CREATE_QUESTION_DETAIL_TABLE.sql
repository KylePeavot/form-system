CREATE TABLE question_detail(
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT REFERENCES question(id),
    title VARCHAR(4000) NOT NULL,
    guidance VARCHAR(4000) NOT NULL,
    question_type VARCHAR(100) NOT NULL,
    parent_question_id INT NOT NULL,
    order_number INT NOT NULL,
    last_updated_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    last_updated_by VARCHAR(4000) NOT NULL,
    status_control VARCHAR(1) NOT NULL
);
CREATE INDEX idx_qd_question_id ON question_detail(question_id);