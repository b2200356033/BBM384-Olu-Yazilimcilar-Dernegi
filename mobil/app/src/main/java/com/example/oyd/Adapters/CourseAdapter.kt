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

class CourseAdapter(private val context: Context, private var courses: List<Course>) :
    RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    private var onItemClickListener: ((Course) -> Unit)? = null
    public fun setCourseList(courseList:List<Course>){
        courses=courseList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view =
            inflater.inflate(R.layout.course_item, parent, false)
        return CourseViewHolder(view)
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


    override fun getItemCount(): Int {
        return courses.size
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.courseName)
        private val creditTextView: TextView = itemView.findViewById(R.id.courseCredit)
        private val typeTextView: TextView = itemView.findViewById(R.id.courseType)
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

        }
    }
}
