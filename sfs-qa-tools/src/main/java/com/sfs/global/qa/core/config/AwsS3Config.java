package com.sfs.global.qa.core.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AwsS3Config {

    private String endpointUrl;
    private String regionName;
    private String accessKey;
    private String secretKey;
    private String bucketName;

}
