/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.file.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.querer.libra.platform.file.model.MovableFileLocation;
import org.springframework.validation.annotation.Validated;

/**
 * File upload helper to write file to target repository.
 */
@Validated 
public interface FileRepositoryService {

    /**
     * Generate random code for one upload. Could be used for unique directory name, file name.
     *
     * @return the random code
     */
    String generateGroupCode();

    /**
     * Generate random code for file name.
     *
     * @return the random code
     */
    String generateFileCode();

    /**
     * Check target image uploaded already and store in file repository/db.
     * If image uri containing file server string meaning that it's sent from server and uploaded before.
     * <p>
     * Not matter single image and multiple image content, once server URI found in image string means these image
     * already stored in repository. 
     *
     * @param fileUri the file uri to be checked
     * @return true if an existing uri otherwise false
     */
    boolean isContainingServerUri(@NotNull String fileUri);

    /**
     * Append image server to image URI before sending to client.
     *
     * @param fileContent the image content with multiple images
     * @return the new image content with all single images appending file server in URI
     */
    String appendServerUriForFileContent(@NotNull String fileContent);

    /**
     * Append server URI (image sever root path) for relative path
     * so that it presents the full image URI can be accessed via http.
     *
     * @param file the image relative path in repository
     * @return the image URI
     */
    String appendServerUriForSingleFile(@NotNull String file);

    /**
     * Append server URI (image sever root path) for file in temp repository,
     * so that it presents the full image URI can be accessed via http.
     *
     * @param singleFileName the image file name in temp repository
     * @return the image URI
     */
    String appendServerUriForTempFile(@NotNull String singleFileName);

    /**
     * Delete file in temp repository.
     *
     * @param fileName target file to be deleted
     * @return true if delete file successfully otherwise false
     */
    boolean deleteFileInTempRepository(@NotNull String fileName);

    /**
     * Delete list of image groups in repository. In update scenario, some images maybe upload with new images. Then
     * original uploaded images in file repository should be marked as dirty and deleted.
     *
     * @param deleteFileGroupList the list of image groups to be deleted
     */
    void deleteFileGroupInRepository(@NotNull List<String> deleteFileGroupList);
    
    /**
     * Find absolute file path start from file server for one single file.
     *
     * @param singleFilePath the one single file relative path content saved in store
     * @return the absolute file path that can access this file directly
     */
    String findAbsolutePathOfFile(@NotNull String singleFilePath);

    /**
     * Find absolute path of image directory to the unique code directory
     *
     * @param fileContent the image content to be find
     * @return the absolute path
     */
    String findAbsolutePathOfCode(@NotNull String fileContent);

    /**
     * Find absolute path of first file in string content. Since sometimes first file used as preview/presentation
     * having diff dimensions needs.
     *
     * @param fileContent the file content to search the first one
     * @return the absolute path
     */
    String findAbsolutePathOfFirstFile(@NotNull String fileContent);

    /**
     * Check fileToCheck not point to a file existing in repository.
     * If fileToCheck no exist in repository and fileToAdd will be in return list.
     * Most usage in update case that edit existing uploading images with new ones just uploaded.
     *
     * @param fileToCheck the file uri to check
     * @param fileToAdd   the file uri to add to list for return
     * @return the list containing fileToAdd
     */
    List<String> checkFileNotInRepository(@NotNull String fileToCheck, @NotNull String fileToAdd);

    /**
     * Generates MovableFileLocation object for image content.
     * After save successfully, MovableFileLocation is used to move files from source to destination in repository.
     * So the logic here is to append the correct file URI of repository for both source/destination files.
     *
     * @param imageContent  the imageContent to be process
     * @param isSingleImage flag identify single image case and multiple image content separate with comma
     * @param uriPrefixes   URI prefix to be in repository path
     * @param uniqueCode    the unique code to identify one or group of files
     * @return the MovableFileLocation object
     */
    MovableFileLocation generateRepositoryLocationForImage(@NotNull String imageContent, @NotNull Boolean isSingleImage,
                                                           @NotNull String[] uriPrefixes, @NotNull String uniqueCode) ;


    /**
     * Move original file location in temp directory to new file location in repository.
     * All params file path are relative file path.
     * Source file relative to temp repository and new file relative to root repository.
     *
     * @param originalFile the file location as source
     * @param newFile      the file location as destination
     * @return true if move successfully otherwise false
     */
    boolean moveFileToRepository(@NotNull String originalFile, @NotNull String newFile);

    /**
     * Move file from source to destination referred in MovableFileLocation
     *
     * @param location the source/destination info
     */
    void moveFileToRepositoryByLocation(@NotNull MovableFileLocation location);

    /**
     * Build image uri with image server info with given directory name, but client needs to append relative file name
     * to get full image uri viewed via http.
     *
     * @param pathToDirectory relative directory path
     * @return file server uri
     */
    String buildImageServerUri(@NotNull String pathToDirectory);

    /**
     * Write bytes of file to target file path.
     *
     * @param filePath the file path to be wrote
     * @param bytes    the bytes of file content
     * @return the absolute path of file on file server
     */
    String writeFileToPath(@NotNull String filePath, @NotNull byte[] bytes);

    /**
     * Write target file bytes to file repository.
     *
     * @param relativeDirectoryPath file relative directory path to root dir of file server
     * @param fileBytes file bytes to write
     * @param fileExtension file suffix name
     * @return relative path to file
     */
    String writeFileToRepository(@NotNull String relativeDirectoryPath, @NotNull byte[] fileBytes, @NotNull String fileExtension);

    /**
     * Write target file bytes to temp file repository.
     *
     * @param fileExtension file suffix name of extend
     * @param bytes file bytes to write
     * @return
     */
    String writeFileToTempRepository(@NotNull byte[] bytes, @NotNull String fileExtension);
    
    /**
     * getRootDirectory.
     *
     * @return
     */
    String getRootDirectoryPath();
    
    
    /**
     * Write target file bytes to file repository with pass-by file name.
     *
     * @param relativeDirectoryPath file relative directory path to root dir of file server
     * @param fileBytes file bytes to write
     * @param fileExtension file suffix name
     * @param fileName target file name
     * @return relative path to file
     */
    String writeFileToRepository(@NotNull String relativeDirectoryPath, @NotNull byte[] fileBytes, @NotNull String fileExtension, @NotNull String fileName);

}
