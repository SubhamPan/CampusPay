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

-- make procedure to get all the items sold by a vendor
delimiter //
create procedure get_all_items_sold_by_vendor(IN vendor_id varchar(50))
begin
    select * from items where items.vendor_id = vendor_id;
end //
delimiter ;

-- make procedure to add an item to the menu of a vendor
delimiter //
create procedure add_item_to_menu(IN item_name varchar(50), IN price int, IN vendor_id varchar(50))
begin
    insert into items (item_name, price, vendor_id) values (item_name, price, vendor_id);
end //
delimiter ;

-- make procedure to change the price of an item
delimiter //
create procedure change_price_of_item(IN item_id int, IN new_price int)
begin
    update items set price = new_price where items.ID = item_id;
end //
delimiter ;

-- make procedure to get price of an item
delimiter //
create procedure get_price_of_item(IN item_id int, OUT price int)
begin
    select items.price into price from items where items.ID = item_id;
end //
delimiter ;

-- make procedure to make a new order
delimiter //
create procedure make_order(IN transaction_id int, IN item_id int, IN price int, IN quantity int)
begin
    insert into orders (transaction_id, item_id, price, quantity) values (transaction_id, item_id, price, quantity);
end //
delimiter ;

-- make procedure to make a new transaction
delimiter //
create procedure make_transaction(IN vendor_id varchar(50), IN student_id varchar(50), IN total_amount int, IN date_time datetime)
begin
    insert into transactions (vendor_id, student_id, total_amount, date_time) values (vendor_id, student_id, total_amount, date_time);
end //
delimiter ;

-- make procedure to update student details
delimiter //
create procedure update_student_details(IN ID varchar(50), IN BITS_account varchar(50), IN s_name varchar(50), IN contact char(10), IN password varchar(256))
begin
    update student set BITS_account = BITS_account, s_name = s_name, contact = contact, password = password where student.ID = ID;
end //
delimiter ;

-- make procedure to update vendor details
delimiter //
create procedure update_vendor_details(IN ID varchar(50), IN v_name varchar(50), IN account_no varchar(50), IN contact char(10), IN password varchar(256))
begin
    update vendors set v_name = v_name, account_no = account_no, contact = contact, password = password where vendors.ID = ID;
end //
delimiter ;

-- make procedure to update item details
delimiter //
create procedure update_item_details(IN ID int, IN item_name varchar(50), IN price int)
begin
    update items set item_name = item_name, price = price where items.ID = ID;
end //
delimiter ;

-- alter procedure get_all_payments_made_by_student to get v_name, amount, date_time
drop procedure get_all_payments_made_by_student;
delimiter //
create procedure get_all_payments_made_by_student(IN student_id varchar(50))
begin
    select vendors.v_name, transactions.total_amount, transactions.date_time from transactions, vendors where transactions.student_id = student_id and transactions.vendor_id = vendors.ID;
end //
delimiter ;

-- make procedure to verify login
delimiter //
create procedure verify_login(IN ID varchar(50), IN password varchar(256))
begin
    select * from login where login.ID = ID and login.password = password;
end //
delimiter ;

-- make procedure to get all the students
delimiter //
create procedure get_all_students()
begin
    select * from student;
end //
delimiter ;

-- make procedure to get all the vendors
delimiter //
create procedure get_all_vendors()
begin
    select * from vendors;
end //
delimiter ;

-- make procedure to get all transactions
delimiter //
create procedure get_all_transactions()
begin
    select * from transactions;
end //

-- make procedure to add transaction
delimiter //
create procedure add_transaction(IN vendor_id varchar(50), IN student_id varchar(50), IN total_amount int)
begin
    insert into transactions (vendor_id, student_id, total_amount, date_time) values (vendor_id, student_id, total_amount, now());
end //

-- make procedure to get all orders of a transaction
delimiter //
create procedure get_all_orders_of_transaction(IN transaction_id int)
begin
    select * from orders where orders.transaction_id = transaction_id;
end //

-- make procedure to get student details
delimiter //
create procedure get_student_details(IN ID varchar(50))
begin
    select * from student where student.ID = ID;
end //

-- make procedure to get vendor details
delimiter //
create procedure get_vendor_details(IN ID varchar(50))
begin
    select * from vendors where vendors.ID = ID;
end //

-- make procedure to get item details
delimiter //
create procedure get_item_details(IN ID int)
begin
    select * from items where items.ID = ID;
end //