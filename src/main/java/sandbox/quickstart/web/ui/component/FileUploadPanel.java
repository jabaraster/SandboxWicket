/**
 * 
 */
package sandbox.quickstart.web.ui.component;

import jabara.general.Empty;
import jabara.wicket.ComponentCssHeaderItem;
import jabara.wicket.ComponentJavaScriptHeaderItem;
import jabara.wicket.IAjaxCallback;
import jabara.wicket.NullAjaxCallback;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.string.interpolator.MapVariableInterpolator;

/**
 * @author jabaraster
 */
@SuppressWarnings({ "serial", "synthetic-access" })
public class FileUploadPanel extends Panel {
    private static final long  serialVersionUID = -220850110042516428L;

    private final Handler      handler          = new Handler();

    private IAjaxCallback      onUpload         = NullAjaxCallback.GLOBAL;

    private WebMarkupContainer container;
    private FileUploadField    file;
    private AjaxButton         hiddenUploader;
    private AjaxButton         deleter;
    private AjaxButton         restorer;

    /**
     * @param pId -
     */
    public FileUploadPanel(final String pId) {
        super(pId);
        this.add(getContainer());
    }

    /**
     * @see org.apache.wicket.Component#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        pResponse.render(ComponentCssHeaderItem.forType(FileUploadPanel.class));
        pResponse.render(ComponentJavaScriptHeaderItem.forType(FileUploadPanel.class));

        final Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("containerId", getContainer().getMarkupId()); //$NON-NLS-1$
        vars.put("url", getHiddenUploaderCallbackUrl()); //$NON-NLS-1$
        vars.put("hiddenUploaderId", getHiddenUploader().getMarkupId()); //$NON-NLS-1$
        vars.put("fileFieldId", getFile().getMarkupId()); //$NON-NLS-1$
        final String script = MapVariableInterpolator.interpolate( //
                "initializeFileUploadPanel('${containerId}', '${url}', '${hiddenUploaderId}', '${fileFieldId}')" // //$NON-NLS-1$
                , vars //
                );
        pResponse.render(OnDomReadyHeaderItem.forScript(script));
    }

    /**
     * @param pCallback -
     * @return -
     */
    public FileUploadPanel setOnUpload(final IAjaxCallback pCallback) {
        this.onUpload = pCallback == null ? NullAjaxCallback.GLOBAL : pCallback;
        return this;
    }

    private WebMarkupContainer getContainer() {
        if (this.container == null) {
            this.container = new WebMarkupContainer("container"); //$NON-NLS-1$
            this.container.setOutputMarkupId(true);
            this.container.add(getFile());
            this.container.add(getHiddenUploader());
            this.container.add(getDeleter());
            this.container.add(getRestorer());

        }
        return this.container;
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
            this.file.setOutputMarkupId(true);
        }
        return this.file;
    }

    private AjaxButton getHiddenUploader() {
        if (this.hiddenUploader == null) {
            this.hiddenUploader = new AjaxButton("hiddenUploader") { //$NON-NLS-1$
                @Override
                protected void onSubmit(final AjaxRequestTarget pTarget, @SuppressWarnings("unused") final Form<?> pForm) {
                    FileUploadPanel.this.handler.onUpload(pTarget);
                }
            };
            this.hiddenUploader.setOutputMarkupId(true);
        }
        return this.hiddenUploader;
    }

    private CharSequence getHiddenUploaderCallbackUrl() {
        final List<AjaxFormSubmitBehavior> bs = getHiddenUploader().getBehaviors(AjaxFormSubmitBehavior.class);
        return bs.isEmpty() ? Empty.STRING : bs.get(0).getCallbackUrl();
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
            FileUploadPanel.this.onUpload.call(pTarget);
            final FileUpload upload = getFile().getFileUpload();
            if (upload != null) {
                jabara.Debug.write("★★★ " + upload.getClientFileName());
            }
        }

    }
}
