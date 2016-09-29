1. The project is using maven 2, spring 4, hibernate 4, mysql, tomcat7
2. To run this project, just run `mvn tomcat7:run`
3. Project needs jdk 1.7.80 or higher to run
4. The project is configured to work with an existing database.
    The database is configured in the `src/main/resources/jdbc.properties` file.
    Please note that the connection string must contain `?zeroDateTimeBehavior=convertToNull` at the end, otherwise, it
    will show exception on date fields.
5. Project can be imported to Intellij Idea directly.
    Any other IDE should work fine, in that case, just import the maven project and ignore the `.idea` directory and
    `biyebariserver.iml` file.

6. All DB interactions are done using JPA in the `com.bitmakersbd.biyebari.server.repository` package.

7. To implement a new CRUD, follow these steps
    1. Add a model in `com.bitmakersbd.biyebari.server.model` package and annotate with `@Entity`. Make sure that the model
        definition matches the existing database schema.
    2. Place validation messages in `src/main/resources/validationMessages.properties` file.
    3. Create an interface in the `com.bitmakersbd.biyebari.server.service` package and declare the desired methods.
    4. Crate a class to implement that interface in the same package.
    5. Register the service bean in the `src/main/webapp/WEB-INF/dispatcher-servlet.xml` file in `services` section.
    6. Create a repository interface in the `com.bitmakersbd.biyebari.server.repository` package extending the `JpaRepository` interface.
    7. Create a controller in the `com.bitmakersbd.biyebari.server.controller` package and add `RestController` and `RequestMapping` to that controller.
        Call your services by autowiring the service you created. Methods must return `RestResponse` and exceptions should be caught.
    8. To secure the resource, add an intercept url to the `src/main/webapp/WEB-INF/spring-security.xml` file with appropriate roles.
        > **NOTE:** This project now uses `spring-data-jpa`. See section 10 for new CRUD implementation.

8. The project uses spring security basic authentication. That means, to request any resource, you need to pass the
    `Authorization` header with the request. The username and password can be configured in the `src/main/webapp/WEB-INF/spring-security.xml` file.

9. Oauth2 enabled in application
    1. To request for a token, call
        `/oauth/token` with POST with the following data **IN URL**
        - `grant_type=password`,
        - `client_id`,
        - `client_secret`,
        - `username`,
        - `password`
    2. To request a refresh token, call
        `/oauth/token` with POST with the following data **IN URL**
        - `grant_type=refresh_token`,
        - `client_id`,
        - `client_secret`,
        - `refresh_token`
    3. For resource, call for example `/user?access_token=936347e7-eda1-4b8f-89f3-13d3dc6731f9`

10. Implementing a new CRUD after using `spring-data-jpa`
    1. Add a model in `com.bitmakersbd.biyebari.server.model` package and annotate with `@Entity`. Make sure that the model
        definition matches the existing database schema.
    2. Place validation messages in `src/main/resources/validationMessages.properties` file.
    3. Create an interface in the `com.bitmakersbd.biyebari.server.service` package and declare the desired methods.
    4. Create a repository interface extending `JpaRepository<T, ID>` in `com.bitmakersbd.biyebari.server.repository` package.
        No need to implement this interface, just declare methods following standard JPA rules. Basic methods are already defined
        in the `JpaRepository` interface. See <http://docs.spring.io/spring-data/jpa/docs/1.4.x/reference/htmlsingle/#jpa.query-methods>
        for reference.
    4. Crate a class to implement that interface in the same package. Inject the created repository in the implementation class.
        Use `@Transactional` annotation to make use transaction.
    5. Register the service bean in the `src/main/webapp/WEB-INF/dispatcher-servlet.xml` file in `services` section.
    6. Create a controller in the `com.bitmakersbd.biyebari.server.controller` package and add `RequestMapping` to that controller.
        Call your services by autowiring the service you created. The controller must be annotated with `@CrossOrigin` if
        cross origin resource sharing is needed. Methods must return `RestResponse` and exceptions should be caught.

11. To add a custom query, in the repository interface, create a method with `@Query` annotation. Define your SQL query in the `value` attribute.
    For example, `@Query(value = "select u from User u where u.firstName like %?1% or u.lastName like %?1%")`.

12. To insert created and updated date fields, declare fields like below.

    >@Temporal(TemporalType.TIMESTAMP)
    >@Column(name = "created_at", columnDefinition = "timestamp", nullable = true, updatable = false)
    >Date createdAt = new Date();
    >
    >@Temporal(TemporalType.TIMESTAMP)
    >@Column(name = "updated_at", columnDefinition = "timestamp", nullable = true, insertable = false)
    >Date updatedAt;

    **DO NOT** create any setter methods  for these fields.

    Add a pre update method for setting updated field.

    >@PreUpdate
    >public void onUpdate() {
    >   this.updatedAt = new Date();
    >}

13. For validation, add constraints annotation to your model fields like `@NotEmpty`. Also, add a `@Valid` annotation to
    controller method.
    If there's a difference in validation while creating and updating, assign groups to constraint and controller annotations
    like `@NotEmpty(groups = {ValidateOnCreate.class, ValidateOnUpdate.class})` and in your controller method, instead of
    using `@Valid`, use `@Validated(ValidateOnCreate.class)` or `@Validated(ValidateOnUpdate.class)`.

14. For logging audit information like `AUDIT INFO >>> URL: GET /vendors, UserId: 1, Total Time: 28ms.`, annotate any
    `RestController` method with `@EnableLogging`. This will log request information.

15. Add `-Dmaven.multiModuleProjectDirectory=$M2_HOME` to maven options if using maven 3

16. API documentation can be found at `/swagger-ui.html`. This app uses springfox-swagger.