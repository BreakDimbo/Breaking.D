CREATE TABLE user
(
  id int PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(20),
  password VARCHAR(20),
  phone int,
  addr VARCHAR(200),
  rdate DATETIME
);

create table category
(
  id int primary key auto_increment,
  pid int, #pid为0的是最顶层节点
  name varchar(255),
  descr varchar(255),
  cno int, #最多三层, 每层占两位, 最多99个子节点
  grade int #代表级别, 从1开始
);

create table product
(
  id int primary key auto_increment,
  name varchar(255),
  descr varchar(255),
  normalPrice double,
  memberPrice double,
  pdate datetime,
  categoryid int references catetory(id)
);

create table salesorder
(
  id int primary key auto_increment,
  userid int,
  addr varchar(255),
  odate datetime,
  status int
);

create table salesitem
(
  id int primary key auto_increment,
  productid int,
  unitprice double,
  pcount int,
  orderid int
);