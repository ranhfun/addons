/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.ranhfun.oauth2;

import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * This class represents a token for a CAS authentication (service ticket + user id + remember me).
 *
 * @since 1.2
 */
public class OAuthToken implements RememberMeAuthenticationToken {
    
    private static final long serialVersionUID = 8587329689973009598L;
    
    private String code = null;
    
    private String state = null;
    
    private String userId = null;
    
    private boolean isRememberMe = false;
    
    public OAuthToken(String code) {
        this.code = code;
    }
    
    public Object getPrincipal() {
        return userId;
    }
    
    public Object getCredentials() {
        return code;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isRememberMe() {
        return isRememberMe;
    }
    
    public void setRememberMe(boolean isRememberMe) {
        this.isRememberMe = isRememberMe;
    }
}
