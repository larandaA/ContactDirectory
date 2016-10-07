# ContactDirectory
## Database and Tomcat
### Database
Before using contactDirectory you should create a database. Creation script you can find in db/create_alexandra_ryzhevich_db.sql.
This script will create all needed tables, fill _country_ table with all needed data, and create a user with limited rights that has access to this database.
Also set MySQL server timezone to UTCin order to have all dates in UTC.
### Tomcat
Set Tomcat timezone to UTC.
## Properties setting
All the _.properies_ files are stored in _ContactDirectory/src/main/resources_.
### Database properties
Database properties are stored in file _db.properties_.
##### Database URL
**databaseUrl** property contains the path to the database. Path to database must start with the hostname. If you didn't changed anything in the creation script you should set databaseUrl to _hostname+port+alexandra_ryzhevich_db_.
For example:
```
databaseUrl=localhost:3306/alexandra_ryzhevich_db
```
##### Unicode
It's very important to set **useUnicode** property to **true** and **characterEncoding** property to **UTF-8**. It tells driver to use Unicode when handling strings.
##### Connection pool
ContactDirectory uses connection pool to connect the database. Properties **minimumConnections** and **maximumConnections** tell connection pool not to create less than **minimumConnections** and more than **maximumConnections** connections. **maximumConnections** must be same as **minimumConnections** or bigger.
##### Database user
Properties **user** and **password** tell connection pool which user to use to connect the database. If you didn't changed anything in the creation script you should set user to _alex_ryzhevich_ and password to _password_.
##### Properties example.
Example of _db.properies_ file:
```
databaseUrl=localhost:3306/alexandra_ryzhevich_db
useUnicode=true
characterEncoding=UTF-8
minimumConnections=5
maximumConnections=10
user=alex_ryzhevich
password=password
```
### Email properties
Email properties are stored in _email.properties_ file.
##### ContactDirectory account
**username** and **password** properties tell ContactDirectory which email account it will use to send emails to contacts and to admin. If you don't want to create new account you can use the existing account:
```
username=javatestar@gmail.com
password=testTEST12
```
##### Admin email
**adminEmail** property tells ContactDirectory where to send daily notifications with list of contacts who has birthday. You can set it to your email address or leave the author's one.
#### Session properties
Properties **mail.smtp.auth**, **mail.smtp.starttls.enable**, **mail.smtp.host**, **mail.smtp.port** are the session properties.
##### Properties example
Example of _email.properties_ file:
```
username=javatestar@gmail.com
password=testTEST12
adminEmail=larandaansil@gmail.com
mail.smtp.auth=true
mail.smtp.starttls.enable=true
mail.smtp.host=smtp.gmail.com
mail.smtp.port=587
```
P.S. Email from **adminEmail** property is the author's email.
### File storing properties
File storing properties are stored in _file.properties_ file.
Property **files.path** tells ContactDirectory where attachment files are stored. Property **photos.path** tells ContactDirectory where contact photos are stored. Note that path to files and to photos must be absolute. 
Property **defaultphoto.path** tells ContactDirectory name (not absolute) of default picture for contact photo. Absolute path to default photo will be build starting with path to contact photos catalog. It's necessary to create a default photo file. If you don't want to use your own default photo you can take it from _files/img_ catalog.
##### Properties example
Example of _file.properties_ file:
```
files.path=C:\\AlexandraRyzhevich\\files\\
photos.path=C:\\AlexandraRyzhevich\\files\\img\\
defaultphoto.path=default.jpg
```
### Log properties
Log properties are stored in _log4j2.properties_ file.
### StringTemplate properties
StringTemplate properties are stored in _st.properties_ file.
Property **stg.path** tells ContactDirectory a path to the STG file with email templates. You can add your own templates to your _.stg_ file. All of templates must have two parameters for contact name (**name.param** property) and for custom message text (**text.param** property). For example, you have _email_text.stg_ file:
```
hello(name,text) ::= <<
Hello, <name>!
<text>
>>

happy_bd(name,text) ::= <<
Dear <name>!
Happy Birthday!
<text>
>>
```
So your _st.properties_ file will be:
```
stg.path=email_text.stg
name.param=name
text.param=text
```
## Maven
After all preparation work done run the following command from the ContactDirectory catalog to get the _.war_ file:
```
mvn install
```