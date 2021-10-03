package com.github.travistx.testplugin

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.icons.AllIcons
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.intellij.util.ConstantFunction
import org.jetbrains.plugins.ruby.ruby.lang.psi.controlStructures.methods.Visibility
import org.jetbrains.plugins.ruby.ruby.lang.psi.impl.controlStructures.methods.RMethodImpl

class VisibilityLineMarkerProvider : LineMarkerProvider {
    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? {
        if (element !is RMethodImpl) {
            return null;
        }
        if (element.visibility != Visibility.PRIVATE) {
            return null;
        }

        var nameElement = element.nameElement ?: return null

        return LineMarkerInfo(
            nameElement,
            nameElement.textRange,
            AllIcons.Diff.Lock,
            ConstantFunction<PsiElement, String>("Private"),
            null,
            GutterIconRenderer.Alignment.CENTER
        )
    }
}