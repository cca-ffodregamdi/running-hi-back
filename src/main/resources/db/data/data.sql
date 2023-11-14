-- dummy data insert
-- 즐겨찾기 테이블
INSERT INTO tbl_bookmark(user_no, folder_no, post_no)
VALUES(1,1,1);

------즐겨찾기 폴더 테이블
INSERT INTO tbl_bookmark_folder(folder_no, folder_name, user_no)
VALUES(1,'testFolder_1',1);
INSERT INTO tbl_bookmark_folder(folder_no, folder_name, user_no)
VALUES(2,'testFolder_2',2);

INSERT INTO tbl_comment(comment_no, comment_content, comment_date, comment_status, comment_report_cnt,  user_no, user_post_no)
VALUES (1, 'test1', '2023-01-01', false, 0, 1, 1);
INSERT INTO tbl_comment(comment_no, comment_content, comment_date, comment_status, comment_report_cnt,  user_no, user_post_no)
VALUES (2, 'test2', '2023-01-02', false, 0, 2, 1);
INSERT INTO tbl_comment(comment_no, comment_content, comment_date, comment_status, comment_report_cnt,  user_no, user_post_no)
VALUES (3, 'test3', '2023-01-03', false, 0, 3, 1);
INSERT INTO tbl_comment(comment_no, comment_content, comment_date, comment_status, comment_report_cnt,  user_no, user_post_no)
VALUES (4, 'test4', '2023-01-04', false, 0, 1, 1);
INSERT INTO tbl_comment(comment_no, comment_content, comment_date, comment_status, comment_report_cnt,  user_no, user_post_no)
VALUES (5, 'test5', '2023-01-05', false, 0, 2, 1);
INSERT INTO tbl_comment(comment_no, comment_content, comment_date, comment_status, comment_report_cnt,  user_no, user_post_no)
VALUES (6, 'test6', '2023-01-06', false, 0, 3, 1);
INSERT INTO tbl_comment(comment_no, comment_content, comment_date, comment_status, comment_report_cnt,  user_no, user_post_no)
VALUES (7, 'test7', '2023-01-07', false, 0, 1, 1);