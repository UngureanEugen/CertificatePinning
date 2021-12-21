package com.certificatepinning.data

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class StoreResponse(@Element val stores: List<Store>)
