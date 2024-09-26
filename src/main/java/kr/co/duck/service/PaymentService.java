package kr.co.duck.service;

import kr.co.duck.domain.Payment;
import kr.co.duck.mapper.PaymentMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

	
	
	@Autowired

	private PaymentMapper paymentMapper;

	// ���� ���� ����
	public void insertPayment(Payment payment) {
		paymentMapper.insertPayment(payment);
	}

	// Ư�� ���� ���� ��ȸ
	public Payment getPayment(int paymentId) {
		return paymentMapper.getPayment(paymentId);
	}

	// ��� ���� ���� ��ȸ
	public List<Payment> getAllPayments() {
		return paymentMapper.getAllPayments();
	}

	// ���� ���� ������Ʈ
	public void updatePayment(Payment payment) {
		paymentMapper.updatePayment(payment);
	}

	// ���� ���� ����
	public void deletePayment(int paymentId) {
		paymentMapper.deletePayment(paymentId);
	}
}
