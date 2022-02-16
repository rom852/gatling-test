package steps

import dto.{Initiate, User}
import org.json4s
import org.json4s._
import org.json4s.jackson.JsonMethods._
import scalaj.http.{Http, HttpResponse}


object InitSteps {

  final val licenseCode: String = "4A78-E21E-ED7D-F541-D7F9-5A37-2E5D-DBA2"

  def getAdminLoginTicket(testServerUrl: String): String = {
    val response: HttpResponse[String] = Http(testServerUrl + "/services/json/v1").postData(User.getAdminLoginTicketArgs)
      .header("content-type", "application/json")
      .header("Content-Length", "1")
      .header("Host", testServerUrl + "/services/json/v1")
      .asString
    val res = parse(response.body)
    println("AdminLoginTicket: " + res.children.last.children.last.children.last.values)
    res.children.last.children.last.children.last.values.toString
  }

  def initiateDatabase(testServerUrl: String): Unit = {
    val response: HttpResponse[String] = Http(testServerUrl + "/services/json/v1").postData(Initiate.initDatabaseArgs())
      .header("content-type", "application/json")
      .header("Content-Length", "1")
      .header("Host", testServerUrl + "/services/json/v1")
      .asString
    println("initiateDatabase response: " + response.body)
  }

  def initiateProduct(testServerUrl: String): Unit = {
    val loginTicket = getAdminLoginTicket(testServerUrl)
    val response: HttpResponse[String] = Http(testServerUrl + "/services/json/v1").postData(Initiate.initProductArgs(loginTicket, licenseCode))
      .header("content-type", "application/json")
      .header("Content-Length", "1")
      .header("Host", testServerUrl + "/services/json/v1")
      .asString
    println("initiateProduct response: " + response.body)
  }

  def deactivateUsers(testServerUrl: String): Unit = {
    val loginTicket = getAdminLoginTicket(testServerUrl)
    val response: HttpResponse[String] = Http(testServerUrl + "/services/json/v1").postData(User.getUsersListArgs(loginTicket))
      .header("content-type", "application/json")
      .header("Content-Length", "1")
      .header("Host", testServerUrl + "/services/json/v1")
      .asString
    val res = parse(response.body)
    val users: List[json4s.JValue] = res.children.last.children.last.children.last.children
    users.foreach(user => {
      val JBool(enabled) = user \ "enabled"
      if (enabled) {
        val JString(fullName) = user \ "fullName"
        println("Deactivating user: " + fullName)
        deactivateUser(testServerUrl, loginTicket, fullName)
      }
    }
    )
  }

  def deactivateUser(testServerUrl: String, loginTicket: String, userName: String): Unit = {
    val response: HttpResponse[String] = Http(testServerUrl + "/services/json/v1").postData(User.deactivateUserArgs(loginTicket, userName))
      .header("content-type", "application/json")
      .header("Content-Length", "1")
      .header("Host", testServerUrl + "/services/json/v1")
      .asString
  }

}
