<h1 align = "center"> Instagram Backend </h1>

<p align="center">
<a href="Java url">
    <img alt="Java" src="https://img.shields.io/badge/Java->=8-darkblue.svg" />
</a>
<a href="Maven url" >
    <img alt="Maven" src="https://img.shields.io/badge/maven-3.0.5-brightgreen.svg" />
</a>
<a href="Spring Boot url" >
    <img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-3.0.6-brightgreen.svg" />
</a>
</p>
The project is a simple Instagram clone, a social media platform where users can create accounts, share photos with captions, follow other users, and interact by liking and commenting on posts. The primary goal of the project is to provide a basic version of Instagram's core functionalities, focusing on user interactions and photo sharing.

---

## Framework used

* Spring Boot
---

## Dependencies
The following dependencies are required to run the project:

* Spring Web
* Spring Data JPA
* MySQL Driver
* Lombok
* Validation
* javax -mail
* Swagger



## Database Configuration
To connect to a MySQL database, update the application.properties file with the appropriate database URL, username, and password. The following properties need to be updated:
```
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.url = jdbc:mysql://localhost:3306/<DatabaseName>
spring.datasource.username = <userName>
spring.datasource.password = <password>
spring.jpa.show-sql = true
spring.jpa.hibernate.ddl-auto = update

spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.use_sql_comments=true
spring.jpa.properties.hibernate.format_sql=true

```

## Language Used
* Java

---

## Data Model

The  data model  has the following attributes:

* User
```
Id: The unique identifier for the user.
name : The name of the user.
handle : user name of the user account.
bio : The discription of user.
email : The email of the user.
password : The password of the user for signUp , signIn and signOut.
dob : The date of birth of the user.
gender : The gender of the user.
bluetick : The bluetick refers an account is official or not.
accountType : The type of account user want to set public or private.


```

* Admin
```
Id: The unique identifier for the admin.
name: The name of the admin.
email : The email of the admin and admin mail should end with something@admin.com.
password : The password of the admin for signUp , signIn and signOut.




```


* AuthenticationToken
```
Id : The unique identifier for the authenticationToken.
value : The token value.
creationTimeStamp : The token created date and time.

RelationShips : 
user : A one-to-one mapping with user model.


```

* Post
```
Id : The unique identifier for the post.
content : The content of the post.
caption : The caption of the post.
location : The location at which the post is uploaded.
creation date and time : The post creation date and time.
type : The type of the post like IMAGE ,REEL and VIDEO.

Relationships :

userList: A many-to-one relationship with the user model.
```


* Like
```
Id : The unique identifier for the like.

Relationships :

postOwnerList: A many-to-one relationship with the post model.

likerList: A many-to-one relationship with the user model.

```

* Comment
```
Id : The unique identifier for the like.
body : The body of the comment.
creationTimeStamp : The comment created time.

Relationships :

postOwnerList: A many-to-one relationship with the post model.

commentOwnerList: A many-to-one relationship with the user model.


```

* Follow
```
Id :  The unique identifier for the follow.

Relationships :

currentuserList: A many-to-one relationship with the user model.

userFollowerList: A many-to-one relationship with the user model.


```

## Data Flow

1. The user at client side sends a request to the application through the API endpoints.
2. The API receives the request and sends it to the appropriate controller method.
3. The controller method makes a call to the method in service class.
4. The method in service class builds logic and retrieves or modifies data from the database, which is in turn given to controller class
5. The controller method returns a response to the API.
6. The API sends the response back to the user.

---


## DataBase Used
* SQL database
```
We have used Persistent database to implement CRUD Operations.
```
---

## Project Summary

The Instagram Clone Spring Boot project is a web application that aims to replicate some of the basic functionalities of the popular social media platform, Instagram. It allows users to share photos, follow other users, like and comment on posts and manage their profile information.
Overall, the portal provides a comprehensive solution for managing different types of relationships and performing CRUD operations on the models involved.

## Author

👤 **Goutham Peddi**

* GitHub: [Goutham Peddi](https://gist.github.com/GOUTHAMPEDDI)

---

## 🤝 Contributing

Contributions, issues and feature requests are welcome!<br />Feel free to check [issues page]("url").
    
---

## Show your support

Give a ⭐️ if this project helped you!
    
---
