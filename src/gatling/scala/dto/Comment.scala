package dto

object Comment {

  def addOverallFileCommentArgs(authorLogin: String, authorLoginTicket: String, reviewId: String, versionId: String): String =
    s"""
      [
       |  {
       |    "args": {
       |      "login": "${authorLogin}",
       |      "ticket": "${authorLoginTicket}"
       |    },
       |    "command": "SessionService.authenticate"
       |  },
       |  {"command": "ReviewService.createOverallComment",
       |  "args" : {
       |        "comment": "This is text comment",
       |        "path": null,
       |        "reviewId" : "${reviewId}",
       |        "versionId": "${versionId}"
       |    }
       |    }
       |]
          """.stripMargin


  def acceptFileCommentArgs(authorLogin: String, authorLoginTicket: String, conversationId: String): String =
    s"""
      [
       |  {
       |    "args": {
       |      "login": "${authorLogin}",
       |      "ticket": "${authorLoginTicket}"
       |    },
       |    "command": "SessionService.authenticate"
       |  },
       |  {"command": "ReviewService.acceptConversation",
       |  "args" : {
       |        "conversationId": "${conversationId}"
       |    }
       |    }
       |]
          """.stripMargin

}
