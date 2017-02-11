import _root_.io.gatling.core.scenario.Simulation
import ch.qos.logback.classic.{Level, LoggerContext}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._

/**
 * Performance test for the SisStudentReg entity.
 */
class SisStudentRegGatlingTest extends Simulation {

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

    val scn = scenario("Test the SisStudentReg entity")
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
            exec(http("Get all sisStudentRegs")
            .get("/api/sisStudentRegs")
            .headers(headers_http_authenticated)
            .check(status.is(200)))
            .pause(10 seconds, 20 seconds)
            .exec(http("Create new sisStudentReg")
            .post("/api/sisStudentRegs")
            .headers(headers_http_authenticated)
            .body(StringBody("""{"id":null, "applicationId":null, "instCategory":"SAMPLE_TEXT", "instituteName":"SAMPLE_TEXT", "curriculum":"SAMPLE_TEXT", "TradeTechnology":"SAMPLE_TEXT", "subject1":"SAMPLE_TEXT", "subject2":"SAMPLE_TEXT", "subject3":"SAMPLE_TEXT", "subject4":"SAMPLE_TEXT", "subject5":"SAMPLE_TEXT", "optional":"SAMPLE_TEXT", "shift":"SAMPLE_TEXT", "semester":"SAMPLE_TEXT", "studentName":"SAMPLE_TEXT", "fatherName":"SAMPLE_TEXT", "motherName":"SAMPLE_TEXT", "dateOfBirth":"SAMPLE_TEXT", "gender":"SAMPLE_TEXT", "religion":"SAMPLE_TEXT", "bloodGroup":"SAMPLE_TEXT", "quota":"SAMPLE_TEXT", "nationality":"SAMPLE_TEXT", "mobileNo":"SAMPLE_TEXT", "contactNoHome":"SAMPLE_TEXT", "emailAddress":"SAMPLE_TEXT", "presentAddress":"SAMPLE_TEXT", "permanentAddress":"SAMPLE_TEXT", "activeStatus":null, "createDate":"2020-01-01T00:00:00.000Z", "createBy":null, "updateDate":"2020-01-01T00:00:00.000Z", "updateBy":null, "maritalStatus":"SAMPLE_TEXT"}""")).asJSON
            .check(status.is(201))
            .check(headerRegex("Location", "(.*)").saveAs("new_sisStudentReg_url")))
            .pause(10)
            .repeat(5) {
                exec(http("Get created sisStudentReg")
                .get("${new_sisStudentReg_url}")
                .headers(headers_http_authenticated))
                .pause(10)
            }
            .exec(http("Delete created sisStudentReg")
            .delete("${new_sisStudentReg_url}")
            .headers(headers_http_authenticated))
            .pause(10)
        }

    val users = scenario("Users").exec(scn)

    setUp(
        users.inject(rampUsers(100) over (1 minutes))
    ).protocols(httpConf)
}
