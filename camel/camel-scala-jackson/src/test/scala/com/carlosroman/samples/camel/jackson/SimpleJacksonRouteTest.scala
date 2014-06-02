package com.carlosroman.samples.camel.jackson

import org.scalatest.{OneInstancePerTest, BeforeAndAfter, Matchers, FunSuite}
import org.slf4j.LoggerFactory
import org.apache.camel.impl.{SimpleRegistry, DefaultCamelContext}
import org.apache.camel.component.mock.MockEndpoint
import com.carlosroman.samples.camel.jackson.domain.DomainObject
import org.json4s.DefaultFormats

class SimpleJacksonRouteTest extends FunSuite with Matchers with BeforeAndAfter with OneInstancePerTest {
  def LOG = LoggerFactory.getLogger(classOf[SimpleJacksonRouteTest])

  val marshalFromUri = "direct:startMarshal"
  val unmarshalFromUri = "direct:startUnmarshal"
  val toUri = "mock:end"
  val camelContext = new DefaultCamelContext(new SimpleRegistry)

  def getMockEndpoint(uri: String) = camelContext.getEndpoint(toUri, classOf[MockEndpoint])

  def template = camelContext.createProducerTemplate

  before {
    camelContext.disableJMX()
    camelContext.addRoutes(new SimpleJacksonRoute(marshalFromUri, unmarshalFromUri, toUri))
    camelContext.start()
  }

  after {
    camelContext.stop()
  }

  test("should create JSON from Scala Domain Object") {
    val payload = DomainObject(Some("value one"), Seq("one", "two"))
    val endpoint = getMockEndpoint(toUri)
    endpoint.expectedMessageCount(1)
    template sendBody(marshalFromUri, payload)
    endpoint.assertIsSatisfied()
    val body = endpoint.getExchanges.get(0).getIn.getBody(classOf[String])
    LOG.info("Got Body of '{}'", body)
    import org.json4s._
    import org.json4s.jackson.JsonMethods._
    implicit lazy val formats = DefaultFormats
    val actual = parse(body).extract[DomainObject]
    actual should equal(payload)
  }


  test("should create Scala Domain Object from JSON") {
    val payload = DomainObject(Some("value one"), Seq("one", "two"))
    val endpoint = getMockEndpoint(toUri)
    endpoint.expectedBodiesReceived(payload)
    template sendBody(unmarshalFromUri, domainObjectJson)
    endpoint assertIsSatisfied()
  }

  def domainObjectJson = """{
  "valueOne": "value one", "values":["one", "two"]
  }"""
}
