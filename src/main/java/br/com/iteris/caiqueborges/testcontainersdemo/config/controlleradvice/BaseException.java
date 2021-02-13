package br.com.iteris.caiqueborges.testcontainersdemo.config.controlleradvice;

import java.util.Map;

public abstract class BaseException extends RuntimeException {

    public abstract String getMessageKey();

    public abstract Map<String, Object> getMessageVariables();

}
