package com.carlosroman.samples.camel.jackson.domain

import com.fasterxml.jackson.annotation.JsonProperty

//import com.fasterxml.jackson.annotation.JsonInclude
//import com.fasterxml.jackson.annotation.JsonInclude.Include
//import Include.NON_NULL

//@JsonInclude(NON_NULL)
case class DomainObject(@JsonProperty("valueOne") valueOne: Option[String] = None,
                        @JsonProperty("values") values: Seq[String] = Nil) {

}
