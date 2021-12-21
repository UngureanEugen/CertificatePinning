package com.certificatepinning.data

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "store")
data class Store(
    @PropertyElement(name = "name")
    val name: String,
    @PropertyElement(name = "address")
    val address: String,
    @PropertyElement(name = "postalCode")
    val postalCode: String,
    @PropertyElement(name = "city")
    val city: String
)
