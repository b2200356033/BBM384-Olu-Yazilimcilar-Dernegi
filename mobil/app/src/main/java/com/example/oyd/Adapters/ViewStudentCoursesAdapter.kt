package com.example.oyd.Adapters

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.oyd.Models.Course
import com.example.oyd.R

class ViewStudentCoursesAdapter(private val context: Context, private val courses: List<Course>) :
    RecyclerView.Adapter<ViewStudentCoursesAdapter.CourseViewHolder>() {

    private var onItemClickListener: ((Course) -> Unit)? = null
    private var onDeleteBtnClickListener: ((Course) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view =
            inflater.inflate(R.layout.course_view_item, parent, false)
        return CourseViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.bind(course)
        //creates a custom dialog popup saying if you want to add this course
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(course)
        }

    }


    fun setOnItemClickListener(listener: (Course) -> Unit) {
        onItemClickListener = listener
    }
    fun setOnDeleteBtnClickListener(listener: (Course) -> Unit) {
        onDeleteBtnClickListener = listener
    }


    override fun getItemCount(): Int {
        return courses.size
    }

    inner class CourseViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.courseName)
        private val creditTextView: TextView = itemView.findViewById(R.id.courseCredit)
        private val typeTextView: TextView = itemView.findViewById(R.id.courseType)
        private val dropBtn: Button = itemView.findViewById(R.id.dropCourseBtn)
        fun bind(course: Course) {
            nameTextView.text = course.name
            creditTextView.text = "("+course.credit.toString()+" Credit)"
            typeTextView.text = course.type
            if(course.type.equals("Mandatory")){
                typeTextView.setTextColor(Color.RED)
            }
            else{
                typeTextView.setTextColor(Color.YELLOW)
            }
            dropBtn.setOnClickListener {
                val outerAdapter = this@ViewStudentCoursesAdapter
                outerAdapter.onDeleteBtnClickListener?.invoke(course)
            }

        }

        //fonksiyon buraya

        fun showCustomDialog(course: Course, context: Context) {
            Toast.makeText(context,"function called", Toast.LENGTH_LONG).show()
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.drop_course_custom_dialog)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            val courseNameBox: TextView = dialog.findViewById(R.id.courseNameTxt)
            val dropBtn: Button = dialog.findViewById(R.id.dropCourseBtn)
            val cancelBtn: Button = dialog.findViewById(R.id.cancelBtn)
            courseNameBox.text=course.name
            dropBtn.setOnClickListener {
                //drop this course from this student's list
                Toast.makeText(context,"Clicked on drop", Toast.LENGTH_LONG).show()

                dialog.cancel()
            }
            cancelBtn.setOnClickListener {
                dialog.cancel()
            }
        }

    }
    //func


}