package com.researchspace.zenodo.model;

import java.net.URI;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * When creating a new Deposition, subjects -- terms from a controlled
 * vocabulary -- can be included.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ControlledVocabularyTerm {
  private String term;
  private URI identifier;
  private String scheme;
}

