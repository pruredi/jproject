drop table joindb purge;

/* 화원가입 */
CREATE TABLE joindb (
	code NUMBER(20) NOT NULL, 
	join_id VARCHAR2(20) NOT NULL, /* 회원아이디 */
	passwd1 VARCHAR2(10) NOT NULL, /* 회원비번 */
	passwd2 VARCHAR2(10), /* 회원비번확인 */
	join_name VARCHAR2(10) NOT NULL, /* 성명 */
	join_date VARCHAR2(7) NOT NULL, /* 생년월일 */
	join_num VARCHAR2(8) NOT NULL, /* 주민번호 뒷자리 */
	addr_num VARCHAR2(5), /* 우편번호 */
	addr1 VARCHAR2(30), /* 주소 */
	addr2 VARCHAR2(30), /* 나머지 주소 */
	tel VARCHAR2(10), /* 집전화번호 */
	phone VARCHAR2(10), /* 휴대전화번호 */
	email VARCHAR2(20), /* 이메일 */
	join_jdate date, /* 가입 날짜 */
	Join_delcont  VARCHAR2(10) /* 탈퇴회원여부 */
);


create sequence join_code_seq 
increment by 1 start with 1 nocache;


-- joindb.sql
select * from tab;
select * from seq;
select * from joindb;