/*
 Master for the form_response_detail table
 */
CREATE TABLE form_response(
    id INT AUTO_INCREMENT PRIMARY KEY,
    form_detail_id INT NOT NULL REFERENCES form_detail(id),
    assigned_to VARCHAR(4000) NOT NULL,
    assigned_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);