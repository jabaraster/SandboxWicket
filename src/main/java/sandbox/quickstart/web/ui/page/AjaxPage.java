package sandbox.quickstart.web.ui.page;

import jabara.general.Empty;
import jabara.general.NameValue;
import jabara.wicket.ComponentCssHeaderItem;
import jabara.wicket.ComponentJavaScriptHeaderItem;
import jabara.wicket.IAjaxCallback;
import jabara.wicket.VariablesJavaScriptHeaderItem;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import sandbox.quickstart.Environment;
import sandbox.quickstart.web.ui.component.FileUploadPanel;

/**
 *
 */
@SuppressWarnings({ "nls", "serial", "synthetic-access" })
public class AjaxPage extends WebPage {
    private static final long      serialVersionUID = -4965903336608758671L;

    private Link<?>                reloader;

    private Form<?>                uploadForm;
    private FileUploadPanel        uploader;
    private FileUploadPanel        uploader2;

    private Label                  now;
    private Label                  noAjaxTarget;
    private Form<?>                form;
    private FileUploadField        file;
    private Button                 ajaxSubmitter;

    private Component              customSubmitter;
    private AjaxFormSubmitBehavior customSubmitBehavior;

    /**
     * 
     */
    public AjaxPage() {
        this.add(getReloader());
        this.add(getUploadForm());
        this.add(getNow());
        this.add(getNoAjaxTarget());
        this.add(getForm());
    }

    /**
     * @see org.apache.wicket.Component#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        pResponse.render(CssHeaderItem.forReference(new CssResourceReference(AjaxPage.class, "bootstrap/css/bootstrap.min.css")));
        pResponse.render(ComponentCssHeaderItem.forType(AjaxPage.class));

        pResponse.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(AjaxPage.class, "bootstrap/js/bootstrap.min.js")));
        pResponse.render(VariablesJavaScriptHeaderItem.forVariables(AjaxPage.class, AjaxPage.class.getSimpleName() + "_vars.js" //
        , new NameValue<String>("package", Environment.class.getPackage().getName()) //
                , new NameValue<String>("formId", getForm().getMarkupId()) //
                , new NameValue<String>("fileId", getFile().getMarkupId()) //
                , new NameValue<String>("customSutmitterId", getCustomSubmitter().getMarkupId()) //
                , new NameValue<CharSequence>("customSubmitUrl", getCustomSubmitterBehavior().getCallbackUrl()) //
                , new NameValue<String>("ajaxSubmitterId", getAjaxSubmitter().getMarkupId()) //
                , new NameValue<CharSequence>("ajaxSubmitUrl", getAjaxSubmitUrl()) //
                ));
        pResponse.render(ComponentJavaScriptHeaderItem.forType(AjaxPage.class));
    }

    private Button getAjaxSubmitter() {
        if (this.ajaxSubmitter == null) {
            this.ajaxSubmitter = new IndicatingAjaxButton("ajaxSubmitter") {
                @Override
                protected void onSubmit(final AjaxRequestTarget pTarget, @SuppressWarnings("unused") final Form<?> pForm) {
                    jabara.Debug.write("★★★★★★★★★★★★★");
                    final FileUpload upload = getFile().getFileUpload();
                    if (upload != null) {
                        System.out.println(upload.getClientFileName());
                    }

                    // pTarget.add(getFile());
                    pTarget.add(getNow());
                }
            };
        }
        return this.ajaxSubmitter;
    }

    private CharSequence getAjaxSubmitUrl() {
        final List<AjaxFormSubmitBehavior> bs = getAjaxSubmitter().getBehaviors(AjaxFormSubmitBehavior.class);
        return bs.isEmpty() ? Empty.STRING : bs.get(0).getCallbackUrl();
    }

    private Component getCustomSubmitter() {
        if (this.customSubmitter == null) {
            this.customSubmitter = new Button("customSubmitter");
            this.customSubmitter.add(getCustomSubmitterBehavior());
        }
        return this.customSubmitter;
    }

    private AjaxFormSubmitBehavior getCustomSubmitterBehavior() {
        if (this.customSubmitBehavior == null) {
            this.customSubmitBehavior = new AjaxFormSubmitBehavior("dummy") {
                @Override
                protected void onEvent(final AjaxRequestTarget pTarget) {
                    jabara.Debug.write("★★★★★★★★★★★★★");
                    final FileUpload upload = getFile().getFileUpload();
                    if (upload != null) {
                        System.out.println(upload.getClientFileName());
                    }
                    pTarget.add(getFile());
                    pTarget.add(getNow());
                }
            };
        }
        return this.customSubmitBehavior;
    }

    private FileUploadField getFile() {
        if (this.file == null) {
            this.file = new FileUploadField("file");
        }
        return this.file;
    }

    private Form<?> getForm() {
        if (this.form == null) {
            this.form = new Form<Object>("form");
            this.form.add(getFile());
            this.form.add(getAjaxSubmitter());
            this.form.add(getCustomSubmitter());
        }
        return this.form;
    }

    private Label getNoAjaxTarget() {
        if (this.noAjaxTarget == null) {
            this.noAjaxTarget = new Label("noAjaxTarget", new AbstractReadOnlyModel<String>() {
                @Override
                public String getObject() {
                    return DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                }
            });
        }
        return this.noAjaxTarget;
    }

    private Label getNow() {
        if (this.now == null) {
            this.now = new Label("now", new AbstractReadOnlyModel<String>() {
                @Override
                public String getObject() {
                    return DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                }
            });
        }
        return this.now;
    }

    private Link<?> getReloader() {
        if (this.reloader == null) {
            this.reloader = new BookmarkablePageLink<Object>("reloader", AjaxPage.class);
        }
        return this.reloader;
    }

    private FileUploadPanel getUploader() {
        if (this.uploader == null) {
            this.uploader = new FileUploadPanel("uploader");
            this.uploader.setOnUpload(new IAjaxCallback() {

                @Override
                public void call(final AjaxRequestTarget pTarget) {
                    pTarget.add(getNow());
                }
            });
        }
        return this.uploader;
    }

    private FileUploadPanel getUploader2() {
        if (this.uploader2 == null) {
            this.uploader2 = new FileUploadPanel("uploader2");
        }
        return this.uploader2;
    }

    private Form<?> getUploadForm() {
        if (this.uploadForm == null) {
            this.uploadForm = new Form<Object>("uploadForm");
            this.uploadForm.add(getUploader());
            this.uploadForm.add(getUploader2());
        }
        return this.uploadForm;
    }
}
