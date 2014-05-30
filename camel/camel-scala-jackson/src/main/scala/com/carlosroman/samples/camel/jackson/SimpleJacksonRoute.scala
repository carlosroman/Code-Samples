package com.carlosroman.samples.camel.jackson

import org.apache.camel.scala.dsl.builder.RouteBuilder
import org.apache.camel.component.jackson.JacksonDataFormat
import org.apache.camel.model.DataFormatDefinition
import com.carlosroman.samples.camel.jackson.domain.DomainObject

class SimpleJacksonRoute(marshalFromUri: String,
                         unmarshalFromUri: String,
                         toUri: String) extends RouteBuilder {

  val dataFormat: JacksonDataFormat = new JacksonDataFormat(classOf[DomainObject])

  val dataFormatDefinition: DataFormatDefinition = new DataFormatDefinition(dataFormat)

  from(marshalFromUri) ==> {
    marshal(dataFormatDefinition)
  } to toUri

  from(unmarshalFromUri) ==> {
    unmarshal(dataFormatDefinition)
  } to toUri

}
