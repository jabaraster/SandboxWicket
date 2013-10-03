/**
 * 
 */
package sandbox.quickstart.web.ui.component;

import jabara.general.Empty;
import jabara.wicket.ComponentJavaScriptHeaderItem;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author jabaraster
 */
@SuppressWarnings("serial")
public class FileUploadPanel extends Panel {
    private static final long serialVersionUID = -220850110042516428L;

    private final Handler     handler          = new Handler();

    private FileUploadField   file;
    private AjaxButton        hiddenUploader;
    private AjaxButton        deleter;
    private AjaxButton        restorer;

    /**
     * @param pId -
     */
    public FileUploadPanel(final String pId) {
        super(pId);
        this.add(getFile());
        this.add(getHiddenUploder());
        this.add(getDeleter());
        this.add(getRestorer());
    }

    /**
     * @see org.apache.wicket.Component#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        pResponse.render(ComponentJavaScriptHeaderItem.forType(FileUploadPanel.class));
        pResponse.render(OnDomReadyHeaderItem.forScript("initializeFileUploadPanel('" + getHiddenUploaderCallbackUrl() + "')")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    private AjaxButton getDeleter() {
        if (this.deleter == null) {
            this.deleter = new IndicatingAjaxButton("deleter") { //$NON-NLS-1$
                @Override
                protected void onSubmit(final AjaxRequestTarget pTarget, @SuppressWarnings("unused") final Form<?> pForm) {
                    FileUploadPanel.this.handler.onDelete(pTarget);
                }
            };
        }
        return this.deleter;
    }

    private FileUploadField getFile() {
        if (this.file == null) {
            this.file = new FileUploadField("file"); //$NON-NLS-1$
        }
        return this.file;
    }

    private CharSequence getHiddenUploaderCallbackUrl() {
        final List<AjaxFormSubmitBehavior> bs = getHiddenUploder().getBehaviors(AjaxFormSubmitBehavior.class);
        return bs.isEmpty() ? Empty.STRING : bs.get(0).getCallbackUrl();
    }

    private AjaxButton getHiddenUploder() {
        if (this.hiddenUploader == null) {
            this.hiddenUploader = new AjaxButton("hiddenUploader") { //$NON-NLS-1$
                @Override
                protected void onSubmit(final AjaxRequestTarget pTarget, @SuppressWarnings("unused") final Form<?> pForm) {
                    FileUploadPanel.this.handler.onUpload(pTarget);
                }
            };
        }
        return this.hiddenUploader;
    }

    private AjaxButton getRestorer() {
        if (this.restorer == null) {
            this.restorer = new IndicatingAjaxButton("restorer") { //$NON-NLS-1$
                @Override
                protected void onSubmit(final AjaxRequestTarget pTarget, @SuppressWarnings("unused") final Form<?> pForm) {
                    FileUploadPanel.this.handler.onRestore(pTarget);
                }
            };
        }
        return this.restorer;
    }

    private class Handler implements Serializable {

        void onDelete(final AjaxRequestTarget pTarget) {
            // TODO Auto-generated method stub

        }

        void onRestore(final AjaxRequestTarget pTarget) {
            // TODO Auto-generated method stub

        }

        void onUpload(final AjaxRequestTarget pTarget) {
            // TODO Auto-generated method stub

        }

    }
}
