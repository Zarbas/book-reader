package com.example.android.bookreader.util

import org.hamcrest.CoreMatchers.`is`
import org.junit.runners.Parameterized
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertThat

@RunWith(Parameterized::class)
class MathUtilParametrizedTest(val number: Int, val prime: Boolean) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
                arrayOf(1, false),
                arrayOf(2, true),
                arrayOf(3, true),
                arrayOf(0, false),
                arrayOf(-5, false),
                arrayOf(17, true),
                arrayOf(131, true),
                arrayOf(133, false)
            )
    }

    @Test
    fun testIsPrime() {
        assertThat(MathUtil.isPrime(number), `is`(prime))
    }
}