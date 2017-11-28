package com.ing.payroll.services;
import java.sql.SQLException;
import java.util.List;

import com.ing.payroll.beans.Associate;
import com.ing.payroll.beans.BankDetails;
import com.ing.payroll.beans.Salary;
import com.ing.payroll.daoservices.PayrollDAOServices;
import com.ing.payroll.exception.AssociateDetailsNotFoundException;
import com.ing.payroll.exception.PayrollServicesDownException;
import com.ing.payroll.provider.ServiceProvider;
public class PayrollServicesImpl implements PayrollServices{
	
	private PayrollDAOServices payrollDAOServices ;
	
	public PayrollServicesImpl() throws PayrollServicesDownException {//throws PayrollServicesDownException {
			payrollDAOServices  = ServiceProvider.getPayrollDAOServices();
	}
	
	public PayrollServicesImpl(PayrollDAOServices payrollDAOServices) {
		this.payrollDAOServices=payrollDAOServices;
	}
	
	@Override
	public int acceptAssociateDetails(String firstName, String lastName, String emailId, String department,
			String designation, String pancard, int yearlyInvestmentUnder80C, int basicSalary, int companyPf, int epf,
			int accountNumber, String bankName, String ifscCode) throws PayrollServicesDownException {
		Associate associate = new Associate(yearlyInvestmentUnder80C, firstName, lastName, department, designation, pancard, emailId, new Salary(basicSalary, epf, companyPf), new BankDetails(accountNumber, bankName, ifscCode));
		try {
			return payrollDAOServices.insertAssociate(associate);
		} catch (SQLException e) {
			throw new PayrollServicesDownException("PayrollServices are down, try again later",e);
		}
	}

	@Override
	public int calculateNetSalary(int associateId)
			throws AssociateDetailsNotFoundException, PayrollServicesDownException {
		try {
			
			Associate associate = payrollDAOServices.getAssociate(associateId);
			
			// Calculate Salary
			int hra = (associate.getSalary().getBasicSalary()*10)/100;
			int conveyenceAllowance = (associate.getSalary().getBasicSalary()*5)/100;
			int otherAllowance = (associate.getSalary().getBasicSalary()*6)/100;
			int personalAllowance = (associate.getSalary().getBasicSalary()*7)/100;
			int epf = 7500;
			int companyPf = 7500;
			int gratuity = 2000;
			int grossSalary = (associate.getSalary().getBasicSalary() + hra + personalAllowance + otherAllowance + epf + gratuity);
			int monthlyTax = ((grossSalary - (hra + epf ))*30)/100;
			int netSalary = grossSalary - (monthlyTax + epf);
			
			// Update Associate in DB
			associate.getSalary().setHra(hra);
			associate.getSalary().setConveyenceAllowance(conveyenceAllowance);
			associate.getSalary().setOtherAllowance(otherAllowance);
			associate.getSalary().setPersonalAllowance(personalAllowance);
			associate.getSalary().setMonthlyTax(monthlyTax);
			associate.getSalary().setEpf(epf);
			associate.getSalary().setCompanyPf(companyPf);
			associate.getSalary().setGratuity(gratuity);
			associate.getSalary().setGrossSalary(grossSalary);
			associate.getSalary().setNetSalary(netSalary);			
			payrollDAOServices.updateAssociate(associate);
			return associate.getSalary().getNetSalary();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new PayrollServicesDownException("PayrollServices are down, try again later",e);
		}
	}

	@Override
	public Associate getAssociateDetails(int associateId)
			throws PayrollServicesDownException, AssociateDetailsNotFoundException {
		try {
			Associate associate =payrollDAOServices.getAssociate(associateId);
			if(associate==null) throw new AssociateDetailsNotFoundException("Associate details with Id "+associateId+" not found");
			return associate;
		} catch (SQLException e) {
			throw new PayrollServicesDownException("PayrollServices are down, try again later",e);
		}
	}

	@Override
	public List<Associate> getAllAssociatesDetails() throws PayrollServicesDownException {
		try {
			return  payrollDAOServices.getAssociates();
		} catch (SQLException e) {
			throw new PayrollServicesDownException("PayrollServices are down, try again later",e);
		}
	}

	@Override
	public boolean removeAssociate(int associateId) throws PayrollServicesDownException {
		
		try {
			return payrollDAOServices.deleteAssciate(associateId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new PayrollServicesDownException("PayrollServices are down, try again later",e);
		}
	}
	
	
}
