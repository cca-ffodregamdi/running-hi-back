-- dummy data insert
-- 즐겨찾기 테이블
INSERT INTO tbl_bookmark(user_no, folder_no, post_no)
VALUES(1,1,1);

-- 피드백 테이블
INSERT INTO tbl_feedback(feedback_no, feedback_date, feedback_title, feedback_content, feedback_status, feedback_reply, feedback_reply_date, feedback_writer_no, feedback_category)
VALUES (1, null, '제목', '내용', false, null, null, 1, 0);