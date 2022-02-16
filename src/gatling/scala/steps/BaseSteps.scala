package steps

import io.gatling.core.Predef.{jsonPath, _}
import io.gatling.core.body.Body
import io.gatling.http.Predef.{http, status, _}
import io.gatling.http.request.BodyPart
import io.gatling.http.request.builder.HttpRequestBuilder

class BaseSteps {

  var path: String = "/services/json/v1"

  def post(requestName: String, body: Body): HttpRequestBuilder = {
    http(requestName)
      .post(path)
      .body(body).asJson
      .check(status.is(200))
      .check(jsonPath("$[:2].errors").notExists)
  }

  def post(requestName: String, json: BodyPart, fileName: String , filePath: String): HttpRequestBuilder  = {
    http(requestName)
      .post(path)
      .header("Content-Type", "multipart/form-data")
      .bodyPart(RawFileBodyPart(fileName, filePath)).asMultipartForm
      .bodyPart(json).asMultipartForm
      .check(status.is(200))
      .check(jsonPath("$[:2].errors").notExists)
  }


}
