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
    fun testCourseNameWhenCourseNameIsEmpty(){
        assertFalse(fragment.validateInputs("","Computer Engineering","4","Mandatory"))
    }

    @Test
    fun testCourseDepartmentWhenCourseDepartmentIsEmpty() {
        assertFalse(fragment.validateInputs("ValidCourseName","","4","Mandatory"))
    }

    @Test
    fun testCourseCreditWhenCourseCreditIsEmpty() {
        assertFalse(fragment.validateInputs("ValidCourseName","Computer Engineering","","Mandatory"))
    }

    @Test
    fun testCourseCreditWhenCourseCreditIsInvalid() {
        assertFalse(fragment.validateInputs("ValidCourseName","Computer Engineering","0","Mandatory"))
    }

    @Test
    fun testCourseTypeWhenCourseTypeIsEmpty() {
        assertFalse(fragment.validateInputs("ValidCourseName","Computer Engineering","3",""))
    }

    @Test
    fun testCourseTypeWhenInputsAreValid() {
        assertTrue(fragment.validateInputs("ValidCourseName","Computer Engineering","3","Elective"))
    }



}