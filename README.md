## Demo Web Application

#### Technology
- Java 8  
- Java EE 7  
- JSF (Primefaces 7.0)  
- JPA (Hibernate 5.4.12)  
- PostgreSQL 12.2  
- Payara 5  

#### Notes
Project use created datasource on Payara. You need to add name of the JTA datasource to the file  src/main/resources/META-INF/persistence.xml, or you can add properties of database connection (driver, url, user, password).

#### SQL Script Path  
src/main/resources/db/events-app-sql.sql









