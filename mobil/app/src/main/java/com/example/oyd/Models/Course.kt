package com.example.oyd.Models

 class Course(var name: String, var department: String, var credit:Int, var type: String) {

     override fun toString(): String {
         return "Course(name='$name', department='$department', credit=$credit, type='$type')"
     }
 }