/*
 * ====================
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 Tirasa. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License("CDDL") (the "License").  You may not use this file
 * except in compliance with the License.
 *
 * You can obtain a copy of the License at
 * http://IdentityConnectors.dev.java.net/legal/license.txt
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing the Covered Code, include this
 * CDDL Header Notice in each file
 * and include the License file at identityconnectors/legal/license.txt.
 * If applicable, add the following below this CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * ====================
 */
package org.connid.bundles.cmd.methods;

import java.util.Iterator;
import java.util.Set;
import org.identityconnectors.common.StringUtil;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.AttributeUtil;
import org.identityconnectors.framework.common.objects.Name;
import org.identityconnectors.framework.common.objects.Uid;

public class CmdCreate extends CmdExec {

    private String scriptPath = null;

    private Set<Attribute> attrs;

    public CmdCreate(final String path, final Set<Attribute> attrs) {
        this.attrs = attrs;
        scriptPath = path;
    }

    public Uid execCreateCmd() {

        final Name name = AttributeUtil.getNameFromAttributes(attrs);

        if (name == null || StringUtil.isBlank(name.getNameValue())) {
            throw new IllegalArgumentException(
                    "No Name attribute provided in the attributes");
        }

        exec(scriptPath, createEnv());
        return new Uid(name.getNameValue());

    }

    private String[] createEnv() {
        String[] arrayAttributes = new String[attrs.size()];
        final Iterator attributes = attrs.iterator();
        int index = 0;
        while (attributes.hasNext()) {
            Attribute attribute = (Attribute) attributes.next();
            arrayAttributes[index] = attribute.getName() + "=" + attribute.getValue().get(0);
            index++;
        }
        return arrayAttributes;
    }
}