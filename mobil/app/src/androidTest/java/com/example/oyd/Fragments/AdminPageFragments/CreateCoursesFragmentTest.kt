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
    fun validateCourseName_whenCourseNameIsEmpty() {
        assertFalse(fragment.validateCourseName(""))
    }

    @Test
    fun validateCourseName_whenCourseNameIsNotEmpty() {
        assertTrue(fragment.validateCourseName("Sample Course"))
    }

    @Test
    fun validateCourseDepartment_whenCourseDepartmentIsEmpty() {
        assertFalse(fragment.validateCourseDepartment(""))
    }

    @Test
    fun validateCourseDepartment_whenCourseDepartmentIsNotEmpty() {
        assertTrue(fragment.validateCourseDepartment("Computer Engineering"))
    }

    @Test
    fun validateCourseCredit_whenCourseCreditIsEmpty() {
        assertFalse(fragment.validateCourseCredit(""))
    }

    @Test
    fun validateCourseCredit_whenCourseCreditIsInvalid() {
        assertFalse(fragment.validateCourseCredit("0"))
    }

    @Test
    fun validateCourseCredit_whenCourseCreditIsValid() {
        assertTrue(fragment.validateCourseCredit("3"))
    }

    @Test
    fun validateCourseType_whenCourseTypeIsEmpty() {
        assertFalse(fragment.validateCourseType(""))
    }

    @Test
    fun validateCourseType_whenCourseTypeIsNotEmpty() {
        assertTrue(fragment.validateCourseType("Mandatory"))
    }


}