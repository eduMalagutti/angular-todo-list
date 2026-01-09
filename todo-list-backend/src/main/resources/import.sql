INSERT INTO todo_list (id, name) VALUES (1, 'Frontend Tasks');
INSERT INTO todo_list (id, name) VALUES (2, 'Backend Tasks');

-- Priority stored as code strings: 'N/A' (UNSPECIFIED), 'L' (LOW), 'M' (MEDIUM), 'H' (HIGH)
INSERT INTO todo (id, task, priority, todo_list_id) VALUES (1, 'Implement loading - frontend only', 'L', 1);
INSERT INTO todo (id, task, priority, todo_list_id) VALUES (2, 'Implement search - frontend only', 'M', 1);
INSERT INTO todo (id, task, priority, todo_list_id) VALUES (3, 'Implement delete on click - frontend only', 'L', 1);
INSERT INTO todo (id, task, priority, todo_list_id) VALUES (4, 'Replace mock service by integrating backend', 'H', 2);
