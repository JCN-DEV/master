import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the RisNewAppForm entity.
 */
class RisNewAppFormGatlingTest extends Simulation {

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

    val scn = scenario("Test the RisNewAppForm entity")
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
            exec(http("Get all risNewAppForms")
            .get("/api/risNewAppForms")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new risNewAppForm")
            .post("/api/risNewAppForms")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "designation":"SAMPLE_TEXT", "circularNo":"SAMPLE_TEXT", "applicationDate":"2020-01-01T00:00:00.000Z", "applicantsNameBn":"SAMPLE_TEXT", "applicantsNameEn":"SAMPLE_TEXT", "nationalId":null, "birthCertificateNo":null, "birthDate":"2020-01-01T00:00:00.000Z", "age":"SAMPLE_TEXT", "fathersName":"SAMPLE_TEXT", "mothersName":"SAMPLE_TEXT", "holdingNameBnPresent":"SAMPLE_TEXT", "villageBnPresent":"SAMPLE_TEXT", "unionBnPresent":"SAMPLE_TEXT", "poBnPresent":"SAMPLE_TEXT", "poCodeBnPresent":"SAMPLE_TEXT", "holdingNameBnPermanent":"SAMPLE_TEXT", "villageBnPermanent":"SAMPLE_TEXT", "unionBnPermanent":"SAMPLE_TEXT", "poBnPermanent":"SAMPLE_TEXT", "poCodeBnPermanent":"SAMPLE_TEXT", "contactPhone":"0", "email":"SAMPLE_TEXT", "nationality":"SAMPLE_TEXT", "profession":"SAMPLE_TEXT", "religion":null, "holdingNameEnPresent":"SAMPLE_TEXT", "villageEnPresent":"SAMPLE_TEXT", "unionEnPresent":"SAMPLE_TEXT", "poEnPresent":"SAMPLE_TEXT", "poCodeEnPresent":"SAMPLE_TEXT", "holdingNameEnPermanent":"SAMPLE_TEXT", "villageEnPermanent":"SAMPLE_TEXT", "unionEnPermanent":"SAMPLE_TEXT", "poEnPermanent":"SAMPLE_TEXT", "poCodeEnPermanent":"SAMPLE_TEXT", "createDate":"2020-01-01T00:00:00.000Z", "createBy":null, "updateDate":"2020-01-01T00:00:00.000Z", "updateBy":null}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_risNewAppForm_url")))
            .pause(10)
            .repeat(5) {
                exec(http("Get created risNewAppForm")
                .get("${new_risNewAppForm_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created risNewAppForm")
            .delete("${new_risNewAppForm_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}
