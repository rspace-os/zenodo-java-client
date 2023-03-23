package com.researchspace.zenodo.model;

import lombok.Data;

/**
 * Metadata returned by the Zenodo server upon a successful submission of a
 * file.
 */
@Data
public class ZenodoFile {
    private String path;
    private Integer size;
    private String mimeType;
    private String status;
}
