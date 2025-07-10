-- spmple SQL script --
CREATE DATABASE IF NOT EXISTS mydb;
USE mydb;

CREATE TABLE IF NOT EXISTS users (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(50),
    email VARCHAR(100)
    );

GRANT ALL PRIVILEGES ON mydb.* TO 'eatgoorm'@'%' IDENTIFIED BY 'eatgoorm';
FLUSH PRIVILEGES;