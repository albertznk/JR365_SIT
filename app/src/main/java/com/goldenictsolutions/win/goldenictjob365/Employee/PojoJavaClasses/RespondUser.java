package com.goldenictsolutions.win.goldenictjob365.Employee.PojoJavaClasses;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Win on 2/3/2017.
 */

public class RespondUser {
        private String token;
        private List<User> user = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public List<User> getUser() {
            return user;
        }

        public void setUser(List<User> user) {
            this.user = user;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }


