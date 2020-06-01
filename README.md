# L3 BDD-2

## Table of Contents

+ [About](#about)
+ [Installation](#getting_started)
+ [Usage](#usage)

## About <a name = "about"></a>

This project was developped as an end project for an advanced database course.

## Getting Started <a name = "getting_started"></a>

### Prerequisites

#### MariaDB

This project is using [MariaDB](https://mariadb.org/) as its database management system. You can install it on Windows, Mac or Linux, or even in a Docker container.

#### JDBC

Download the [MariaDB Connector/J 2.6.0](https://downloads.mariadb.com/Connectors/java/connector-java-2.6.0/mariadb-java-client-2.6.0.jar).

### Installing

#### Cloning

First thing to do is to clone the repository: 

```
git clone git@github.com:nerstak/L3-BDD2.git
```

#### Setting up the database

Next, we have to set up the database structure. Start the MariaDB server, connect to it. This can be done in a terminal or a database administration tool.
Execute the script `SQL-BDD/DDL.sql`. 

After that, we need to populate the database. 
Execute the script `SQL-BDD/Insert.sql`.

#### Installing the JDBC

Finally, place the JDBC in a known folder (`lib` is a good one), and add it to your Java project. This manipulation mays depends on your development environment.

## Usage <a name = "usage"></a>

Run the application, using the main function located in `Java-GUI-BDD/src/Project/Main.java` as a target.

To log in, you can use credentials set in the `Insert.sql` file, or the following values.

|           | Username                | Password |
| --------- | ----------------------- | -------- |
| Therapist | admin                   | admin    |
| Patient   | pascal.dupont@gmail.com | 1234     |
