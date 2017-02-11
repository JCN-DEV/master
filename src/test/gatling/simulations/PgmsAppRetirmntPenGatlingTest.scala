import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the PgmsAppRetirmntPen entity.
 */
class PgmsAppRetirmntPenGatlingTest extends Simulation {

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

    val scn = scenario("Test the PgmsAppRetirmntPen entity")
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
            exec(http("Get all pgmsAppRetirmntPens")
            .get("/api/pgmsAppRetirmntPens")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new pgmsAppRetirmntPen")
            .post("/api/pgmsAppRetirmntPens")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "withdrawnType":"SAMPLE_TEXT", "applicationType":"SAMPLE_TEXT", "rcvGrStatus":null, "workDuration":"SAMPLE_TEXT", "emergencyContact":null, "bankAccStatus":null, "bankName":"SAMPLE_TEXT", "bankAcc":"SAMPLE_TEXT", "bankBranch":"SAMPLE_TEXT", "appDate":"2020-01-01T00:00:00.000Z", "appNo":"SAMPLE_TEXT", "aprvStatus":null, "aprvDate":"2020-01-01T00:00:00.000Z", "aprvBy":null, "activeStatus":null, "createDate":"2020-01-01T00:00:00.000Z", "createBy":null, "updateBy":null, "updateDate":"2020-01-01T00:00:00.000Z"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_pgmsAppRetirmntPen_url")))
            .pause(10)
            .repeat(5) {
                exec(http("Get created pgmsAppRetirmntPen")
                .get("${new_pgmsAppRetirmntPen_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created pgmsAppRetirmntPen")
            .delete("${new_pgmsAppRetirmntPen_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}
