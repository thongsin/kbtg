# Exercise 2 : Working with Database.

1. Create a CRUD repository, new folder `repositories` in `src\main\java\main\com\kbtg\tech\` and java file `OrdersRepository.java` and insert code below.

```java
package com.kbtg.tech.repositories;

import org.springframework.data.repository.CrudRepository;

import com.kbtg.tech.dto.*;

public interface OrdersRepository extends CrudRepository<Order, Long> {
    
}
```

2.  Create a DTO, new folder `dto` in `src\main\java\main\com\kbtg\tech\` and java file `Order.java` and insert code below.

```java
package com.kbtg.tech.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column
    private String reference_order;
    
	@Column
    private String req_param;
    
	@Column
    private String resp_param;
    
    @Column 
    private String status;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setReference_order(String reference_order) {
        this.reference_order = reference_order;
    }

    public String getReference_order() {
        return reference_order;
    }

    public void setReq_param(String req_param) {
        this.req_param = req_param;
    }

    public String getReq_param() {
        return req_param;
    }

    public void setResp_param(String resp_param) {
        this.resp_param = resp_param;
    }

    public String getResp_param() {
        return resp_param;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
```

3. Open browser and log-in https://api.elephantsql.com/, click on Browser menu then paste a script below press excute to create `orders`  table inside your database instance. 

```sql
CREATE TABLE orders (
    id serial PRIMARY KEY,
	reference_order VARCHAR ( 50 ) UNIQUE NOT NULL,
	req_param VARCHAR ( 255 ) ,
    resp_param VARCHAR ( 255 ) ,
	status VARCHAR ( 50 )
);
```

4.  CRUD repository, new folder `serivces` in `src\main\java\main\com\kbtg\tech\` and java file `OrderSerivce.java` and insert code below.

```java
package com.kbtg.tech.services;

import java.io.IOException;

import com.kbtg.tech.dto.Order;
import com.kbtg.tech.repositories.OrdersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class OrderService {

    private String url = "https://dev-kpaymentgateway-services.kasikornbank.com/card/v2/charge";
    private String pKey = "skey_test_1nOxF19iQM1qIfeyFFWs1F9plaKHqibaH";

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private OkHttpClient client = new OkHttpClient();

    @Autowired
    private OrdersRepository ordersRepository;

    public String confirm (String token) {

        //TODO: Bonus point, please help me to create a unique reference_order per each order.

        String referenceOrder = "INV12345671111112";

        try {

            String jsonReq = "{\"token\":\""+ token + "\"," + 
                             "\"mode\":\"token\"," + 
                             "\"currency\":\"THB\"," +
                             "\"amount\":\"199\"," + 
                             "\"description\":\"\"," + 
                             "\"reference_order\":\"" + referenceOrder + "\"," + 
                             "\"source_type\":\"card\"}";

            Order order = new Order();
            order.setReference_order(referenceOrder);
            order.setReq_param(jsonReq);
            order.setStatus("Pending");
            
            RequestBody body = RequestBody.create(JSON, jsonReq);
            Request request = new Request.Builder().header("x-api-key", pKey).url(url).post(body).build();
            Response response = client.newCall(request).execute();
            String resp = response.body().string();

            if (resp != null && resp.contains("error")) {
                order.setResp_param(resp);
                order.setStatus("Failed");
            } else {
                order.setResp_param(resp);
                order.setStatus("completed");
            }

            ordersRepository.save(order);

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return referenceOrder;
    }
    
}
```

then modify  `CheckOutController.java` with code below.

```java
package com.kbtg.tech.controllers;

import com.kbtg.tech.services.OrderService;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
     
@Controller
public class CheckOutController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value="/Checkout")
    public String checkout() {
        return "checkout";
    }

    @PostMapping(value="/Confirm")
    public String confirm(@RequestParam Map<String, String> body, String name, Model model) {
        String chargeNo = orderService.confirm(body.get("token"));
        model.addAttribute("chargeNo", chargeNo);
        return "confirm";
    }

}
```

Run command... !!! by using `./mvnw spring-boot:run`  inside `initial` folder, Open browser http://localhost:8080