package com.seroean.apps.ui.customview

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.seroean.apps.R

class CustomViewLocation : AppCompatEditText, View.OnFocusChangeListener {

    private var isLocationValid = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init()
    }

    private fun init() {
        setBackgroundResource(R.drawable.edit_text_background)
        inputType = InputType.TYPE_CLASS_TEXT
        onFocusChangeListener = this
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateLocation()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (!hasFocus) {
            validateLocation()
        }
    }

    private fun validateLocation() {
        val location = text.toString().trim()
        isLocationValid = location.isNotEmpty() && location.length <= 100
        error = if (!isLocationValid) {
            if (location.isEmpty()) {
                resources.getString(R.string.ERROR_LOCATION_EMPTY)
            } else {
                resources.getString(R.string.ERROR_LOCATION_TOOLONG)
            }
        } else {
            null
        }
    }
}
