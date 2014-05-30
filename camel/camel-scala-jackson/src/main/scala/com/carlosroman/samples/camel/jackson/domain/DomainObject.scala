package com.carlosroman.samples.camel.jackson.domain

import com.fasterxml.jackson.annotation.JsonProperty

case class DomainObject(@JsonProperty("valueOne") valueOne: String) {
  def this() = this(null)
}