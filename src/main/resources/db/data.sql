insert into user_tb(username, password, email) values('ssar', '$2a$10$zyt7uVaXSKRprEBb3viGFunlvYsmiDqF8KmetDAjQSchwji4olruC', 'ssar@nate.com');
insert into user_tb(username, password, email) values('cos', '$2a$10$zyt7uVaXSKRprEBb3viGFunlvYsmiDqF8KmetDAjQSchwji4olruC', 'cos@nate.com');
insert into board_tb(title, content, user_id, created_at) values('제목2', '내용1', 1, now());
insert into board_tb(title, content, user_id, created_at) values('2', '내용2', 1, now());
insert into board_tb(title, content, user_id, created_at) values('22', '내용3', 1, now());
insert into board_tb(title, content, user_id, created_at) values('222', '내용4', 2, now());
insert into board_tb(title, content, user_id, created_at) values('제목5', '내용5', 2, now());
insert into reply_tb(comment, board_id, user_id) values('댓글1', 1, 1);
insert into reply_tb(comment, board_id, user_id) values('댓글2', 1, 1);