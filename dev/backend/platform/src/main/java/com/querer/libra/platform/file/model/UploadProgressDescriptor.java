/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.file.model;

/**
 * Upload progress descriptor containing upload parameters while file uploading.
 */
public class UploadProgressDescriptor {

    /* fields ------------------------------------------------------ */

    private long bytesRead;
    private long bytesTotal;

    /* constructors ------------------------------------------------------ */

    public UploadProgressDescriptor() {
        bytesRead = bytesTotal = 0;
    }

    public UploadProgressDescriptor(long bytesRead, long bytesTotal) {
        this.bytesRead = bytesRead;
        this.bytesTotal = bytesTotal;
    }

    /* public methods ------------------------------------------------------ */

    @Override
    public String toString() {
        return "UploadProgressDescriptor{" + "bytesRead=" + bytesRead + ", bytesTotal=" + bytesTotal + '}';
    }

    /* getters/setters ------------------------------------------------------ */

    public long getBytesRead() {
        return bytesRead;
    }

    public void setBytesRead(long bytesRead) {
        this.bytesRead = bytesRead;
    }

    public long getBytesTotal() {
        return bytesTotal;
    }

    public void setBytesTotal(long bytesTotal) {
        this.bytesTotal = bytesTotal;
    }
}
