package dto

object User {

  def createUserArgs(adminLoginTicket: String, userLogin: String): String =
    s"""
       |[
       |  {
       |    "args": {
       |      "login": "admin",
       |      "ticket": "${adminLoginTicket}"
       |    },
       |    "command": "SessionService.authenticate"
       |  },
       |  {
       |    "args": {
       |      "login": "${userLogin}",
       |      "newLogin": null,
       |      "password": "test",
       |      "fullName": "${userLogin}",
       |      "phone": null,
       |      "email": "${userLogin}@mail.com",
       |      "enabled": "true",
       |      "remoteMapping": null
       |    },
       |    "command": "UserService.create"
       |  }
       |]
          """.stripMargin


  def getUserLoginTicketArgs(userLogin: String): String =
    s"""
       |[
       |  {
       |    "args": {
       |      "login": "${userLogin}",
       |      "password": "test"
       |    },
       |    "command": "SessionService.getLoginTicket"
       |  }
       |]
          """.stripMargin


  def loginUserArgs(userLogin: String, userLoginTicket: String): String =
    s"""
       |[
       |  {
       |    "args": {
       |      "login": "${userLogin}",
       |      "ticket": "${userLoginTicket}"
       |    },
       |    "command": "SessionService.authenticate"
       |  },
       |  {
       |    "args": {
       |      "login": "${userLogin}",
       |      "source": "JSON_API"
       |    },
       |    "command": "SessionService.login"
       |  }
       |]
          """.stripMargin


  val getAdminLoginTicketArgs: String =
    s"""
       |[
       |  {
       |    "args": {
       |      "login": "admin",
       |      "password": "admin"
       |    },
       |    "command": "SessionService.getLoginTicket"
       |  }
       |]
          """.stripMargin

  def getUsersListArgs(adminLoginTicket: String): String =
    s"""
       |[{
       |    "args": {
       |        "login": "admin",
       |        "ticket": "${adminLoginTicket}"
       |    },
       |    "command": "SessionService.authenticate"
       |}, {
       |    "args": null,
       |    "command": "UserService.getUserList"
       |}]
          """.stripMargin

  def deactivateUserArgs(adminLoginTicket: String, userLogin: String): String =
    s"""
       |[{
       |    "args": {
       |        "login": "admin",
       |        "ticket": "${adminLoginTicket}"
       |    },
       |    "command": "SessionService.authenticate"
       |}, {
       |    "args": {
       |      "login": "${userLogin}",
       |      "enabled": "false"
       |    },
       |    "command": "UserService.edit"
       |}]
          """.stripMargin

}
