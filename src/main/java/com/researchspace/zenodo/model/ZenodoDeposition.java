package com.researchspace.zenodo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a Zenodo Deposition object which is returned by the API
 */
@Data
@NoArgsConstructor
public class ZenodoDeposition {

  @Data
  @NoArgsConstructor
  private class ZenodoDepositionLinks {
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

  private List<String> files; // might not be strings; new Deposition has empty list
  private ZenodoDepositionLinks links;

  public String created;
  private String modified;
  private long owner;

  private String submitted;
  private String title;
}
