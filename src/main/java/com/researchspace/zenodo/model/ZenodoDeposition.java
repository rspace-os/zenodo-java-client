package com.researchspace.zenodo.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import lombok.Data;

/**
 * A Zenodo Deposition, as returned by the API.
 */
@Data
public class ZenodoDeposition {

  /**
   * Various endpoints that can be invoked to perform operations on the Deposition.
   */
  @Data
  private class ZenodoDepositionLinks {

    // PUTing files at this endpoint will deposit them into the Deposition.
    private URL bucket;

    // This is a link to the depositions web page, where the user can amend and
    // publish the deposition.
    private URL html;

    private URL discard;
    private URL edit;
    private URL files;
    private URL latest_draft;
    private URL latest_draft_html;
    private URL publish;
    private URL self;
  }

  private String conceptrecid;
  private long id;
  private long record_id;
  private ZenodoDepositionMetadata metadata;

  // This list will always be empty for new Depositions. As such, for now, it
  // is typed as a list of string.
  private List<String> files;
  private ZenodoDepositionLinks links;

  public String created;
  private String modified;
  private long owner;

  private String submitted;
  private String title;

  public URL getBucketURL() {
    return this.links.bucket;
  }

  public URL getHtmlUrl() {
    return this.links.html;
  }

  public URL getDoiUrl() throws MalformedURLException {
    return new URL("https://doi.org/" + this.getMetadata().getPrereservedDoi().getDoi());
  }

}
