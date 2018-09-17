/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.file.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.querer.libra.platform.code.UniqueCodeGenerator;
import com.querer.libra.platform.core.exception.SystemException;
import com.querer.libra.platform.file.exception.FileRepositoryException;
import com.querer.libra.platform.file.model.MovableFileLocation;
import com.querer.libra.platform.file.service.FileRepositoryService;
import com.querer.libra.platform.file.util.FileRepositoryConstants;
import com.querer.libra.platform.util.constant.PlatformConstants;

/**
 * File upload helper to write file to target repository.
 */
public class FileRepositoryServiceImpl implements FileRepositoryService, InitializingBean {

    /* fields          ------------------------------------------------------*/
 
    private final static Logger logger = LoggerFactory.getLogger(FileRepositoryServiceImpl.class);

    /* configurable file repository structures */
    // file server, e.g.: http://image.libra.com
    private String fileServer;
    // root directory on file server/shared server, e.g.: x:/file-repository
    private String rootDirectory;
    // temp repository folder name, e.g.: temp
    private String tempFolder;

    // temp repository directory (absolute path to this directory) to store uploaded files
    private String tempDirectory;

    private UniqueCodeGenerator uniqueCodeGenerator;
    
    /* constructors    ------------------------------------------------------*/

    /**
     * init file repository structure when server startup.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.initializeFileRepository();
        logger.info("File upload repository has been initialized successfully!");
    }

    /* public methods  ------------------------------------------------------*/

    /**
     * @see FileRepositoryService#generateGroupCode()
     */
    @Override
    public String generateGroupCode() {
        return this.uniqueCodeGenerator.generateCodeTimestamp();
    }

    /**
     * @see FileRepositoryService#generateFileCode()
     */
    @Override
    public String generateFileCode() {
        return this.uniqueCodeGenerator.generateCodeAlphanumeric();
    }

    /**
     * @see FileRepositoryService#isContainingServerUri(String)
     */
    @Override
    public boolean isContainingServerUri(@NotNull String fileUri) {
        return StringUtils.containsIgnoreCase(fileUri, this.fileServer);
    }

    /**
     * @see FileRepositoryService#appendServerUriForFileContent(String)
     */
    @Override
    public String appendServerUriForFileContent(@NotNull String fileContent) {
        if (StringUtils.isBlank(fileContent)) return null;

        String[] files = StringUtils.split(fileContent, PlatformConstants.COMMA);
        String fileServerUri = Arrays.stream(files)
                .map(e -> appendServerUriForSingleFile(e))
                .collect(Collectors.joining(PlatformConstants.COMMA));
        return fileServerUri;
    }

    /**
     * @see FileRepositoryService#appendServerUriForSingleFile(String)
     */
    @Override
    public String appendServerUriForSingleFile(@NotNull String file) {
        if (StringUtils.isBlank(file)) return PlatformConstants.EMPTY_STRING;
        if (isContainingServerUri(file)) return file;

        // replace file separator to URI separator
        StringUtils.replaceChars(file, PlatformConstants.BACK_SLASH, PlatformConstants.SLASH);
        String imageUri = new StringBuilder()
                .append(this.fileServer)
                .append(PlatformConstants.SLASH).append(file)
                .toString();
        return imageUri.toString();
    }

    /**
     * @see FileRepositoryService#deleteFileInTempRepository(String)
     */
    @Override
    public boolean deleteFileInTempRepository(@NotNull String fileName) {
        boolean isDelete = false;
        if (StringUtils.isBlank(fileName)) return isDelete;

        StringBuilder absolutePath = new StringBuilder();
        absolutePath
                .append(this.tempDirectory)
                .append(PlatformConstants.SLASH).append(fileName);

        File file = new File(absolutePath.toString());
        // If this pathname denotes a directory, then the directory must be empty in order to be deleted
        if (file.exists()) {
            isDelete = file.delete();
        }
        return isDelete;
    }

    /**
     * @see FileRepositoryService#deleteFileGroupInRepository(List)
     */
    @Override
    public void deleteFileGroupInRepository(@NotNull List<String> deleteFileGroupList) {
        if (CollectionUtils.isEmpty(deleteFileGroupList)) return;

        deleteFileGroupList.forEach(e -> {
            String pathToUniqueCode = findCodePathInFileContent(e);
            deleteFileGroupInRepository(pathToUniqueCode);
        });
    }

    /**
     * @see FileRepositoryService#findAbsolutePathOfFile(String) 
     */
    @Override
    public String findAbsolutePathOfFile(@NotNull String singleFilePath) {
        return appendToAbsolutePath(singleFilePath);
    }

    /**
     * @see FileRepositoryService#findAbsolutePathOfCode(String)
     */
    @Override
    public String findAbsolutePathOfCode(@NotNull String fileContent) {
        String pathToUniqueCode = findCodePathInFileContent(fileContent);
        return appendToAbsolutePath(pathToUniqueCode);
    }

    /**
     * @see FileRepositoryService#findAbsolutePathOfFirstFile(String)
     */
    @Override
    public String findAbsolutePathOfFirstFile(@NotNull String fileContent) {
        String absolutePath = null;
        String firstFile = findFirstFileInContent(fileContent);
        if (StringUtils.isNotBlank(firstFile)) {
            absolutePath = appendToAbsolutePath(firstFile);
        }
        return absolutePath;
    }

    /**
     * @see FileRepositoryService#checkFileNotInRepository(String, String)
     */
    @Override
    public List<String> checkFileNotInRepository(@NotNull String fileToCheck, @NotNull String fileToAdd) {
        List<String> notInRepositoryList = new ArrayList<>();

        if (!isContainingServerUri(fileToCheck)) {
            // server side image content to be removed
            if (StringUtils.isNotBlank(fileToAdd)) {
                notInRepositoryList.add(fileToAdd);
            }
        }

        return notInRepositoryList;
    }

    /**
     * @see FileRepositoryService#generateRepositoryLocationForImage(String, Boolean, String[], String)
     */
    @Override
    public MovableFileLocation generateRepositoryLocationForImage(@NotNull String imageContent, @NotNull Boolean isSingleImage,
                                                                  @NotNull String[] uriPrefixes, @NotNull String uniqueCode) {
        MovableFileLocation location = new MovableFileLocation();

        // params checking
        if (StringUtils.isBlank(imageContent)) return location;
        // check prefix to ensure that no null obj
        List uriPrefixList = new ArrayList<>();
        if (uriPrefixes != null) {
            Arrays.stream(uriPrefixes).forEach(e -> CollectionUtils.addIgnoreNull(uriPrefixList, e));
            if (CollectionUtils.isEmpty(uriPrefixList)) return location;
        }
        if (StringUtils.isBlank(uniqueCode)) {
            uniqueCode = generateGroupCode();
        }

        String[] images = StringUtils.split(StringUtils.trimToEmpty(imageContent), PlatformConstants.COMMA);
        if (ArrayUtils.isNotEmpty(images)) {
            List<String> newImages = new ArrayList<>();
            for (int i = 0; i < images.length; i++) {
                String image = StringUtils.trimToEmpty(images[i]);
                /* if multiple images case, each image with suffix with index in file name */
                int index = BooleanUtils.isTrue(isSingleImage) ? FileRepositoryConstants.INDEX_FOR_SINGLE_IMAGE : i;
                // generated image uri in repository as destination
                String newImage = generatePathForImageInContent(image, index + 1, uniqueCode, uriPrefixList);
                newImages.add(newImage);

                // single image case just process once a time
                if (BooleanUtils.isTrue(isSingleImage)) {
                    break;
                }
            }

            // in case any process inconsistent, throw exception and stop
            if (CollectionUtils.size(newImages) != images.length) {
                throw new SystemException("Save file/image error - images and to be moved images are not the same number of files.");
            }

            String newImageContent = StringUtils.join(newImages, PlatformConstants.COMMA);
            location = new MovableFileLocation(imageContent, newImageContent);
        }

        return location;
    }

    /**
     * @see FileRepositoryService#moveFileToRepository(String, String)
     */
    @Override
    public boolean moveFileToRepository(@NotNull String originalFile, @NotNull String newFile) {
        if (isContainingServerUri(originalFile)) return true;

        /* target temp file path and check file exists */
        StringBuilder absolutePathOfTemp = new StringBuilder();
        absolutePathOfTemp
                .append(this.tempDirectory)
                .append(PlatformConstants.SLASH).append(originalFile);
        File sourceFile = new File(absolutePathOfTemp.toString());
        if (!sourceFile.exists()) return false;

        /* generate destination of file to copy to */
        StringBuilder absolutePathOfRepository = new StringBuilder();
        absolutePathOfRepository
                .append(this.rootDirectory)
                .append(PlatformConstants.SLASH).append(newFile);
        File targetFile = new File(absolutePathOfRepository.toString());

        /* FILE IO */
        try {
            FileUtils.moveFile(sourceFile, targetFile);
            return true; // true means FILE IO op successfully
        } catch (IOException e) {
            // if FILE IO failed, continue next
            logger.info("Move file failed while saving product. Source file: {}; Destination file: {}",
                    sourceFile.getAbsolutePath(), targetFile.getAbsolutePath(), e);
            return false;
        }
    }

    /**
     * @see FileRepositoryService#moveFileToRepositoryByLocation(MovableFileLocation)
     */
    @Override
    public void moveFileToRepositoryByLocation(@NotNull MovableFileLocation location) {
        if (location == null) return;

        String sourceFileContent = location.getSource();
        String destinationFileContent = location.getDestination();
        if (StringUtils.isBlank(sourceFileContent) || StringUtils.isBlank(destinationFileContent)) return;

        // these two array length MUST be same
        String[] sourceFiles = StringUtils.split(sourceFileContent, PlatformConstants.COMMA);
        String[] destinationFiles = StringUtils.split(destinationFileContent, PlatformConstants.COMMA);
        if (!ArrayUtils.isSameLength(sourceFiles, destinationFiles)) {
            logger.info("two lengths of image array are not the same.");
            return;
        }

        // move images one by one
        for (int i = 0; i < sourceFiles.length; i++) {
            String sourceFile = sourceFiles[i];
            String destinationFile = destinationFiles[i];
            if (StringUtils.isBlank(sourceFile) || StringUtils.isBlank(destinationFile)) return;

            moveFileToRepository(sourceFile, destinationFile);
        }
    }

    /**
     * @see FileRepositoryService#appendServerUriForTempFile(String)
     */
    @Override
    public String appendServerUriForTempFile(@NotNull String singleFileName) {
        if (StringUtils.isBlank(singleFileName)) return PlatformConstants.EMPTY_STRING;
        if (isContainingServerUri(singleFileName)) return singleFileName;

        String imageUri = new StringBuilder()
                .append(this.fileServer)
                .append(PlatformConstants.SLASH).append(this.tempFolder)
                .append(PlatformConstants.SLASH).append(singleFileName)
                .toString();
        return imageUri.toString();
    }

    /**
     * @see FileRepositoryService#buildImageServerUri(String)
     */
    @Override
    public String buildImageServerUri(@NotNull String pathToDirectory) {
        // used to add/remove server suffix to/from image uri
        return new StringBuilder()
                .append(this.fileServer)
                .append(PlatformConstants.SLASH).append(pathToDirectory)
                .append(PlatformConstants.SLASH)
                .toString();
    }

    /**
     * @see FileRepositoryService#writeFileToPath(String, byte[])
     */
    @Override
    public String writeFileToPath(@NotNull String filePath, @NotNull byte[] bytes) {
        // if file path is blank, return null directly but not throwing exception
        if (StringUtils.isBlank(filePath)) return null;

        File uploadedFile = new File(filePath);
        try {
            // write file to target path
            FileUtils.writeByteArrayToFile(uploadedFile, bytes);
            logger.debug("Upload file successfully. File location = {}", uploadedFile.getAbsolutePath());
        } catch (IOException e) {
            throw new FileRepositoryException("Failed to write file to path.", e);
        }

        return uploadedFile.getAbsolutePath();
    }

    /**
     * @see FileRepositoryService#writeFileToRepository(String, byte[], String)
     */
    @Override
    public String writeFileToRepository(@NotNull String relativeDirectoryPath, @NotNull byte[] fileBytes,
                                        @NotNull String fileExtension) {
        String fileName = generateGroupCode();
        return writeFileToRepository(relativeDirectoryPath, fileBytes, fileExtension, fileName);
    }
    
    
    /**
     * @see FileRepositoryService#writeFileToRepository(String, byte[], String, String)
     */
    @Override
    public String writeFileToRepository(@NotNull String relativeDirectoryPath, @NotNull byte[] fileBytes,
                                        @NotNull String fileExtension, @NotNull String fileName) {
        if (StringUtils.isBlank(fileName)) {
            fileName = generateGroupCode();
        }
        
        StringBuilder path = new StringBuilder();
        path.append(relativeDirectoryPath).append(fileName).append(fileExtension);
        String relativePath = path.toString();

        StringBuilder absolutePath = new StringBuilder();
        absolutePath
                .append(this.rootDirectory)
                .append(PlatformConstants.SLASH)
                .append(relativePath);

        writeFileToPath(absolutePath.toString(), fileBytes);
        return relativePath;
    }


    /**
     * @see FileRepositoryService#writeFileToTempRepository(byte[], String)
     */
    @Override
    public String writeFileToTempRepository(@NotNull byte[] bytes, @NotNull String fileExtension) {
        String fileName = generateGroupCode();

        StringBuilder path = new StringBuilder();
        path.append(fileName).append(fileExtension);
        String relativePath = path.toString();

        StringBuffer absolutePath = new StringBuffer();
        absolutePath
                .append(this.tempDirectory)
                .append(PlatformConstants.SLASH)
                .append(relativePath);

        writeFileToPath(absolutePath.toString(), bytes);
        return relativePath;
    }

    /* private methods ------------------------------------------------------*/

    private void initializeFileRepository() {
        try {
            if (StringUtils.isBlank(this.rootDirectory)) {
                throw new FileRepositoryException("file root repository is not setup.");
            } else {
                Files.createDirectories(new File(this.rootDirectory).toPath());
            }

            if (StringUtils.isBlank(this.tempFolder)) {
                throw new FileRepositoryException("file temp directory is not setup.");
            } else {
                StringBuilder path = new StringBuilder();
                path
                        .append(this.rootDirectory)
                        .append(PlatformConstants.SLASH)
                        .append(this.tempFolder);

                this.tempDirectory = path.toString();
                Files.createDirectories(new File(this.tempDirectory).toPath());
            }
        } catch (IOException e) {
            throw new FileRepositoryException("Failed to setup file repository to store upload files.", e);
        }
    }

    private String findCodePathInFileContent(String fileContent) {
        String firstFile = findFirstFileInContent(fileContent);
        String normalizedFileUri = StringUtils.removeStart(firstFile, this.fileServer);
        String pathToUniqueCode = StringUtils.substringBeforeLast(normalizedFileUri, PlatformConstants.SLASH);
        return pathToUniqueCode;
    }

    private String findFirstFileInContent(String fileContent) {
        String firstFile = null;
        if (StringUtils.isBlank(fileContent)) {
            return firstFile;
        }

        String[] files = StringUtils.trimToEmpty(fileContent).split(PlatformConstants.COMMA);
        if (ArrayUtils.isNotEmpty(files)) {
            firstFile = StringUtils.trimToEmpty(files[0]);
        }

        return firstFile;
    }

    private boolean deleteFileGroupInRepository(String fileGroupPath) {
        if (StringUtils.isBlank(fileGroupPath)) return true; // nothing to delete

        StringBuilder absolutePathOfRepository = new StringBuilder();
        absolutePathOfRepository.append(PlatformConstants.SLASH).append(fileGroupPath);
        File targetFile = new File(absolutePathOfRepository.toString());

        try {
            FileUtils.forceDelete(targetFile);
            // save delete file/directory successfully
            return true;
        } catch (Exception e) {
            logger.debug("Save delete image group file failed: " + absolutePathOfRepository, e);
            return false;
        }
    }

    private String appendToAbsolutePath(String path) {
        StringBuilder absolutePath = new StringBuilder();
        absolutePath
                .append(this.rootDirectory)
                .append(PlatformConstants.SLASH)
                .append(path);
        return absolutePath.toString();
    }

    /**
     * Generate relative path for images stored in repository. This value would be saved in DB as well. When the path
     * sent to client, it would be append file server prefix to support external/configurable file system.
     *
     * @param imagePath     the image path in temp repository
     * @param index         the index of images in group/section
     * @param uniqueCode    the random code for this operation
     * @param uriPrefixList the prefix list of URI in repository
     * @return
     */
    private String generatePathForImageInContent(String imagePath, int index, String uniqueCode, List<String> uriPrefixList) {
        /* existing image uri string containing file server uri */
        if (isContainingServerUri(imagePath)) {
            String normalizedImageUri = StringUtils.removeStart(imagePath, this.fileServer);
            return normalizedImageUri;
        }
        /* new image need to generate uri */
        StringBuilder absolutePathOfTemp = new StringBuilder();
        absolutePathOfTemp
                .append(this.tempDirectory)
                .append(PlatformConstants.SLASH)
                .append(imagePath);

        File sourceFile = new File(absolutePathOfTemp.toString());
        if (!sourceFile.exists()) return null; // if file not exist any more, stop here and return

        String sourceFileName = StringUtils.substringAfterLast(absolutePathOfTemp.toString(), PlatformConstants.SLASH);
        String sourceFileExtension = StringUtils.substringAfterLast(sourceFileName, PlatformConstants.DOT);
        // if index > 0, means multiple images with index appended; otherwise, it's single image and no index append
        String indexString = index > 0 ? PlatformConstants.UNDERSCORE + index : PlatformConstants.EMPTY_STRING;

        StringBuilder relativePathOfRepository = new StringBuilder();
        // presentation picture and detail intro images stored in different location
        relativePathOfRepository.append(StringUtils.join(uriPrefixList, PlatformConstants.SLASH));
        relativePathOfRepository
                .append(PlatformConstants.SLASH).append(uniqueCode)
                .append(PlatformConstants.SLASH).append(uniqueCode).append(indexString).append(PlatformConstants.DOT).append(sourceFileExtension);

        return relativePathOfRepository.toString();
    }

    /* getters/setters ------------------------------------------------------*/

    public void setFileServer(String fileServer) {
        this.fileServer = fileServer;
    }

    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }
    
    public String getRootDirectory() {
        return rootDirectory;
    }
    
    public void setTempFolder(String tempFolder) {
        this.tempFolder = tempFolder;
    }

    public void setTempDirectory(String tempDirectory) {
        this.tempDirectory = tempDirectory;
    }

    public void setUniqueCodeGenerator(UniqueCodeGenerator uniqueCodeGenerator) {
        this.uniqueCodeGenerator = uniqueCodeGenerator;
    }

    @Override
    public String getRootDirectoryPath() {
        return this.getRootDirectory();
    }
}
