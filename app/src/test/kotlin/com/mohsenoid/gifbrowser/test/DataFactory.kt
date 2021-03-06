package com.mohsenoid.gifbrowser.test

import java.util.ArrayList
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

object DataFactory {

    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomStringList(count: Int): List<String> {
        val list: MutableList<String> = ArrayList()

        for (i: Int in 0 until count) {
            list.add(randomString())
        }

        return list
    }

    fun randomInt(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Int {
        return ThreadLocalRandom.current().nextInt(min, max)
    }

    fun randomIntList(count: Int, min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): List<Int> {
        val list: MutableList<Int> = ArrayList()

        for (i: Int in 0 until count) {
            list.add(randomInt(min, max))
        }

        return list
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }
}
