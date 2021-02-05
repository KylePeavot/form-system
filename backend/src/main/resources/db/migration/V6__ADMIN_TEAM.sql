/* Insert admin team */
INSERT INTO team(id, created_timestamp, created_by)
VALUES (1, CURRENT_TIMESTAMP, 'weffs_system');

INSERT INTO team_detail(id, team_id, name, created_timestamp, created_by, status_control)
VALUES (1, 1, 'Admin', CURRENT_TIMESTAMP, 'weffs_system', true);

/* Create default admin users */
INSERT INTO team_member(team_detail_id, username, can_modify_forms, can_manage_team, created_timestamp, created_by, status_control)
VALUES (1, 'cra', true, true, CURRENT_TIMESTAMP, 'weffs_system', true);