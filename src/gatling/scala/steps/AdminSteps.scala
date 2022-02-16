package steps

import dto.User
import io.gatling.core.Predef.{exec, jsonPath, _}
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef.{http, status, _}

object AdminSteps extends BaseSteps {

  val getAdminLoginTicket: ChainBuilder =
    exec(post("admin_login_ticket_request",
      StringBody(User.getAdminLoginTicketArgs))
      .check(jsonPath("$[:1].result.loginTicket").saveAs("adminLoginTicket"))
    )
}
