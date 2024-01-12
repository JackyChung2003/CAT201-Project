# CAT201-Project

## GUI Installation Instructions

### Pre-setup:

1. Download the appropriate JavaFX SDK (javafx-sdk-19 is highly preferred) for your operating system and unzip it to a desired location.
2. Download the appropriate JavaSDK (Java SDK 18/19 is highly preferred) for your operating system and unzip it to a desired location.

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
10. Press `Apply` and `Ok` to save changes.

### Setup Run Configuration:
11. Choose `Run` and select `Edit Configurations` on the toolbar or on the top right corner of the IDE interface.
12. Access the `Run` menu to select `Edit Configurations` from the toolbar, or alternatively, click on `Edit Configurations` at the top right corner of the IDE interface.
13. In the popup window, choose `Add new run configuration`, and from the options, select `Application`.
14. In the `Name` section, provide a preferred name for the configuration (e.g., TimeTableApplication).
15. In the "Build and run" section, press the "Browse" Icon on the `Main class` input box.
16. Select "HelloApplication" as the main class and press "OK" or type in com.group_22.timetablemanagement.HelloApplication
17. Within the "Build and run" section, click the "Browse" icon next to the `Main class` input box.
16. Choose `TimeTableApplication` from the available options or manually enter `com.group_22.timetablemanagement.TimeTableApplication`, and click `OK` .
17. Click `Apply` and then click `OK`.
18. Your run/build configuration is now set up. Press the "Run" icon to execute the application.


