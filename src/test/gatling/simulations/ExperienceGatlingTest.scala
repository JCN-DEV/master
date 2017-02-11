import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the Experience entity.
 */
class ExperienceGatlingTest extends Simulation {

    val context: LoggerContext = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]
    // Log all HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("TRACE"))
    // Log failed HTTP requests
    //context.getLogger("io.gatling.http").setLevel(Level.valueOf("DEBUG"))

    val baseURL = Option(System.getProperty("baseURL")) getOrElse """http://127.0.0.1:8080"""

    val httpConf = http
        .baseURL(baseURL)
        .inferHtmlResources()
        .acceptHeader("*/*")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
        .connection("keep-alive")
        .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")

    val headers_http = Map(
        "Accept" -> """application/json"""
    )

    val headers_http_authenticated = Map(
        "Accept" -> """application/json""",
        "X-CSRF-TOKEN" -> "${csrf_token}"
    )

    val scn = scenario("Test the Experience entity")
        .exec(http("First unauthenticated request")
        .get("/api/account")
        .headers(headers_http)
        .check(status.is(401))
        .check(headerRegex("Set-Cookie", "CSRF-TOKEN=(.*); [P,p]ath=/").saveAs("csrf_token")))
        .pause(10)
        .exec(http("Authentication")
        .post("/api/authentication")
        .headers(headers_http_authenticated)
        .formParam("j_username", "admin")
        .formParam("j_password", "admin")
        .formParam("remember-me", "true")
        .formParam("submit", "Login"))
        .pause(1)
        .exec(http("Authenticated request")
        .get("/api/account")
        .headers(headers_http_authenticated)
        .check(status.is(200))
        .check(headerRegex("Set-Cookie", "CSRF-TOKEN=(.*); [P,p]ath=/").saveAs("csrf_token")))
        .pause(10)
        .repeat(2) {
            exec(http("Get all experiences")
            .get("/api/experiences")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new experience")
            .post("/api/experiences")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "indexNo":"SAMPLE_TEXT", "address":"SAMPLE_TEXT", "designation":"SAMPLE_TEXT", "subject":"SAMPLE_TEXT", "salaryCode":"SAMPLE_TEXT", "joiningDate":"2020-01-01T00:00:00.000Z", "mpoEnlistingDate":"2020-01-01T00:00:00.000Z", "resignDate":"2020-01-01T00:00:00.000Z", "dateOfPaymentReceived":"2020-01-01T00:00:00.000Z", "startDate":"2020-01-01T00:00:00.000Z", "endDate":"2020-01-01T00:00:00.000Z", "vacationFrom":"2020-01-01T00:00:00.000Z", "vacationTo":"2020-01-01T00:00:00.000Z", "totalExpFrom":"2020-01-01T00:00:00.000Z", "totalExpTo":"2020-01-01T00:00:00.000Z", "current":null, "attachment":null}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_experience_url")))
            .pause(10)
            .repeat(5) {
                exec(http("Get created experience")
                .get("${new_experience_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created experience")
            .delete("${new_experience_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}