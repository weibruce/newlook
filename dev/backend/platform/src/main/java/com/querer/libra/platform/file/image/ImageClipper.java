/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.file.image;

import java.util.List;

import com.querer.libra.platform.file.model.ImageDimension;

/**
 * Image clipper interface to abstract image clipping interfaces.
 */
public interface ImageClipper {
    /**
     * Clip one image with one specified dimension.
     *
     * @param filePath
     *            file path of target image
     * @param clippingDimension
     *            the dimension to be clipped with
     */
    void clipImage(String filePath, ImageDimension clippingDimension);

    /**
     * Clip one image with specified set of dimensions.
     *
     * @param filePath
     *            file path of target image
     * @param clippingDimensionList
     *            the list of dimensions to be clipped with
     */
    void clipImage(String filePath, List<ImageDimension> clippingDimensionList);

    /**
     * Clip all images in one directory with specified dimension.
     *
     * @param directory
     *            target directory with all images to be clipped
     * @param clippingDimension
     *            the dimension to be clipped with
     */
    void clipImagesInDirectory(String directory, ImageDimension clippingDimension);

    /**
     * Clip all images in one directory with specified set of dimensions.
     *
     * @param directory
     *            target directory with all images to be clipped
     * @param clippingDimensionList
     *            the list of dimensions to be clipped with
     */
    void clipImagesInDirectory(String directory, List<ImageDimension> clippingDimensionList);
}
