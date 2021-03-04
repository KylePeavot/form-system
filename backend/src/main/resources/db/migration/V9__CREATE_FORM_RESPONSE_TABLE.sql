/*
 Master for the form_response_detail table
 */
CREATE TABLE form_response(
    id INT AUTO_INCREMENT PRIMARY KEY,
    assigned_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
)