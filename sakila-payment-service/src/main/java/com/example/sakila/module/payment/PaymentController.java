package com.example.sakila.module.payment;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.api.PaymentsApi;
import com.example.sakila.generated.server.model.PaymentDTO;
import com.example.sakila.module.customer.Customer;
import com.example.sakila.module.customer.CustomerService;
import com.example.sakila.module.rental.Rental;
import com.example.sakila.module.rental.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PaymentController implements PaymentsApi {

  private final PaymentService paymentService;

  private final CustomerService customerService;

  private final RentalService rentalService;

  @Autowired
  public PaymentController(
      PaymentService paymentService, CustomerService customerService, RentalService rentalService
  ) {
    this.paymentService = paymentService;
    this.customerService = customerService;
    this.rentalService = rentalService;
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

  @Override
  public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
    return ResponseEntity.ok(toDTO(paymentService.createPayment(toEntity(paymentDTO))));
  }

  @Override
  public ResponseEntity<PaymentDTO> replacePayment(@PathVariable("id") Long id, @RequestBody PaymentDTO paymentDTO) {
    return ResponseEntity.ok(toDTO(paymentService.updatePayment(id, toEntity(paymentDTO))));
  }

  @Override
  public ResponseEntity<Void> deletePayment(@PathVariable("id") Long id) {
    paymentService.deletePayment(id);
    return ResponseEntity.ok(null);
  }

  private PaymentDTO toDTO(Payment payment) {
    PaymentDTO paymentDTO = new PaymentDTO();
    paymentDTO.setId(payment.getId());
    paymentDTO.setStaffId(payment.getStaff_id());
    paymentDTO.setAmount(payment.getAmount());
    if (paymentDTO.getPaymentDate() != null) {
      paymentDTO.setPaymentDate(OffsetDateTime.ofInstant(payment.getPaymentDate().toInstant(), ZoneId.systemDefault()));
    }
    if (payment.getCustomer() != null) paymentDTO.setCustomerId(payment.getCustomer().getId());
    if (payment.getRental() != null) paymentDTO.setRentalId(payment.getRental().getId());
    return paymentDTO;
  }

  private Payment toEntity(PaymentDTO paymentDTO) {
    Payment payment = new Payment();
    payment.setId(paymentDTO.getId());
    payment.setAmount(paymentDTO.getAmount());
    payment.setStaff_id(paymentDTO.getStaffId());

    if (paymentDTO.getCustomerId() != null) {
      Customer customer = customerService.getCustomerById(paymentDTO.getCustomerId());
      if (customer == null) throw new NotFoundException(
          "Customer for ID " + paymentDTO.getCustomerId() + " does not exist"
      );

      payment.setCustomer(customer);
    }

    if (paymentDTO.getRentalId() != null) {
      Rental rental = rentalService.getRentalById(paymentDTO.getRentalId());
      if (rental == null) throw new NotFoundException("Rental for ID " + paymentDTO.getRentalId() + " does not exist");

      payment.setRental(rental);
    }

    if (paymentDTO.getPaymentDate() != null) payment.setPaymentDate(Date.from(paymentDTO.getPaymentDate().toInstant()));

    return payment;
  }
}
