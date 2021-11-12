package com.example.task5_mvvm.view

import android.content.Context
import android.text.Html
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.example.task5_mvvm.R
import com.example.task5_mvvm.constants.DEF_STYLE_RES

/**
 *
 * Кастомный элемент на основе TextView
 *
 * @property context контекст, на котором происходит создание View
 * @property attrs интерфейс для доступа к набору атрибутов
 * @property defStyleAttr ссылка на ресурс стиля по умолчанию
 */

class CustomTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = DEF_STYLE_RES
) : AppCompatTextView(context, attrs, defStyleAttr) {

    var htmlText: String? = null
        set(value) {
            field = value
            text = value?.let {
                Html.fromHtml(value)
            }
        }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextView,
            defStyleAttr,
            DEF_STYLE_RES
        ).also { typedArray ->
            htmlText = typedArray.getString(R.styleable.CustomTextView_htmlText)
        }.recycle()
    }

}