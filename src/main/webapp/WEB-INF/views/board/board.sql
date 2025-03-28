show tables;

create table board (
  idx		int not null auto_increment,	/* 게시글의 고유번호 */
  mid		varchar(20) not null,					/* 게시글 올린이의 아이디 */
  nickName varchar(20) not null,			/* 게시글 올린이의 닉네임 */
  title varchar(100) not null,				/* 글 제목 */
  content text not null,							/* 글 내용 */
  hostIp  varchar(40) not null,				/* 글 올린이 IP */
  openSw  char(2) default 'OK',				/* 게시글 공개여부(OK:공개, NO:비공개) */
  readNum int default 0,							/* 글 조회수 */
  wDate		datetime default now(),			/* 글쓴 날짜 */
  good		int default 0,							/* '좋아요' 클릭횟수 누적 */
  complaint char(2) default 'NO',			/* 신고글 유무(신고당한글:OK, 정상글:NO, 감춘글:HI(hidden)) */
  primary key (idx),
  foreign key (mid) references member(mid)
);
desc board;

insert into board values (default, 'admin','관리맨','게시판 서비스를 시작합니다.','좋은글 많이 올려주세요','192.168.50.20',default,default,default,default,default);
insert into board values (default, 'atom1234','아톰맨','아톰 다녀갑니다.','앞으로 잘 부탁드립니다.','192.168.50.19',default,default,default,default,default);

select * from board order by idx desc;

-- 댓글의 개수를 원본글과 함께 출력
select *,(select count(idx) from boardReply where boardIdx=b.idx) as replyCnt from board b;


-- 이전글(preVo)
select idx,title from board where idx < 5 order by idx desc limit 1;
-- 다음글(nextVo)
select idx,title from board where idx > 5 order by idx limit 1;

/* 댓글 달기 */
create table boardReply (
  idx			  int not null auto_increment,	/* 댓글 고유번호 */
  boardIdx  int not null,						/* 원본글의 고유번호 - 외래키로 지정 */
  mid			  varchar(20) not null,		/* 댓글 올린이 아이디 */
  nickName  varchar(20) not null,		/* 댓글 올린이 닉네임 */
  content   text not null,					/* 댓글 내용 */
  hostIp		varchar(50) not null,		/* 댓글 올린 PC의 고유 IP */
  wDate			datetime default now(),	/* 댓글 올린 날짜/시간 */
  primary key(idx),
  foreign key(boardIdx) references board(idx)
  on update cascade
  on delete cascade
);
desc boardReply;

insert into boardReply values (default,45,'admin','관리맨','댓글 연습중..','192.168.50.1',default);

select * from boardReply;
