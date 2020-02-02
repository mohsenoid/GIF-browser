package com.mohsenoid.gifbrowser.data.mapper

interface Mapper<I, O> {

    fun map(input: I): O
}
