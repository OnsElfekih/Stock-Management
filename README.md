# Stock Management Platform

This Stock Management Platform is a robust application designed to manage users and products within a company efficiently. It allows different roles, including **Administrator**, **Manager**, and **Employee**, to manage products and handle stock-related operations. The platform is developed using **Java** for both the frontend and backend, **JavaFx** for user interfaces, and **MySQL** for database management.

## Features

### Roles and Permissions
- **Administrator:**
  - Manages users (add, edit, delete, search).
  - Views and manages product lists.
  - Access to all platform functionalities.
  - **Identifiants par défaut** :  
    - Nom d'utilisateur : `admin`  
    - Mot de passe : `adminadmin`  
  
- **Manager:**
  - Manages products (add, update, delete, and search products).
  - Views stock and product details.
  - Limited access compared to the Administrator but more functionality than the Employee.

- **Employee:**
  - Limited functionality.
  - Views products and searches the stock list.
  - No management access to products.

### Core Features
- **User Management:** The Administrator can manage user accounts and assign roles.
- **Product Management:** Managers can manage product listings, including adding, updating, and deleting products.
- **Stock Management:** Employees and Managers can view product stocks and search for products.
- **MySQL Database Integration:** The application uses a MySQL database for storing and retrieving user and product information.

## Tech Stack
- **Java (Frontend & Backend):** The frontend is developed using manual Java interfaces, and the backend logic is implemented using Java.
- **JavaFX:** JavaFX is used for building the graphical user interface.
- **MySQL:** MySQL is used for database management and storing product and user data.

## Installation

### Prerequisites:
1. Install **Java** (Java 8 or later).
2. Install **PHP** (if applicable for the web services).
3. Install **MySQL** server and set up a database.
4. Set up a web server (Apache/Nginx for PHP application).
5. Ensure you have all required Java libraries and dependencies installed.

### Steps:
1. Clone or download the repository.
2.Open the project
3.Build and run the application

## Usage

1. **Administrator:**
   - Log in as an Administrator.
   - Manage users and their roles.
   - View and manage product listings.

2. **Manager:**
   - Log in as a Manager.
   - Add, update, or delete products.
   - View product stocks.

3. **Employee:**
   - Log in as an Employee.
   - View available products and check stock levels.
   - No permissions to modify products.


