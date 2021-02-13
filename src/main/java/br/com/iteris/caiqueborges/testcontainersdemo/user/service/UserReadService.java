package br.com.iteris.caiqueborges.testcontainersdemo.user.service;

import br.com.iteris.caiqueborges.testcontainersdemo.user.entity.User;

import javax.validation.constraints.NotNull;

public interface UserReadService {

    User findById(@NotNull Long id);

}
