# rent-system

### 创建数据库
```sql
create database rent DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
create user 'rent'@'%' identified by 'rent';
grant all privileges on rent.* to 'rent'@'%' with grant option;
flush privileges;
```