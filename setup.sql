-- =============================================
-- RMS Database Setup
-- Run this file in phpMyAdmin before starting
-- Steps:
-- 1. Open phpMyAdmin on your machine
-- 2. Click SQL tab
-- 3. Copy and paste everything in this file
-- 4. Click Go
-- 5. Use admin/1234 to login for now
-- =============================================
CREATE DATABASE IF NOT EXISTS rms;
USE rms;

CREATE USER IF NOT EXISTS 'rms_user'@'localhost' IDENTIFIED BY 'StrongPassword123';
GRANT SELECT, INSERT, UPDATE, DELETE ON rms.* TO 'rms_user'@'localhost';
FLUSH PRIVILEGES;


CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    passwordHash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS reservations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customerName VARCHAR(100) NOT NULL,
    date DATE NOT NULL,
    tableNumber INT NOT NULL
    );

CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(20) NOT NULL,
    totalAmount DOUBLE NOT NULL
    );

CREATE TABLE IF NOT EXISTS order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orderId INT NOT NULL,
    itemName VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL
    );

CREATE TABLE IF NOT EXISTS menu (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL
    );

CREATE TABLE IF NOT EXISTS inventory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    itemName VARCHAR(100) NOT NULL,
    stockLevel INT NOT NULL
    );

CREATE TABLE IF NOT EXISTS payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orderId INT NOT NULL,
    amount DOUBLE NOT NULL,
    paymentDate DATE NOT NULL
);
INSERT INTO users (username, passwordHash, role, firstName, lastName) VALUES
    ('admin', '$2a$12$bRvfVSp5ckFg7hlJVEWk3.e.L8LnKXZNRzleUFxbVmp8lIMN0klm2', 'admin', 'Admin', 'User'),
    ('manager1', '$2a$12$xlCU.5tyz9IowAxxddq8Te7DCERFPmny3o6pPiFMw97QmjR6TgYBq', 'manager', 'Sara', 'Ali'),
    ('waiter1', '$2a$12$xlCU.5tyz9IowAxxddq8Te7DCERFPmny3o6pPiFMw97QmjR6TgYBq', 'waiter', 'Omar', 'Hassan'),
    ('cashier1', '$2a$12$xlCU.5tyz9IowAxxddq8Te7DCERFPmny3o6pPiFMw97QmjR6TgYBq', 'cashier', 'Fatima', 'Said'),
    ('chef1', '$2a$12$xlCU.5tyz9IowAxxddq8Te7DCERFPmny3o6pPiFMw97QmjR6TgYBq', 'chef', 'Khalid', 'Mohammed'),
    ('invmanager1', '$2a$12$xlCU.5tyz9IowAxxddq8Te7DCERFPmny3o6pPiFMw97QmjR6TgYBq', 'inventorymanager', 'Layla', 'Ahmed');

-- Test inventory items
INSERT INTO inventory (itemName, stockLevel) VALUES
    ('Tomatoes', 100),
    ('Chicken', 50),
    ('Rice', 200),
    ('Olive Oil', 30),
    ('Cheese', 75);

-- Test payments
INSERT INTO payments (orderId, amount, paymentDate) VALUES
    (1, 25.50, CURDATE()),
    (2, 42.00, CURDATE()),
    (3, 18.75, CURDATE());