package br.com.iteris.caiqueborges.testcontainersdemo.transference.service.impl;

import br.com.iteris.caiqueborges.testcontainersdemo.transference.service.CalculateFeeService;
import br.com.iteris.caiqueborges.testcontainersdemo.transference.service.model.TransferenceDTO;
import br.com.iteris.caiqueborges.testcontainersdemo.user.entity.User;
import br.com.iteris.caiqueborges.testcontainersdemo.user.fixture.UserTemplateLoader;
import br.com.iteris.caiqueborges.testcontainersdemo.user.service.UserReadService;
import br.com.iteris.caiqueborges.testcontainersdemo.user.service.UserUpdateService;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Tag("transference")
@ExtendWith(MockitoExtension.class)
class TransferenceServiceTest {

    @Mock
    private UserReadService userReadService;

    @Mock
    private UserUpdateService userUpdateService;

    @Mock
    private CalculateFeeService calculateFeeService;

    @InjectMocks
    private TransferenceService transferenceService;

    @BeforeAll
    static void setupForAll() {
        FixtureFactoryLoader.loadTemplates("br.com.iteris.caiqueborges.testcontainersdemo.user.fixture");
    }

    @Test
    void whenTransferMoney_andTaxIsZero_thenUpdateBalanceForSenderAndReceiver() {

        // given
        final User senderUser = Fixture.from(User.class).gimme(UserTemplateLoader.USER_1);
        final User receiverUser = Fixture.from(User.class).gimme(UserTemplateLoader.USER_2);
        final BigDecimal value = new BigDecimal("100.00");
        final var userCaptor = ArgumentCaptor.forClass(User.class);

        given(userReadService.findById(senderUser.getId())).willReturn(senderUser);
        given(userReadService.findById(receiverUser.getId())).willReturn(receiverUser);

        given(calculateFeeService.discountFeeFromValue(value, senderUser, receiverUser))
                .willReturn(value);

        given(userUpdateService.updateUser(senderUser)).willReturn(senderUser);
        given(userUpdateService.updateUser(receiverUser)).willReturn(receiverUser);

        final TransferenceDTO transferenceDTO = new TransferenceDTO(senderUser.getId(), value, receiverUser.getId());

        // when
        assertThatCode(() -> transferenceService.transferMoney(transferenceDTO))
                .doesNotThrowAnyException();

        // then
        verify(userReadService).findById(senderUser.getId());
        verify(userReadService).findById(receiverUser.getId());

        verify(calculateFeeService).discountFeeFromValue(value, senderUser, receiverUser);

        verify(userUpdateService, times(2)).updateUser(userCaptor.capture());

        final List<User> usersCaptured = userCaptor.getAllValues();
        final User senderUserCaptured = usersCaptured.get(0);
        final User receiverUserCaptured = usersCaptured.get(1);

        assertThat(senderUserCaptured.getBalance()).isEqualTo(new BigDecimal("900.00"));
        assertThat(receiverUserCaptured.getBalance()).isEqualTo(new BigDecimal("1100.00"));

    }

}
