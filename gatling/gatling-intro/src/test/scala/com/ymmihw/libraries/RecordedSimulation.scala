package com.ymmihw.libraries

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

	val httpProtocol = http
		.baseUrl("http://computer-database.gatling.io")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("zh-CN,zh;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_4 = Map(
		"Origin" -> "http://computer-database.gatling.io",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")



	val scn = scenario("RecordedSimulation")
		.exec(http("request_0")
			.get("/")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/favicon.ico")
			.headers(headers_1)
			.check(status.is(404))))
		.pause(0)
		.exec(http("request_2")
			.get("/computers?f=amstrad")
			.headers(headers_0))
		.pause(0)
		.exec(http("request_3")
			.get("/computers/412")
			.headers(headers_0))
		.pause(0)
		.exec(http("request_4")
			.post("/computers/412")
			.headers(headers_4)
			.formParam("name", "Amstrad CPC 6128")
			.formParam("introduced", "")
			.formParam("discontinued", "")
			.formParam("company", "38"))
		.pause(0)
		.exec(http("request_5")
			.get("/computers/501")
			.headers(headers_0)
			.resources(http("request_6")
			.post("/computers/501")
			.headers(headers_4)
			.formParam("name", "AN/FSQ-32")
			.formParam("introduced", "1960-01-01")
			.formParam("discontinued", "")
			.formParam("company", "13")))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}