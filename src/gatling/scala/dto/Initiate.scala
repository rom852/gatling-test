package dto

object Initiate {

  def initDatabaseArgs(): String =
    s"""
       |[ {
       |    "args": null,
       |    "command": "InitializationService.initDatabase"
       |}]
          """.stripMargin


  def initProductArgs(adminLoginTicket: String, license: String): String =
    s"""
       |[{
       |    "args": {
       |        "login": "admin",
       |        "ticket": "${adminLoginTicket}"
       |    },
       |    "command": "SessionService.authenticate"
       |}, {
       |    "args": {
       |        "licenseType": "LICENSE",
       |        "licenseCodes": "${license}"
       |    },
       |    "command": "InitializationService.initProduct"
       |}]
          """.stripMargin



}
