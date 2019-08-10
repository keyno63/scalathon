package jp.co.something.libs

import io.circe.Json
import io.circe.parser.parse

class SampleJsonParser {

  def sampleParse(jsonString: String):Json = {
    parse(jsonString).getOrElse(Json.Null)
  }
}
