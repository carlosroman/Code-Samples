package com.carlosroman.samples.camel.jaxb.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "domain")
@XmlAccessorType(XmlAccessType.FIELD)
public class DomainObject {

    private String valueOne;
    private String valueTwo;

    private DomainObject() {

    }

    private DomainObject(final Builder builder) {
        this.valueOne = builder.valueOne;
        this.valueTwo = builder.valueTwo;
    }

    public String getValueOne() {
        return valueOne;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public boolean equals(final Object rhs) {
        return EqualsBuilder.reflectionEquals(this, rhs);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public static class Builder {
        private String valueOne;
        private String valueTwo;

        public Builder withValueOne(final String valueOne) {
            this.valueOne = valueOne;
            return this;
        }

        public Builder withValueTwo(final String valueTwo) {
            this.valueTwo = valueTwo;
            return this;
        }

        public DomainObject build() {
            return new DomainObject(this);
        }
    }
}
