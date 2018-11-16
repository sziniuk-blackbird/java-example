# Application start

> mvn clean install

> docker-compose build  

> docker-compose start

The app will run on port 8080. http://localhost:8080/ping should return “ok”. There are couple endpoints, CRUD for http://localhost:8080/document and CRUD for http://localhost:8080/project . Project has One-To-Many relationship to Document.

