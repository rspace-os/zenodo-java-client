package com.researchspace.zenodo.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * When creating a new Deposition, metadata may be attached to the request that
 * will be associated with the new Deposition.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZenodoSubmission {

  /*
   * If a ZenodoSubmission is sent with the request to create a new Deposition,
   * these three properties are required.
   */
  private String title;
  private String description;
  private String upload_type;
  private boolean prereserve_doi;

  private List<RelatedIdentifier> related_identifiers = new ArrayList<>();

  private List<ControlledVocabularyTerm> subjects = new ArrayList<>();
}
