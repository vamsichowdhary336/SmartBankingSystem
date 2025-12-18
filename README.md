# Smart Banking System

A full-stack Smart Banking System built using Java Spring Boot (REST APIs) and HTML, CSS, JavaScript, designed to simulate real-world banking operations with secure authentication, session management, and Redis-based caching.

## 🚀 Features

- User Registration with OTP Verification  
- Secure Login & Logout (Session-based authentication)  
- Session management using **Redis**  
- User Dashboard with account details  
- Fund Transfer between accounts  
- View Account Statement / Transaction History  
- Loan Application module  
- Input validation and proper error handling  
- RESTful APIs tested using Postman

 ## 🛠️ Tech Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- **Redis (for session storage & caching)**
- REST APIs

### Frontend
- HTML
- CSS
- JavaScript
  
### Tools
- Postman
- GitHub
- Maven

## ⚡ Redis Usage
- Stores HTTP session data for authenticated users
- Improves performance by reducing database calls
- Enables scalable session management for multiple users
- Helps maintain session persistence across requests

## 📁 Project Structure
- `controller` – REST API endpoints
- `service` – Business logic
- `repository` – Database operations
- `model` – Entity classes
- `frontend` – HTML, CSS, JS files

## 🔐 Security
- Session-based authentication with **Redis-backed sessions**
- Unauthorized access handling
- Input validation on both frontend & backend

## 🎯 Purpose
Built to gain hands-on experience with **Spring Boot REST APIs**,  
**Redis integration**, and **full-stack banking workflows**.


