create table "User"("id" varchar(32) PRIMARY KEY, "age" int, "income" decimal(20,6), "birthday" date, "sex" int default 1);
create table "Book"("id" varchar(32) PRIMARY KEY, "name" varchar(32), "publisher" varchar(32), "publishTime" timestamp);
create table "User_Book_Record"("USER_ID" varchar(32), "book_id" varchar(32), "borrowDate" date, "returnDate" date);