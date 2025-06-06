package com.sfs.project.emp.web.common;

import java.util.ArrayList;


public class Users {
    private static final ThreadLocal<TestSessionVariables> testSessionThreadLocal = new ThreadLocal<>();

    public static <T> T sessionVariableCalled(EnvironmentVariables key) {
        return (T) getCurrentSession().get(key);
    }

    public static TestSessionVariables getCurrentSession() {

        if (testSessionThreadLocal.get() == null) {
            testSessionThreadLocal.set(new TestSessionVariables());
        }
        return testSessionThreadLocal.get();
    }

    public static SessionVariableSetter setSessionVariable(EnvironmentVariables key) {
        return new SessionVariableSetter(key);
    }

    public static void sessioNEnd() {
        testSessionThreadLocal.remove();
    }

    public void addUser(User user) {
        ArrayList<User> runTimeUsers = new ArrayList<>();
        if (sessionVariableCalled(EnvironmentVariables.USERS) == null) {
            runTimeUsers.add(user);
        } else {
            runTimeUsers = sessionVariableCalled(EnvironmentVariables.USERS);
            runTimeUsers.add(user);
        }
        setSessionVariable(EnvironmentVariables.USERS).to(runTimeUsers);
    }

    public User getCurrent() {
        User user;
        if (sessionVariableCalled(EnvironmentVariables.USERS) == null) {
            throw new NullPointerException("Users list is empty! "
                    + "make sure you added a user by calling Given a user ....");
        } else {
            ArrayList<Object> listOfUsers = sessionVariableCalled(EnvironmentVariables.USERS);
            user = (User) listOfUsers.get(listOfUsers.size() - 1);
        }
        return user;
    }

    public static class SessionVariableSetter {
        final EnvironmentVariables key;

        public SessionVariableSetter(EnvironmentVariables key) {
            this.key = key;
        }

        public <T> void to(T value) {
            if (value != null) {
                getCurrentSession().put(key, value);
            } else {
                getCurrentSession().remove(key);
            }
        }
    }
}
