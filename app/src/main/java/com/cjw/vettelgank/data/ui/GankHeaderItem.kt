package com.cjw.vettelgank.data.ui

data class GankHeaderItem(val name: String) : GankItem() {

    init {
        type = GankItem.ITEM_HEADER
    }
}