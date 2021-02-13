package br.com.iteris.caiqueborges.testcontainersdemo.user.service.impl;

import br.com.iteris.caiqueborges.testcontainersdemo.user.entity.User;
import br.com.iteris.caiqueborges.testcontainersdemo.user.exception.UserNotFoundException;
import br.com.iteris.caiqueborges.testcontainersdemo.user.fixture.UserTemplateLoader;
import br.com.iteris.caiqueborges.testcontainersdemo.user.repository.UserRepository;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeAll
    static void setupForAll() {
        FixtureFactoryLoader.loadTemplates("br.com.iteris.caiqueborges.testcontainersdemo.user.fixture");
    }

    @Test
    void whenFindById_thenReturnUser() {

        final User user = Fixture.from(User.class).gimme(UserTemplateLoader.USER_1);
        final Long userId = user.getId();

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        final User userReturned = userService.findById(userId);

        assertThat(userReturned).isEqualTo(user);
        verify(userRepository).findById(userId);

    }

    @Test
    void whenFindById_andReturnsOptionalEmpty_thenThrowUserNotFoundException() {

        final Long userId = 1L;

        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById(userId))
                .isInstanceOf(UserNotFoundException.class);

        verify(userRepository).findById(userId);

    }

    @Test
    void whenUpdateUser_thenReturnUser() {

        final User user = Fixture.from(User.class).gimme(UserTemplateLoader.USER_1);

        given(userRepository.save(user)).willReturn(user);

        final User returnedUser = userService.updateUser(user);

        assertThat(returnedUser)
                .usingRecursiveComparison()
                .isEqualTo(user);

    }

}
