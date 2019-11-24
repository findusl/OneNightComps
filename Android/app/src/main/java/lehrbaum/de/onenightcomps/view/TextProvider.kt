package lehrbaum.de.onenightcomps.view

import android.content.Context
import androidx.annotation.StringRes

typealias TextProvider = (c: Context) -> String

fun String.asTextProvider(): TextProvider {
	return {
		this
	}
}

fun @receiver:StringRes Int.asTextProvider(vararg formatArgs: Any?): TextProvider {
	return {
		it.getString(this, *formatArgs)
	}
}