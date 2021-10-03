package com.github.travistx.testplugin

import com.intellij.codeInsight.daemon.impl.HighlightInfo
import com.intellij.codeInsight.daemon.impl.HighlightInfoType
import com.intellij.codeInsight.daemon.impl.HighlightVisitor
import com.intellij.codeInsight.daemon.impl.analysis.HighlightInfoHolder
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import org.jetbrains.plugins.ruby.ruby.lang.psi.controlStructures.methods.Visibility
import org.jetbrains.plugins.ruby.ruby.lang.psi.impl.controlStructures.methods.RMethodImpl

class RubyVisitor : HighlightVisitor {
    private val COLOR_ELEMENT: HighlightInfoType = HighlightInfoType.HighlightInfoTypeImpl(
        HighlightSeverity.INFORMATION,
        DefaultLanguageHighlighterColors.CONSTANT
    )
    private var highlightInfoHolder: HighlightInfoHolder? = null
    override fun suitableForFile(file: PsiFile): Boolean {
        println("travis suitableForFile:" + file.name)
        return true;
//        return file is RFile;
    }

    override fun visit(element: PsiElement) {
        if (element !is RMethodImpl)
        {
            return;
        }
        if(element.visibility != Visibility.PRIVATE)
        {
            return;
        }
        var nameElement = element.nameElement ?: return
        highlight(nameElement)
    }

    override fun analyze(file: PsiFile, updateWholeFile: Boolean, holder: HighlightInfoHolder, action: Runnable): Boolean {
        highlightInfoHolder = holder
        try {
            action.run()
        } finally {
            highlightInfoHolder = null
        }
        return true
    }

    override fun clone(): HighlightVisitor = RubyVisitor()

    fun highlight(element: PsiElement?) {
        assert(highlightInfoHolder != null)

        var newHighlightInfo = HighlightInfo.newHighlightInfo(COLOR_ELEMENT)
            .gutterIconRenderer(IconRenderer())

        var highlightColor = newHighlightInfo.range(element!!).create()
        highlightInfoHolder!!.add(highlightColor)
    }
}