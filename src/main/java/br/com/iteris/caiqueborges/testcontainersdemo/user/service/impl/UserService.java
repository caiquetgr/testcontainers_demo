package br.com.iteris.caiqueborges.testcontainersdemo.user.service.impl;

import br.com.iteris.caiqueborges.testcontainersdemo.user.entity.User;
import br.com.iteris.caiqueborges.testcontainersdemo.user.exception.UserNotFoundException;
import br.com.iteris.caiqueborges.testcontainersdemo.user.repository.UserRepository;
import br.com.iteris.caiqueborges.testcontainersdemo.user.service.UserReadService;
import br.com.iteris.caiqueborges.testcontainersdemo.user.service.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@RequiredArgsConstructor
@Service
class UserService implements UserReadService, UserUpdateService {

    private final UserRepository userRepository;

    @Override
    public User findById(@NotNull Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

}
