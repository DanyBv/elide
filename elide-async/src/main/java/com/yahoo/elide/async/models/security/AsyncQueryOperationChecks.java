package com.yahoo.elide.async.models.security;

import java.security.Principal;
import java.util.Optional;

import com.yahoo.elide.annotation.SecurityCheck;
import com.yahoo.elide.async.models.AsyncQuery;
import com.yahoo.elide.async.models.AsyncQueryResult;
import com.yahoo.elide.async.models.QueryStatus;
import com.yahoo.elide.security.ChangeSpec;
import com.yahoo.elide.security.RequestScope;
import com.yahoo.elide.security.checks.OperationCheck;

public class AsyncQueryOperationChecks {
    @SecurityCheck(AsyncQueryOwner.PRINCIPAL_IS_OWNER)
    public static class AsyncQueryOwner extends OperationCheck<Object> {
        
        public static final String PRINCIPAL_IS_OWNER = "Principal is Owner";
       
        @Override
        public boolean ok(Object object, RequestScope requestScope, Optional<ChangeSpec> changeSpec) {
            Principal principal = ((Principal) requestScope.getUser().getOpaqueUser());

            if(object.getClass().equals(AsyncQuery.class)) {
            	return ((AsyncQuery) object).getPrincipalName().equals(principal.getName());
            } else {
            	return ((AsyncQueryResult) object).getPrincipalName().equals(principal.getName());
            }
        }
    }

    @SecurityCheck(AsyncQueryStatusValue.VALUE_IS_CANCELLED)
    public static class AsyncQueryStatusValue extends OperationCheck<AsyncQuery> {
        
        public static final String VALUE_IS_CANCELLED = "value is Cancelled";
       
        @Override
        public boolean ok(AsyncQuery object, RequestScope requestScope, Optional<ChangeSpec> changeSpec) {
            
            return changeSpec.get().getModified().toString().equals(QueryStatus.CANCELLED.name());
        }
    }
}