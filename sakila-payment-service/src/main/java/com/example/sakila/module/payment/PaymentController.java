package com.example.sakila.module.payment;

import com.example.sakila.generated.server.api.PaymentsApi;
import com.example.sakila.generated.server.model.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PaymentController implements PaymentsApi {

  private final PaymentService paymentService;

  @Autowired
  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @Override
  public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        toDTO(paymentService.getPaymentById(id))
    );
  }

  @Override
  public ResponseEntity<List<PaymentDTO>> getPaymentsByRentalId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        paymentService.getPaymentsByRentalId(id).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<List<PaymentDTO>> getPaymentsByCustomerId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        paymentService.getPaymentsByCustomerId(id).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<List<PaymentDTO>> getPaymentsByStaffId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        paymentService.getPaymentsByStaffId(id).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  private PaymentDTO toDTO(Payment payment) {
    PaymentDTO paymentDTO = new PaymentDTO();
    paymentDTO.setId(payment.getId());
    paymentDTO.setCustomerId(payment.getCustomer().getId());
    paymentDTO.setStaffId(payment.getStaff_id());
    paymentDTO.setRentalId(payment.getRental().getId());
    paymentDTO.setAmount(payment.getAmount());
    paymentDTO.setPaymentDate(OffsetDateTime.ofInstant(payment.getPaymentDate().toInstant(), ZoneId.systemDefault()));
    return paymentDTO;
  }
}
