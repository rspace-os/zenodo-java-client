package com.researchspace.zenodo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZenodoDepositionMetadata {

  private String title;

  @JsonProperty("publication_date")
  private String publicationDate;

  private String description;


  @JsonProperty("access_right")
  private String accessRight;

  private String license;

  @JsonProperty("imprint_publisher")
  private String imprintPublisher;

  @JsonProperty("upload_type")
  private String uploadType;

  @JsonProperty("prereserve_doi")
  private ZenodoPrereservedDoi prereservedDoi;

  @Data
  public static class ZenodoPrereservedDoi {
    private String doi;
    private Long recid;
  }

}
