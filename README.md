# Inventory Management System


The Inventory Management System was developed as a Java desktop application with a GUI built using Swing and MySQL as the database. JDBC was used to manage the connectivity to the database.

The software code is divided into four packages:

1. DAO: Retrieves and modifies data from the database tables.

2. Model: Transfers data between the data access and the GUI.

3. Database: Retrieves the database connection using the MyJDBC class.

4. GUI: Contains all the classes that make up the software interface.


Application Preview


1. Login Page

The login page receives the user's entered credentials and verifies them with the database. If match, go to Main page, if not, show error message.

(To access the application, please run the loginGUI class and proceed to navigate to each page.)


<img width="808" alt="login" src="https://user-images.githubusercontent.com/114201053/232668242-ec05f89b-9349-4cf8-92a4-a3107f181968.png">


2. Main Page

The main page displays a menu with buttons for each section and a logout button.


<img width="805" alt="main" src="https://user-images.githubusercontent.com/114201053/232668224-f35d6b7b-b905-4480-ac96-6026e414e403.png">


3. Product

In the Product section, the user can view, add, edit, and delete products. Additionally, the user can search for products by their ID or name.

<img width="806" alt="product" src="https://user-images.githubusercontent.com/114201053/232668202-02814492-952b-4b42-b3db-b653f57a264f.png">


4. Customer

Customer section allows user to view, edit, search, add and delete any customers.

<img width="805" alt="customer" src="https://user-images.githubusercontent.com/114201053/232668109-4b9675bb-f6ff-4da2-a355-1fdbaf4ade27.png">


5. Order

In the Order section, the user can view, search, add, and delete orders. The order quantity is automatically updated in the inventory.


<img width="808" alt="order" src="https://user-images.githubusercontent.com/114201053/232668089-90c0fddc-9284-47f6-a8d7-39a028127f30.png">

6. Restcok

In the restock section, the user can view, search, add, and delete restock records. The restock quantity is automatically updated in the inventory.

<img width="808" alt="restock" src="https://user-images.githubusercontent.com/114201053/232668056-40080b32-2ec5-4c14-8361-3e4fd7ab8b5f.png">

7. Inventory

The inventory section enables the user to view and search the current stock of every product. The inventory quantity is automatically updated based on the order and restock's product quantities.

<img width="803" alt="inventory" src="https://user-images.githubusercontent.com/114201053/232668155-bd307e18-02f8-4016-b709-4ac07b205d30.png">

8. Supplier

Supplier section allows user to view, search, edit, add and delete any suppliers.

<img width="806" alt="supplier" src="https://user-images.githubusercontent.com/114201053/232668130-5106ee1b-9795-4fb9-ac0c-c255c39f6517.png">


9. User

User section allows user to view, search, edit, add and delete any users.

<img width="803" alt="user" src="https://user-images.githubusercontent.com/114201053/232667996-a4320f20-ebe5-4eee-8c92-db4a11b34b82.png">

Please feel free to let me know is you have any questions.

