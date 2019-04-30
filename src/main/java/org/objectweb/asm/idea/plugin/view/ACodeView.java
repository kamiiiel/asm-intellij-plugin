/*
 *
 *  Copyright 2011 Cédric Champeau
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */

package org.objectweb.asm.idea.plugin.view;


import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.keymap.KeymapManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.PopupHandler;
import org.objectweb.asm.idea.plugin.action.ShowASMDiffAction;
import org.objectweb.asm.idea.plugin.action.ShowASMSettingsAction;
import org.objectweb.asm.idea.plugin.common.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * Base class for editors which displays bytecode or ASMified code.
 */
public class ACodeView extends SimpleToolWindowPanel implements Disposable {
    protected final Project project;

    protected final ToolWindowManager toolWindowManager;
    protected final KeymapManager keymapManager;
    private final String extension;
    protected Editor editor;
    private ShowASMDiffAction showASMDiffAction;

    public ACodeView(final ToolWindowManager toolWindowManager, KeymapManager keymapManager, final Project project, final String fileExtension) {
        super(true, true);
        this.toolWindowManager = toolWindowManager;
        this.keymapManager = keymapManager;
        this.project = project;
        this.extension = fileExtension;
        setupUI();
    }

    private void setupUI() {
        final EditorFactory editorFactory = EditorFactory.getInstance();
        Document document = editorFactory.createDocument("");
        editor = editorFactory.createEditor(document, project, FileTypeManager.getInstance().getFileTypeByExtension(extension), true);
        showASMDiffAction = new ShowASMDiffAction(null, null, document, extension);

        final JComponent editorComponent = editor.getComponent();
        add(editorComponent);
        DefaultActionGroup group = new DefaultActionGroup();
        group.add(showASMDiffAction);
        group.add(new ShowASMSettingsAction());

        final ActionManager actionManager = ActionManager.getInstance();
        final ActionToolbar actionToolBar = actionManager.createActionToolbar(Constants.PLUGIN_WINDOW_NAME, group, true);
        final JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.add(actionToolBar.getComponent(), BorderLayout.CENTER);
        PopupHandler.installPopupHandler(editor.getContentComponent(), group, Constants.PLUGIN_WINDOW_NAME, actionManager);
        setToolbar(buttonsPanel);
    }

    public void setCode(final VirtualFile file, final String code) {
        final String text = showASMDiffAction.getDocument().getText();
        if (showASMDiffAction.getPreviousFile() == null || file == null || showASMDiffAction.getPreviousFile().getPath().equals(file.getPath()) && !Constants.NO_CLASS_FOUND.equals(text)) {
            if (file != null) showASMDiffAction.setPreviousCode(text);
        } else if (!showASMDiffAction.getPreviousFile().getPath().equals(file.getPath())) {
            showASMDiffAction.setPreviousCode(""); // reset previous code
        }
        showASMDiffAction.getDocument().setText(code);
        if (file != null) showASMDiffAction.setPreviousFile(file);
        editor.getScrollingModel().scrollTo(editor.offsetToLogicalPosition(0), ScrollType.MAKE_VISIBLE);
    }


    @Override
    public void dispose() {
        if (editor != null) {
            final EditorFactory editorFactory = EditorFactory.getInstance();
            editorFactory.releaseEditor(editor);
            editor = null;
        }
    }
}
