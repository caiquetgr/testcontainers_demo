package br.com.iteris.caiqueborges.testcontainersdemo.transference.service.impl;

import br.com.iteris.caiqueborges.testcontainersdemo.config.RedisConfig;
import br.com.iteris.caiqueborges.testcontainersdemo.user.entity.User;
import br.com.iteris.caiqueborges.testcontainersdemo.user.fixture.UserTemplateLoader;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class FeeServiceTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @InjectMocks
    private FeeService feeService;

    @BeforeAll
    static void setupForAll() {
        FixtureFactoryLoader.loadTemplates("br.com.iteris.caiqueborges.testcontainersdemo.user.fixture");
    }

    @Test
    void whenDiscountFeeFromValue_andUsersBanksAreEqual_thenShouldNotDiscountFee() {

        // given
        final User senderUser = Fixture.from(User.class).gimme(UserTemplateLoader.USER_1);
        final User receiverUser = Fixture.from(User.class).gimme(UserTemplateLoader.USER_2);
        final BigDecimal transferValue = new BigDecimal("100.00");

        //when
        final BigDecimal calculatedValue = feeService.discountFeeFromValue(transferValue, senderUser, receiverUser);

        //then
        assertThat(calculatedValue).isEqualByComparingTo(transferValue);

    }

    @Test
    void whenDiscountFeeFromValue_andUsersBanksAreNotEqual_thenShouldDiscountFee() {

        // given
        final User senderUser = Fixture.from(User.class).gimme(UserTemplateLoader.USER_1);
        final User receiverUser = Fixture.from(User.class).gimme(UserTemplateLoader.USER_2);
        final BigDecimal transferValue = new BigDecimal("100.00");

        receiverUser.setBankName(receiverUser.getBankName() + "2");

        final var valueOperationsMock = mock(ValueOperations.class);

        given(redisTemplate.opsForValue())
                .willReturn(valueOperationsMock);
        given(valueOperationsMock.get(RedisConfig.FEE_PERCENTAGE_DISCOUNT_KEY))
                .willReturn("0.05");

        //when
        final BigDecimal calculatedValue = feeService.discountFeeFromValue(transferValue, senderUser, receiverUser);

        //then
        assertThat(calculatedValue).isEqualByComparingTo(new BigDecimal("95.00"));

    }

}
