-- dummy data insert
-- 즐겨찾기 테이블
INSERT INTO tbl_bookmark(user_no, folder_no, post_no)
VALUES(1,1,1);

INSERT INTO tbl_bookmark_folder(folder_no, folder_name, user_no)
VALUES(1,'testFolder_1',1);

-- 키워드 테이블
INSERT INTO tbl_keyword(keyword_name)
values ("test1");
INSERT INTO tbl_keyword(keyword_name)
values ("test2");