# EasyLunchPrep API

EasyLunchPrep API is a Spring Boot REST API designed to help busy parents prepare lunch for themselves and their kids. 
It allows users to manage profiles, lunchboxes, and recipes, and provides nutrition information.
### Features:
- User and Lunchbox Management: Manage user profiles and multiple lunchboxes.
- Food/recipe Management: Upload, save, and view personalized food/recipes.
- Food Data Storage: Provide categorized common food items.
- Amazon S3 Integration: Store images related to food data.
- Secure Access: Spring Security integration for secure access.
- External API Integration: Fetch detailed food and nutrition information from external APIs.

### Technologies Used
- Java 22
- Spring Boot 3.3
- Spring Data JPA
- Spring Security
- H2 Database (for testing)
- Amazon S3
- Maven
- JUnit

### To access API doc (OpenAPI)
[/swagger-ui/index.html
](http://localhost:8080/swagger-ui/index.html)

### Monitoring with actuator
[/actuator](http://localhost:8080/actuator)