create table product (
    id long GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(255),
    description varchar(255),
    price decimal(10, 2)
);

create table customer (
    id long GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(255),
    email varchar(255)
);

create table orders (
    id long not null primary key auto_increment,
    customer_id long not null
);

create table order_item (
    orders long references orders(id),
    product_id long not null,
    quantity int not null,
    price decimal(10, 2) not null
);

insert into product (name, description, price) values ('Product 1', 'Description 1', 100.00);
insert into customer (name, email) values ('bilbo baggins', 'b.baggins@theshire.com');
insert into orders (customer_id) values (1);
insert into order_item (orders, product_id, quantity, price) values (1, 1, 1, 100.00);