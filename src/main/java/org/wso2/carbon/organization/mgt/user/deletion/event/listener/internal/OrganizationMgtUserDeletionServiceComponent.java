/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.organization.mgt.user.deletion.event.listener.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.wso2.carbon.identity.organization.user.role.mgt.core.OrganizationUserRoleManager;
import org.wso2.carbon.user.core.listener.UserOperationEventListener;
import org.wso2.carbon.organization.mgt.user.deletion.event.listener.impl.OrganizationMgtUserDeletionEventListener;

/**
 * OSGi service component for user operation handler service.
 */
@Component(
        name = "organization.mgt.user.operation.service",
        immediate = true
)
public class OrganizationMgtUserDeletionServiceComponent {

    private static Log log = LogFactory.getLog(OrganizationMgtUserDeletionServiceComponent.class);

    @Activate
    protected void activate(ComponentContext context) {

        try {
            BundleContext bundleContext = context.getBundleContext();
            bundleContext.registerService(UserOperationEventListener.class, new OrganizationMgtUserDeletionEventListener(), null);
            log.info("Organization mgt user operations listener activated");
        } catch (Throwable e) {
            log.error("Error occurred while activating listener.", e);
        }
    }

    @Reference(name = "carbon.organization.and.user.role.mgt.component",
            service = org.wso2.carbon.identity.organization.user.role.mgt.core.OrganizationUserRoleManager.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetOrganizationUserRoleMgtService")
    protected void setOrganizationUserRoleMgtService(OrganizationUserRoleManager organizationUserRoleService) {

        if (log.isDebugEnabled()) {
            log.debug("Setting the OrganizationUserRole Manager Service.");
        }
        OrganizationMgtUserDeletionDataHolder
                .getInstance().setOrganizationUserRoleMgtService(organizationUserRoleService);
    }

    protected void unsetOrganizationUserRoleMgtService(OrganizationUserRoleManager organizationUserRoleService) {

        if (log.isDebugEnabled()) {
            log.debug("Unset the OrganizationUserRole Manager Service.");
        }
        OrganizationMgtUserDeletionDataHolder.getInstance().setOrganizationUserRoleMgtService(null);
    }
}
