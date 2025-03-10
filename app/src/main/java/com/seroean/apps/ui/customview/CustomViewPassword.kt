package com.seroean.apps.ui.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.seroean.apps.R

class CustomViewPassword @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var isPasswordVisible = false

    private val eyeIcon: Drawable by lazy {
        ContextCompat.getDrawable(context, R.drawable.ic_eye_open)!!.mutate()
    }
    private val eyeOffIcon: Drawable by lazy {
        ContextCompat.getDrawable(context, R.drawable.ic_eye_closed)!!.mutate()
    }

    init {
        setupView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupView() {
        setBackgroundResource(R.drawable.edit_text_background)
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        transformationMethod = PasswordTransformationMethod.getInstance()
        setEyeIcon(false)

        setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = compoundDrawablesRelative[2] ?: return@setOnTouchListener false
                if (event.rawX >= (right - drawableEnd.bounds.width() - paddingEnd)) {
                    togglePasswordVisibility()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible

        inputType = if (isPasswordVisible) {
            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        transformationMethod = if (isPasswordVisible) null else PasswordTransformationMethod.getInstance()
        setSelection(text?.length ?: 0)

        typeface = Typeface.create(typeface, Typeface.NORMAL)

        post {
            setEyeIcon(isPasswordVisible)
            invalidate()
        }
    }

    private fun setEyeIcon(isVisible: Boolean) {
        val icon = if (isVisible) eyeIcon else eyeOffIcon
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, icon, null)
    }
}
