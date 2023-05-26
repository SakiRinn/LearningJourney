# Academic Gala

![](icon.png)

EBU6304-Software Engineering Project of Group 48

## Environment

The project is built on Maven and the recommended configuration is

* JDK 17+, The tested and supported JDKs are:
  - Azul Zulu JDK
  - OpenJDK
* Maven (The tested and supported version is 3.8.5)

The external packages used can be viewed in `pom.xml`, mainly include:
* Jackson 2.14.2
* JavaFX 17.0.2
* Junit 5

By the way, we use **IntelliJ IDEA 2023.1.2** as the development tool (IDE).

## Setup Instruction

### 1. JAR

In **Windows** OS, perform the following steps:

* Clone the project
* Navigate to the folder
* Open a terminal in the project folder, and
  run 
  ```shell
  java --module-path .\lib --add-modules javafx.controls,javafx.fxml -jar .\LearningJourney.jar
  ```
* Enjoy!

### 2. Command Line

Use Maven to compile and run the program from within the command line. The specific steps are as follows.


* Open the cmd and move to the project dictionary.
* Compile the programme
  ```sh
  mvn compile
  ```
* Run the programme
  ```sh
  mvn exec:java -Dexec.mainClass="uk.qmul.learningjourney.MainApplication"
  ```
* Enjoy!

The simpler way is to compile and run directly in IntelliJ IDEA or other IDEs.
