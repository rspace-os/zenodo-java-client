package com.researchspace.zenodo.client;

import com.researchspace.zenodo.model.ZenodoSubmission;
import com.researchspace.zenodo.model.ZenodoDeposition;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ZenodoClient {

    /**
     * Create a Zenodo submission.
     * @param submission The Zenodo submission to create.
     * @return The created Zenodo deposition.
     */
    ZenodoDeposition createDeposition(ZenodoSubmission submission) throws MalformedURLException, URISyntaxException ;

//     /**
//      * Get all datasets
//      * @return List of ZenodoDataset
//      */
//     List<ZenodoDataset> getDatasets();

//     /**
//      * Get a dataset by its DOI.
//      * @param doi The DOI of the dataset.
//      * @return The dataset.
//      */
//     ZenodoDataset getDataset(String doi);

//     /**
//      * Update a dataset.
//      * @param doi The dataset's DOI.
//      * @param zenodoDataset The dataset to update.
//      * @return The updated dataset.
//      */
//     ZenodoDataset updateDataset(String doi, ZenodoDataset zenodoDataset);

//     /**
//      * Upload a file to a Zenodo dataset.
//      * @param doi the Zenodo dataset DOI
//      * @param filename the filename of the file to upload
//      * @param file the file to upload
//      * @return the ZenodoFile object representing the uploaded file.
//      */
//     ZenodoFile stageFile(String doi, String filename, File file) throws IOException;

//     /**
//      * Attach a file to a Zenodo dataset by its url.
//      * @param doi The Zenodo dataset's doi.
//      * @param url The url of the file to attach.
//      * @return The ZenodoFile object representing the file.
//      */
//     ZenodoFile stageFile(String doi, String url);
}
