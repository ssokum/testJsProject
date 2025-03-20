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
  complaint char(2) default 'NO',			/* 신고글 유무(신고당한글:OK, 정상글:NO) */
  primary key (idx),
  foreign key (mid) references member(mid)
);
desc board;

insert into board values (default, 'admin','관리맨','게시판 서비스를 시작합니다.','좋은글 많이 올려주세요','192.168.50.20',default,default,default,default,default);

select * from board;

