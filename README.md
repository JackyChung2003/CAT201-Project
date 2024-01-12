# CAT201-Project

## GUI Installation Instructions

### Pre-setup:

1. Download the [JavaFX SDK](https://gluonhq.com/products/javafx/) suitable for your operating system. Unzip the package to your preferred location.
2. Obtain the [Java SDK](https://www.oracle.com/java/technologies/downloads/) compatible with your operating system. Unzip the package to a directory of your choice.
3. Download the appropriate [MYSQL JDBC DRIVER](https://dbschema.com/jdbc-driver/mysql.html).
4. Install [XAMPP](https://www.apachefriends.org/index.html) for database setup.
5. Ensure [Git](https://git-scm.com/download/win) is installed on your machine.
6. Make sure you have [IntelliJ IDEA](https://www.jetbrains.com/idea/) installed.

### Installation Steps:

1. To get started, clone this repository:

```bash
git clone https://github.com/jackychung2003/CAT201-Assignment-2.git
```
2. Open the `TimeTableManagement` folder within the `CAT201-Project` project in IntelliJ IDEA.


### Configure Project in IntelliJ:
4.  In the `Project Structure` window, go to the `Project` section. Under the `SDK` section, choose your downloaded Java SDK and click on `Apply` to confirm the selection.
5.  Still in the `Project Structure` window, move to the `Libraries` section. Click on the `+` icon to add a new library.
6.  Choose `Java` from the available options and specify the location of the `lib` directory in your JavaFX SDK. Once done, click `OK` to confirm.
7.  Click on the `+` icon once more, opt for "Java," and include the `mysql-connector-j-[version].jar` to add the MYSQL JDBC DRIVER. Click `OK` to proceed.
8.  Once completed, click `OK` to confirm your selections.


### Setup Run Configuration:
9. Choose `Run` and select `Edit Configurations` on the toolbar or on the top right corner of the IDE interface.
10. Access the `Run` menu to select `Edit Configurations` from the toolbar, or alternatively, click on `Edit Configurations` at the top right corner of the IDE interface.
11. In the popup window, choose `Add new run configuration`, and from the options, select `Application`.
12. In the `Name` section, provide a preferred name for the configuration (e.g., TimeTableApplication).
13. In the "Build and run" section, press the "Browse" Icon on the `Main class` input box.
14. Select "HelloApplication" as the main class and press "OK" or type in com.group_22.timetablemanagement.HelloApplication
15. Within the "Build and run" section, click the "Browse" icon next to the `Main class` input box.
16. Choose `TimeTableApplication` from the available options or manually enter `com.group_22.timetablemanagement.TimeTableApplication`, and click `OK` .
17. Click `Apply` and then click `OK`.
18. Your run/build configuration is now set up.


19. Open the XAMPP Control Panel application.
20. Under the "Module" section, find and click the `Start` button for both "Apache" and "MySQL".

21. Once Apache and MySQL are running, click on the `Admin` button next to MySQL. This will open your default web browser and navigate to the MySQL admin panel.

22. In the MySQL admin panel:
   - Click on the `Import` tab.

   - Choose the `Choose File` option to browse and select the `jdbc-time-table.sql` file from the local repository you cloned earlier.

   - Click the "Go" or "Import" button to initiate the SQL file import process.

23. Wait for the import process to complete. You should see a success message indicating that the SQL file has been successfully imported. Your database is now set up with the necessary tables and data.

25. Back to IntelliJ IDEA, press the `Run` icon to execute the application.


