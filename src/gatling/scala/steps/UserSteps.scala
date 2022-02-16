package steps

import dto.User
import io.gatling.core.Predef.{exec, jsonPath, _}
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import utils.Utils

object UserSteps extends BaseSteps {

  def createUser(user: String): ChainBuilder = {
    exec { session =>
      session.set(s"${user}Login", Utils.generateRandomName(user))
    }
      .exec(post(s"create_${user}_request",
        StringBody(session => User.createUserArgs(session("adminLoginTicket").as[String], session(s"${user}Login").as[String])))
      )
      .pause(2)
      .exec(post(s"${user}_login_ticket_request",
        StringBody(session => User.getUserLoginTicketArgs(session(s"${user}Login").as[String])))
        .check(jsonPath("$[:1].result.loginTicket").saveAs(s"${user}LoginTicket"))
      )
      .pause(2)
      .exec(post(s"${user}_login_request",
        StringBody(session => User.loginUserArgs(session(s"${user}Login").as[String], session(s"${user}LoginTicket").as[String])))
      )
  }

  def loginDbUser(user: String, userAlias: String): ChainBuilder = {
    exec { session =>
      session.set(s"${userAlias}Login", session("user_login").as[String])
    }
      .exec(post(s"${userAlias}_login_ticket_request",
        StringBody(User.getUserLoginTicketArgs(user)))
        .check(jsonPath("$[:1].result.loginTicket").saveAs(s"${userAlias}LoginTicket"))
      )
      .pause(2)
      .exec(post(s"${userAlias}_login_request",
        StringBody(session => User.loginUserArgs(session("user_login").as[String], session(s"${userAlias}LoginTicket").as[String])))
      )
  }

}
