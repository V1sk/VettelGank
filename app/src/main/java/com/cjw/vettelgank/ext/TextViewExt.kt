package com.cjw.vettelgank.ext

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import android.widget.TextView

// TextView设置autoLink之后，可自动回调点击的url
fun TextView.spanClick(action: (url: String) -> Unit) {
    if (text is Spannable) {
        val end = text.length
        val sp = text as Spannable
        val urls = sp.getSpans(0, end, URLSpan::class.java)
        val style = SpannableStringBuilder(text)
        style.clearSpans()
        for (urlSpan in urls) {
            val myURLSpan = MyURLSpan(urlSpan.url, action)
            style.setSpan(
                myURLSpan, sp.getSpanStart(urlSpan),
                sp.getSpanEnd(urlSpan),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )

        }
        text = style
    }

}

class MyURLSpan(val url: String, val action: (url: String) -> Unit) : ClickableSpan() {

    override fun onClick(widget: View) {
        action(url)
    }

}