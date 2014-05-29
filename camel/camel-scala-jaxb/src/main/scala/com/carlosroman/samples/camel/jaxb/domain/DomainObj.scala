package com.carlosroman.samples.camel.jaxb.domain

import javax.xml.bind.annotation.{XmlAccessorType, XmlRootElement, XmlAccessType}

@XmlRootElement(name = "dom")
@XmlAccessorType(XmlAccessType.FIELD)
case class DomainObj(valueOne: String = null, valueTwo: String = null) {
  def this() = this(null, null)
}
