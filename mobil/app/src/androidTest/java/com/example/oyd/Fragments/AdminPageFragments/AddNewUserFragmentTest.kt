package com.example.oyd.Fragments.AdminPageFragments

import com.example.oyd.databinding.FragmentAddNewUserBinding
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AddNewUserFragmentTest{
    private lateinit var fragment: AddNewUserFragment


    @Before
    fun setUp() {
        fragment = AddNewUserFragment()
    }

    @Test
    fun testRoleWhenRoleIsEmpty(){
        assertFalse(fragment.validateInputForTest("","ValidName",
            "ValidSurname","validMail@gmail.com","ValidPassword123"))
    }

    @Test
    fun testUserNameWhenUserNameIsEmpty(){
        assertFalse(fragment.validateInputForTest("Instructor","",
            "ValidSurname","validMail@gmail.com","ValidPassword123"))
    }

    @Test
    fun testUserSurnameWhenUserSurnameIsEmpty(){
        assertFalse(fragment.validateInputForTest("Instructor","ValidName",
            "","validMail@gmail.com","ValidPassword123"))
    }

    @Test
    fun testUserMailWhenUserMailIsEmpty(){
        assertFalse(fragment.validateInputForTest("Instructor","ValidName",
            "ValidSurname","","ValidPassword123"))
    }

    @Test
    fun testUserMailWhenUserMailIsInvalid(){
        assertFalse(fragment.validateInputForTest("Instructor","ValidName",
            "ValidSurname","invalidMail.com","ValidPassword123"))
    }

    @Test
    fun testUserPasswordWhenUserPasswordIsEmpty(){
        assertFalse(fragment.validateInputForTest("Instructor","ValidName",
            "ValidSurname","validMail@gmail.com",""))
    }

    @Test
    fun testUserPasswordWhenUserPasswordIsInvalid(){
        assertFalse(fragment.validateInputForTest("Instructor","ValidName",
            "ValidSurname","validMail@gmail.com","123"))
    }

    @Test
    fun testUserInputsWhenUserInputsAreValid(){
        assertTrue(fragment.validateInputForTest("Instructor","ValidName",
            "ValidSurname","validMail@gmail.com","ValidPassword123"))
    }
}