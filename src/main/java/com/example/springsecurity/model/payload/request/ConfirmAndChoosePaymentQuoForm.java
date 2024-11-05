package com.example.springsecurity.model.payload.request;

import com.example.springsecurity.model.entity.Quotation;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ConfirmAndChoosePaymentQuoForm {
    @NotBlank( message = "Status is required")
    private Quotation.Status status;
    @NotBlank(message = "Payment method is required")
    private Quotation.PaymentMethod paymentMethod;

}
