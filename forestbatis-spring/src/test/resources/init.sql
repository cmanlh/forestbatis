truncate table "User";
truncate table "Book";
truncate table "User_Book_Record";

insert into "User" values('Tom', 15, 8888.88, '2000-12-12',1);
insert into "User" values('Jerry', 25, 9999.99, '1990-12-12',1);
insert into "User" values('Lucy', 18, null, null,2);
insert into "User" values('Monica', 35, 12080.12, '1980-05-04',2);

insert into "Book" values('1', 'Live', 'Free', '2013-10-12 12:12:12');
insert into "Book" values('2', 'Mao', 'Amazon', '2015-11-23 12:12:12');
insert into "Book" values('3', '中国', '商务图书馆', '1978-10-12 12:12:12');
insert into "Book" values('4', '好 is Good', 'Free', '1989-10-12 12:12:12');
insert into "Book" values('5', '美国陷阱', 'Amazon', '1978-10-12 12:12:12');

insert into "User_Book_Record" values('Tom', '1', '2019-04-12', '2019-04-28');
insert into "User_Book_Record" values('Tom', '2', '2019-04-12', '2019-06-28');
insert into "User_Book_Record" values('Jerry', '2', '2019-04-12', '2019-06-28');
insert into "User_Book_Record" values('Lucy', '4', '2019-04-12', '2019-06-28');