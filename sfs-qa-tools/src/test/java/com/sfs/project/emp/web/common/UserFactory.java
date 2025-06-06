package com.sfs.project.emp.web.common;

import java.util.Map;
import java.util.Optional;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

public class UserFactory {

    // This will hold the parsed JSON structure
	private static LinkedTreeMap<String, ? extends Map> myUSERS;

    // This is the field used for deserialization by Gson
	@SerializedName("users")
    private LinkedTreeMap<String, ? extends Map> users;

    static {
        loadUsers(FilePathReader.getFileUserJson(System.getProperty("env")));
    }

    /**
     * Retrieves a User object populated from the JSON file based on the provided key.
     */
    public User getUser(String userKey) {
        User user = new User();
        user.setUserTestName(userKey);
        return getUserFromJSON(user);
    }

    /**
     * Reads the user details from the JSON and updates the User object.
     */
    public User getUserFromJSON(User user) {
        Map<String, Object> jsonUser = (Map<String, Object>) myUSERS.get(user.getUserTestName());
        user.setUsername(Optional.ofNullable(jsonUser.get("userName")).orElse("").toString());
        user.setPassword(Optional.ofNullable(jsonUser.get("password")).orElse("").toString());
        return user;
    }

    /**
     * Loads the users.json and sets the static map.
     */
    private static void loadUsers(String filepath) {
    	 try {
    	        UserFactory myClass = new UserFactory();
    	        myClass = (UserFactory) new JsonUtil().deserialize(myClass, filepath);

    	        if (myClass == null || myClass.users == null) {
    	            throw new RuntimeException("UserFactory or users is null after deserialization. Check the JSON structure.");
    	        }

    	        myUSERS = myClass.users;
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        throw new RuntimeException("Failed to load users from file: " + filepath, e);
    	    }
    }
}
