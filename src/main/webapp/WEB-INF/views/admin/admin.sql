show tables;

select * from member;

select mid from member where userDel='OK';

-- 날짜함수
select now();
select year(now());
select month(now());
select day(now());
select hour(now());
select minute(now());
select second(now());
select week(now());
select weekday(now());
select weekday('2025-3-22');
select weekday('2025-3-23');

select year(now()),month(now()),day(now());
select year(now()) as 년,month(now()) 월,day(now()) 일;

select concat(year(now()),'-',month(now()),'-',day(now())) as ymd;

-- 시간 연산
select second(now());
select date_add(now(), interval 10 second);

select minute(now());
select date_add(now(), interval 10 minute);

select hour(now());
select date_add(now(), interval 10 hour);

select day(now());
select date_add(now(), interval 10 day);

select month(now());
select date_add(now(), interval 10 month);

select year(now());
select date_add(now(), interval 10 year);

select hour(now());
select date_sub(now(), interval 10 hour);

select hour(now());
select date_add(now(), interval -10 hour);

-- 날짜 차이를 구하는 함수 : datediff / timestampdiff
select datediff(now(), '2025-3-18');
select datediff('2025-3-18', now());

select timestampdiff(minute, '2025-3-19 15:30:0', now());

select mid 아이디,startDate 가입일 from member;

-- 가입한 날로부터 오늘은 몇일이 지났는지를 출력?
select mid 아이디,startDate 가입일, datediff(now(), startDate) as 지난날수 from member;

select mid,startDate, datediff(now(), startDate) as deleteDiff from member where userDel='OK';

select * from board;
select date_format(wDate, '%y-%m-%d') from board;	/* %y : 2자리 연도 */
select date_format(wDate, '%y/%m/%d') from board;
select date_format(wDate, '%y년%m월%d일') from board;
select date_format(wDate, '%Y-%m-%d') from board; /* %Y : 4자리 연도 */
select date_format(wDate, '%Y-%m-%d %w') from board; /* %w : 요일(숫자: 월-1) */
select date_format(wDate, '%Y-%m-%d %W') from board; /* %W : 요일(영어로) */
select date_format(wDate, '%Y-%M-%d') from board; /* %M : 월이 영어로 */
select date_format(wDate, '%Y-%m-%d %p %h:%i') from board; /* %p : AM/PM, %h:12시간제 */
select date_format(wDate, '%Y-%m-%d %H:%i') from board;	/* %H : 24시간제 */
select date_format(wDate, '%Y-%m-%d %H:%i:%s') from board;	/* %s : 초 */

/* 리뷰 테이블 */
create table review (
  idx  int not null auto_increment,	/* 리뷰 고유번유 */
  part varchar(10) not null,				/* 분야(board, pds,.....) */
  partIdx int not null,							/* 해당분야의 고유번유 */
  mid		varchar(20) not null,
  nickName varchar(20) not null,
  star     int not null default 0,	/* 별점 부여 점수 */
  content  text,										/* 리뷰 내용 */
	rDate		 datetime default now(),	/* 리뷰 등록날짜 */
	primary key(idx),
	foreign key(mid) references member(mid)
);

/* 리뷰에 댓글 달기 */
create table reviewReply(
  replyIdx  int not null auto_increment,	/* 댓글 고유번호 */
  reviewPart varchar(10) not null,		/* 분야(board, pds, .....) */
  reviewIdx int not null,						/* 원본글(부모글:리뷰)의 고유번호 */
  replyMid  varchar(20) not null,
  replyNickName varchar(20) not null,
  replyRDate	 datetime default now(),/* 댓글 올린 날짜 */
  replyContent text not null,					/* 댓글 내용 */
  primary key(replyIdx),
  foreign key(reviewIdx) references review(idx),
  foreign key(replyMid) references member(mid)
);
