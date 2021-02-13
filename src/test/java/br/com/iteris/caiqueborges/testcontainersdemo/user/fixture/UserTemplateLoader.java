package br.com.iteris.caiqueborges.testcontainersdemo.user.fixture;

import br.com.iteris.caiqueborges.testcontainersdemo.user.entity.User;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.math.BigDecimal;

public class UserTemplateLoader implements TemplateLoader {

    public static final String USER_1 = "USER_1";
    public static final String USER_2 = "USER_2";

    @Override
    public void load() {
        loadEntities();
    }

    private void loadEntities() {

        Fixture.of(User.class)
                .addTemplate(USER_1, new Rule() {{
                    add("id", 1L);
                    add("bankName", "CaiqueBank");
                    add("balance", new BigDecimal("1000.00"));
                }});

        Fixture.of(User.class)
                .addTemplate(USER_2)
                .inherits(USER_1, new Rule() {{
                    add("id", 2L);
                }});

    }

}
