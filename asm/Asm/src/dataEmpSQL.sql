create database Java2_Employee;
go

use Java2_Employee;
go

create table employee
(code varchar(10), name nvarchar(50), age int, email varchar(30), salary money);
go

insert into employee values
(1, N'Vũ Đăng Quang', 25, 'vudangquang@gmail.com', 5000),
(2, N'Trương Thị Minh Ngọc', 22, 'truongthiminhngoc@gmail.com', 2000),
(3, N'Vũ Hoàng Long', 24, 'vuhoanglong@gmail.com', 6000);
go