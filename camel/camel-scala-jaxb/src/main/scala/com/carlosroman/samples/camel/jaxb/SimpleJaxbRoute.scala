package com.carlosroman.samples.camel.jaxb

import org.apache.camel.scala.dsl.builder.RouteBuilder
import org.apache.camel.model.DataFormatDefinition

import org.apache.camel.converter.jaxb.JaxbDataFormat
import org.apache.camel.spi.DataFormat

class SimpleJaxbRoute(fromUri: String, toUri: String) extends RouteBuilder {

  val format: DataFormat = new JaxbDataFormat("com.carlosroman.samples.camel.jaxb.domain")
  val formatDefinition: DataFormatDefinition = new DataFormatDefinition(format)

  from(fromUri) ==> {
    unmarshal(formatDefinition)
    to(toUri)
  }

}
