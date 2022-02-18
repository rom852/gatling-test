-- $$
CREATE PROCEDURE generate_users(IN usersCount INTEGER(255))
BEGIN
  DECLARE i INT DEFAULT 0;
  WHILE i < usersCount DO
INSERT INTO `mainbdb`.`user` (`user_password`, `user_name`, `user_email`, `user_phone`,
`user_initials`, `user_guid`, `user_department`, `user_firstname`, `user_lastname`,
`user_lastlogin`, `user_lastactivity`, `user_lastlogout`, `user_admin`, `user_tutorials`,
`user_active`, `user_nextpagescroll`, `user_createchildgroups`, `user_edittemplates`,
`user_editcustomfields`, `user_editchecklists`, `user_editroles`, `user_editautomaticlinks`,
`user_createremotesystems`, `user_login`) VALUES
('098f6bcd4621d373cade4e832627b4f6', 'Test user', CONCAT(FLOOR(rand() * 10000000),'@mail.com'),
'', '', uuid_short(), '', '', '', '1989-12-31 23:00:00',
'1989-12-31 23:00:00', '1989-12-31 23:00:00', 'N', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', CONCAT('db_user_', FLOOR(rand() * 10000000)));
    SET i = i + 1;
  END WHILE;
END