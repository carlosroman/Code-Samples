package com.carlosroman.samples.camel.jaxb

import org.scalatest.{BeforeAndAfter, OneInstancePerTest, Matchers, FunSuite}
import org.apache.camel.impl.{SimpleRegistry, DefaultCamelContext}
import com.carlosroman.samples.camel.jaxb.domain.{DomainObject, DomainObj}
import com.carlosroman.samples.camel.jaxb.domain.DomainObject.Builder
import org.apache.camel.component.mock.MockEndpoint
import org.slf4j.LoggerFactory

class SimpleJaxbRouteTest extends FunSuite with Matchers with BeforeAndAfter with OneInstancePerTest {
  def LOG = LoggerFactory.getLogger(classOf[SimpleJaxbRouteTest])
  val fromUri = "direct:start"
  val toUri = "mock:end"
  val camelContext = new DefaultCamelContext(new SimpleRegistry)

  before {
    camelContext.disableJMX()
    camelContext.addRoutes(new SimpleJaxbRoute(fromUri, toUri))
    camelContext.start()
  }

  after {
    camelContext.stop()
  }

  test("should unmarshal DomainObject correctly") {
    val builder: Builder = new Builder
    builder withValueOne "value one"
    builder withValueTwo "value two"
    val domainObject: DomainObject = builder.build()

    val endpoint: MockEndpoint = camelContext.getEndpoint(toUri, classOf[MockEndpoint])
    endpoint.expectedBodiesReceived(domainObject)

    camelContext.createProducerTemplate().sendBody(fromUri, domainObjectXml)
    endpoint.assertIsSatisfied()
  }

  test("should unmarshal DomainObj correctly") {
    val domainObject: DomainObj = DomainObj("value one", "value two")

    val endpoint: MockEndpoint = camelContext.getEndpoint(toUri, classOf[MockEndpoint])
    endpoint.expectedBodiesReceived(domainObject)

    camelContext.createProducerTemplate().sendBody(fromUri, domainObjXml)
    LOG.info("Expecting {}", domainObject)
    endpoint.assertIsSatisfied()
  }

  def domainObjXml =
    """<?xml version="1.0" encoding="UTF-8"?>
      |<dom>
      |<valueOne>value one</valueOne>
      |<valueTwo>value two</valueTwo>
      |</dom>
    """.stripMargin

  def domainObjectXml =
    """<?xml version="1.0" encoding="UTF-8"?>
      |<domain>
      |<valueOne>value one</valueOne>
      |<valueTwo>value two</valueTwo>
      |</domain>
    """.stripMargin

  def domainObjectJson = """{
  "valueOne": "value one",
  "valueTwo": "value two"
  }"""
}
