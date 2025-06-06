package com.sfs.project.emp.web.common;

import java.util.List;

public final class FilePathReader {
    public static final String TEMPLATE_WEB_PROPERTYNAME = "template.mob.propertyname";
    private static final String RESOURCE_FOLDER = System.getProperty("user.dir")
            + "/src/test/resources/";
    private static final String USERS_FOLDER = RESOURCE_FOLDER + "data/users/";
    private static final String DATA_FOLDER = RESOURCE_FOLDER + "data/";
    private static final String ENVIRONMENT_PROPERTYNAME = RESOURCE_FOLDER
            + "environment/properties/";
    private static final String UPLOAD_FILE_FOLDER = DATA_FOLDER + "UploadFiles/";
    private static final String DOWNLOAD_FILE_FOLDER = DATA_FOLDER + "DownloadFiles/";


    public FilePathReader() {
        super();
    }

    public static String getFileUserJson(String fileUserName) {
        if (fileUserName.contains("qa")) {
            return USERS_FOLDER + "qa_users.json";
        } else if(fileUserName.contains("prod")){
            return USERS_FOLDER + fileUserName + "_users.json";
        } else {
        	 return USERS_FOLDER + fileUserName + "dev_users.json";
        }
    }

    public static String getEnvironmentPropertyFiles(String envfileName) {
        return ENVIRONMENT_PROPERTYNAME + envfileName + ".properties";
    }

    public static String getUserPropertyFiles(String envfileName) {
        return USERS_FOLDER + envfileName + ".properties";
    }

    public static String getExcelfile(String datafileName) {
        return DATA_FOLDER + datafileName + ".xlsx";
    }

    public static String getfilePath(String datafileNamewithExt) {
        return UPLOAD_FILE_FOLDER + datafileNamewithExt;
    }
    
    public static String getDownloadFolder() {
        return DOWNLOAD_FILE_FOLDER;
    }
}
