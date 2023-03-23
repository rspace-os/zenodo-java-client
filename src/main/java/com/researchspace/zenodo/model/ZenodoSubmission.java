package com.researchspace.zenodo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Represents a Zenodo Submission object which can be used to create a new
 * submission.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZenodoSubmission {
  private String title;
  private String description;
  private String upload_type;
}
