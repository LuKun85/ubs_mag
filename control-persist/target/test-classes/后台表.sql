--1. 创建系统资源表
    create table TBL_BTS_SYS_FUNCTION
    (
      func_id              VARCHAR2(20) not null,
      func_name            VARCHAR2(20),
      func_father_id       VARCHAR2(20),
      func_desc            VARCHAR2(100),
      func_remark          VARCHAR2(255),
      func_disable_tag     CHAR(1) default 1,
      func_create_by       VARCHAR2(20),
      func_create_datetime TIMESTAMP(6),
      func_update_by       VARCHAR2(20),
      func_update_datetime TIMESTAMP(6),
      func_tag             CHAR(1) default 0,
      func_level           CHAR(1) default 4,
      func_url             VARCHAR2(255),
      func_icon            VARCHAR2(20),
      func_priority        NUMBER default 99 not null
    );
    comment on column TBL_BTS_SYS_FUNCTION.func_disable_tag  is '0-禁用 1-启用';
    comment on column TBL_BTS_SYS_FUNCTION.func_tag  is '0-菜单 1-资源';
    comment on column TBL_BTS_SYS_FUNCTION.func_level  is '1-一级菜单 2-二级菜单 3-三级菜单 4-资源';
    comment on column TBL_BTS_SYS_FUNCTION.func_url  is '功能链接';
    comment on column TBL_BTS_SYS_FUNCTION.func_icon  is '功能图标';
    comment on column TBL_BTS_SYS_FUNCTION.func_priority  is '优先级（值越小优先级越高）';
    alter table TBL_BTS_SYS_FUNCTION add primary key (FUNC_ID)  using index ;

--2. 创建系统角色表
    create table TBL_BTS_SYS_ROLE
    (
      role_id                  VARCHAR2(20) not null,
      role_name                VARCHAR2(50) not null,
      role_disable_tag         CHAR(1) default 1,
      role_remark              VARCHAR2(255),
      role_create_by           VARCHAR2(20),
      role_create_datetime     TIMESTAMP(6),
      role_updatetime_by       VARCHAR2(20),
      role_updatetime_datetime TIMESTAMP(6)
    )
    ;
    comment on column TBL_BTS_SYS_ROLE.role_disable_tag  is '0-禁用 1-启用';
    create unique index ROLE_NAME_UK on TBL_BTS_SYS_ROLE (ROLE_NAME);
    alter table TBL_BTS_SYS_ROLE  add primary key (ROLE_ID)  using index ;
--3. 创建角色资源表
    create table TBL_BTS_SYS_ROLE_FUNC
    (
      role_id                   VARCHAR2(20) not null,
      func_id                   VARCHAR2(20) not null,
      role_func_remark          VARCHAR2(255),
      role_func_create_by       VARCHAR2(20),
      role_func_create_datetime TIMESTAMP(6),
      role_func_update_by       VARCHAR2(20),
      role_func_update_datetime TIMESTAMP(6)
    )
    ;
    alter table TBL_BTS_SYS_ROLE_FUNC  add primary key (ROLE_ID, FUNC_ID)  using index ;
--4. 创建用户表
    create table TBL_BTS_SYS_USR
    (
      usr_id          VARCHAR2(20) not null,
      usr_name        VARCHAR2(20) not null,
      usr_pwd         VARCHAR2(64) not null,
      usr_remark      VARCHAR2(255),
      usr_disable_tag CHAR(1) default 1 not null,
      usr_email       VARCHAR2(50),
      usr_create_by   VARCHAR2(20),
      usr_create_date TIMESTAMP(6),
      usr_update_by   VARCHAR2(20),
      usr_update_date TIMESTAMP(6),
      usr_type        VARCHAR2(24)
    );
    comment on column TBL_BTS_SYS_USR.usr_remark  is '用户备注';
    comment on column TBL_BTS_SYS_USR.usr_disable_tag  is '0-禁用 1-启用';
    comment on column TBL_BTS_SYS_USR.usr_email  is '用户邮箱';
    create unique index USR_NAME_UN on TBL_BTS_SYS_USR (USR_NAME);

    alter table TBL_BTS_SYS_USR  add primary key (USR_ID)  using index;
--5. 创建用户角色表
    create table TBL_BTS_SYS_USR_ROLE
    (
      usr_id                   VARCHAR2(20) not null,
      role_id                  VARCHAR2(20) not null,
      usr_role_remark          VARCHAR2(255),
      usr_role_create_by       VARCHAR2(20),
      usr_role_create_datetime TIMESTAMP(6),
      usr_role_update_by       VARCHAR2(20),
      usr_role_update_datetime TIMESTAMP(6)
    );
    alter table TBL_BTS_SYS_USR_ROLE  add primary key (USR_ID, ROLE_ID)  using index ;

 --一些序列(mysql 改成自动增加字段)
    create sequence TBL_MER_ROUTE_COUNT_ID_SEQ
      minvalue 1
      maxvalue 999999999999999
      start with 1
      increment by 1
      cache 20
      cycle;

      create sequence TBL_BTS_SYS_FUNCTION_ID_SEQ
      minvalue 1
      maxvalue 999999999999999
      start with 55
      increment by 1
      cache 20
      cycle;

      create sequence TBL_BTS_SYS_USR_ID_SEQ
      minvalue 1
      maxvalue 999999999999999
      start with 3
      increment by 1
      cache 20
      cycle;

      create sequence TBL_BTS_SYS_ROLE_ID_SEQ
      minvalue 1
      maxvalue 999999999999999
      start with 5
      increment by 1
      cache 20
      cycle;

      create sequence TBL_BTS_INST_ID_SEQ
      minvalue 1
      maxvalue 999999999999999
      start with 1
      increment by 1
      cache 20
      cycle;

      create sequence TBL_BTS_INST_CODE_SEQ
      minvalue 1
      maxvalue 9999999
      start with 1
      increment by 1
      cache 20
      cycle;

      create sequence TBL_INST_ROUTE_SEQ
      minvalue 1
      maxvalue 99999999
      start with 1
      increment by 1
      cache 20
      cycle;



--初始数据
insert into TBL_BTS_SYS_USR_ROLE (USR_ID, ROLE_ID, USR_ROLE_REMARK, USR_ROLE_CREATE_BY, USR_ROLE_CREATE_DATETIME, USR_ROLE_UPDATE_BY, USR_ROLE_UPDATE_DATETIME)
values ('1', '1', '管理员', null, null, null, null);
insert into TBL_BTS_SYS_USR (USR_ID, USR_NAME, USR_PWD, USR_REMARK, USR_DISABLE_TAG, USR_EMAIL, USR_CREATE_BY, USR_CREATE_DATE, USR_UPDATE_BY, USR_UPDATE_DATE, USR_TYPE)
values ('1', 'admin', 'nKaUqQKFwDRDLJVQQht7nb1cD0tmc/BfbbzlgFK6IOQkgEGVbujJouyfECkM3AeC', '管理员', '1', 'admin@77pay.com.cn', null, null, null, null, null);
insert into TBL_BTS_SYS_ROLE (ROLE_ID, ROLE_NAME, ROLE_DISABLE_TAG, ROLE_REMARK, ROLE_CREATE_BY, ROLE_CREATE_DATETIME, ROLE_UPDATETIME_BY, ROLE_UPDATETIME_DATETIME)
values ('1', '管理员', '1', '1', null, null, null, null);
insert into TBL_BTS_SYS_FUNCTION (FUNC_ID, FUNC_NAME, FUNC_FATHER_ID, FUNC_DESC, FUNC_REMARK, FUNC_DISABLE_TAG, FUNC_CREATE_BY, FUNC_CREATE_DATETIME, FUNC_UPDATE_BY, FUNC_UPDATE_DATETIME, FUNC_TAG, FUNC_LEVEL, FUNC_URL, FUNC_ICON, FUNC_PRIORITY)
values ('900', '用户维护', '90', '用户维护', '左菜单栏', '1', null, null, null, null, '0', '3', '/userManagement/query_all_user', 'table', 99);

insert into TBL_BTS_SYS_FUNCTION (FUNC_ID, FUNC_NAME, FUNC_FATHER_ID, FUNC_DESC, FUNC_REMARK, FUNC_DISABLE_TAG, FUNC_CREATE_BY, FUNC_CREATE_DATETIME, FUNC_UPDATE_BY, FUNC_UPDATE_DATETIME, FUNC_TAG, FUNC_LEVEL, FUNC_URL, FUNC_ICON, FUNC_PRIORITY)
values ('901', '角色维护', '90', '角色维护', '左菜单栏', '1', null, null, null, null, '0', '3', '/roleManagement/query_all_role', 'table', 99);

insert into TBL_BTS_SYS_FUNCTION (FUNC_ID, FUNC_NAME, FUNC_FATHER_ID, FUNC_DESC, FUNC_REMARK, FUNC_DISABLE_TAG, FUNC_CREATE_BY, FUNC_CREATE_DATETIME, FUNC_UPDATE_BY, FUNC_UPDATE_DATETIME, FUNC_TAG, FUNC_LEVEL, FUNC_URL, FUNC_ICON, FUNC_PRIORITY)
values ('902', '功能维护', '90', '功能维护', '左菜单栏', '1', null, null, null, null, '0', '3', '/funcManagement/query_all_func', 'table', 99);

insert into TBL_BTS_SYS_FUNCTION (FUNC_ID, FUNC_NAME, FUNC_FATHER_ID, FUNC_DESC, FUNC_REMARK, FUNC_DISABLE_TAG, FUNC_CREATE_BY, FUNC_CREATE_DATETIME, FUNC_UPDATE_BY, FUNC_UPDATE_DATETIME, FUNC_TAG, FUNC_LEVEL, FUNC_URL, FUNC_ICON, FUNC_PRIORITY)
values ('13', '系统管理', '8', '系统管理', null, '1', null, null, null, null, '0', '2', null, 'fa-credit-card', 99);

insert into TBL_BTS_SYS_FUNCTION (FUNC_ID, FUNC_NAME, FUNC_FATHER_ID, FUNC_DESC, FUNC_REMARK, FUNC_DISABLE_TAG, FUNC_CREATE_BY, FUNC_CREATE_DATETIME, FUNC_UPDATE_BY, FUNC_UPDATE_DATETIME, FUNC_TAG, FUNC_LEVEL, FUNC_URL, FUNC_ICON, FUNC_PRIORITY)
values ('90', '权限管理', '9', '系统基本管理', '左菜单栏', '1', null, null, null, null, '0', '2', null, null, 99);

insert into TBL_BTS_SYS_FUNCTION (FUNC_ID, FUNC_NAME, FUNC_FATHER_ID, FUNC_DESC, FUNC_REMARK, FUNC_DISABLE_TAG, FUNC_CREATE_BY, FUNC_CREATE_DATETIME, FUNC_UPDATE_BY, FUNC_UPDATE_DATETIME, FUNC_TAG, FUNC_LEVEL, FUNC_URL, FUNC_ICON, FUNC_PRIORITY)
values ('9', '系统管理', null, '系统管理', '标签栏', '1', null, null, null, null, '0', '1', null, null, 99);
insert into TBL_BTS_SYS_ROLE_FUNC (ROLE_ID, FUNC_ID, ROLE_FUNC_REMARK, ROLE_FUNC_CREATE_BY, ROLE_FUNC_CREATE_DATETIME, ROLE_FUNC_UPDATE_BY, ROLE_FUNC_UPDATE_DATETIME)
values ('1', '10', '用户维护', null, null, null, null);

insert into TBL_BTS_SYS_ROLE_FUNC (ROLE_ID, FUNC_ID, ROLE_FUNC_REMARK, ROLE_FUNC_CREATE_BY, ROLE_FUNC_CREATE_DATETIME, ROLE_FUNC_UPDATE_BY, ROLE_FUNC_UPDATE_DATETIME)
values ('1', '11', '角色维护', null, null, null, null);

insert into TBL_BTS_SYS_ROLE_FUNC (ROLE_ID, FUNC_ID, ROLE_FUNC_REMARK, ROLE_FUNC_CREATE_BY, ROLE_FUNC_CREATE_DATETIME, ROLE_FUNC_UPDATE_BY, ROLE_FUNC_UPDATE_DATETIME)
values ('1', '12', '功能维护', null, null, null, null);

insert into TBL_BTS_SYS_ROLE_FUNC (ROLE_ID, FUNC_ID, ROLE_FUNC_REMARK, ROLE_FUNC_CREATE_BY, ROLE_FUNC_CREATE_DATETIME, ROLE_FUNC_UPDATE_BY, ROLE_FUNC_UPDATE_DATETIME)
values ('1', '9', '用户管理', null, null, null, null);

insert into TBL_BTS_SYS_ROLE_FUNC (ROLE_ID, FUNC_ID, ROLE_FUNC_REMARK, ROLE_FUNC_CREATE_BY, ROLE_FUNC_CREATE_DATETIME, ROLE_FUNC_UPDATE_BY, ROLE_FUNC_UPDATE_DATETIME)
values ('1', '8', '123', null, null, null, null);

insert into TBL_BTS_SYS_ROLE_FUNC (ROLE_ID, FUNC_ID, ROLE_FUNC_REMARK, ROLE_FUNC_CREATE_BY, ROLE_FUNC_CREATE_DATETIME, ROLE_FUNC_UPDATE_BY, ROLE_FUNC_UPDATE_DATETIME)
values ('1', '21', null, null, null, null, null);

insert into TBL_BTS_SYS_ROLE_FUNC (ROLE_ID, FUNC_ID, ROLE_FUNC_REMARK, ROLE_FUNC_CREATE_BY, ROLE_FUNC_CREATE_DATETIME, ROLE_FUNC_UPDATE_BY, ROLE_FUNC_UPDATE_DATETIME)
values ('1', '14', null, null, null, null, null);

insert into TBL_BTS_SYS_ROLE_FUNC (ROLE_ID, FUNC_ID, ROLE_FUNC_REMARK, ROLE_FUNC_CREATE_BY, ROLE_FUNC_CREATE_DATETIME, ROLE_FUNC_UPDATE_BY, ROLE_FUNC_UPDATE_DATETIME)
values ('1', '34', null, null, null, null, null);

insert into TBL_BTS_SYS_ROLE_FUNC (ROLE_ID, FUNC_ID, ROLE_FUNC_REMARK, ROLE_FUNC_CREATE_BY, ROLE_FUNC_CREATE_DATETIME, ROLE_FUNC_UPDATE_BY, ROLE_FUNC_UPDATE_DATETIME)
values ('1', '35', null, null, null, null, null);

insert into TBL_BTS_SYS_ROLE_FUNC (ROLE_ID, FUNC_ID, ROLE_FUNC_REMARK, ROLE_FUNC_CREATE_BY, ROLE_FUNC_CREATE_DATETIME, ROLE_FUNC_UPDATE_BY, ROLE_FUNC_UPDATE_DATETIME)
values ('1', '900', '左菜单栏', null, null, null, null);

insert into TBL_BTS_SYS_ROLE_FUNC (ROLE_ID, FUNC_ID, ROLE_FUNC_REMARK, ROLE_FUNC_CREATE_BY, ROLE_FUNC_CREATE_DATETIME, ROLE_FUNC_UPDATE_BY, ROLE_FUNC_UPDATE_DATETIME)
values ('1', '901', '左菜单栏', null, null, null, null);

insert into TBL_BTS_SYS_ROLE_FUNC (ROLE_ID, FUNC_ID, ROLE_FUNC_REMARK, ROLE_FUNC_CREATE_BY, ROLE_FUNC_CREATE_DATETIME, ROLE_FUNC_UPDATE_BY, ROLE_FUNC_UPDATE_DATETIME)
values ('1', '902', '左菜单栏', null, null, null, null);

insert into TBL_BTS_SYS_ROLE_FUNC (ROLE_ID, FUNC_ID, ROLE_FUNC_REMARK, ROLE_FUNC_CREATE_BY, ROLE_FUNC_CREATE_DATETIME, ROLE_FUNC_UPDATE_BY, ROLE_FUNC_UPDATE_DATETIME)
values ('1', '13', null, null, null, null, null);

insert into TBL_BTS_SYS_ROLE_FUNC (ROLE_ID, FUNC_ID, ROLE_FUNC_REMARK, ROLE_FUNC_CREATE_BY, ROLE_FUNC_CREATE_DATETIME, ROLE_FUNC_UPDATE_BY, ROLE_FUNC_UPDATE_DATETIME)
values ('1', '90', '左菜单栏', null, null, null, null);
