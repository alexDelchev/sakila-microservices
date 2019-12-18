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
