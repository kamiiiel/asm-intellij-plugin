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


/**
 * @author Kamiel Ahmadpour - 2017
 */
public class ApplicationConfig {
    public static final String APPLICATION_NAME = "ASM Bytecode Outline";
    private boolean skipFrames = false;
    private boolean skipDebug = false;
    private boolean skipCode = false;
    private boolean expandFrames = false;
    private GroovyCodeStyle groovyCodeStyle = GroovyCodeStyle.LEGACY;


    public boolean isSkipFrames() {
        return skipFrames;
    }

    public void setSkipFrames(boolean skipFrames) {
        this.skipFrames = skipFrames;
    }

    public boolean isSkipDebug() {
        return skipDebug;
    }

    public void setSkipDebug(boolean skipDebug) {
        this.skipDebug = skipDebug;
    }

    public boolean isSkipCode() {
        return skipCode;
    }

    public void setSkipCode(boolean skipCode) {
        this.skipCode = skipCode;
    }

    public boolean isExpandFrames() {
        return expandFrames;
    }

    public void setExpandFrames(boolean expandFrames) {
        this.expandFrames = expandFrames;
    }

    public GroovyCodeStyle getGroovyCodeStyle() {
        return groovyCodeStyle;
    }

    public void setGroovyCodeStyle(GroovyCodeStyle groovyCodeStyle) {
        this.groovyCodeStyle = groovyCodeStyle;
    }
}
