package steps

import dto.{Comment, Defect, Review}
import io.gatling.core.Predef.{exec, jsonPath, _}
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import utils.Utils

object ReviewSteps extends BaseSteps {

  def createReview(review: String, author: String): ChainBuilder = {
    exec { session => session.set("reviewTitle", Utils.generateRandomName(review)) }
      .exec(post("create_review_request",
        StringBody(session => Review.createReviewArgs(session(s"${author}Login").as[String],
          session(s"${author}LoginTicket").as[String], session("reviewTitle").as[String])))
        .check(jsonPath("$[:2].result.reviewId").saveAs("reviewId"))
      )
  }

  def addUserToReview(user: String, role: String): ChainBuilder = {
    exec(post(s"add_user_${user}_as_${role}_to_review_request",
      StringBody(session => Review.addUserToReviewArgs(session("authorLogin").as[String],
        session("authorLoginTicket").as[String], session("reviewId").as[String], session(s"${user}Login").as[String],
        role.toUpperCase)))
    )
  }

  def addUserToReviewByAdmin(user: String, role: String): ChainBuilder = {
    exec(post(s"add_user_${user}_as_${role}_to_review_request",
      StringBody(session => Review.addUserToReviewArgs("admin",
        session("adminLoginTicket").as[String], session("reviewId").as[String], session(s"${user}Login").as[String],
        role.toUpperCase)))
    )
  }

  def uploadFile(user: String): ChainBuilder = {
    exec(post("upload_file_request", StringBodyPart("json", session => Review.uploadFileArgs(session(s"${user}Login").as[String],
      session(s"${user}LoginTicket").as[String], session("reviewId").as[String],
      "d8bd6e0d812f08d3155f812899a374fc",
      "file", "archive.zip")
    ), "archive.zip", "archive.zip")
    )
  }

  def uploadBigFile(user: String): ChainBuilder = {
    exec(post("upload__big_file_request", StringBodyPart("json", session => Review.uploadFileArgs(session(s"${user}Login").as[String],
      session(s"${user}LoginTicket").as[String], session("reviewId").as[String],
      "65c389a6d3907ce833c05591fcb2dcdd",
      "10Mb file", "archiveBig.zip")
    ), "archiveBig.zip", "archiveBig.zip")
    )
  }

  def getLastVersionId(user: String): ChainBuilder = {
    exec(post("get_review_summary_request",
      StringBody(session => Review.getReviewSummaryArgs(session(s"${user}Login").as[String],
        session(s"${user}LoginTicket").as[String], session("reviewId").as[String])))
      .check(jsonPath("$[:2].result.scmMaterials[:1].consolidatedChangelist.reviewSummaryFiles[:1].latestVersionId").saveAs("versionId"))
    )
  }

  def addFileComment(user: String): ChainBuilder = {
    exec(getLastVersionId(user))
      .exec(post("create_file_comment_request",
        StringBody(session => Comment.addOverallFileCommentArgs(session(s"${user}Login").as[String],
          session(s"${user}LoginTicket").as[String], session("reviewId").as[String], session("versionId").as[String])))
        .check(jsonPath("$[:2].result.commentId").saveAs("commentId"))
      )
  }

  def acceptComment(user: String): ChainBuilder = {
    exec(post("accept_comment_request",
      StringBody(session => Comment.acceptFileCommentArgs(session(s"${user}Login").as[String],
        session(s"${user}LoginTicket").as[String], session("commentId").as[String])))
    )
  }

  def addFileDefect(user: String): ChainBuilder = {
    exec(getLastVersionId(user))
      .exec(post("create_file_defect_request",
        StringBody(session => Defect.addOverallFileDefectArgs(session(s"${user}Login").as[String],
          session(s"${user}LoginTicket").as[String], session("reviewId").as[String], session("versionId").as[String])))
        .check(jsonPath("$[:2].result.defectId").saveAs("defectId"))
      )
  }

  def markDefectFixed(user: String): ChainBuilder = {
    exec(post("mark_defect_fixed_request",
      StringBody(session => Defect.markDefectFixedArgs(session(s"${user}Login").as[String],
        session(s"${user}LoginTicket").as[String], session("defectId").as[String])))
    )
  }

  def moveReviewToAnnotatePhase(review: String, author: String): ChainBuilder = {
    exec { session => session.set("reviewTitle", Utils.generateRandomName(review)) }
      .exec(post("move_to_annotate_request",
        StringBody(session => Review.moveReviewToAnnotatePhaseArgs(session(s"${author}Login").as[String],
          session(s"${author}LoginTicket").as[String], session("reviewId").as[String])))
      )
  }

  def finishReviewPhase(review: String, author: String): ChainBuilder = {
    exec { session => session.set("reviewTitle", Utils.generateRandomName(review)) }
      .exec(post("finish_review_phase_request",
        StringBody(session => Review.finishReviewPhaseArgs(session(s"${author}Login").as[String],
          session(s"${author}LoginTicket").as[String], session("reviewId").as[String])))
      )
  }

  def storeReviewIdFromFeeder(): ChainBuilder = {
    exec { session =>
      session.set("reviewId", session("review_id").as[String])
    }
  }
}
