package com.carlosroman.samples.camel.jackson

import org.apache.camel.scala.dsl.builder.RouteBuilder
import org.apache.camel.component.jackson.JacksonDataFormat
import org.apache.camel.model.DataFormatDefinition
import com.carlosroman.samples.camel.jackson.domain.DomainObject
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

class SimpleJacksonRoute(marshalFromUri: String,
                         unmarshalFromUri: String,
                         toUri: String) extends RouteBuilder {

  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)
  val dataFormat: JacksonDataFormat = new JacksonDataFormat(mapper, classOf[DomainObject])
  val dataFormatDefinition: DataFormatDefinition = new DataFormatDefinition(dataFormat)

  from(marshalFromUri) ==> {
    marshal(dataFormatDefinition)
  } to toUri

  from(unmarshalFromUri) ==> {
    unmarshal(dataFormatDefinition)
  } to toUri

}
