package br.com.iteris.caiqueborges.testcontainersdemo.transference.service.impl;

import br.com.iteris.caiqueborges.testcontainersdemo.config.RedisConfig;
import br.com.iteris.caiqueborges.testcontainersdemo.transference.service.CalculateFeeService;
import br.com.iteris.caiqueborges.testcontainersdemo.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FeeService implements CalculateFeeService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public BigDecimal discountFeeFromValue(BigDecimal transferValue, User senderUser, User receiverUser) {

        if (StringUtils.equals(senderUser.getBankName(), receiverUser.getBankName())) {
            return transferValue;
        }

        return discountFee(transferValue);

    }

    private BigDecimal discountFee(BigDecimal transferValue) {

        final BigDecimal feePercentageDiscount = getFeePercentageToDiscount();
        final BigDecimal feeToDiscount = transferValue.multiply(feePercentageDiscount);

        return transferValue.subtract(feeToDiscount);

    }

    private BigDecimal getFeePercentageToDiscount() {

        return Optional.ofNullable(redisTemplate.opsForValue().get(RedisConfig.FEE_PERCENTAGE_DISCOUNT_KEY))
                .map(BigDecimal::new)
                .orElse(BigDecimal.ZERO);
        
    }

}
