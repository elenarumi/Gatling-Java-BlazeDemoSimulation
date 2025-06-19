package example;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class BlazeDemoSimulation extends Simulation {

  HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://blazedemo.com") 
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .userAgentHeader("Gatling/Performance Test");

  ScenarioBuilder scn = scenario("BlazeDemo Load Test")
    .exec(
      http("Open Home Page")
        .get("/")  
        .check(status().is(200))
    )
    .pause(1) 
    .exec(
      http("Choose Flights")
        .post("/reserve.php")
        .formParam("fromPort", "San Diego")
        .formParam("toPort", "New York")
        .check(status().is(200))
    );

  {
    setUp(
      scn.injectOpen(atOnceUsers(5000))  
    ).protocols(httpProtocol);
  }
}
