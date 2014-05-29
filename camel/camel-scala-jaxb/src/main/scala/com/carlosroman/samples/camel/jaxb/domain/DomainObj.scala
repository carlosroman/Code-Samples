package com.carlosroman.samples.camel.jaxb.domain

import javax.xml.bind.annotation.{XmlAccessorType, XmlRootElement, XmlAccessType}
import org.apache.commons.lang3.builder.{ToStringStyle, ToStringBuilder, EqualsBuilder, HashCodeBuilder}


@XmlRootElement(name = "dom")
@XmlAccessorType(XmlAccessType.FIELD)
case class DomainObj(valueOne: String = null, valueTwo: String = null) {
  def this() = this(null, null)

  override def hashCode(): Int = HashCodeBuilder reflectionHashCode (this)

  override def equals(rhs: scala.Any): Boolean = EqualsBuilder reflectionEquals(this, rhs)

  override def toString: String = ToStringBuilder reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
}
