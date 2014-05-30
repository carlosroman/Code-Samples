package com.carlosroman.samples.camel.jackson

import org.scalatest.{OneInstancePerTest, BeforeAndAfter, Matchers, FunSuite}
import org.slf4j.LoggerFactory
import org.apache.camel.impl.{SimpleRegistry, DefaultCamelContext}
import org.apache.camel.component.mock.MockEndpoint
import com.carlosroman.samples.camel.jackson.domain.DomainObject

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
    val payload = DomainObject(Some("value one"))
    template sendBody(marshalFromUri, payload)
  }


  test("should create Scala Domain Object from JSON") {
    val payload = DomainObject(Some("value one"))
    val endpoint = getMockEndpoint(toUri)
    endpoint.expectedBodiesReceived(payload)
    template sendBody(unmarshalFromUri, domainObjectJson)
    endpoint assertIsSatisfied()
  }

  def domainObjectJson = """{
  "valueOne": "value one"
  }"""
}
