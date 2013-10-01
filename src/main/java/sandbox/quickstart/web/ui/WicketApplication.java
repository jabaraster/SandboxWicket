package sandbox.quickstart.web.ui;

import jabara.general.ArgUtil;
import jabara.wicket.MarkupIdForceOutputer;

import org.apache.wicket.Page;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.IProvider;

import sandbox.quickstart.web.ui.page.AjaxPage;

import com.google.inject.Injector;

/**
 *
 */
public class WicketApplication extends WebApplication {

    private static final String       ENC = "UTF-8";   //$NON-NLS-1$

    private final IProvider<Injector> injectorProvider;

    /**
     * @param pInjectorProvider Guiceの{@link Injector}を供給するオブジェクト. DI設定に使用します.
     */
    public WicketApplication(final IProvider<Injector> pInjectorProvider) {
        ArgUtil.checkNull(pInjectorProvider, "pInjectorProvider"); //$NON-NLS-1$
        this.injectorProvider = pInjectorProvider;
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends Page> getHomePage() {
        return AjaxPage.class;
    }

    /**
     * @see org.apache.wicket.protocol.http.WebApplication#init()
     */
    @Override
    protected void init() {
        super.init();

        mountPages();
        initializeEncoding();
        initializeInjection();
        initializeOther();
    }

    private void initializeEncoding() {
        getMarkupSettings().setDefaultMarkupEncoding(ENC);
        getRequestCycleSettings().setResponseRequestEncoding(getMarkupSettings().getDefaultMarkupEncoding());
    }

    private void initializeInjection() {
        getComponentInstantiationListeners().add(new GuiceComponentInjector(this, this.injectorProvider.get()));
    }

    private void initializeOther() {
        getComponentInstantiationListeners().add(new MarkupIdForceOutputer());
    }

    private void mountPages() {
    }
}
