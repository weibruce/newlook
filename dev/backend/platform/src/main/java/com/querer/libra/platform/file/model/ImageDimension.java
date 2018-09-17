/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.file.model;

/**
 * Image dimension object with clipping parameters.
 */
public class ImageDimension {

    /* fields ------------------------------------------------------ */

    // quality level
    private ImageQualityType qualityType;
    // The width dimension.
    private int width;
    // The height dimension.
    private int height;

    /* constructors ------------------------------------------------------ */

    public ImageDimension() {
    }

    public ImageDimension(ImageQualityType qualityType, int width, int height) {
        this.qualityType = qualityType;
        this.width = width;
        this.height = height;
    }

    /* getters/setters ------------------------------------------------------ */

    public ImageQualityType getQualityType() {
        return qualityType;
    }

    public void setQualityType(ImageQualityType qualityType) {
        this.qualityType = qualityType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
