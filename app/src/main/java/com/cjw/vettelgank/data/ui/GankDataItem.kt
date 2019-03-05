package com.cjw.vettelgank.data.ui

import com.cjw.vettelgank.data.Gank

data class GankDataItem(val gank: Gank) : GankItem() {
    init {
        type = GankItem.ITEM_DATA
    }
}