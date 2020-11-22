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

package org.wso2.carbon.custom.user.deletion.event.listener.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.custom.user.deletion.event.listener.internal.CustomUserDeletionDataHolder;
import org.wso2.carbon.identity.core.AbstractIdentityUserOperationEventListener;
import org.wso2.carbon.identity.organization.user.role.mgt.core.exception.OrganizationUserRoleMgtException;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.user.core.UserStoreManager;

public class CustomUserDeletionEventListener extends AbstractIdentityUserOperationEventListener {

    private static Log log = LogFactory.getLog(CustomUserDeletionEventListener.class);

    @Override
    public int getExecutionOrderId() {
        return 91;
    }

    @Override
    public boolean doPostDeleteUserWithID(String userId, UserStoreManager userStoreManager) throws UserStoreException {

        try {
            CustomUserDeletionDataHolder.getInstance().getOrganizationUserRoleMgtService()
                    .deleteOrganizationsUserRoleMappings(userId);
            if (log.isDebugEnabled()) {
                log.debug("Delete all organization user role mappings assigned to user: " + userId);
            }
        } catch (OrganizationUserRoleMgtException e) {
            throw new UserStoreException(e);
        }
        return true;
    }
}
