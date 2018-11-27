package com.parkingapp.core

interface Command {

    fun execute()

    companion object {
        val NULL = {} as Command
    }
}
