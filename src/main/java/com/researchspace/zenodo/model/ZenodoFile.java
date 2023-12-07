package com.researchspace.zenodo.model;

import lombok.Data;
import java.net.URL;

/**
 * Metadata returned by the Zenodo server upon a successful submission of a
 * file.
 */
@Data
public class ZenodoFile {
    private String key;
    private Integer size;
    private String mimetype;
    private String checksum;
    private ZenodoFileLinks links;

    @Data
    private class ZenodoFileLinks {

      // The URL to access this data as a JSON object
      private URL self;
    }
}
