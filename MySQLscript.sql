create database campuspay;
use campuspay;
create table login (
    ID varchar(50),
    password varchar(255),
    primary key (ID)
);

create table student (
    ID varchar(50),
    BITS_account varchar(50),
    s_name varchar(50),
    contact char(10),
    password varchar(256),
    primary key (ID)
);
use campuspay;
create table vendors (
    ID varchar(50),
    v_name varchar(50),
    account_no varchar(50),
    contact char(10),
    password varchar(256),
    primary key (ID)
);

create table items (
    ID int auto_increment,
    item_name varchar(50),
    price int,
    vendor_id varchar(50),
    primary key (ID),
    foreign key (vendor_id) references vendors(ID)
);

create table transactions (
    ID int auto_increment,
    vendor_id varchar(50),
    student_id varchar(50),
    total_amount int,
    date_time datetime,
    primary key (ID),
    foreign key (vendor_id) references vendors(ID),
    foreign key (student_id) references student(ID)
);

create table orders (
    ID int auto_increment,
    transaction_id int,
    item_id int,
    price int,
    quantity int,
    primary key (ID),
    foreign key (transaction_id) references transactions(ID),
    foreign key (item_id) references items(ID)
);

-- define a procedure to get the total amount spent by a student
delimiter //
create procedure get_total_amount_spent_by_student(IN student_id varchar(50), OUT total_amount_spent int)
begin
    select sum(total_amount) into total_amount_spent from transactions where transactions.student_id = student_id;
end //
delimiter ;

-- make procedure to get all the payments made by a student
delimiter //
create procedure get_all_payments_made_by_student(IN student_id varchar(50))
begin
    select * from transactions where transactions.student_id = student_id;
end //
delimiter ;

-- make procedure to register a vendor
delimiter //
create procedure register_vendor(IN ID varchar(50), IN v_name varchar(50), IN account_no varchar(50), IN contact char(10), IN password varchar(256))
begin
    insert into vendors (ID, v_name, account_no, contact, password) values (ID, v_name, account_no, contact, password);
    insert into login (ID, password) values (ID, password);
end //
delimiter ;

-- make procedure to register a student
delimiter //
create procedure register_student(IN ID varchar(50), IN BITS_account varchar(50), IN s_name varchar(50), IN contact char(10), IN password varchar(256))
begin
    insert into student (ID, BITS_account, s_name, contact, password) values (ID, BITS_account, s_name, contact, password);
    insert into login (ID, password) values (ID, password);
end //
delimiter ;

-- make procedure to check total amount earned by a vendor
delimiter //
create procedure get_total_amount_earned_by_vendor(IN vendor_id varchar(50), OUT total_amount_earned int)
begin
    select sum(total_amount) into total_amount_earned from transactions where transactions.vendor_id = vendor_id;
end //
delimiter ;

-- make procedure to get all the transactions made by a vendor
delimiter //
create procedure get_all_transactions_made_by_vendor(IN vendor_id varchar(50))
begin
    select * from transactions where transactions.vendor_id = vendor_id;
end //
delimiter ;