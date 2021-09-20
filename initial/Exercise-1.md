# Exercise 1 : Create web application with Spring boot.

1. Create a Web Controller, Create new folder `controllers` in `src\main\java\main\com\kbtg\tech\` and java file `CheckoutController.java` and insert code below.

```java
package com.kbtg.tech.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CheckoutController {

	@GetMapping("/Checkout")
    public String checkout() {
        return "checkout";
    }

    @PostMapping("/Confirm")
    public String confirm() {
        return "confirm";
    }

}
```
2. Edit a web page, in folder `resources\public` at  `index.html` then find a comment `<!-- Input code here to connect with KPayment UI -->` and insert code below.

```html
<form id="formCheckOut" method="POST" action="/Confirm">
    <script type="text/javascript" src="https://dev-kpaymentgateway.kasikornbank.com/ui/v2/kpayment.min.js"
            data-apikey="pkey_test_1GzaL0tZIZVQPJk1CZYGpIA4qRL3uo4y6"
            data-name="Silpakorn University" 
            data-description="Silpakorn Shop" 
            data-amount="199" 
            data-currency="THB"
            data-savecard=false >
     </script>
</form>
```

3. Go to `applications.properties` in resources folder and modify the database connection with parameter below. (Please, Make sure your Data Base Instance at https://www.elephantsql.com/ is already created)

```properties
spring.jpa.hibernate.ddl-auto=none

spring.datasource.initialization-mode=always
spring.datasource.platform=postgres
spring.datasource.url=jdbc:postgresql://your_server_name:5432/your_database_name
spring.datasource.username=your_user_name
spring.datasource.password=your_password

spring.devtools.livereload.enabled=true
```

test your code by using `./mvnw spring-boot:run`  inside `initial` folder, if service is running you will see result below.

```text
 .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.3.2.RELEASE)

2020-12-15 12:42:36.159  INFO 2644 --- [  restartedMain] c.k.tech.ServingWebContentApplication    : Starting ServingWebContentApplication on KungDez with PID 2644 (D:\payment-101\complete\target\classes started by support in D:\payment-101\complete)
2020-12-15 12:42:36.449  INFO 2644 --- [  restartedMain] c.k.tech.ServingWebContentApplication    : No active profile set, falling back to default profiles: default
2020-12-15 12:42:37.084  INFO 2644 --- [  restartedMain] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFERRED mode.
Cannot find template location: classpath:/templates/ (please add some templates or check your Thymeleaf configuration)
2020-12-15 12:42:38.768  INFO 2644 --- [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
2020-12-15 12:42:38.832  INFO 2644 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2020-12-15 12:42:38.874  INFO 2644 --- [  restartedMain] DeferredRepositoryInitializationListener : Triggering deferred initialization of Spring Data repositoriesà
2020-12-15 12:42:38.945  INFO 2644 --- [  restartedMain] DeferredRepositoryInitializationListener : Spring Data repositories initialized!
2020-12-15 12:42:38.963  INFO 2644 --- [  restartedMain] c.k.tech.ServingWebContentApplication    : Started ServingWebContentApplication in 3.37 seconds (JVM running for 3701.464)
2020-12-15 12:42:39.015  INFO 2644 --- [  restartedMain] .ConditionEvaluationDeltaLoggingListener : Condition evaluation unchanged
2020-12-15 12:42:39.040  INFO 2644 --- [         task-1] com.zaxxer.hikari.HikariDataSource       : HikariPool-5 - Start completed.
2020-12-15 12:42:39.066  INFO 2644 --- [         task-1] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.PostgreSQL10Dialect
2020-12-15 12:42:39.533  INFO 2644 --- [         task-1] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
2020-12-15 12:42:39.540  INFO 2644 --- [         task-1] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
```

