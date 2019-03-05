package com.cjw.vettelgank.data.ui

open class GankItem {

    companion object {
        const val ITEM_HEADER = 0
        const val ITEM_DATA = 1
    }

    open var type: Int = ITEM_HEADER
}