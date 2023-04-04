package com.researchspace.zenodo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * When creating a new Deposition, identifiers related to the deposition can
 * be included such as URIs, DOIs, and others
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatedIdentifier {
  private String identifier;
  private String relation;
  private String resource_type;
}

