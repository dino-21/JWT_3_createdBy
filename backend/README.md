boot_step1_java17_member

![45](https://github.com/user-attachments/assets/ff9662c3-3719-48e7-967c-2b6a130dd0cc)



![2](https://github.com/user-attachments/assets/07c82059-3fc9-47f8-a493-4c3a8106ad07)




## MySQL member ##

```

-- member 데이터베이스를 생성
-- 기본 문자 세트는 utf8mb4로 설정
-- 기본 콜레이션은 utf8mb4_general_ci로 설정
CREATE DATABASE member DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

show databases;

use member;
desc member;

select * from member;

insert into member(member_id, address, age, name, phone) values('6', '심양', 20, '관우', '010-9999-9999');
insert into member(member_id, address, age, name, phone) values('7', '심양', 27, '유비', '010-9999-9999');
insert into member(member_id, address, age, name, phone) values('8', '심양', 29, '장비', '010-9999-9999');
insert into member(member_id, address, age, name, phone) values('12', '심양', 20, '관우', '010-9999-9999');
insert into member(member_id, address, age, name, phone) values('13', '심양', 27, '유비', '010-9999-9999');
insert into member(member_id, address, age, name, phone) values('14', '심양', 29, '장비', '010-9999-9999');
insert into member(member_id, address, age, name, phone) values('22', '심양', 20, '관우', '010-9999-9999');
insert into member(member_id, address, age, name, phone) values('23', '심양', 27, '유비', '010-9999-9999');
insert into member(member_id, address, age, name, phone) values('24', '심양', 29, '장비', '010-9999-9999');



```
