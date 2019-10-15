
SHOW DATABASES;


SHOW GRANTS FOR 'grien'@'%';

GRANT ALL PRIVILEGES ON Haksa.* TO 'grien'@'%';

FLUSH PRIVILEGES;

CREATE DATABASE Haksa;

USE Haksa;


-- 학생테이블 
CREATE TABLE Student (
	Student_id CHAR(7) NOT NULL PRIMARY KEY,
	Student_name VARCHAR(10) NOT NULL,
	Student_dept CHAR(10) NOT NULL,
	Student_address VARCHAR(100) NULL
);


DROP TABLE Student;
SELECT * FROM Student;

INSERT INTO Student VALUES ('1234321', '이순신', '0000000001', NULL);
INSERT INTO Student VALUES ('0034567', '김철수', '0000000002', null);

INSERT INTO Student VALUES ('0123123', '율곡이이', '0000000003', null);

Select Student.Student_id, Student.Student_name, Majors.Major_name from Student, Majors where Student.Student_dept = Majors.Major_id and Student_name = '유관순';
DELETE FROM Student;

--책 정보

create table Books(
  Book_no char(6) NOT null primary KEY , -- 책번호
  Book_title varchar(50) not null, -- 책이름
  Book_author varchar(50) not NULL, -- 저자
  Book_loan CHAR(1) NOT NULL DEFAULT 'Y'
);

SELECT * FROM Books;
DROP TABLE Books;

UPDATE Books SET Book_loan = 'N' WHERE Book_no = '000001';

insert into Books values('000001','오라클기본','이황', default);
insert into Books values('000002','자바정복','율곡', default);
insert into Books values('000003','HTML5','강감찬', default);




-- 책 빌리는 테이블
create table BookRental
( BookRental_no int(10) primary KEY NOT NULL AUTO_INCREMENT, -- 대여번호
  RentStudent_id char(7) not null, -- 학번
  RentBook_No char(6) not null, -- 책번호
  BookRental_Date CHAR(8) not NULL,
  BookRental_loan CHAR(4) NOT NULL,
  CONSTRAINT cons_RS FOREIGN KEY (RentStudent_id) REFERENCES Student (Student_id),
  CONSTRAINT cons_RB FOREIGN KEY (RentBook_No) REFERENCES Books (Book_no)
);

DROP TABLE BookRental;
SELECT * FROM BookRental;


SELECT Student_dept FROM Student
GROUP BY Student_dept;


DELETE FROM BookRental;





insert into BookRental(RentStudent_id, RentBook_No, BookRental_Date, BookRental_loan) values('0034567','000001','20170713', '반납');
insert into BookRental(RentStudent_id, RentBook_No, BookRental_Date, BookRental_loan) values('0123123','000003','20170713', '반납');
insert into BookRental(RentStudent_id, RentBook_No, BookRental_Date, BookRental_loan) values('0123123','000002','20170713', '반납');
insert into BookRental(RentStudent_id, RentBook_No, BookRental_Date, BookRental_loan) values('0034567','000003','20170713', '반납');
insert into BookRental(RentStudent_id, RentBook_No, BookRental_Date, BookRental_loan) values('3216547','000001','20170713', '반납');
insert into BookRental(RentStudent_id, RentBook_No, BookRental_Date, BookRental_loan) values('3216547','000002','20170713', '반납');





-- 로그인 테이블
CREATE TABLE Login (
	Login_no INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	Login_id VARCHAR(20) NOT NULL,
	Login_pass VARCHAR(10) NOT NULL,
	Login_pivot INT null
);

SELECT * FROM Login;

ALTER TABEL 'Login' MODIFY 'Login_no' INT AUTO_INCREMENT;
DROP TABLE Login;


INSERT INTO Login(Login_id, Login_pass, Login_pivot) VALUES('admin', '1234', 1);
INSERT INTO Login(Login_id, Login_pass, Login_pivot) VALUES('gest', '1234', 2);


-- 학점 관리 테이블

CREATE TABLE Grades (
	Grade_no INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	Grade_studentId CHAR(7) NOT NULL,
	Grade_SubjId INT NOT NULL,
	Grade_rank CHAR(2) NOT NULL DEFAULT '',
	CONSTRAINT cons_GradesSI FOREIGN KEY (Grade_studentId) REFERENCES Student (Student_id),
	CONSTRAINT cons_GradesSJI FOREIGN KEY (Grade_SubjId) REFERENCES Subjects (Subject_id)
);

DROP TABLE Grades;
SELECT * FROM Grades


-- 학과

CREATE TABLE Majors (
	Major_id CHAR(10) NOT NULL PRIMARY KEY,
	Major_name VARCHAR(20) NOT NULL
);

SELECT * FROM Majors;
DROP TABLE Majors;

INSERT INTO Majors(Major_id, Major_name) VALUES ('0000000001', '멀티미디어');
INSERT INTO Majors(Major_id, Major_name) VALUES ('0000000002', '컴퓨터시스템');
INSERT INTO Majors(Major_id, Major_name) VALUES ('0000000003', '전자공학');



-- 과목

CREATE TABLE Subjects(
	Subject_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	Subject_majId CHAR(10) NOT NULL,
	Subject_name VARCHAR(20),
	CONSTRAINT cons_subj FOREIGN KEY (Subject_majId) REFERENCES Majors (Major_id)
);

SELECT * FROM Subjects;







SELECT Subject_name FROM Subjects WHERE Subject_majId = '';

DROP TABLE Subjects;

INSERT INTO Subjects(Subject_majId, Subject_name) VALUES('0000000001', '뉴미디어 개론');
INSERT INTO Subjects(Subject_majId, Subject_name) VALUES('0000000001', '멀티미디어 개론');
INSERT INTO Subjects(Subject_majId, Subject_name) VALUES('0000000001', '멀티미디어 기획');
INSERT INTO Subjects(Subject_majId, Subject_name) VALUES('0000000001', '영상편집');
INSERT INTO Subjects(Subject_majId, Subject_name) VALUES('0000000001', '웹서버구축');
INSERT INTO Subjects(Subject_majId, Subject_name) VALUES('0000000001', '컨텐츠편집기법');
INSERT INTO Subjects(Subject_majId, Subject_name) VALUES('0000000001', '컴퓨터그래픽스');
INSERT INTO Subjects(Subject_majId, Subject_name) VALUES('0000000001', '컴퓨터프로그래밍');




--------------------------------------------- 쿼리 테스트 구간 ------------------------------------------------------------

-- 헉생정보

SELECT Student.Student_id, Student.Student_name, Majors.Major_name, Student.Student_address FROM Student, Majors
WHERE Student.Student_dept = Majors.Major_id;


SELECT Student.Student_name, Majors.Major_name, Subjects.Subject_name  FROM Majors, Subjects, Student
WHERE Majors.Major_id = Subjects.Subject_majId
AND Student.Student_dept = Majors.Major_id
AND Majors.Major_name = '멀티미디어'


-- 학생 등록 쿼리
DELIMITER //
CREATE PROCEDURE Insert_Student(_STUDENTID CHAR(7), _STUDENTNAME VARCHAR(10), _STUDENTMAJORID CHAR(10), _STUDENTADDR VARCHAR(100))
BEGIN
	DECLARE done INT DEFAULT FALSE;
	DECLARE subjId INT;

	DECLARE openCursor CURSOR(p_STUDENTMAJORID VARCHAR(10)) FOR SELECT Subjects.Subject_id FROM Subjects WHERE Subjects.Subject_majId = p_STUDENTMAJORID;
	
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	DECLARE exit handler FOR SQLEXCEPTION
		BEGIN
			CLOSE openCursor;
			ROLLBACK;
		END;
		
		START TRANSACTION;
		
		OPEN openCursor(_STUDENTMAJORID);
		
			INSERT INTO Student(Student_id, Student_name, Student_dept, Student_address)
			VALUES (_STUDENTID, _STUDENTNAME, _STUDENTMAJORID, _STUDENTADDR);
			
			read_loop: LOOP
			
			FETCH openCursor INTO subjId;
			
			IF done THEN
				LEAVE read_loop;
			END IF;
			
			INSERT INTO Grades(Grade_studentId, Grade_SubjId, Grade_rank) VALUES (_STUDENTID, subjId, DEFAULT);
			
			END LOOP;
			
	COMMIT;
			
	CLOSE openCursor;
END;
//
DELIMITER ;

-- 학생 수정 쿼리
DELIMITER 
//
CREATE PROCEDURE update_Student(_STUDENTID CHAR(7), _STUDENTNAME VARCHAR(10), _STUDENTDEPT CHAR(10), _STUDENTADDR VARCHAR(100), OUT _RESULT INT)
BEGIN
	DECLARE done INT DEFAULT FALSE;
	DECLARE sDept CHAR(10);
	DECLARE subjId INT;
	
	DECLARE openCursor CURSOR(p_STUDENTDEPT VARCHAR(10)) FOR SELECT Subjects.Subject_id FROM Subjects WHERE Subjects.Subject_majId = p_STUDENTDEPT;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	
	DECLARE exit handler FOR SQLEXCEPTION
		BEGIN
			ROLLBACK;
			SET _RESULT = -1;
		END;
		
		START TRANSACTION;
		
			SET sDept = (SELECT Student_dept FROM Student WHERE Student_id = _STUDENTID);
			
			IF sDept <=> _STUDENTDEPT THEN
				UPDATE Student SET Student_name = _STUDENTNAME, Student_address = _STUDENTADDR WHERE Student_id = _STUDENTID;
			ELSE
				OPEN openCursor(_STUDENTDEPT);
				UPDATE Student SET Student_name = _STUDENTNAME, Student_dept = _STUDENTDEPT, Student_address = _STUDENTADDR WHERE Student_id = _STUDENTID;
				
				DELETE FROM Grades WHERE Grade_studentId = _STUDENTID;
				
				read_loop: LOOP
				
				FETCH openCursor INTO subjId;
				
				IF done THEN
					LEAVE read_loop;
				END IF;
				
				INSERT INTO Grades(Grade_studentId, Grade_SubjId, Grade_rank) VALUES (_STUDENTID, subjId, DEFAULT);			
				END LOOP;
			
				CLOSE openCursor;
			END IF;
		
	COMMIT;
	SET _RESULT = 0;
END;
//
DELIMITER ;


-- 학생 삭제 쿼리
DELIMITER 
//
CREATE PROCEDURE delete_Student(_STUDENTID CHAR(7))
BEGIN
	DECLARE sDept CHAR(10);
	DECLARE exit handler FOR SQLEXCEPTION
		BEGIN
			ROLLBACK;
		END;
		
		START TRANSACTION;
		
			DELETE FROM Grades WHERE Grade_studentId = _STUDENTID;
			
			DELETE FROM Student WHERE Student_id = _STUDENTID;
		
	COMMIT;
END;
//
DELIMITER ;

CALL delete_Student('2222222');


drop PROCEDURE delete_Student;

CALL update_Student('2222222', '유관순', '0000000010', '강원도 정동', @sel);
SELECT @sel;

SELECT * FROM Grades WHERE Grade_studentId = 2222222;

	UPDATE Grades SET Grade_SubjId = subjId WHERE Grade_studentId = _STUDENTID;

SELECT * FROM Grades;

DELETE FROM Grades WHERE Grade_studentId = 2222222;

DELETE FROM Student WHERE Student_id = 2222222;

CALL Insert_Student('4213256', '유관순', '0000000001', '강원도 정동진');
SELECT @RESULT;

SELECT Student_dept FROM Student GROUP BY Student_dept;

SELECT * FROM Student WHERE Student_id = '1234321';


SELECT br.RentStudent_id, s.Student_name, b.Book_title, br.BookRental_Date
FROM BookRental br, Student s, Books b
WHERE br.RentStudent_id = s.Student_id
AND br.RentBook_No = b.Book_no
AND s.Student_dept = '전자공학';


DELIMITER //
CREATE PROCEDURE Select_Subject(_STUDENTID CHAR(7), _STUDENTNAME VARCHAR(10))
BEGIN
	DECLARE done INT DEFAULT FALSE;
	DECLARE majId CHAR(10);
	
	IF _STUDENTID IS NULL THEN
		SET majId := (SELECT Student_dept FROM Student WHERE Student_name = _STUDENTNAME);
	ELSE
		SET majId := (SELECT Student_dept FROM Student WHERE Student_id = _STUDENTID);
	END IF;
	
	SELECT Subject_name, Subject_id FROM Subjects WHERE Subject_majId = majId;
	 
END;
//
DELIMITER ;

DROP PROCEDURE Select_Subject;

CALL Select_Subject(NULL, '유관순');

--------------------------------- 테스트 예제 샘플 --------------------------------------

USE Test;

USE Haksa;

DROP PROCEDURE IF EXISTS p1;
DROP TABLE IF EXISTS t1;
CREATE TABLE t1 (a INT, b VARCHAR(10));

INSERT INTO t1 VALUES (1,'old'),(2,'old'),(3,'old'),(4,'old'),(5,'old');

DELIMITER //

CREATE PROCEDURE p1(min INT,max INT)
BEGIN
  DECLARE done INT DEFAULT FALSE;
  DECLARE va INT;
  DECLARE cur CURSOR(pmin INT, pmax INT) FOR SELECT a FROM t1 WHERE a BETWEEN pmin AND pmax;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=TRUE;
  OPEN cur(min,max);
  read_loop: LOOP
    FETCH cur INTO va;
    IF done THEN
      LEAVE read_loop;
    END IF;
    INSERT INTO t1 VALUES (va,'new');
  END LOOP;
  CLOSE cur; 
END;
//

DELIMITER ;

SELECT a FROM t1 WHERE a BETWEEN 2 AND 4;

CALL p1(2,4);

SELECT * FROM t1;