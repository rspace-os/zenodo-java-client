package com.researchspace.zenodo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A Zenodo Deposition, as returned by the API.
 */
@Data
@NoArgsConstructor
public class ZenodoDeposition {

  /**
   * Various endpoints that can be invoked to perform operations on the
   * Deposition.
   */
  @Data
  @NoArgsConstructor
  private class ZenodoDepositionLinks {

    // PUTing files at this endpoint will deposit them into the Deposition.
    private String bucket;

    private String discard;
    private String edit;
    private String files;
    private String html;
    private String latest_draft;
    private String latest_draft_html;
    private String publish;
    private String self;
  }

  private String conceptrecid;
  private long id;
  private long record_id;

  public long getId() {
    return this.id;
  }

  // This list will always be empty for new Depositions. As such, for now, it
  // is typed as a list of string.
  private List<String> files;
  private ZenodoDepositionLinks links;

  public String created;
  private String modified;
  private long owner;

  private String submitted;
  private String title;

  public String getBucketURL() {
    return this.links.bucket;
  }
}
