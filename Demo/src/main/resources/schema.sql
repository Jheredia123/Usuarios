CREATE SCHEMA IF NOT EXISTS "C:/Users/JHeredia/test";


CREATE TABLE IF NOT EXISTS "User" (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  created TIMESTAMP,
  modified TIMESTAMP,
  last_login TIMESTAMP,
  token VARCHAR(255),
  is_active BOOLEAN
);



CREATE TABLE IF NOT EXISTS phone (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(255) NOT NULL,
    city_code VARCHAR(255),
    country_code VARCHAR(255),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES "User"(id)
);
