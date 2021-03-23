DROP USER IF EXISTS useruser;
DROP DATABASE IF EXISTS web_prac;

CREATE DATABASE web_prac;
CREATE USER useruser WITH ENCRYPTED PASSWORD 'password';
GRANT ALL ON DATABASE web_prac TO useruser;