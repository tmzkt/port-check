# port-check
Command line utility to determine if a port on the host machine is open.

## Prerequisites
To work on this project, please have the following tools installed:
 - JDK 7 or higher
 - Maven 3

## Building the solution
To build the solution, navigate to the directory of the `pom.xml` and simply use the Maven package phase to generate an assembly.
(this assumes that the `mvn` command is already in the `PATH` environment variable)

```shell
cd C:\MyProjects\port-check
mvn package
```

## Running the solution
After running the package phase, a `target` folder should have been created in the same directory as the `pom.xml`.
Inside, there should be a file named `port-check-1.0-SNAPSHOT-jar-with-dependencies.jar`.
Use java to run this jar from the command line.
(this assumes that the java command is already in the `PATH` environment variable)

```shell
java -jar port-check-1.0-SNAPSHOT-jar-with-dependencies.jar 135 148
```

The output should look something like this:
```
Checking port 135...Closed
Checking port 148...Open
```
