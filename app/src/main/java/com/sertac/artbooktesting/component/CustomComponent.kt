package com.sertac.artbooktesting.component

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.sertac.artbooktesting.R


//class CustomComponent (
//    context: Context,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0
//) : LinearLayout(context, attrs, defStyleAttr) {
//
//    init {
//        LayoutInflater.from(context)
//            .inflate(R.layout.custom_view, this, true)
//
//
//        attrs?.let {
//            val typedArray = context.obtainStyledAttributes(it,
//                R.styleable.custom_component_attributes, 0, 0)
//            val title = resources.getText(typedArray
//                .getResourceId(R.styleable
//                    .custom_component_attributes_custom_component_title,
//                    R.string.component_one))
//
//            my_title.text = title
//            my_edit.hint =
//                "${resources.getString(R.string.hint_text)} $title"
//
//            typedArray.recycle()
//        }
//    }
//}

class CustomComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private lateinit var bindingUtil:DataBindingUtil
    private lateinit var my_title : TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_view, this, true)
        orientation = VERTICAL

        componentRequirements()


        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.custom_component_attributes, 0, 0)
            val title = resources.getText(typedArray
                .getResourceId(R.styleable.custom_component_attributes_custom_component_title, R.string.component_one))



            my_title.text = title
//            my_edit.hint = "${resources.getString(R.string.hint_text)} $title"

            typedArray.recycle()
        }
    }

    private fun componentRequirements(){
        my_title = findViewById<TextView>(R.id.my_title)
    }

}