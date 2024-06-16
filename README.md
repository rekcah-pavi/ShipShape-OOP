
# ShipShape OOP Project

## Overview

ShipShape is a maritime maintenance and supply company that provides ship repair services, sells marine spare parts, and offers ship repainting services. This project automates ShipShape’s manual processes through a Java Swing application.

## Features

# Project Name

## Features

1. **Manage Customer Orders:** Add, update, and remove orders for repairs, repainting, and spare parts.
   - **SQL Table:** `orders`
   - Columns: `order_id`, `item_id`, `order_status`, `customer_id`

2. **Manage Customers:** Track and maintain customer information.
   - **SQL Table:** `customers`
   - Columns: `customer_id`, `name`, `email`, `city`

3. **Manage Suppliers:** Track suppliers and maintain contact information.
   - **SQL Table:** `suppliers`
   - Columns: `supplier_id`, `supplier_name`, `contact_info`

4. **Manage Inventory:** Track stock levels, manage inventory items, and predict low stock.
   - **SQL Table:** `inventory`
   - Columns: `item_id`, `item_name`, `quantity`, `SupplierId`

5. **Manage Employees:** Track employee information and schedules.
   - **SQL Table:** `employees`
   - Columns: `employee_id`, `employee_name`, `job_role`, `schedule`, `work_location`, `skills`, `email`

6. **Allocate Employees to Jobs:** Schedule employees based on skills and availability.
   - **SQL Table:** `employees`
   - Columns: `employee_id`, `skills`, `schedule`

7. **Monthly Sales Reports:** Generate reports on sales trends and top-selling items.
   - **SQL Table:** `orders`
   - Columns: `order_id`, `item_id`, `order_status`

8. **Customer Notifications:** Notify customers when their order (e.g., repairs, repainting) is ready for collection.
   - **SQL Table:** `customers`
   - Columns: `customer_id`, `email`

9. **Employee Notifications:** Notify employees when they are assigned a new job.
   - **SQL Table:** `employees`
   - Columns: `employee_id`, `email`


## Setup Instructions

### Prerequisites

- Java Development Kit (JDK)
- Eclipse IDE
- MySQL Database

### Installation

1. **Clone the repository:**
   ```sh
   git clone https://github.com/rekcah-pavi/ShipShape-OOP.git
   ```

2. **Open in Eclipse:**
   - Open Eclipse IDE.
   - Go to `File > Import > Existing Projects into Workspace`.
   - Select the cloned repository.

3. **Configure Database Connection:**
   - Open `sql_connecter.java`.
   - Update the following fields with your database details:
     ```java
     private static final String JDBC_URL = "your_database_url";
     private static final String USERNAME = "your_username";
     private static final String PASSWORD = "your_password";
     private static final String DATABASE_NAME = "your_database_name";
     ```

4. **Configure Mail Sender:**
   - Open `Mail_sender.java`.
   - Update the following fields with your SMTP server details:
     ```java
     String smtp_host= "your_smtp_host";
     String smtp_port= "your_smtp_port";
     String mail_username= "your_mail_username";
     String mail_password= "your_mail_password";
     ```

5. **Run the Application:**
   - Right-click on the project in Eclipse.
   - Select `Run As > Java Application`.

## Preview

<table>
  <tr>
    <td><img src="https://github.com/rekcah-pavi/ShipShape-OOP/blob/main/photos/Screenshot%20from%202024-06-16%2010-41-00.png?raw=true" width="200"/></td>
    <td><img src="https://github.com/rekcah-pavi/ShipShape-OOP/blob/main/photos/Screenshot%20from%202024-06-16%2010-41-14.png?raw=true" width="200"/></td>
  </tr>
  <tr>
    <td><img src="https://github.com/rekcah-pavi/ShipShape-OOP/blob/main/photos/Screenshot%20from%202024-06-16%2010-41-22.png?raw=true" width="200"/></td>
    <td><img src="https://github.com/rekcah-pavi/ShipShape-OOP/blob/main/photos/Screenshot%20from%202024-06-16%2010-41-30.png?raw=true" width="200"/></td>
  </tr>
  <tr>
    <td><img src="https://github.com/rekcah-pavi/ShipShape-OOP/blob/main/photos/Screenshot%20from%202024-06-16%2010-42-17.png?raw=true" width="200"/></td>
    <td><img src="https://github.com/rekcah-pavi/ShipShape-OOP/blob/main/photos/Screenshot%20from%202024-06-16%2010-42-25.png?raw=true" width="200"/></td>
  </tr>
</table>

## Project Specification

For detailed project specifications and requirements, refer to the project document [here](https://github.com/rekcah-pavi/ShipShape-OOP/blob/main/docs/OOP%20Project%202024.pdf).

## Contribution

To contribute to this project, please fork the repository, create a new branch, and submit a pull request. Ensure your code follows the project's coding standards and includes appropriate documentation.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
