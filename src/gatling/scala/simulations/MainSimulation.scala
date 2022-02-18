package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.jdbc.Predef.jdbcFeeder
import scenarios.{ReviewFlow, UsersFlow}
import steps.InitSteps

class MainSimulation extends Simulation {

  final val author: String = "author"
  final val reviewer: String = "reviewer"

  val testServerUrl: String = if (System.getProperty("base_url") != null) System.getProperty("base_url") else "http://127.0.0.1:8080"
  val dbUrl: String = if (System.getProperty("dbUrl") != null) System.getProperty("dbUrl") else "jdbc:mysql://localhost:3306/maindb?useSSL=false"
  val dbUser: String = if (System.getProperty("dbUser") != null) System.getProperty("dbUser") else "root"
  val dbPassword: String = if (System.getProperty("dbPassword") != null) System.getProperty("dbPassword") else "Qwerty123!"

  val getUserLoginsSql: String = "SELECT user_login FROM user where user_active = 'Y' and user_password = '098f6bcd4621d373cade4e832627b4f6'"
  val getReviewIdsSql: String = "SELECT review_id FROM review where review_title like 'db_review_%'"

  val usersFeeder =jdbcFeeder(dbUrl, dbUser, dbPassword, getUserLoginsSql).queue
  val reviewsFeeder =jdbcFeeder(dbUrl, dbUser, dbPassword, getReviewIdsSql).queue


  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(testServerUrl)

  before {
    InitSteps.initiateDatabase(testServerUrl)
    InitSteps.initiateProduct(testServerUrl)
  //  InitSteps.deactivateUsers(testServerUrl)
  }

  setUp(
    scenario("ReviewDbFlowSimulation")  // 1st Scenario - using db feeder
      .feed(usersFeeder)
      .feed(reviewsFeeder)
      .exec(
            ReviewFlow.loginAndAddDbUserToReview("${user_login}", author, author)
      )
      .feed(usersFeeder)
      .exec(
        ReviewFlow.loginAndAddDbUserToReview("${user_login}", reviewer, reviewer)
      )
      .inject(atOnceUsers(3))
      .andThen(ReviewFlow.moveReviewToCompletePhase.inject(atOnceUsers(100)))   // 2nd Scenario
      .andThen(UsersFlow.addingDefectsAndCommentsToReview.inject(incrementUsersPerSec(2)  // 3rd Scenario
        .times(5)
        .eachLevelLasting(2)
        .startingFrom(1) // Double
      ))

  ).protocols(httpProtocol)

}

