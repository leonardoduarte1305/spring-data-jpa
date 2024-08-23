-- Using 'docker run -d --name mysql-docker -e MYSQL_ROOT_PASSWORD=senhaSegura -p 3306:3306 mysql:8.0'
-- MySQL1 Datasource
DROP DATABASE IF EXISTS datasourcemysql1;
DROP USER IF EXISTS `mysql1-admin`@`%`;
DROP USER IF EXISTS `mysql1-user`@`%`;

CREATE DATABASE IF NOT EXISTS datasourcemysql1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS `mysql1-admin`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `datasourcemysql1`.* TO `mysql1-admin`@`%`;

CREATE USER IF NOT EXISTS `mysql1-user`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `datasourcemysql1`.* TO `mysql1-user`@`%`;

FLUSH PRIVILEGES;

-- MySQL2 Datasource
DROP DATABASE IF EXISTS datasourcemysql2;
DROP USER IF EXISTS `mysql2-admin`@`%`;
DROP USER IF EXISTS `mysql2-user`@`%`;

CREATE DATABASE IF NOT EXISTS datasourcemysql2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS `mysql2-admin`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `datasourcemysql2`.* TO `mysql2-admin`@`%`;

CREATE USER IF NOT EXISTS `mysql2-user`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `datasourcemysql2`.* TO `mysql2-user`@`%`;

FLUSH PRIVILEGES;

-- MySQL3 Datasource
DROP DATABASE IF EXISTS datasourcemysql3;
DROP USER IF EXISTS `mysql3-admin`@`%`;
DROP USER IF EXISTS `mysql3-user`@`%`;

CREATE DATABASE IF NOT EXISTS datasourcemysql3 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS `mysql3-admin`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `datasourcemysql3`.* TO `mysql3-admin`@`%`;

CREATE USER IF NOT EXISTS `mysql3-user`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `datasourcemysql3`.* TO `mysql3-user`@`%`;

FLUSH PRIVILEGES;
