CREATE DATABASE IF NOT EXISTS guacamole_db;
GRANT ALL ON guacamole_db.* TO 'guacamole_user'@'%';
FLUSH PRIVILEGES;