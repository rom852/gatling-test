package utils

import scala.util.Random

object Utils {

  def generateRandomName(prefix: String): String = prefix + "_" + Random.alphanumeric.filter(_.isDigit).take(8).mkString

}
