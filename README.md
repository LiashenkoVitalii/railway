## Project name
### **_System  railway ticket window_**

## Description
**_Task 22._** Passenger submits an application for the ticket to the needed 
 station, time and date of journey.  The system performs searching of required appropriate train. 
The passenger chooses train and gets bill to pay. Administrator controls list of registered passengers. 

## Table of Contents
* [Project name](#project-name)
* [Description](#description)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation and running](#installation-and-running)
* [Running the tests](#running-the-tests)  
* [Authors](#authors)

## Getting Started

### Prerequisites
To run the project you need installed (according to the documentation): 
  * Java 8 (jre/jdk) or higher version 
  * Apache Tomcat 8 or higher version
  * Apache Maven 3.3.9 or higher version
  * MySQL 5.7.20 or higher version
  
### Installation and running
To install and run the project on localhost:
 * Clone or download the project [railway](https://github.com/LiashenkoVitalii/railway) from the GitHub 
 * Create database **_"railway"_** on your MySQL server. After creating the database, edit file "/src/main/webapp/META-INF/context.xml". 
 Find key "username" and set your database username then set your password for key "password". 
 Execute at first sql-script **_"railwayDbStructure.sql"_** and then script **_"railwayDbDataDump.sql"_** from the directori **_"sql"_** of the project.                                                                                         
 * Compile project to package **_"railway.war"_**. To do it just execute command "mvn clean package -DskipTests" 
 (build skipping tests, or command "mvn clean package" if you want to run unit-tests as well)  from your prompt in the project root directory.
 Then place created file **_"railway.war"_** to the directory **_"webapps"_** of your Tomcat root folder  
 * Run Tomcat server. To do it run file **_"startup.sh"_** (for Mac/Linux/Unix, or file **_"startup.bat"_** for Windows)  
 from **_"bin"_** directory of your Tomcat installation folder. 
 When server starts, the site will be available by web-address: 
 _http:\\localhost:8080\railway_
 * To shutdown Tomcat just run file **_"shutdown.sh"_** (for Mac/Linux/Unix, or file **_"shutdown.bat"_** for Windows)  
 from **_"bin"_** directory of your Tomcat root folder.
 
## Running the tests
Project has unit tests. They allows you to make big changes to code and check if it still works.
For running the tests you need also created database "railway_test" on your MySQL server (username and password to the 
database should be the same as in the constants "USER" and "PASS" of source file "railway/src/test/java/test_utils/TestDbConnectServiceImpl.java").
To run the unit-test execute command "mvn clean package" from your prompt in the project root directory.

## Authors
Vitalii Liashenko (e-mail: [liashenkovitaliy@gmail.com](mailto:liashenkovitaliy@gmail.com))




