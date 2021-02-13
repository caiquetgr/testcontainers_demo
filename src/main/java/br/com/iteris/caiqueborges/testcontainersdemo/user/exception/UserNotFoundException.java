package br.com.iteris.caiqueborges.testcontainersdemo.user.exception;

import br.com.iteris.caiqueborges.testcontainersdemo.config.controlleradvice.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends BaseException {

    private final Long userId;

    public UserNotFoundException(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getMessageKey() {
        return "user.not-found";
    }

    @Override
    public Map<String, Object> getMessageVariables() {
        return Map.of("id", userId);
    }

}
