/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.file.image.thumbnailator;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.querer.libra.platform.file.image.ImageClipper;
import com.querer.libra.platform.file.model.ImageDimension;
import com.querer.libra.platform.util.constant.PlatformConstants;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Thumbnailator Implementation for ImageClipper interfaces.
 */
public class ThumbnailatorImageClipper implements ImageClipper {

    /* fields ------------------------------------------------------ */

    private final static Logger logger = LoggerFactory.getLogger(ThumbnailatorImageClipper.class);

    /* public methods ------------------------------------------------------ */

    /**
     * @see ImageClipper#clipImage(String, ImageDimension)
     */
    @Override
    public void clipImage(String filePath, ImageDimension clippingDimension) {
        // not throwing exception and just return, ignore FILE IO failed for now
        if (StringUtils.isBlank(filePath) && clippingDimension == null)
            return;
        /* construct destination path of clipped image */
        ClippingImageRename rename = new ClippingImageRename(clippingDimension);
        String suffix = rename.getSuffix();

        String filePathPrefix = StringUtils.substringBeforeLast(filePath, PlatformConstants.DOT);
        String extension = StringUtils.substringAfterLast(filePath, PlatformConstants.DOT);

        StringBuffer clippingFilePath = new StringBuffer();
        clippingFilePath.append(filePathPrefix).append(PlatformConstants.UNDERSCORE).append(suffix)
                .append(PlatformConstants.DOT).append(extension);

        // clip image
        try {
            Thumbnails.of(filePath).size(clippingDimension.getWidth(), clippingDimension.getHeight())
                    .toFile(clippingFilePath.toString());
        } catch (Exception e) {
            // not throw out exception and just return, ignore FILE IO failed
            // for now
            logger.info("image clipping exception.", e);
        }
    }

    /**
     * @see ImageClipper#clipImage(String, List)
     */
    @Override
    public void clipImage(String filePath, List<ImageDimension> clippingDimensionList) {
        // not throwing exception and just return, ignore FILE IO failed for now
        if (StringUtils.isBlank(filePath) && CollectionUtils.isEmpty(clippingDimensionList))
            return;
        clippingDimensionList.forEach(e -> clipImage(filePath, e));
    }

    /**
     * @see ImageClipper#clipImagesInDirectory(String, ImageDimension)
     */
    @Override
    public void clipImagesInDirectory(String directory, ImageDimension clippingDimension) {
        // not throwing exception and just return, ignore FILE IO failed for now
        if (StringUtils.isBlank(directory) && clippingDimension == null)
            return;

        try {
            File[] files = new File(directory).listFiles();
            if (ArrayUtils.isNotEmpty(files)) {
                Arrays.stream(files).forEach(e -> clipImage(e.getAbsolutePath(), clippingDimension));
            }
        } catch (Exception e) {
            // not throw out exception and just return, ignore FILE IO failed
            // for now
            logger.info("image clipping exception.", e);
        }
    }

    /**
     * @see ImageClipper#clipImagesInDirectory(String, List)
     */
    @Override
    public void clipImagesInDirectory(String directory, List<ImageDimension> clippingDimensionList) {
        // not throwing exception and just return, ignore FILE IO failed for now
        if (StringUtils.isBlank(directory) && CollectionUtils.isEmpty(clippingDimensionList))
            return;

        try {
            File[] files = new File(directory).listFiles();
            if (ArrayUtils.isNotEmpty(files)) {
                Arrays.stream(files).forEach(e -> clipImage(e.getAbsolutePath(), clippingDimensionList));
            }
        } catch (Exception e) {
            // not throw out exception and just return, ignore FILE IO failed
            // for now
            logger.info("image clipping exception.", e);
        }
    }
}
