CREATE USER 'alex_ryzhevich'@'localhost' IDENTIFIED BY 'password';
GRANT SELECT ON alexandra_ryzhevich_db.* TO 'alex_ryzhevich'@'localhost';
GRANT DELETE ON alexandra_ryzhevich_db.* TO 'alex_ryzhevich'@'localhost';
GRANT UPDATE ON alexandra_ryzhevich_db.* TO 'alex_ryzhevich'@'localhost';
GRANT INSERT ON alexandra_ryzhevich_db.* TO 'alex_ryzhevich'@'localhost';
FLUSH PRIVILEGES;