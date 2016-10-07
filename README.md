# ContactDirectory
## Database and Tomcat
### Database
Before using contactDirectory you should create a database. Creation script you can find in db/create_alexandra_ryzhevich_db.sql.
This script will create all needed tables, fill _country_ table with all needed data, and create a user with limited rights that has access to this database.
Also set MySQL server timezone to UTCin order to have all dates in UTC.
### Tomcat
Set Tomcat timezone to UTC.
## Properies setting
All the .properies files are stored in ContactDirectory/src/main/resources.
### Database properties
Database properties are stored in file db.properties.
###### Database URL
**databaseUrl** property contains the path to the database. Path to database must start with the hostname. If you didn't changed anything in the creation script you would set databaseUrl to _hostname+port+alexandra_ryzhevich_db_.
For example:
```
databaseUrl = localhost:3306/alexandra_ryzhevich_db
```
###### Unicode
