package sandbox.quickstart.web.ui.page;

import jabara.general.Empty;
import jabara.general.NameValue;
import jabara.wicket.CssUtil;
import jabara.wicket.JavaScriptUtil;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;

import sandbox.quickstart.Environment;

/**
 *
 */
@SuppressWarnings({ "nls", "serial", "synthetic-access" })
public class AjaxPage extends WebPage {
    private static final long      serialVersionUID = -4965903336608758671L;

    private Link<?>                reloader;
    private Label                  now;
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
        this.add(getNow());
        this.add(getForm());
    }

    /**
     * @see org.apache.wicket.Component#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        pResponse.render(CssUtil.forComponentCssHeaderItem(AjaxPage.class));
        pResponse.render(JavaScriptUtil.forVariablesScriptHeaderItem(AjaxPage.class, AjaxPage.class.getSimpleName() + "_vars.js" //
        , new NameValue<>("package", Environment.class.getPackage().getName()) //
                , new NameValue<>("formId", getForm().getMarkupId()) //
                , new NameValue<>("fileId", getFile().getMarkupId()) //
                , new NameValue<>("customSutmitterId", getCustomSubmitter().getMarkupId()) //
                , new NameValue<>("customSubmitUrl", getCustomSubmitterBehavior().getCallbackUrl()) //
                , new NameValue<>("ajaxSubmitterId", getAjaxSubmitter().getMarkupId()) //
                , new NameValue<>("ajaxSubmitUrl", getAjaxSubmitUrl()) //
                ));
        pResponse.render(JavaScriptUtil.forComponentJavaScriptHeaderItem(AjaxPage.class));
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

                    pTarget.add(getFile());
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
            this.form = new Form<>("form");
            this.form.add(getFile());
            this.form.add(getAjaxSubmitter());
            this.form.add(getCustomSubmitter());
        }
        return this.form;
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
            this.reloader = new BookmarkablePageLink<>("reloader", AjaxPage.class);
        }
        return this.reloader;
    }
}
