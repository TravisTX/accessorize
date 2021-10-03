package com.github.travistx.testplugin

import com.intellij.icons.AllIcons
import com.intellij.openapi.editor.markup.GutterIconRenderer
import javax.swing.Icon

class IconRenderer : GutterIconRenderer() {
    override fun getIcon(): Icon {
        return AllIcons.Diff.Lock
    }

    override fun equals(other: Any?): Boolean {
        // TODO
        return false;
    }

    override fun hashCode(): Int {
        // TODO
        return -1
    }
}