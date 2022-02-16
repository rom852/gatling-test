package dto

object Defect {

  def addOverallFileDefectArgs(authorLogin: String, authorLoginTicket: String, reviewId: String, versionId: String): String =
    s"""
      [
       |  {
       |    "args": {
       |      "login": "${authorLogin}",
       |      "ticket": "${authorLoginTicket}"
       |    },
       |    "command": "SessionService.authenticate"
       |  },
       |  {"command": "ReviewService.createOverallDefect",
       |  "args" : {
       |        "customFields": [{
       |            "name": "Severity",
       |            "value": ["Major"]
       |        }, {
       |            "name": "Type",
       |            "value": ["Build"]
       |        }],
       |        "comment": "This is text of the defect",
       |        "path": null,
       |        "reviewId" : "${reviewId}",
       |        "versionId": "${versionId}"
       |    }
       |    }
       |]
          """.stripMargin


  def markDefectFixedArgs(authorLogin: String, authorLoginTicket: String, defectId: String): String =
    s"""
       |[
       |  {
       |    "args": {
       |      "login": "${authorLogin}",
       |      "ticket": "${authorLoginTicket}"
       |    },
       |    "command": "SessionService.authenticate"
       |  },
       |  {"command": "ReviewService.markDefect",
       |  "args" : {
       |        "defectId": "${defectId}",
       |        "defectMarkType": "FIXED",
       |        "externalName": null
       |    }
       |    }
       |]
          """.stripMargin

}
