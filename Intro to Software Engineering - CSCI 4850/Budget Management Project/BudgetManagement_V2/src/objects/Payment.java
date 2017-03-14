package objects;


public class Payment extends Transaction {
	int paymentid;
	int daydue;
	boolean paid;
	int datepaid;
	
	public Payment(int paymentid, int daydue, int paid) {
		super();
		this.paymentid = paymentid;
		this.daydue = daydue;
		if(paid==1) 
			this.paid = true;
		else
			this.paid = false;
	}
	
	public int getPaymentid() {
		return paymentid;
	}
	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public void setPaymentid(int paymentid) {
		this.paymentid = paymentid;
	}
	public int getDaydue() {
		return daydue;
	}
	public void setDaydue(int daydue) {
		this.daydue = daydue;
	}

	public void setDatepaid(int datepaid) {
		this.datepaid = datepaid;
	}
	public int getDatepaid() {
		return datepaid;
	}
	
}
