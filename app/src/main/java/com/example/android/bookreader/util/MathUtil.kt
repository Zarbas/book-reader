package com.example.android.bookreader.util

import kotlin.math.sqrt

object MathUtil {
    fun isPrime(value: Int) : Boolean {
        if(value < 2) return false
        if(value == 2) return true
        if(value % 2 == 0) return false

        val end = sqrt(value.toDouble()).toInt()

        for(d in 3..end step 2) {
            if(value % d == 0) {
                return false
            }
        }

        return true
    }
}
