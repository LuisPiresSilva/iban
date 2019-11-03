package net.luispiressilva.iban.utils.helper

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.CharacterStyle

class SimpleSpanBuilder() {
    class Span {
        private var startIndex: Int = 0
        internal var text: String
        private var styles: Array<out CharacterStyle>

        internal constructor(index: Int, text: String, vararg styles: CharacterStyle) {
            this.startIndex = index
            this.text = text
            this.styles = styles
        }

        constructor(text: String, vararg styles: CharacterStyle) : this(0, text, *styles)

        internal fun setIndex(index: Int): Span {
            return Span(index, this.text, *this.styles)
        }

        internal fun apply(spanStringBuilder: SpannableStringBuilder?) {
            if (spanStringBuilder == null) return
            for (style in styles) {
                spanStringBuilder.setSpan(
                        style,
                        startIndex,
                        startIndex + text.length,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
        }
    }

    private val spanSections = mutableListOf<Span>()
    private val stringBuilder = StringBuilder()

    constructor(text: String, vararg styles: CharacterStyle) : this() {
        plus(Span(text, *styles))
    }

    fun append(text: String, vararg styles: CharacterStyle): SimpleSpanBuilder {
        spanSections.add(Span(text, *styles).setIndex(stringBuilder.length))
        stringBuilder.append(text)
        return this
    }

    fun appendWithSpace(text: String, vararg styles: CharacterStyle): SimpleSpanBuilder {
        return append(text.plus(" "), *styles)
    }

    fun appendWithLineBreak(text: String, vararg styles: CharacterStyle): SimpleSpanBuilder {
        return append(text.plus("\n"), *styles)
    }

    operator fun plus(span: Span): SimpleSpanBuilder {
        spanSections.add(span.setIndex(stringBuilder.length))
        stringBuilder.append(span.text)
        return this
    }

    fun build(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(stringBuilder.toString())
        for (section in spanSections) {
            section.apply(ssb)
        }
        return ssb
    }

    override fun toString(): String {
        return stringBuilder.toString()
    }
}