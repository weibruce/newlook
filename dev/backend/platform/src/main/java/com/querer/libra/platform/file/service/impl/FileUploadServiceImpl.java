/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.file.service.impl;

import java.io.IOException;
import java.util.Properties;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.querer.libra.platform.file.exception.FileUploadException;
import com.querer.libra.platform.file.service.FileRepositoryService;
import com.querer.libra.platform.file.service.FileUploadService;
import com.querer.libra.platform.util.constant.PlatformConstants;

/**
 * File upload helper to write file to target repository.
 */
public class FileUploadServiceImpl implements FileUploadService {

    /* fields          ------------------------------------------------------*/

    private String allowImageExtensions;
 
    private Properties mediaTypeExtensionMapping;

    private FileRepositoryService fileRepositoryService;

    /* public methods  ------------------------------------------------------*/

    /**
     * @see FileUploadService#getUploadFileExtension(String)
     */
    @Override
    public String getUploadFileExtension(@NotNull String contentType) {
        return this.mediaTypeExtensionMapping.getProperty(contentType);
    }

    /**
     * @see FileUploadService#allowUploadImageContentType(String)
     */
    @Override
    public boolean allowUploadImageContentType(@NotNull String contentType) {
        String extension = getUploadFileExtension(contentType);
        String extensionWithoutDot = StringUtils.substringAfterLast(extension, PlatformConstants.DOT);
        boolean isAllowed = StringUtils.isNotBlank(extensionWithoutDot)
                && StringUtils.containsIgnoreCase(this.allowImageExtensions, extensionWithoutDot);
        return isAllowed;
    }

    /**
     * @see FileUploadService#storeUploadFileToRepository(MultipartFile, String)
     */
    @Override
    public String storeUploadFileToRepository(@NotNull MultipartFile file, @NotNull String relativeDirectory) {
        return storeUploadFileToRepository(file, relativeDirectory, StringUtils.EMPTY);
    }
    
    
    /**
     * @see FileUploadService#storeUploadFileToRepository(MultipartFile, String, String)
     */
    @Override
    public String storeUploadFileToRepository(@NotNull MultipartFile file, @NotNull String relativeDirectory, @NotNull String fileName) {
        if (!file.isEmpty()) {

            if (!allowUploadImageContentType(file.getContentType())) {
                throw new FileUploadException("Non supported file content type to upload");
            }
            // generate file path
            String fileExtension = getUploadFileExtension(file.getContentType());
            try {
                return this.fileRepositoryService.writeFileToRepository(relativeDirectory, file.getBytes(), fileExtension, fileName);
            } catch (IOException e) {
                throw new FileUploadException("Failed to store upload file.", e);
            }
        } else {
            throw new FileUploadException("Failed to upload because the file was empty.");
        }
    }

    /**
     * @see FileUploadService#storeUploadFileToTempRepository(MultipartFile)
     */
    @Override
    public String storeUploadFileToTempRepository(@NotNull MultipartFile file) {
        if (!file.isEmpty()) {

            if (!allowUploadImageContentType(file.getContentType())) {
                throw new FileUploadException("Non supported file content type to upload");
            }

            // generate file path
            String fileExtension = getUploadFileExtension(file.getContentType());
            try {
                return this.fileRepositoryService.writeFileToTempRepository(file.getBytes(), fileExtension);
            } catch (IOException e) {
                throw new FileUploadException("Failed to store upload file.", e);
            }
        } else {
            throw new FileUploadException("Failed to upload because the file was empty.");
        }
    }

    /**
     * @see FileUploadService#deleteUploadedFileInTempRepository(String)
     */
    @Override
    public boolean deleteUploadedFileInTempRepository(@NotNull String fileName) {
        return fileRepositoryService.deleteFileInTempRepository(fileName);
    }

    /* getters/setters ------------------------------------------------------*/

    public void setAllowImageExtensions(String allowImageExtensions) {
        this.allowImageExtensions = allowImageExtensions;
    }

    public void setMediaTypeExtensionMapping(Properties mediaTypeExtensionMapping) {
        this.mediaTypeExtensionMapping = mediaTypeExtensionMapping;
    }

    public void setFileRepositoryService(FileRepositoryService fileRepositoryService) {
        this.fileRepositoryService = fileRepositoryService;
    }
}
