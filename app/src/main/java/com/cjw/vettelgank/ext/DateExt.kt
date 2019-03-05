package com.cjw.vettelgank.ext

import java.text.SimpleDateFormat
import java.util.*

//格式化日期
fun Date.getDateString(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
    return sdf.format(this)
}