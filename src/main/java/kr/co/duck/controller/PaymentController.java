package kr.co.duck.controller;

import kr.co.duck.domain.Payment;
import kr.co.duck.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // 결제 정보 삽입
    @PostMapping("/add")
    public String addPayment(@RequestBody Payment payment) {
        paymentService.insertPayment(payment);
        return "Payment added successfully!";
    }

    // 특정 결제 정보 조회
    @GetMapping("/{paymentId}")
    public Payment getPayment(@PathVariable int paymentId) {
        return paymentService.getPayment(paymentId);
    }

    // 모든 결제 정보 조회
    @GetMapping("/all")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    // 결제 정보 업데이트
    @PutMapping("/update")
    public String updatePayment(@RequestBody Payment payment) {
        paymentService.updatePayment(payment);
        return "Payment updated successfully!";
    }

    // 결제 정보 삭제
    @DeleteMapping("/delete/{paymentId}")
    public String deletePayment(@PathVariable int paymentId) {
        paymentService.deletePayment(paymentId);
        return "Payment deleted successfully!";
    }
}
