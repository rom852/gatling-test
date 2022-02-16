-- $$
CREATE PROCEDURE generate_review(IN reviewCount INTEGER(255))
BEGIN
  DECLARE i INT DEFAULT 0;
  WHILE i < reviewCount DO
INSERT INTO `ccollabdb`.`review` (`review_creatorid`,
`review_creationdate`, `review_lastphasechangedateinsecs`, `review_deadline`,
`review_phaseid`, `review_ruletemplate`, `review_customfieldtemplateid`, `review_privateview`,
`review_groupid`, `review_title`)
VALUES ('1', now(),
unix_timestamp(), '1989-12-31 23:00:00', '1', 'Standard', '1', 'N', '1',
CONCAT('db_review_', FLOOR(rand() * 10000000)));
    SET i = i + 1;
  END WHILE;
END