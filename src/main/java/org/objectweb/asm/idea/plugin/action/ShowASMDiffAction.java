/*
 *
 *  Copyright 2017 Kamiel Ahmadpour
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

package org.objectweb.asm.idea.plugin.action;

import com.intellij.diff.DiffContentFactory;
import com.intellij.diff.DiffManager;
import com.intellij.diff.contents.DocumentContent;
import com.intellij.diff.requests.SimpleDiffRequest;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.idea.plugin.common.Constants;

public class ShowASMDiffAction extends AnAction {
    private static final String DIFF_WINDOW_TITLE = "Show differences from previous class contents";
    private static final String[] DIFF_TITLES = {"Previous version", "Current version"};
    private String previousCode;
    private VirtualFile previousFile;
    private Document document;
    private String extension;


    public ShowASMDiffAction(String previousCode, VirtualFile previousFile, Document document, String extension) {
        super("Show Differences",
                "Shows differences from the previous version of bytecode for this file",
                AllIcons.Actions.Diff);
        this.previousCode = previousCode;
        this.previousFile = previousFile;
        this.document = document;
        this.extension = extension;
    }

    @Override
    public void update(final AnActionEvent e) {
        e.getPresentation().setEnabled(!"".equals(previousCode) && (previousFile != null));
    }

    @Override
    public boolean displayTextInToolbar() {
        return true;
    }

    @Override
    public void actionPerformed(final AnActionEvent e) {
        PsiFile psiFile = PsiFileFactory.getInstance(e.getProject()).createFileFromText(Constants.FILE_NAME, FileTypeManager.getInstance().getFileTypeByExtension(extension), "");
        DocumentContent currentContent = (previousFile == null) ? DiffContentFactory.getInstance().create("") : DiffContentFactory.getInstance().create(document.getText(), psiFile.getFileType());
        DocumentContent oldContent = (previousCode == null) ? DiffContentFactory.getInstance().create("") : DiffContentFactory.getInstance().create(previousCode, psiFile.getFileType());
        SimpleDiffRequest request = new SimpleDiffRequest(DIFF_WINDOW_TITLE, oldContent, currentContent, DIFF_TITLES[0], DIFF_TITLES[1]);
        DiffManager.getInstance().showDiff(e.getProject(), request);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }


    // Property files
    public String getPreviousCode() {
        return previousCode;
    }

    public void setPreviousCode(String previousCode) {
        this.previousCode = previousCode;
    }

    public VirtualFile getPreviousFile() {
        return previousFile;
    }

    public void setPreviousFile(VirtualFile previousFile) {
        this.previousFile = previousFile;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
