import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the PgmsElpc entity.
 */
class PgmsElpcGatlingTest extends Simulation {

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

    val scn = scenario("Test the PgmsElpc entity")
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
            exec(http("Get all pgmsElpcs")
            .get("/api/pgmsElpcs")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new pgmsElpc")
            .post("/api/pgmsElpcs")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "empCode":"SAMPLE_TEXT", "instCode":"SAMPLE_TEXT", "empIndexNo":"SAMPLE_TEXT", "empName":"SAMPLE_TEXT", "instName":"SAMPLE_TEXT", "desigId":null, "designation":"SAMPLE_TEXT", "dateOfBirth":"2020-01-01T00:00:00.000Z", "joinDate":"2020-01-01T00:00:00.000Z", "beginDateOfRetirement":"2020-01-01T00:00:00.000Z", "retirementDate":"2020-01-01T00:00:00.000Z", "lastRcvPayscale":null, "incrsDtOfYrlyPayment":null, "gainingLeave":null, "leaveType":"SAMPLE_TEXT", "leaveTotal":null, "monPayDescVsource":null, "mainPayment":null, "incrMonRateLeaving":null, "specialPayment":null, "specialAllowance":null, "houserentAl":null, "treatmentAl":null, "dearnessAl":null, "travellingAl":null, "laundryAl":null, "personalAl":null, "technicalAl":null, "hospitalityAl":null, "tiffinAl":null, "advOfMakingHouse":null, "vechileStatus":null, "advTravAl":null, "advSalary":null, "houseRent":null, "carRent":null, "gasBill":null, "santryWaterTax":"SAMPLE_TEXT", "bankAcc":"SAMPLE_TEXT", "accBookNo":"SAMPLE_TEXT", "bookPageNo":"SAMPLE_TEXT", "bankInterest":"SAMPLE_TEXT", "monlyDepRateFrSalary":"SAMPLE_TEXT", "expectedDeposition":"SAMPLE_TEXT", "accDate":"2020-01-01T00:00:00.000Z", "appNo":"SAMPLE_TEXT", "appDate":"2020-01-01T00:00:00.000Z", "appType":"SAMPLE_TEXT", "appComents":"SAMPLE_TEXT", "aprvStatus":null, "aprvDate":"2020-01-01T00:00:00.000Z", "aprvBy":null, "notificationStatus":null, "activeStatus":null, "createDate":"2020-01-01T00:00:00.000Z", "createBy":null, "updateBy":null, "updateDate":"2020-01-01T00:00:00.000Z"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_pgmsElpc_url")))
            .pause(10)
            .repeat(5) {
                exec(http("Get created pgmsElpc")
                .get("${new_pgmsElpc_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created pgmsElpc")
            .delete("${new_pgmsElpc_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}
