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

package org.objectweb.asm.idea.config;
/**
 * Created by IntelliJ IDEA.
 * User: cedric
 * Date: 18/01/11
 * Time: 19:51
 */

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jdom.Element;

/**
 * A component created just to be able to configure the plugin.
 */
@State(
        name = ASMPluginConfiguration.COMPONENT_NAME,
        storages = {
                @Storage(id = "ASMPlugin", file = "$PROJECT_FILE$")
        }
)
public class ASMPluginComponent implements PersistentStateComponent<Element> {

    private static ApplicationConfig applicationConfig = new ApplicationConfig();

    @Override
    public Element getState() {
        Element root = new Element("state");
        Element asmNode = new Element("asm");
        asmNode.setAttribute("skipDebug", String.valueOf(applicationConfig.isSkipDebug()));
        asmNode.setAttribute("skipFrames", String.valueOf(applicationConfig.isSkipFrames()));
        asmNode.setAttribute("skipCode", String.valueOf(applicationConfig.isSkipCode()));
        asmNode.setAttribute("expandFrames", String.valueOf(applicationConfig.isExpandFrames()));
        root.addContent(asmNode);
        Element groovyNode = new Element("groovy");
        groovyNode.setAttribute("codeStyle", applicationConfig.getGroovyCodeStyle().toString());
        root.addContent(groovyNode);
        return root;
    }

    @Override
    public void loadState(final Element state) {
        Element asmNode = state.getChild("asm");
        if (asmNode != null) {
            final String skipDebugStr = asmNode.getAttributeValue("skipDebug");
            if (skipDebugStr != null) applicationConfig.setSkipDebug(Boolean.valueOf(skipDebugStr));
            final String skipFramesStr = asmNode.getAttributeValue("skipFrames");
            if (skipFramesStr != null) applicationConfig.setSkipFrames(Boolean.valueOf(skipFramesStr));
            final String skipCodeStr = asmNode.getAttributeValue("skipCode");
            if (skipCodeStr != null) applicationConfig.setSkipCode(Boolean.valueOf(skipCodeStr));
            final String expandFramesStr = asmNode.getAttributeValue("expandFrames");
            if (expandFramesStr != null) applicationConfig.setExpandFrames(Boolean.valueOf(expandFramesStr));
        }
        Element groovyNode = state.getChild("groovy");
        if (groovyNode != null) {
            String codeStyleStr = groovyNode.getAttributeValue("codeStyle");
            if (codeStyleStr != null) applicationConfig.setGroovyCodeStyle(GroovyCodeStyle.valueOf(codeStyleStr));
        }
    }


    // Property methods
    public static ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

}


