package com.mohsenoid.gifbrowser.presentation.mapper

interface Mapper<I, O> {

    fun map(input: I): O
}
