/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014/7/14 15:05:28                           */
/*==============================================================*/


drop database if exists wuxianhui001;

create database wuxianhui001;

drop table if exists GoodsManage;

drop table if exists GoodsType;

drop table if exists Menu;

drop table if exists MenuUser;

drop table if exists OrderDetail;

drop table if exists OrderManage;

drop table if exists TableManage;

drop table if exists User;

drop table if exists UserVisitingSystemHistory;

drop table if exists UserWifiVisitingHistory;

drop table if exists WSPUser;

drop table if exists WSPUserVisitHistory;

drop table if exists WifiManage;

/*==============================================================*/
/* Table: GoodsManage                                           */
/*==============================================================*/
create table GoodsManage
(
   GoodsID              bigint not null auto_increment,
   DetailID             bigint,
   GoodsType            char(10),
   WSPUser              bigint,
   GoodsName            char(20),
   Unit                 char(10),
   Price                numeric(8,0),
   Description          text,
   Picture              char(100),
   ConsumedTimes        int,
   primary key (GoodsID)
);

/*==============================================================*/
/* Table: GoodsType                                             */
/*==============================================================*/
create table GoodsType
(
   GoodsType            char(10) not null,
   GoodsTypeDescribe    text,
   Extends              text,
   primary key (GoodsType)
);

/*==============================================================*/
/* Table: Menu                                                  */
/*==============================================================*/
create table Menu
(
   MenuID               bigint not null auto_increment,
   MenuName             char(20) not null,
   Level                bigint not null,
   ParentID             bigint,
   isLeaf               bool,
   primary key (MenuID)
);

alter table Menu comment 'ParentID属于MenuID集合';

/*==============================================================*/
/* Table: MenuUser                                              */
/*==============================================================*/
create table MenuUser
(
   WSPUser              bigint not null auto_increment,
   MenuID               bigint not null,
   primary key (WSPUser, MenuID)
);

/*==============================================================*/
/* Table: OrderDetail                                           */
/*==============================================================*/
create table OrderDetail
(
   DetailID             bigint not null auto_increment,
   OrderId              bigint,
   GoodsID              bigint,
   WSPUser              bigint,
   Number               int not null,
   primary key (DetailID)
);

/*==============================================================*/
/* Table: OrderManage                                           */
/*==============================================================*/
create table OrderManage
(
   OrderId              bigint not null auto_increment,
   WSPUser              bigint,
   UserID               bigint,
   TableID              bigint,
   OrderDate            date,
   OrderTime            time,
   Expenditure          float(8,2),
   Status               char(10),
   primary key (OrderId)
);

/*==============================================================*/
/* Table: TableManage                                           */
/*==============================================================*/
create table TableManage
(
   TableID              bigint not null auto_increment,
   WSPUser              bigint,
   OrderId              bigint,
   GoodsTypeDescribe    text,
   Extends              text,
   primary key (TableID)
);

/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
create table User
(
   UserID               bigint not null auto_increment,
   PhoneNumber          char(11) not null unique,
   QQNumber             bigint,
   UserName             char(30),
   RegistrateDate       date,
   RegistrateTime       time,
   Password             char(20),
   primary key (UserID)
);

alter table User comment '消费者用户';

/*==============================================================*/
/* Table: UserVisitingSystemHistory                             */
/*==============================================================*/
create table UserVisitingSystemHistory
(
   UserVisitingID       bigint not null auto_increment,
   UserID               bigint,
   WSPVisitingDate      date,
   WSPVisitingTime      time,
   Country              char(20),
   Province             char(20),
   City                 char(20),
   District             char(20),
   primary key (UserVisitingID)
);

/*==============================================================*/
/* Table: UserWifiVisitingHistory                               */
/*==============================================================*/
create table UserWifiVisitingHistory
(
   WifiVisitingID       bigint not null auto_increment,
   UserID               bigint,
   WifiID               bigint,
   AuthorizedResult     bool,
   WifiVisitingDate     date,
   StartTime            time,
   EndTime              time,
   primary key (WifiVisitingID)
);

/*==============================================================*/
/* Table: WSPUser                                               */
/*==============================================================*/
create table WSPUser
(
   WSPUser              bigint not null auto_increment,
   PhoneNumber          char(11) not null,
   QQNumber             bigint,
   UserName             char(30),
   RegistrateDate       date,
   RegistrateTime       time,
   Email                text,
   Country              char(20),
   Province             char(20),
   City                 char(20),
   District             char(20),
   DetailAddress        text,
   Password             char(20),
   Showinfo             char(100),
   primary key (WSPUser)
);

alter table WSPUser comment '无线服务提供者用户';

/*==============================================================*/
/* Table: WSPUserVisitHistory                                   */
/*==============================================================*/
create table WSPUserVisitHistory
(
   WSPVisitingID        bigint not null auto_increment,
   WSPUser              bigint,
   WSPVisitingDate      date,
   WSPVisitingTime      time,
   Country              char(20),
   Province             char(20),
   City                 char(20),
   District             char(20),
   primary key (WSPVisitingID)
);

/*==============================================================*/
/* Table: WifiManage                                            */
/*==============================================================*/
create table WifiManage
(
   WifiID               bigint not null auto_increment,
   WSPUser              bigint,
   WifiName             char(30),
   MACAddress           char(30),
   Password             char(20),
   Authorise            bool,
   DisplayName          char(30),
   primary key (WifiID)
);

alter table GoodsManage add constraint FK_HavingGoods foreign key (WSPUser)
      references WSPUser (WSPUser) on delete restrict on update restrict;

alter table GoodsManage add constraint FK_HavingType foreign key (GoodsType)
      references GoodsType (GoodsType) on delete restrict on update restrict;

alter table GoodsManage add constraint FK_OrderDetailGoods2 foreign key (DetailID)
      references OrderDetail (DetailID) on delete restrict on update restrict;

alter table MenuUser add constraint FK_MenuUser foreign key (WSPUser)
      references WSPUser (WSPUser) on delete restrict on update restrict;

alter table MenuUser add constraint FK_MenuUser2 foreign key (MenuID)
      references Menu (MenuID) on delete restrict on update restrict;

alter table OrderDetail add constraint FK_OrderDetail foreign key (OrderId)
      references OrderManage (OrderId) on delete restrict on update restrict;

alter table OrderDetail add constraint FK_OrderDetailGoods foreign key (GoodsID)
      references GoodsManage (GoodsID) on delete restrict on update restrict;

alter table OrderDetail add constraint FK_Relationship_15 foreign key (WSPUser)
      references WSPUser (WSPUser) on delete restrict on update restrict;

alter table OrderManage add constraint FK_OrderTable foreign key (TableID)
      references TableManage (TableID) on delete restrict on update restrict;

alter table OrderManage add constraint FK_UserOrder foreign key (UserID)
      references User (UserID) on delete restrict on update restrict;

alter table OrderManage add constraint FK_WSPOrder foreign key (WSPUser)
      references WSPUser (WSPUser) on delete restrict on update restrict;

alter table TableManage add constraint FK_HavingTable foreign key (WSPUser)
      references WSPUser (WSPUser) on delete restrict on update restrict;

alter table TableManage add constraint FK_OrderTable2 foreign key (OrderId)
      references OrderManage (OrderId) on delete restrict on update restrict;

alter table UserVisitingSystemHistory add constraint FK_UserVisitingSystem foreign key (UserID)
      references User (UserID) on delete restrict on update restrict;

alter table UserWifiVisitingHistory add constraint FK_WifiUsing foreign key (WifiID)
      references WifiManage (WifiID) on delete restrict on update restrict;

alter table UserWifiVisitingHistory add constraint FK_WifiVisiting foreign key (UserID)
      references User (UserID) on delete restrict on update restrict;

alter table WSPUserVisitHistory add constraint FK_WSPVisiting foreign key (WSPUser)
      references WSPUser (WSPUser) on delete restrict on update restrict;

alter table WifiManage add constraint FK_HavingWifi foreign key (WSPUser)
      references WSPUser (WSPUser) on delete restrict on update restrict;

