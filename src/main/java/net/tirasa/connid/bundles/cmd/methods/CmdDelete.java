/**
 * Copyright (C) 2011 ConnId (connid-dev@googlegroups.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.tirasa.connid.bundles.cmd.methods;

import org.identityconnectors.common.logging.Log;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.Uid;

public class CmdDelete extends CmdExec {

    private static final Log LOG = Log.getLog(CmdDelete.class);

    private String scriptPath = null;

    private final Uid uid;

    public CmdDelete(final ObjectClass oc, final String path, final Uid uid) {
        super(oc);

        this.uid = uid;
        scriptPath = path;
    }

    public void execDeleteCmd() {
        LOG.info("Executing deletion for {0}", uid);
        
        waitFor(exec(scriptPath, createEnv()));
    }

    private String[] createEnv() {
        LOG.ok("Creating environment for deletion with:");
        LOG.ok("ObjectClass: {0}" , oc.getObjectClassValue());
        LOG.ok("Environment variable {0}: {1}" , uid.getName(), uid.getUidValue());
        
        return new String[] {"OBJECT_CLASS=" + oc.getObjectClassValue(), uid.getName() + "=" + uid.getUidValue()};
    }
}
