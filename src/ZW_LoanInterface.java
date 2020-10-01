/**
	* Class ZW_LoanInterface.java
	* Purpose: Interface that stores ANNUAL_RATE_TO_MONTHLY_RATE constant and an abstract method for calculating loan payment
	* @author:Zitong Wang, 0975104
	* Date:    Jul 18, 2020
*/
public interface ZW_LoanInterface {
	//Declare constant and abstract method
	final double ANNUAL_RATE_TO_MONTHLY_RATE = 1/1200.0;
	double calculateLoanPayment(double principal, double primeRate, int amortization);
}//End of interface
