import com.example.oyd.Fragments.AdminPageFragments.CreateCoursesFragment
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class CreateCoursesFragmentTest{
    private lateinit var fragment: CreateCoursesFragment

    @Before
    fun setUp() {
        fragment = CreateCoursesFragment()
    }



    @Test
    fun validateCourseNameWhenCourseNameIsEmpty(){
        assertFalse(fragment.validateInputs("","Computer Engineering","4","Mandatory"))
    }

    @Test
    fun validateCourseDepartmentWhenCourseDepartmentIsEmpty() {
        assertFalse(fragment.validateInputs("ValidCourseName","","4","Mandatory"))
    }

    @Test
    fun validateCourseCreditWhenCourseCreditIsEmpty() {
        assertFalse(fragment.validateInputs("ValidCourseName","Computer Engineering","","Mandatory"))
    }

    @Test
    fun validateCourseCreditWhenCourseCreditIsInvalid() {
        assertFalse(fragment.validateInputs("ValidCourseName","Computer Engineering","0","Mandatory"))
    }

    @Test
    fun validateCourseTypeWhenCourseTypeIsEmpty() {
        assertFalse(fragment.validateInputs("ValidCourseName","Computer Engineering","3",""))
    }

    @Test
    fun validateCourseTypeWhenInputsAreValid() {
        assertTrue(fragment.validateInputs("ValidCourseName","Computer Engineering","3","Elective"))
    }



}