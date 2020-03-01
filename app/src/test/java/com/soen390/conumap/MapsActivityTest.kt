package com.soen390.conumap
import org.junit.Test

import org.junit.Assert.*
import com.soen390.conumap.MapsActivity as MapsActivity

class MapsActivityTest {

    private val activity = MapsActivity()
    @Test
    fun sum_isCorrect() {
        assertEquals(0, activity.sum(0, 0))
        assertEquals(0, activity.sum(-5, 5))
        assertEquals(100, activity.sum(25, 75))
        assertEquals(-50, activity.sum(10, -60))
    }

    @Test
    fun square_isCorrect() {
        assertEquals(9.0, activity.square(3.0), 0.0)
        assertEquals(9.0, activity.square(-3.0), 0.0)
        assertEquals(0.0, activity.square(0.0), 0.0)
        assertEquals(17.64, activity.square(4.2), 0.0)
    }

    @Test
    fun reverse_isCorrect() {
        assertEquals("Android", activity.reverse("diordnA"))
        assertEquals("zyxwvutsrqponmlkjihgfedcba",
            activity.reverse("abcdefghijklmnopqrstuvwxyz"))
        assertEquals("4321", activity.reverse("1234"))
        assertEquals("", activity.reverse(""))
    }

    @Test
    fun canYouDrink_isCorrect() {
        assertEquals(true, activity.canYouDrink(20))
        assertEquals(true, activity.canYouDrink(18))
        assertEquals(false, activity.canYouDrink(10))
    }

    @Test
    fun originDestination_isCorrect(){
        assert
    }

}