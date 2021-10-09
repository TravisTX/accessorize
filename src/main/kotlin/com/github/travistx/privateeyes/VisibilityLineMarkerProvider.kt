package com.github.travistx.accessorize

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.icons.AllIcons
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.intellij.util.ConstantFunction
import com.intellij.util.IconUtil
import org.jetbrains.plugins.ruby.ruby.lang.psi.RPsiElement
import org.jetbrains.plugins.ruby.ruby.lang.psi.controlStructures.methods.Visibility
import org.jetbrains.plugins.ruby.ruby.lang.psi.impl.controlStructures.methods.RMethodImpl
import java.awt.Color
import java.util.function.Supplier
import javax.swing.Icon

class VisibilityLineMarkerProvider : LineMarkerProvider {
    private var icon: Icon = IconUtil.colorize(AllIcons.Diff.Lock, Color(175,175,175))

    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? {
        if (element !is RMethodImpl) {
            return null;
        }
        if (element.visibility == Visibility.PUBLIC) {
            return null;
        }

        var nameElement = element.nameElement ?: return null

        return when (element.visibility) {
            Visibility.PRIVATE -> {
                GetLineMarkerInfo(nameElement, icon, "Private")
            }
            Visibility.PROTECTED -> {
                GetLineMarkerInfo(nameElement, icon, "Protected")
            }
            else -> {
                null
            }
        }
    }

    private fun GetLineMarkerInfo(nameElement: RPsiElement, icon: Icon, tooltip: String): LineMarkerInfo<RPsiElement> {
        return LineMarkerInfo(
            nameElement,
            nameElement.textRange,
            icon,
            ConstantFunction<PsiElement, String>(tooltip),
            null,
            GutterIconRenderer.Alignment.CENTER,
            Supplier<String> { tooltip }
        )
    }
}