create table product (
    id long GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(255),
    price decimal(10, 2)
);

create table customer (
    id long GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(255),
    email varchar(255)
);

create table orders (
    id long not null primary key auto_increment,
    customerId long not null,
    foreign key (customerId) references customer(id)
);

create table order_item (
    id long not null primary key auto_increment,
    productId long not null,
    quantity int not null,
    price decimal(10, 2) not null,
    orderId long not null,
    foreign key (productId) references product(id),
    foreign key (orderId) references orders(id)
);