drop database if exists campuspay;
create database campuspay;

use campuspay;

create table login
(
    ID       varchar(50),
    password varchar(256) NOT NULL,
    primary key (ID),
    role     int,
    CONSTRAINT chk_role CHECK (role = 0 OR role = 1 OR role = 2)
);

create table student
(
    ID         varchar(50),
    account_no varchar(50) NOT NULL,
    s_name     varchar(50) NOT NULL,
    contact    char(10),
    budget     int unsigned NOT NULL DEFAULT 100000,
    primary key (ID)
);

create table vendors
(
    ID         varchar(50),
    v_name     varchar(50) NOT NULL,
    account_no varchar(50) NOT NULL,
    contact    char(10),
    primary key (ID)
);

create table items
(
    ID        int auto_increment,
    item_name varchar(50) NOT NULL,
    price     int unsigned NOT NULL,
    vendor_id varchar(50),
    primary key (ID),
    foreign key (vendor_id) references vendors (ID) on delete cascade
);

create table transactions
(
    ID           int auto_increment,
    vendor_id    varchar(50),
    student_id   varchar(50),
    total_amount int unsigned NOT NULL,
    date_time    datetime,
    primary key (ID),
    foreign key (vendor_id) references vendors (ID) on delete set null,
    foreign key (student_id) references student (ID) on delete set null
);

create table orders
(
    ID             int auto_increment,
    transaction_id int,
    item_id        int,
    price          int unsigned NOT NULL,
    quantity       int unsigned NOT NULL,
    primary key (ID),
    foreign key (transaction_id) references transactions (ID) on delete cascade,
    foreign key (item_id) references items (ID) on delete set null
);


delimiter //


-- define a procedure to get the total amount spent by a student
create procedure get_total_amount_spent_by_student(IN student_id varchar (50), OUT total_amount_spent int)
begin
select sum(total_amount)
into total_amount_spent
from transactions
where transactions.student_id = student_id;
end //


-- procedure to check total amount earned by a vendor

create procedure get_total_amount_earned_by_vendor(IN vendor_id varchar (50), OUT total_amount_earned int)
begin
select sum(total_amount)
into total_amount_earned
from transactions
where transactions.vendor_id = vendor_id;
end //


-- procedure to get all the transactions made by a vendor

create procedure get_all_transactions_made_by_vendor(IN vendor_id varchar (50))
begin
    -- transaction_id, student_id, s_name, total_amount, date_time
select transactions.ID, transactions.student_id, student.s_name, transactions.total_amount, transactions.date_time
from transactions
            join student on transactions.student_id = student.ID
    where transactions.vendor_id = vendor_id;
end //


-- procedure to get all the items sold by a vendor

create procedure get_all_items_sold_by_vendor(IN vendor_id varchar (50))
begin
select *
from items
where items.vendor_id = vendor_id;
end //


-- procedure to add an item to the menu of a vendor

create procedure add_item_to_menu(IN item_name varchar (50), IN price int, IN vendor_id varchar (50))
begin
insert into items (item_name, price, vendor_id)
values (item_name, price, vendor_id);
end //


-- procedure to a new order

create procedure make_order(IN transaction_id int, IN item_id int, IN price int, IN quantity int)
begin
insert into orders (transaction_id, item_id, price, quantity)
values (transaction_id, item_id, price, quantity);
end //


-- procedure to a new transaction

create procedure make_transaction(IN vendor_id varchar (50), IN student_id varchar (50), IN total_amount int,
                                  OUT status int)
begin
    -- check if student has enough budget
    declare
budget_left int;
start transaction;
call get_budget_left(student_id, budget_left);
if
budget_left < total_amount then
        set status = 0;
rollback;
else
        set status = 1;
        -- insert into transactions
insert into transactions (vendor_id, student_id, total_amount, date_time)
values (vendor_id, student_id, total_amount, now());
end if;
commit;
end //


-- procedure to update student details

create procedure update_student_details(IN ID varchar (50), IN account_no varchar (50), IN s_name varchar (50),
                                        IN contact char (10), IN password varchar (256))
begin
start transaction;
update student
set account_no = account_no,
    s_name     = s_name,
    contact    = contact
where student.ID = ID;
update login
set password = password
where login.ID = ID;
commit;
end //


-- procedure to update vendor details

create procedure update_vendor_details(IN ID varchar (50), IN v_name varchar (50), IN account_no varchar (50),
                                       IN contact char (10), IN password varchar (256))
begin
start transaction;
update vendors
set v_name     = v_name,
    account_no = account_no,
    contact    = contact
where vendors.ID = ID;
update login
set password = password
where login.ID = ID;
commit;
end //


-- procedure to update item details

create procedure update_item_details(IN ID int, IN item_name varchar (50), IN price int)
begin
update items
set item_name = item_name,
    price     = price
where items.ID = ID;
end //



create procedure get_all_payments_made_by_student(IN student_id varchar (50))
begin
select transactions.id, vendors.v_name, transactions.total_amount, transactions.date_time
from transactions
            join vendors on transactions.vendor_id = vendors.ID
    where transactions.student_id = student_id;
end //


-- procedure to get all the students

create procedure get_all_students()
begin
select *
from student;
end //


-- procedure to get all the vendors

create procedure get_all_vendors()
begin
select *
from vendors;
end //


-- procedure to get all transactions

create procedure get_all_transactions()
begin
select *
from transactions;
end //


-- procedure to get all orders of a transaction

create procedure get_all_orders_of_transaction(IN transaction_id int)
begin
    -- item_id, item_name, price, quantity
select items.ID, items.item_name, items.price, orders.quantity
from orders
            join items on orders.item_id = items.ID
    where orders.transaction_id = transaction_id;
end //


-- procedure to get student details

create procedure get_student_details(IN ID varchar (50))
begin
select *
from student
where student.ID = ID;
end //


-- procedure to get vendor details

create procedure get_vendor_details(IN ID varchar (50))
begin
select *
from vendors
where vendors.ID = ID;
end //


-- procedure to get item details

create procedure get_item_details(IN ID int)
begin
select *
from items
where items.ID = ID;
end //


-- procedure to verify login

create procedure verify_login(IN ID varchar (50), IN password varchar (256), IN role int)
begin
select *
from login
where login.ID = ID
  and login.password = password
  and login.role = role;
end //


-- procedure to register a vendor

create procedure register_vendor(IN ID varchar (50), IN v_name varchar (50), IN account_no varchar (50),
                                 IN contact char (10), IN password varchar (256))
begin
start transaction;
insert into vendors (ID, v_name, account_no, contact)
values (ID, v_name, account_no, contact);
insert into login (ID, password, role)
values (ID, password, 1);
commit;
end //


-- procedure wrapped in a transaction to register a student

create procedure register_student(IN ID varchar (50), IN account_no varchar (50), IN s_name varchar (50),
                                  IN contact char (10), IN password varchar (256))
begin
start transaction;
insert into student (ID, account_no, s_name, contact)
values (ID, account_no, s_name, contact);
insert into login (ID, password, role)
values (ID, password, 0);
commit;
end //



-- procedure to register an admin

create procedure register_admin(IN ID varchar (50), IN password varchar (256))
begin
insert into login (ID, password, role)
values (ID, password, 2);
end //


-- procedure to delete an item

create procedure delete_item(IN ID int)
begin
    -- set vendor_id of item null
update items
set vendor_id = null
where items.ID = ID;
end //


-- procedure to delete a transaction

create procedure delete_transaction(IN ID int)
begin
delete
from transactions
where transactions.ID = ID;
end //


-- procedure to get most popular item of a vendor

create procedure get_most_popular_item_of_vendor(IN vendor_id varchar (50), OUT ID int, OUT item_name varchar (50))
begin
select items.ID, items.item_name
into ID, item_name
from items
            join orders on items.ID = orders.item_id
    where items.vendor_id = vendor_id
group by items.ID
order by sum(orders.quantity) desc limit 1;
end //


-- procedure to get most bought item by a student

create procedure get_most_bought_item_by_student(IN student_id varchar (50), OUT ID int, OUT item_name varchar (50),
                                                 OUT v_name varchar (50))
begin
select items.ID, items.item_name, vendors.v_name
into ID, item_name, v_name
from items join orders on items.ID = orders.item_id
          join transactions on orders.transaction_id = transactions.ID
          join vendors on items.vendor_id = vendors.ID
where transactions.student_id = student_id
group by items.ID
order by sum(orders.quantity) desc limit 1;
end //


-- UPDATE transactions SET vendor_id = ?, student_id = ?, total_amount = ?, date_time = ? WHERE ID = ?

create procedure update_transaction(IN vendor_id varchar (50), IN student_id varchar (50), IN total_amount int,
                                    IN date_time datetime, IN ID int)
begin
start transaction;
update transactions
set vendor_id    = vendor_id,
    student_id   = student_id,
    total_amount = total_amount,
    date_time    = date_time
where transactions.ID = ID;
commit;
end //


-- procedure to set monthly budget of a student

create procedure set_monthly_budget(IN ID varchar (50), IN budget int)
begin
update student
set budget = budget
where student.ID = ID;
end //


-- procedure to get monthly budget of a student

create procedure get_monthly_budget(IN ID varchar (50), OUT monthly_budget int)
begin
    -- select budget into budget from student where student.ID = ID;
select budget
into monthly_budget
from student
where student.ID = ID;
end //


-- procedure to get total amount spent by a student in the last month

create procedure get_total_amount_spent_by_student_last_month(IN student_id varchar (50), OUT total_amount_spent int)
begin
select sum(total_amount)
into total_amount_spent
from transactions
where transactions.student_id = student_id
  and transactions.date_time >= date_sub(now(), interval 1 month);
set
total_amount_spent = ifnull(total_amount_spent, 0);
end //


-- procedure to get budget left of a student

create procedure get_budget_left(IN ID varchar (50), OUT budget_left int)
begin
    -- get total amount spent by student in the last month
    declare
total_amount_spent int;
    -- get budget of student
    declare
budget int;
call get_total_amount_spent_by_student_last_month(ID, total_amount_spent);
call get_monthly_budget(ID, budget);
set
budget_left = budget - total_amount_spent;
end //


delimiter ;
