/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.file.service;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

/**
 * File upload helper to write file to target repository.
 */
@Validated
public interface FileUploadService {

    /**
     * Get upload file extension according to predefined mapping by HTTP Request content type.
     *
     * @param contentType the content type from request
     * @return the mapped file extension
     */
    String getUploadFileExtension(@NotNull String contentType);

    /**
     * Check whether content type is allowed upload on server.
     *
     * @param contentType the content type from request
     * @return true if allowed otherwise false
     */
    boolean allowUploadImageContentType(@NotNull String contentType);

    /**
     * Write file to directory of repository for later file processing.
     *
     * @param relativeDirectory relative directory to write file into
     * @param file uploaded file
     * @return the save file path in temp repository
     */
    String storeUploadFileToRepository(@NotNull MultipartFile file, @NotNull String relativeDirectory);

    /**
     * Write file to temp directory of repository for later file processing.
     *
     * @param file uploaded file
     * @return the save file path in temp repository
     */
    String storeUploadFileToTempRepository(@NotNull MultipartFile file);

    /**
     * Delete upload file in temp repository.
     *
     * @param fileName the file name to be deleted
     * @return true if deleted successfully otherwise false 
     */
    boolean deleteUploadedFileInTempRepository(@NotNull String fileName);
    
    
    /**
     * Write file to directory of repository for later file processing.
     *
     * @param relativeDirectory relative directory to write file into
     * @param file uploaded file
     * @param fileName target file name to be stored
     * @return the save file path in temp repository
     */
    String storeUploadFileToRepository(@NotNull MultipartFile file, @NotNull String relativeDirectory, @NotNull String fileName);

}
