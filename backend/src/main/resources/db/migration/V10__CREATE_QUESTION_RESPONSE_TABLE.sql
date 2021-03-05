/*
 Stores the actual response to a particular question (qd_id) on a particular form response (frd_id)
 */
CREATE TABLE question_response (
    id INT AUTO_INCREMENT PRIMARY KEY,
    frd_id INT REFERENCES form_response_detail(id),
    qd_id INT REFERENCES question_detail(id),
    response VARCHAR(4000) NOT NULL
);

CREATE INDEX idx_question_response_frd_id ON question_response(frd_id);