# Currency Exchange Rate application

### Build
###### There is nothing special about the build. Import it with InteliJ Ultimate and everything should be fine.

---

### The application aims to fulfill few buisness cases
1. Makes periodical sync with data.fixer.io. See ExchangeRateScheduler 
and application.prop file
2. Expose the following apis
    1. **api/json/current** which accepts
        ```
        {
            "requestId": "34567812-3312223312121222",
            "timestamp": "123444",
            "client": "1234",
            "currency": "BGN"
        }
        ```
    2. **api/json/history** which accepts
       ```
       {
           	"requestId": "345678212-33122233112121222",
           	"timestamp": "123444",
           	"client": "1234",
           	"currency": "BGN",
           	"period" : "10"
       }
       ```
    3.**api/xml/current** which accepts
    ``` 
    <command id="1234">
    	<get consumer="122233333">
    		<currency>EUR</currency>
    	</get>
    </command>
    ```
   4. **api/xml/history** which accepts
   ```
    <command id="1234">
        <history consumer="122233333" currency="EUR" period="24">
    </command>
    ```
   5. GET endpoint  */api/statistics/{serviceName (optional)}* was exposed
  3. Additionally every request is stored in database. 
  Recurring requests with same id are consider bad and CONFLICT responce is returned.
  See aspects package -> com.egt.demo.demo.aspects
  4. Periodically statistics are send to cloud instance of messege broker -> RabbitMQ. See com.egt.demo.demo.scheduled.tasks.StatisticsCollector 
  5. Dummy validation with a lot of assumption based on document that was supplied. See com.egt.demo.demo.util.DummyValidator
