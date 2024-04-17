
# Project Title

A brief description of what this project does and who it's for

CampusPay is a Java application that provides a platform for students, vendors, and administrators to manage payments and orders within a campus.

## Technology used
- Java: The application is written in Java, making use of its robust OOP features.
- SQL: The application interacts with a database using SQL stored procedures.
- Java Swing framewok:is a platform independent
framework,,built on top of the AWT package.
- JDBC:is a connector, is an API for the Java programming language. Its primary purpose is to define how a client (Java application) can access a database.
## AddItem
The `AddItem` class in the `AddItem.java` file is used to create a form that allows a vendor to add a new item to the database. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the add item form. It creates a `JFrame` and adds several components to it, including labels, text fields for the item name and price, and buttons for submitting the form and going back to the previous page.

2. Submit Button: The "Submit" button has an `ActionListener` attached to it. When the button is clicked, it retrieves the item name and price from the text fields, connects to the database, and calls a stored procedure `add_item_to_menu` to add the item to the database. If the item is added successfully, it displays a success message. If there's an error, it displays an error message.

3. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `VendorItems`, calls the `show` method to display the vendor items page, and disposes of the current frame.
## AdminAddTransaction
The `AdminAddTransaction.java` file is a part of a Java application that provides an interface for an admin to add a transaction. Here's a breakdown of the key components:

1. Class Declaration: The class `AdminAddTransaction` is declared.

2. Method Declaration: The `show()` method is declared. This method is responsible for setting up the GUI for the transaction addition interface.

3. Frame Setup: A `JFrame` is created and its properties are set. This includes setting the default close operation, size, and layout.

4. Container Setup: A `Container` is created and its layout and background color are set.

5. Labels and Fields: Various `JLabel` and `JTextField` objects are created for the transaction details. This includes fields for the student ID, vendor ID, and transaction amount.

6. Dropdown Lists: Two `JComboBox` objects are created to list all students and vendors. These lists are populated by querying the respective tables in the database.

7. Add Transaction Button: A `JButton` is created for adding the transaction. An `ActionListener` is added to this button which retrieves the selected student and vendor IDs, and the entered transaction amount. It then calls a stored procedure `make_transaction` in the database to add the transaction. If the transaction is successful, it shows a success message and redirects to the transaction list. If the transaction fails, it shows an error message.

8. Back Button: A `JButton` is created for going back to the previous screen. An `ActionListener` is added to this button which redirect
## AdminEditTransaction
The `AdminEditTransaction` class in the `AdminEditTransaction.java` file is used to create a form that allows an admin to edit an existing transaction in the database. Here's a breakdown of its functionality:

1. Constructor: The constructor takes five parameters: `transaction_id`, `vendor_id`, `student_id`, `total_amount`, and `date_time`. It initializes a `JFrame` and several GUI components including labels, text fields for the vendor ID, student ID, total amount, and date time, and buttons for editing the transaction and going back to the previous page. The text fields are pre-filled with the current values of the transaction.

2. Edit Button: The "Edit" button has an `ActionListener` attached to it. When the button is clicked, it retrieves the values from the text fields, connects to the database, and calls a stored procedure `update_transaction` to update the transaction in the database. If the transaction is updated successfully, it displays a success message, disposes of the current frame, and displays the transactions page. If there's an error, it displays an error message.

3. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `ShowTransactions`, calls the `show` method to display the transactions page, and disposes of the current frame.

4. show Method: The `show` method is used to make the form visible.

Please note that the actual functionality might vary based on the rest of your codebase and the implementation of other classes like `Conn`, `ShowTransactions`, etc.
## AdminHome
The `AdminHome` class in the `AdminHome.java` file is used to create the home page for the admin user. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the admin home page. It creates a `JFrame` and adds several components to it, including a title label and buttons for viewing vendors, viewing students, showing transactions, showing orders, and logging out.

2. View Vendors Button: The "View Vendors" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `ViewVendors`, calls the `show` method to display the vendors page, and disposes of the current frame.

3. View Students Button: The "View Students" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `ViewStudents`, calls the `show` method to display the students page, and disposes of the current frame.

4. Show Transactions Button: The "Show Transactions" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `ShowTransactions`, calls the `show` method to display the transactions page, and disposes of the current frame.

5. Show Orders Button: The "Show Orders" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `ShowOrders`, calls the `show` method to display the orders page, and disposes of the current frame.

6. Logout Button: The "Logout" button has an `ActionListener` attached to it. When the button is clicked, it clears the current user instance, creates an instance of `LoginSystem`, calls the `show` method to display the login page, and disposes of the current frame.
## AdminRegistration
The `AdminRegistration` class in the `AdminRegistration.java` file is used to create a form that allows the registration of a new admin. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the admin registration form. It creates a `JFrame` and adds several components to it, including labels, text fields for the admin ID and password, and buttons for submitting the form and resetting the form.

2. Submit Button: The "Submit" button has an `ActionListener` attached to it. When the button is clicked, it retrieves the admin ID and password from the text fields, hashes the password, connects to the database, and calls a stored procedure `register_admin` to register the admin in the database. If the admin is registered successfully, it displays a success message, disposes of the current frame, and displays the login page. If there's an error, it displays an error message.

3. Reset Button: The "Reset" button has an `ActionListener` attached to it. When the button is clicked, it clears the text fields.

## AdminViewStudentTransaction
The `AdminViewStudentTransaction` class in the `AdminViewStudentTransaction.java` file is likely used to create a form that allows an admin to view transactions associated with a specific student. Here's a hypothetical breakdown of its functionality based on the naming convention and the pattern observed in your codebase:

1. show Method: The `show` method is used to set up and display the GUI for the student transaction view form. It creates a `JFrame` and adds several components to it, including a `JTable` to display the transactions.

2. Student Transactions Table: The `JTable` is populated by querying the database for transactions associated with the selected student. This might be done using a stored procedure or a SQL query.

3. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `ViewStudents`, calls the `show` method to display the students page, and disposes of the current frame.

## AdminViewVendorItems
The `AdminViewVendorItems` class in the `AdminViewVendorItems.java` file is used to create a form that allows an admin to view items sold by a specific vendor. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the vendor items view form. It creates a `JFrame` and adds several components to it, including a `JTable` to display the items.

2. Vendor Items Table: The `JTable` is populated by querying the database for items sold by the selected vendor. This is done using a stored procedure `get_all_items_sold_by_vendor`.

3. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `ViewVendors`, calls the `show` method to display the vendors page, and disposes of the current frame.

## AdminViewVendorTransaction
The `AdminViewVendorTransaction` class in the `AdminViewVendorTransaction.java` file is likely used to create a form that allows an admin to view transactions associated with a specific vendor. Here's a hypothetical breakdown of its functionality based on the code snippet you provided:

1. show Method: The `show` method is used to set up and display the GUI for the vendor transaction view form. It creates a `JFrame` and adds several components to it, including a `JTable` to display the transactions.

2. Vendor Transactions Table: The `JTable` is populated by querying the database for transactions associated with the selected vendor. This might be done using a stored procedure or a SQL query.

3. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `ViewVendors`, calls the `show` method to display the vendors page, and disposes of the current frame.


## EditItem
The `EditItem.java` file contains a class named `EditItem` which is used to edit the details of an item sold by a vendor in the CampusPay1 application. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the edit item form. It creates a `JFrame` and adds several components to it, including labels, text fields, a drop-down menu for selecting the item to edit, and buttons.

2. Drop-down Menu: The drop-down menu is populated with the items sold by the vendor. This is done by connecting to the database and calling the stored procedure `get_all_items_sold_by_vendor`, passing the vendor's ID as a parameter.

3. Form Fields: The form includes fields for the item name and price. The item name and price are set to the current name and price of the selected item by calling the stored procedure `get_item_details`, passing the item's ID as a parameter.

4. Submit Button: The "Submit" button has an `ActionListener` attached to it. When the button is clicked, it connects to the database and calls the stored procedure `update_item_details` to update the details of the item. The item's ID, the new name, and the new price are passed as parameters to the stored procedure.

5. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `VendorItems`, calls the `show` method to display the vendor items page, and disposes of the current frame.


## EditStudent 
The `EditStudent.java` file contains a class named `EditStudent` which is used to edit the details of a student in the CampusPay1 application. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the edit student form. It creates a `JFrame` and adds several components to it, including labels, text fields, a drop-down menu for selecting the student to edit, and buttons.

2. Drop-down Menu: The drop-down menu is populated with the students. If the user is an admin, it gets all students from the database. If the user is a student, the drop-down list is uneditable and shows only the student's details.

3. Form Fields: The form includes fields for the student's name, account number, contact, and password. The fields are set to the current details of the selected student by calling the stored procedure `get_student_details`, passing the student's ID as a parameter.

4. Edit Button: The "Edit" button has an `ActionListener` attached to it. When the button is clicked, it connects to the database and calls the stored procedure `update_student_details` to update the details of the student. The student's ID, the new name, account number, contact, and hashed password are passed as parameters to the stored procedure.

5. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `ViewStudents` or `StudentHome` (based on the user's role), calls the `show` method to display the respective page, and disposes of the current frame.


## EditVendor
The `EditVendor.java` file likely contains a class named `EditVendor` which is used to edit the details of a vendor in the CampusPay1 application. Here's a hypothetical breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the edit vendor form. It creates a `JFrame` and adds several components to it, including labels, text fields, a drop-down menu for selecting the vendor to edit, and buttons.

2. Drop-down Menu: The drop-down menu is populated with the vendors. This is done by connecting to the database and calling a stored procedure or SQL query to get all vendors.

3. Form Fields: The form includes fields for the vendor's name, contact, and password. The fields are set to the current details of the selected vendor by calling a stored procedure or SQL query `get_vendor_details`, passing the vendor's ID as a parameter.

4. Edit Button: The "Edit" button has an `ActionListener` attached to it. When the button is clicked, it connects to the database and calls the stored procedure or SQL query `update_vendor_details` to update the details of the vendor. The vendor's ID, the new name, contact, and hashed password are passed as parameters to the stored procedure.

5. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `ViewVendors`, calls the `show` method to display the vendors page, and disposes of the current frame.


## LoginSystem 
The `LoginSystem.java` file contains a class named `LoginSystem` which is used to handle the login functionality of the CampusPay1 application. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the login form. It creates a `JFrame` and adds several components to it, including labels, text fields, and buttons.

2. User Verification: If a user is already logged in (checked by `User.getInstance().getId() != null`), the method will redirect the user to their respective home page based on their role (student, vendor, or admin).

3. Form Fields: The form includes fields for the user's ID, role, and password. The role is entered as a string and converted to an integer representation (0 for student, 1 for vendor, 2 for admin).

4. Submit Button: The "Submit" button has an `ActionListener` attached to it. When the button is clicked, it connects to the database and calls the stored procedure `verify_login` to verify the user's credentials. If the credentials are valid, the user is redirected to their respective home page.

5. Reset Button: The "Reset" button has an `ActionListener` attached to it. When the button is clicked, it clears the text fields.

6. Registration Buttons: There are two buttons for registration - one for students and one for vendors. When clicked, they open the respective registration forms.

7. Password Hashing: The password entered by the user is hashed using the `Hash` class before it is sent to the `verify_login` stored procedure. This is done to protect the user's password.

## Hash
The `Hash.java` file contains a utility class named `Hash` which is used to hash a password using the SHA-256 algorithm in the CampusPay1 application. Here's a breakdown of its functionality:

1. hash Method: The `hash` method is a static method that takes a password as a string and returns the hashed version of the password. It uses the SHA-256 hashing algorithm to create a hash of the password.

2. MessageDigest: The `MessageDigest` class is used to create a MessageDigest instance for the SHA-256 algorithm. This instance is used to calculate the hash of the password.

3. hashInBytes: The `digest` method of the `MessageDigest` instance is used to hash the password. The password is converted to bytes using the UTF-8 character set, and these bytes are hashed. The `digest` method returns the hash as a byte array.

4. StringBuilder: A `StringBuilder` is used to build the final hashed password string. Each byte in the hashed byte array is converted to a hexadecimal string and appended to the `StringBuilder`.

5. NoSuchAlgorithmException: This exception is caught if the algorithm specified when creating the `MessageDigest` instance is not found. In this case, the `hash` method returns null.

## Payment 
The `Payment.java` file is a part of a Java application that provides an interface for making a payment. Here's a breakdown of the key components:

1. Class Declaration: The class `Payment` is declared.

2. Method Declaration: The `show()` method is declared. This method is responsible for setting up the GUI for the payment interface.

3. Frame Setup: A `JFrame` is created and its properties are set. This includes setting the default close operation, size, layout, and background color.

4. Labels and Fields: Various `JLabel` and `JTextField` objects are created for the payment details. This includes fields for the amount and a dropdown list for vendors.

5. Dropdown Lists: A `JComboBox` object is created to list all vendors. This list is populated by querying the `get_all_vendors()` stored procedure in the database.

6. Pay Button: A `JButton` is created for making the payment. An `ActionListener` is added to this button which retrieves the entered amount and selected vendor ID. It then calls a stored procedure `make_transaction` in the database to make the payment. If the payment is successful, it shows a success message. If the payment fails, it shows an error message.

7. Back Button: A `JButton` is created for going back to the previous screen. An `ActionListener` is added to this button which redirects to the student home page.

8. Frame Visibility and Position: The frame is made visible and its position is set to the center of the screen.

9. Main Method: The `main` method is declared which creates an instance of `Payment` and calls the `show()` method. This is the entry point of the program.
## RefundTransaction
The `RefundTransaction.java` file is a part of a Java application that provides an interface for refunding a transaction. Here's a breakdown of the key components:

1. Class Declaration: The class `RefundTransaction` is declared.

2. Method Declaration: The `show()` method is declared. This method is responsible for setting up the GUI for the refund transaction interface.

3. Frame Setup: A `JFrame` is created and its properties are set. This includes setting the default close operation, size, layout, and background color.

4. Labels: Two `JLabel` objects are created. One for the title which includes the transaction ID and another for a confirmatory message.

5. Refund Button: A `JButton` is created for confirming the refund. An `ActionListener` is added to this button which calls a stored procedure `delete_transaction` in the database to refund the transaction. If the operation is successful, it shows a success message. If the operation fails, it shows an error message.

6. Cancel Button: A `JButton` is created for canceling the refund. An `ActionListener` is added to this button which disposes the frame, effectively closing the refund transaction interface.

7. Frame Visibility and Position: The frame is made visible and its position is set to the center of the screen.

8. Main Method: The `main` method is declared which creates an instance of `RefundTransaction` and calls the `show()` method with a transaction ID. This is the entry point of the program.
## SetBudget
The `SetBudget.java` file is a part of a Java application that provides an interface for setting a budget for a student. Here's a breakdown of the key components:

1. Class Declaration: The class `SetBudget` is declared.

2. Method Declaration: The `show()` method is declared. This method is responsible for setting up the GUI for the budget setting interface.

3. Frame Setup: A `JFrame` is created and its properties are set. This includes setting the default close operation, size, layout, and background color.

4. Labels and Fields: A `JLabel` and a `JTextField` are created for the budget amount.

5. Get Current Budget: The current budget is retrieved by calling the `get_monthly_budget` stored procedure in the database and the result is set as the text of the amount field.

6. Set Budget Button: A `JButton` is created for setting the budget. An `ActionListener` is added to this button which retrieves the entered amount and calls a stored procedure `set_monthly_budget` in the database to set the budget. If the operation is successful, it shows a success message. If the operation fails, it shows an error message.

7. Back Button: A `JButton` is created for going back to the previous screen. An `ActionListener` is added to this button which redirects to the student home page.

8. Frame Visibility and Position: The frame is made visible and its position is set to the center of the screen.
## ShowOrders 
The `ShowOrders.java` file contains a class named `ShowOrders` which is used to display the orders made by a user in the CampusPay1 application. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the orders page. It creates a `JFrame` and adds several components to it, including labels, a drop-down menu for selecting the transaction, a table to display the orders, and buttons.

2. Transaction Selection: A drop-down menu is populated with the transactions made by the current user. If the user is an admin, it gets all transactions. If the user is a vendor, it gets all transactions made by the vendor. If the user is a student, it gets all payments made by the student.

3. Orders Table: A `JTable` is used to display the orders of the selected transaction. The table has columns for the item ID, item name, quantity, and price.

4. Show Orders Button: The "Show Orders" button has an `ActionListener` attached to it. When the button is clicked, it connects to the database and calls the stored procedure `get_all_orders_of_transaction` to get the orders of the selected transaction. The orders are then displayed in the table.

5. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `AdminHome`, `VendorHome`, or `StudentHome` (based on the user's role), calls the `show` method to display the respective home page, and disposes of the current frame.

## ShowTransactions 
The `ShowTransactions.java` file contains a class named `ShowTransactions` which is used to display all transactions in the CampusPay1 application. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the transactions page. It creates a `JFrame` and adds several components to it, including labels, a table to display the transactions, and buttons.

2. Transactions Table: A `JTable` is used to display the transactions. The table has columns for the transaction ID, vendor ID, student ID, total amount, and date/time.

3. Database Connection: It connects to the database and calls the stored procedure `get_all_transactions` to get all transactions. The transactions are then displayed in the table.

4. Add Button: The "Add Transaction" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `AdminAddTransaction`, calls the `show` method to display the add transaction page, and disposes of the current frame.

5. Edit Button: The "Edit Transaction" button has an `ActionListener` attached to it. When the button is clicked, it gets the selected transaction from the table, creates an instance of `AdminEditTransaction` with the transaction details as parameters, calls the `show` method to display the edit transaction page, and disposes of the current frame.

6. Back Button: The "Back" button has a `MouseListener` attached to it. When the button is clicked, it creates an instance of `AdminHome`, calls the `show` method to display the admin home page, and disposes of the current frame.

## StudentHome
The `StudentHome.java` file is a part of a Java application that provides the home interface for a student. Here's a breakdown of the key components:

1. Class Declaration: The class `StudentHome` is declared.

2. Method Declaration: The `show()` method is declared. This method is responsible for setting up the GUI for the student home interface.

3. Frame Setup: A `JFrame` is created and its properties are set. This includes setting the default close operation, size, layout, and background color.

4. Labels and Fields: Various `JLabel` objects are created for displaying the total amount spent and the most bought item by the student. The total amount spent and the most bought item are retrieved by calling the `get_total_amount_spent_by_student` and `get_most_bought_item_by_student` stored procedures in the database respectively.

5. Buttons: Various `JButton` objects are created for different functionalities. Each button has an `ActionListener` added to it which redirects to the respective interface when clicked. The buttons include:

   - Vendors Button: Redirects to the vendors menu when clicked.
   
   - Payment Button: Redirects to the payment interface when clicked.
   
   - Payment History Button: Redirects to the payment history interface when clicked.
   
   - Update Details Button: Redirects to the student details editing interface when clicked.
   
   - Show Orders Button: Redirects to the orders interface when clicked.
   
   - Set Budget Button: Redirects to the budget setting interface when clicked.
   
   - Logout Button: Logs out the user and redirects to the login interface when clicked.

6. Frame Visibility and Position: The frame is made visible and its position is set to the center of the screen.

7. Main Method: The `main` method is declared which creates an instance of `StudentHome` and calls the `show()` method. This is the entry point of the program.
## StudentPaymentHistory
The `StudentRegistration.java` file contains a class named `StudentRegistration` which is used to handle the student registration functionality in the CampusPay1 application. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the student registration page. It creates a `JFrame` and adds several components to it, including labels, text fields for name, ID, contact, and password, and buttons.

2. Form Fields: The form includes fields for the student's name, ID, contact, and password. The ID is used as the BITS account as well.

3. Submit Button: The "Submit" button has an `ActionListener` attached to it. When the button is clicked, it connects to the database and calls the stored procedure `register_student` to register the student. The student's ID, BITS account (which is the same as the ID), name, contact, and hashed password are included in the student record. After successful registration, it shows a success message and redirects to the login page.

4. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `LoginSystem`, calls the `show` method to display the login page, and disposes of the current frame.

## StudentRegistration 
The `StudentRegistration.java` file contains a class named `StudentRegistration` which is used to handle the student registration functionality in the CampusPay1 application. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the student registration page. It creates a `JFrame` and adds several components to it, including labels, text fields for name, ID, contact, and password, and buttons.

2. Form Fields: The form includes fields for the student's name, ID, contact, and password. The ID is used as the BITS account as well.

3. Submit Button: The "Submit" button has an `ActionListener` attached to it. When the button is clicked, it connects to the database and calls the stored procedure `register_student` to register the student. The student's ID, BITS account (which is the same as the ID), name, contact, and hashed password are included in the student record. After successful registration, it shows a success message and redirects to the login page.

4. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `LoginSystem`, calls the `show` method to display the login page, and disposes of the current frame.

## User 
The `User.java` file contains a class named `User` which is used to store and manage user information in the CampusPay1 application. This class is implemented as a singleton, meaning that only one instance of this class can exist at any given time. Here's a breakdown of its functionality:

1. Private Constructor: The constructor of the `User` class is private to prevent the creation of new instances of the `User` class from outside the class.

2. getInstance Method: This static method returns the single instance of the `User` class. If the instance doesn't exist, it creates one.

3. setId, getId Methods: These methods are used to set and get the ID of the user.

4. setRole, getRole Methods: These methods are used to set and get the role of the user. The role could be an integer representing different user types (like admin, vendor, student, etc.).

5. setPassword, getPassword Methods: These methods are used to set and get the password of the user.

6. clear Method: This method is used to clear the user information. It sets the ID, role, and password to their default values

## VendorHome 
The `VendorHome.java` file contains a class named `VendorHome` which is used to handle the home page functionality for a vendor user in the CampusPay1 application. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the vendor home page. It creates a `JFrame` and adds several components to it, including labels, buttons, and a total amount earned label.

2. Total Amount Earned: The total amount earned by the vendor is retrieved from the database using the `get_total_amount_earned_by_vendor` stored procedure and displayed on the home page.

3. Transaction History Button: The "Transaction History" button, when clicked, creates an instance of `VendorTransactionHistory`, calls the `show` method to display the transaction history page, and disposes of the current frame.

4. Items Sold Button: The "My Items" button, when clicked, creates an instance of `VendorItems`, calls the `show` method to display the items page, and disposes of the current frame.

5. New Transaction Button: The "New Transaction" button, when clicked, creates an instance of `VendorTransactionProcess`, calls the `show` method to display the new transaction page, and disposes of the current frame.

6. Edit Details Button: The "Edit Details" button, when clicked, creates an instance of `EditVendor`, calls the `show` method to display the vendor details editing page, and disposes of the current frame.

7. Show Orders Button: The "Show Orders" button, when clicked, creates an instance of `ShowOrders`, calls the `show` method to display the orders page, and disposes of the current frame.

8. Logout Button: The "Logout" button, when clicked, clears the user information, creates an instance of `LoginSystem`, calls the `show` method to display the login page, and disposes of the current frame.

## VendorItems 
The `VendorItems.java` file contains a class named `VendorItems` which is used to display the items sold by a vendor in the CampusPay1 application. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the vendor items page. It creates a `JFrame` and adds several components to it, including labels, a text area to display the items, and buttons.

2. Items Text Area: A `JTextArea` is used to display the items sold by the vendor. The text area is populated by calling the stored procedure `get_all_items_sold_by_vendor` with the current user's ID as a parameter. Each item's ID, name, and price are appended to the text area.

3. Add Item Button: The "Add Item" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `AddItem`, calls the `show` method to display the add item page, and disposes of the current frame.

4. Edit Item Button: The "Edit Item" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `EditItem`, calls the `show` method to display the edit item page, and disposes of the current frame.

5. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `VendorHome`, calls the `show` method to display the vendor home page, and disposes of the current frame.


## VendorRegistration 
The `VendorRegistration.java` file contains a class named `VendorRegistration` which is used to handle the vendor registration functionality in the CampusPay1 application. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the vendor registration page. It creates a `JFrame` and adds several components to it, including labels, text fields for name, account number, contact, and password, and buttons.

2. Form Fields: The form includes fields for the vendor's name, account number, contact, and password. The account number is used as the vendor's ID as well.

3. Register Button: The "Register" button has an `ActionListener` attached to it. When the button is clicked, it connects to the database and calls the stored procedure `register_vendor` to register the vendor. The vendor's ID, name, account number, contact, and hashed password are included in the vendor record. After successful registration, it shows a success message with the vendor's ID and redirects to the login page.

4. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `LoginSystem`, calls the `show` method to display the login page, and disposes of the current frame.

## VendorsMenu 
The `VendorMenu.java` file contains a class named `VendorMenu` which is used to display the list of vendors in the CampusPay1 application. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the vendor menu page. It creates a `JFrame` and adds several components to it, including a title label, a scrollable text area to display the list of vendors, and a back button.

2. Vendors Text Area: A `JTextArea` is used to display the list of vendors. The text area is populated by executing the SQL query `SELECT * FROM vendors` and appending each vendor's name and contact information to the text area.

3. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `StudentHome`, calls the `show` method to display the student home page, and disposes of the current frame.

## VendorTransactionHistory
The `VendorTransactionHistory.java` file contains a class named `VendorTransactionHistory` which is used to display the transaction history of a vendor in the CampusPay1 application. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the vendor transaction history page. It creates a `JFrame` and adds several components to it, including a title label, a scrollable text area to display the transactions, and a back button.

2. Transactions Text Area: A `JTextArea` is used to display the transactions made by the vendor. The text area is populated by calling the stored procedure `get_all_transactions_made_by_vendor` with the current user's ID as a parameter. Each transaction's ID, student ID, total amount, and date and time are appended to the text area.

3. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `VendorHome`, calls the `show` method to display the vendor home page, and disposes of the current frame.

## VendorTransactionProcess 
The `VendorTransactionProcess.java` file is a part of a Java application that provides an interface for a vendor to process a transaction. Here's a detailed breakdown of the key components:

1. Class Declaration: The class `VendorTransactionProcess` is declared.

2. Method Declaration: The `show()` method is declared. This method is responsible for setting up the GUI for the vendor transaction process interface.

3. Frame Setup: A `JFrame` is created and its properties are set. This includes setting the default close operation, size, layout, and background color.

4. Labels and Fields: Various `JLabel` and `JTextField` objects are created for displaying and inputting the amount, student ID, product, and quantity. The product list is retrieved by calling the `get_all_items_sold_by_vendor` stored procedure in the database.

5. Table Setup: A `JTable` is created to display the products, quantities, and prices. A `DefaultTableModel` is used to manage the data in the table.

6. Buttons: Various `JButton` objects are created for different functionalities. This includes buttons for:

   - Adding a Product: Adds the selected product and its quantity to the table and updates the total amount.
   
   - Removing a Product: Removes the selected product from the table and updates the total amount.
   
   - Confirming the Transaction: Confirms the transaction by calling the `make_transaction` and `make_order` stored procedures in the database. If the transaction is successful, it shows a success message and redirects to the vendor home interface. If the transaction fails, it shows an error message.
   
   - Going Back: Redirects to the vendor home interface.

7. Frame Visibility and Position: The frame is made visible and its position is set to the center of the screen.

8. Main Method: The `main` method is declared which creates an instance of `VendorTransactionProcess` and calls the `show()` method. This is the entry point of the program.

9. Helper Method: The `configureButton` method is a helper method used to configure the properties of a button. This includes setting the font, size, location, background color, and border color of the button.


## ViewStudents 
The `ViewStudents.java` file contains a class named `ViewStudents` which is used to display a list of students in the application. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the student list page. It creates a `JFrame` and adds several components to it, including a title label, a scrollable table to display the list of students, and several buttons.

2. Students Table: A `JTable` is used to display the list of students. The table is populated by executing the SQL query `SELECT * FROM students` and appending each student's ID, BITS_account, s_name, and Contact to the table.

3. Add Button: The "Add" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `StudentRegistration`, calls the `show` method to display the student registration page, and disposes of the current frame.

4. Edit Button: The "Edit" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `EditStudent`, calls the `show` method to display the edit student page, and disposes of the current frame.

5. View Payments Button: The "View Payments" button has an `ActionListener` attached to it. When the button is clicked, it retrieves the selected student's ID from the table, creates an instance of `AdminViewStudentTransactions`, calls the `show` method with the student's ID as a parameter to display the student's payment history, and disposes of the current frame.

6. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `AdminHome`, calls the `show` method to display the admin home page, and disposes of the current frame.

## ViewVendors
The `ViewVendors.java` file contains a class named `ViewVendors` which is used to display a list of vendors in the application. Here's a breakdown of its functionality:

1. show Method: The `show` method is used to set up and display the GUI for the vendor list page. It creates a `JFrame` and adds several components to it, including a title label, a scrollable table to display the list of vendors, and several buttons.

2. Vendors Table: A `JTable` is used to display the list of vendors. The table is populated by executing the stored procedure `get_all_vendors` and appending each vendor's ID, name, account number, and contact to the table.

3. Add Button: The "Add Vendor" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `VendorRegistration`, calls the `show` method to display the vendor registration page, and disposes of the current frame.

4. Edit Button: The "Edit Vendor" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `EditVendor`, calls the `show` method to display the edit vendor page, and disposes of the current frame.

5. View Transactions Button: The "View Transactions" button has an `ActionListener` attached to it. When the button is clicked, it retrieves the selected vendor's ID from the table, creates an instance of `AdminViewVendorTransaction`, calls the `show` method with the vendor's ID as a parameter to display the vendor's transaction history, and disposes of the current frame.

6. View Items Button: The "View Items" button has an `ActionListener` attached to it. When the button is clicked, it retrieves the selected vendor's ID from the table, creates an instance of `AdminViewVendorItems`, calls the `show` method with the vendor's ID as a parameter to display the vendor's items, and disposes of the current frame.

7. Back Button: The "Back" button has an `ActionListener` attached to it. When the button is clicked, it creates an instance of `AdminHome`, calls the `show` method to display the admin home page, and disposes of the current frame.
