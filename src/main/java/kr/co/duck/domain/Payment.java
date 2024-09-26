package kr.co.duck.domain;

public class Payment {
    private int payment_id;
    private int member_id;
    private String subscription_date;
    private String expiry_date;
    private String is_renewed;
    private String payment_amount;
    private String payment_method;
    private String payment_info;
	public int getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public String getSubscription_date() {
		return subscription_date;
	}
	public void setSubscription_date(String subscription_date) {
		this.subscription_date = subscription_date;
	}
	public String getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}
	public String getIs_renewed() {
		return is_renewed;
	}
	public void setIs_renewed(String is_renewed) {
		this.is_renewed = is_renewed;
	}
	public String getPayment_amount() {
		return payment_amount;
	}
	public void setPayment_amount(String payment_amount) {
		this.payment_amount = payment_amount;
	}
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	public String getPayment_info() {
		return payment_info;
	}
	public void setPayment_info(String payment_info) {
		this.payment_info = payment_info;
	}

    // Getters and Setters
    
    
    
    
    
}
