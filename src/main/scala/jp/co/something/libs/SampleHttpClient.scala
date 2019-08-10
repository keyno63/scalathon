package jp.co.something.libs

import skinny.http.{HTTP, Request, Response}

object SampleHttpClient {

  def get(url: String, query: Map[String, String]): Response = {
    val request = Request(url)
    query.foreach{
      kv => val (k,v) = kv
      request.queryParams(k -> v)
    }
    HTTP.get(request)
  }
}
