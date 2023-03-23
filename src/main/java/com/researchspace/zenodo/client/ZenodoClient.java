package com.researchspace.zenodo.client;

import com.researchspace.zenodo.model.ZenodoSubmission;
import com.researchspace.zenodo.model.ZenodoDeposition;
import com.researchspace.zenodo.model.ZenodoFile;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.net.URL;

/*
 * This interface declares the operations that this library supports with
 * regard to the Zenodo API.
 */
public interface ZenodoClient {

    /**
     * Create a Zenodo Deposition.
     *
     * A Zenodo Deposition is used for editing and uploading records to Zenodo
     * and acts as a conceptual grouping of files. Here, we provide two methods
     * for creating a new Deposition; one with predefined metadata and one
     * without. In either case, a new Deposition is created on the Zenodo
     * server and its details are returned.
     *
     * @param submission The metadata to associate with the Deposition.
     * @return The created Zenodo deposition.
     */
    ZenodoDeposition createDeposition(ZenodoSubmission submission) throws IOException;
    ZenodoDeposition createDeposition() throws IOException;

    /**
     * Once a Zenodo deposition has been created, files can be deposited within
     * it.
     *
     * @param deposition The Deposition into which the file will be deposited.
     * @param filename The name of the file to be deposited.
     * @param file The file to be deposited.
     * @return The details of the successful deposit.
     */
    ZenodoFile depositFile(ZenodoDeposition deposition, String filename, File file) throws IOException;
}
