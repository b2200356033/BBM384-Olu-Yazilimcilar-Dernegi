package com.example.oyd.Fragments.AdminPageFragments

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class StartSemesterFragmentTest{
    private lateinit var fragment: StartSemesterFragment

    @Before
    fun setUp() {
        fragment = StartSemesterFragment()
    }

    @Test
    fun testDateWhenStartDateIsEmpty(){
        assertFalse(fragment.validateDates("","22/01/2022"))
    }

    @Test
    fun testDateWhenEndDateIsEmpty(){
        assertFalse(fragment.validateDates("11/22/2023",""))
    }

    @Test
    fun testDateWhenEndDateIsBeforeTheStartDay(){
        assertFalse(fragment.validateDates("01/01/2023","01/01/2022"))
    }

    @Test
    fun testDateWhenDatesAreValid(){
        assertTrue(fragment.validateDates("09/09/2022","15/06/2023"))
    }
}