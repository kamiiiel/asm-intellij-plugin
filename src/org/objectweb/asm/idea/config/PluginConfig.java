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

package org.objectweb.asm.idea.config;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;

import javax.swing.*;

/**
 * @author Kamiel Ahmadpour - 2017
 */
public class PluginConfig implements Configurable {

    private ApplicationConfig applicationConfig;
    private ASMPluginConfiguration configDialog;

    public PluginConfig() {
        this.applicationConfig = ASMPluginComponent.getApplicationConfig();

    }

    @Nls
    public String getDisplayName() {
        return applicationConfig.APPLICATION_NAME;
    }

    public String getHelpTopic() {
        return null;
    }

    public JComponent createComponent() {
        if (configDialog == null) configDialog = new ASMPluginConfiguration();
        return configDialog.getRootPane();
    }

    public boolean isModified() {
        return configDialog != null && configDialog.isModified(applicationConfig);
    }

    public void apply() throws ConfigurationException {
        if (configDialog != null) {
            configDialog.getData(applicationConfig);
        }
    }

    public void reset() {
        if (configDialog != null) {
            configDialog.setData(applicationConfig);
        }
    }

    public void disposeUIResources() {
        configDialog = null;
    }

}
