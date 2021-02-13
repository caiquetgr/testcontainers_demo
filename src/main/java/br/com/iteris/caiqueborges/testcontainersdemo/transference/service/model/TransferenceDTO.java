package br.com.iteris.caiqueborges.testcontainersdemo.transference.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferenceDTO {

    @NotNull(message = "{transference.sender-id.not-null}")
    private Long senderId;

    @NotNull(message = "{transference.value.not-null}")
    @Positive(message = "{transference.value.positive}")
    private BigDecimal value;

    @NotNull(message = "{transference.sender-id.not-null}")
    private Long receiverId;

}
