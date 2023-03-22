package com.researchspace.zenodo.model;

import lombok.Data;

@Data
public class ZenodoFile {

    private String path;
    private Integer size;
    private String mimeType;
    private String status;


}
