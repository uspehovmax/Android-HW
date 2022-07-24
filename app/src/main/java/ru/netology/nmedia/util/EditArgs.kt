package ru.netology.nmedia.util

import android.os.Bundle
import ru.netology.nmedia.activity.EditPostResult
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object EditArgs : ReadWriteProperty<Bundle, EditPostResult> {
    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: EditPostResult) {
        thisRef.putSerializable(property.name, EditPostResult(value.content, value.video))
    }

    override fun getValue(thisRef: Bundle, property: KProperty<*>): EditPostResult {
        return thisRef.get(property.name) as EditPostResult
    }
}