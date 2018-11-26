package com.sniped.ui

interface Presentable {

    fun onAttach() = Unit
    fun onDetach() = Unit

    companion object {
        val NULL = object : Presentable {}
    }
}
