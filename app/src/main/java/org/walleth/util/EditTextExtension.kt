package org.AimEx.util

import android.widget.EditText

fun EditText.hasText() = text?.isNotBlank() == true
