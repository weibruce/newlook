/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.file.upload;

import com.querer.libra.platform.file.service.FileUploadService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.querer.libra.platform.file.model.UploadProgressDescriptor;
import com.querer.libra.platform.util.constant.PlatformConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Enable progress listener on file upload. And more, restrict upload file
 * extensions support.
 */
public class ProgressCapableMultipartResolver extends CommonsMultipartResolver {

    /* fields ------------------------------------------------------ */

    public final static String PROGRESS_PREFIX = "ProgressCapableMultipartResolver:";
    // switcher to enable BE upload progress listener
    private boolean progressListenerEnable = PlatformConstants.SWITCHER_OFF;

    private FileUploadService fileUploadService;

    /* protected methods --------------------------------------------------- */
    @Override
    protected MultipartParsingResult parseRequest(final HttpServletRequest request) throws MultipartException {
        String encoding = determineEncoding(request);
        ServletFileUpload fileUpload = (ServletFileUpload) prepareFileUpload(encoding);

        // add progress listener
        if (this.progressListenerEnable) {
            initProgressProcessor(request);
            fileUpload.setProgressListener(
                    (pBytesRead, pContentLength, pItems) -> processProgress(pBytesRead, pContentLength, request));
        }

        try {
            // checking file extension support
            List<FileItem> fileItems = fileUpload.parseRequest(request);
            fileItems.forEach(e -> {
                if (!fileUploadService.allowUploadImageContentType(e.getContentType())) {
                    throw new MultipartException("Not supported file extension: ." + e.getContentType());
                }
            });
            // go ahead default processing to parse upload file
            return parseFileItems(fileItems, encoding);
        } catch (FileUploadBase.SizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
        } catch (FileUploadException ex) {
            throw new MultipartException("Could not parse multipart servlet request", ex);
        } finally {
            if (this.progressListenerEnable) {
                clearProgressProcessor(request);
            }
        }
    }

    /* private methods ------------------------------------------------------ */

    private void initProgressProcessor(HttpServletRequest request) {
        request.getSession().setAttribute(PROGRESS_PREFIX + request.getRequestURI(), new UploadProgressDescriptor());
    }

    private void clearProgressProcessor(HttpServletRequest request) {
        request.getSession().removeAttribute(PROGRESS_PREFIX + request.getRequestURI());
    }

    private void processProgress(long bytesRead, long bytesTotal, HttpServletRequest request) {
        request.getSession().setAttribute(PROGRESS_PREFIX + request.getRequestURI(),
                new UploadProgressDescriptor(bytesRead, bytesTotal));
        // TODO Print
        String key = PROGRESS_PREFIX + request.getRequestURI();
        System.out.println(
                "UploadProgressDescriptor ================: " + (request.getSession().getAttribute(key)).toString());
    }

    /* getters/setters ------------------------------------------------------ */

    public boolean isProgressListenerEnable() {
        return progressListenerEnable;
    }

    public void setProgressListenerEnable(boolean progressListenerEnable) {
        this.progressListenerEnable = progressListenerEnable;
    }

    public FileUploadService getFileUploadService() {
        return fileUploadService;
    }

    public void setFileUploadService(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }
}
