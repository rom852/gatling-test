package dto

object Review {

  def createReviewArgs(authorLogin: String, authorLoginTicket: String, reviewTitle: String): String =
    s"""
       |[
       |  {
       |    "args": {
       |      "login": "${authorLogin}",
       |      "ticket": "${authorLoginTicket}"
       |    },
       |    "command": "SessionService.authenticate"
       |  },
       |  {
       |    "args": {
       |      "templateId": null,
       |      "title": "${reviewTitle}",
       |      "creator": "${authorLogin}",
       |      "accessPolicy": "ANYONE",
       |      "customFields": null
       |    },
       |    "command": "ReviewService.createReview"
       |  }
       |]
          """.stripMargin


  def addUserToReviewArgs(authorLogin: String, authorLoginTicket: String, reviewId: String, userLogin: String, role: String): String =
    s"""
       |[
       |  {
       |    "args": {
       |      "login": "${authorLogin}",
       |      "ticket": "${authorLoginTicket}"
       |    },
       |    "command": "SessionService.authenticate"
       |  },
       |  {
       |    "args": {
       |      "reviewId": "${reviewId}",
       |      "assignments": [
       |        {
       |          "user": "${userLogin}",
       |          "poolGuid": null,
       |          "role": "${role}"
       |        }
       |      ]
       |    },
       |    "command": "ReviewService.updateAssignments"
       |  }
       |]
          """.stripMargin


  def uploadFileArgs(authorLogin: String, authorLoginTicket: String, reviewId: String, fileHash: String, localPath: String, zipName: String): String =
    s"""
      [
       |  {
       |    "args": {
       |      "login": "${authorLogin}",
       |      "ticket": "${authorLoginTicket}"
       |    },
       |    "command": "SessionService.authenticate"
       |  },
       |  {"command": "ReviewService.addFiles",
       |  "args" : {
       |        "reviewId" : "${reviewId}",
       |        "changelists" : [{
       |        "zipName" : "${zipName}",
       |        "versions" : [ { "md5" : "${fileHash}",
       |        "localPath" : "${localPath}",
       |        "source" : "LOCAL" } ]
       |        }]
       |    }
       |    }
       |]
          """.stripMargin


  def getReviewSummaryArgs(authorLogin: String, authorLoginTicket: String, reviewId: String): String =
    s"""
      [
       |  {
       |    "args": {
       |      "login": "${authorLogin}",
       |      "ticket": "${authorLoginTicket}"
       |    },
       |    "command": "SessionService.authenticate"
       |  },
       |  {"command": "ReviewService.getReviewSummary",
       |  "args" : {
       |        "reviewId" : "${reviewId}",
       |        "clientBuild": 11200,
       |        "active": true
       |    }
       |    }
       |]
          """.stripMargin


  def moveReviewToAnnotatePhaseArgs(authorLogin: String, authorLoginTicket: String, reviewId: String): String =
    s"""
      [
       |  {
       |    "args": {
       |      "login": "${authorLogin}",
       |      "ticket": "${authorLoginTicket}"
       |    },
       |    "command": "SessionService.authenticate"
       |  },
       |  {"command": "ReviewService.moveReviewToAnnotatePhase",
       |  "args" : {
       |        "reviewId" : "${reviewId}"
       |    }
       |    }
       |]
          """.stripMargin

  def finishReviewPhaseArgs(authorLogin: String, authorLoginTicket: String, reviewId: String): String =
    s"""
      [
       |  {
       |    "args": {
       |      "login": "${authorLogin}",
       |      "ticket": "${authorLoginTicket}"
       |    },
       |    "command": "SessionService.authenticate"
       |  },
       |  {"command": "ReviewService.finishReviewPhase",
       |  "args" : {
       |        "until": "ANY",
       |        "reviewId" : "${reviewId}"
       |    }
       |    }
       |]
          """.stripMargin

}
