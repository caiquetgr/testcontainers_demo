package br.com.iteris.caiqueborges.testcontainersdemo.transference.fixture;

import br.com.iteris.caiqueborges.testcontainersdemo.transference.controller.model.TransferenceRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.math.BigDecimal;

public class TransferenceTemplateLoader implements TemplateLoader {

    public static final String TRANSFERENCE_REQUEST_VALID = "TRANSFERENCE_REQUEST_VALID";

    @Override
    public void load() {
        loadRequestTemplates();
    }

    private void loadRequestTemplates() {

        Fixture.of(TransferenceRequest.class)
                .addTemplate(TRANSFERENCE_REQUEST_VALID, new Rule() {{
                    add("senderId", 1L);
                    add("value", new BigDecimal("100.99"));
                    add("receiverId", 2L);
                }});

    }

}
