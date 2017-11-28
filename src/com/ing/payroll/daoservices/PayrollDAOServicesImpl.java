package com.ing.payroll.daoservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ing.payroll.beans.Associate;
import com.ing.payroll.beans.BankDetails;
import com.ing.payroll.beans.Salary;
import com.ing.payroll.exception.PayrollServicesDownException;
import com.ing.payroll.provider.ServiceProvider;

import java.sql.ResultSet;

public class PayrollDAOServicesImpl implements PayrollDAOServices {

	public static HashMap<Integer, Associate> associates = new HashMap<>();
	public static int ASSOCIATE_ID_COUNTER = 1005;

	@Override
	public int insertAssociate(Associate associate) throws SQLException,
			PayrollServicesDownException {

		int associateId = PayrollDAOServicesImpl.ASSOCIATE_ID_COUNTER;
		Connection con = ServiceProvider.getDBConnection();
		try {
			con.setAutoCommit(false);
			String sql = "INSERT INTO associate (firstName,lastName,emailId,department,designation,pancard,yearlyInvestmentUnder8oC) VALUES(?,?,?,?,?,?,?);";
			PreparedStatement stmt = con.prepareStatement(sql);
			// Associate
			stmt.setString(1, associate.getFirstName());
			stmt.setString(2, associate.getLastName());
			stmt.setString(3, associate.getEmailId());
			stmt.setString(4, associate.getDepartment());
			stmt.setString(5, associate.getDesignation());
			stmt.setString(6, associate.getPancard());
			stmt.setInt(7, associate.getYearlyInvestmentUnder80C());
			stmt.executeUpdate();
			ResultSet rs = stmt
					.executeQuery("select max(associateId) from associate");
			rs.next();
			associateId = rs.getInt(1);

			// Salary
			sql = "INSERT INTO salary (associateId, basicSalary,hra,conveyanceAllowance,otherAllowance,personalAllowance,monthlyTax,epf,companyPf,gratuity,grossSalary,netSalary) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, associateId);
			stmt.setInt(2, associate.getSalary().getBasicSalary());
			stmt.setInt(3, associate.getSalary().getHra());
			stmt.setInt(4, associate.getSalary().getConveyenceAllowance());
			stmt.setInt(5, associate.getSalary().getOtherAllowance());
			stmt.setInt(6, associate.getSalary().getPersonalAllowance());
			stmt.setInt(7, associate.getSalary().getMonthlyTax());
			stmt.setInt(8, associate.getSalary().getEpf());
			stmt.setInt(9, associate.getSalary().getCompanyPf());
			stmt.setInt(10, associate.getSalary().getGratuity());
			stmt.setInt(11, associate.getSalary().getGrossSalary());
			stmt.setInt(12, associate.getSalary().getNetSalary());
			stmt.executeUpdate();

			sql = "INSERT INTO bankdetails (associateId,accountNo,bankName, ifscCode) VALUES(?,?,?,?)";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, associateId);
			stmt.setInt(2, associate.getBankDetails().getAccountNumber());
			stmt.setString(3, associate.getBankDetails().getBankName());
			stmt.setString(4, associate.getBankDetails().getIfscCode());
			stmt.executeUpdate();

			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			con.setAutoCommit(true);
		}

		return associateId;
	}

	@Override
	public boolean updateAssociate(Associate associate) throws SQLException,
			PayrollServicesDownException {

		int rowsUpdated = 0;
		int associateId = PayrollDAOServicesImpl.ASSOCIATE_ID_COUNTER;
		Connection con = ServiceProvider.getDBConnection();
		try {
			con.setAutoCommit(false);
			String sql = "update associate set firstName=?, lastName=?,emailId=?,department=?,designation=?,pancard=?,yearlyInvestmentUnder8oC=? where associateId=?;";
			PreparedStatement stmt = con.prepareStatement(sql);
			// Associate
			stmt.setString(1, associate.getFirstName());
			stmt.setString(2, associate.getLastName());
			stmt.setString(3, associate.getEmailId());
			stmt.setString(4, associate.getDepartment());
			stmt.setString(5, associate.getDesignation());
			stmt.setString(6, associate.getPancard());
			stmt.setInt(7, associate.getYearlyInvestmentUnder80C());
			stmt.setInt(8, associate.getAssociateID());
			stmt.executeUpdate();

			// Salary
			sql = "update salary set basicSalary=?,hra=?,conveyanceAllowance=?,otherAllowance=?,personalAllowance=?,monthlyTax=?,epf=?,companyPf=?,gratuity=?,grossSalary=?,netSalary=? where associateId=?;";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, associate.getSalary().getBasicSalary());
			stmt.setInt(2, associate.getSalary().getHra());
			stmt.setInt(3, associate.getSalary().getConveyenceAllowance());
			stmt.setInt(4, associate.getSalary().getOtherAllowance());
			stmt.setInt(5, associate.getSalary().getPersonalAllowance());
			stmt.setInt(6, associate.getSalary().getMonthlyTax());
			stmt.setInt(7, associate.getSalary().getEpf());
			stmt.setInt(8, associate.getSalary().getCompanyPf());
			stmt.setInt(9, associate.getSalary().getGratuity());
			stmt.setInt(10, associate.getSalary().getGrossSalary());
			stmt.setInt(11, associate.getSalary().getNetSalary());
			stmt.setInt(12, associate.getAssociateID());
			stmt.executeUpdate();

			sql = "update bankdetails set accountNo=?,bankName=?, ifscCode=? where associateId=?;";
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, associate.getBankDetails().getAccountNumber());
			stmt.setString(2, associate.getBankDetails().getBankName());
			stmt.setString(3, associate.getBankDetails().getIfscCode());
			stmt.setInt(4, associateId);
			rowsUpdated = stmt.executeUpdate();

			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			con.setAutoCommit(true);
		}

		if (rowsUpdated == 1)
			return true;
		else
			return false;
	}

	@Override
	public boolean deleteAssciate(int associateId) throws SQLException,
			PayrollServicesDownException {

		int rowsDeleted = 0;
		Connection con = ServiceProvider.getDBConnection();
		try {
			con.setAutoCommit(false);
			PreparedStatement stmt = con
					.prepareStatement("delete from salary where associateId = ?;");
			stmt.setInt(1, associateId);
			stmt.executeUpdate();

			stmt = con
					.prepareStatement("delete from bankdetails where associateId = ?;");
			stmt.setInt(1, associateId);
			stmt.executeUpdate();

			stmt = con
					.prepareStatement("delete from associate where associateId = ?;");
			stmt.setInt(1, associateId);
			rowsDeleted = stmt.executeUpdate();

			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			con.setAutoCommit(true);
		}
		if (rowsDeleted == 1)
			return true;
		else
			return false;
	}

	@Override
	public Associate getAssociate(int associateId) throws SQLException,
			PayrollServicesDownException {

		Connection con = ServiceProvider.getDBConnection();
		String sql = "select * from associate a,salary s,bankdetails b where a.associateId = s.associateId and a.associateId = b.associateId and a.associateId = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, associateId);
		ResultSet rs = stmt.executeQuery();
		rs.next();

		Associate associate = new Associate(associateId,
				rs.getInt("yearlyInvestmentUnder8oC"),
				rs.getString("firstName"), rs.getString("lastName"),
				rs.getString("department"), rs.getString("designation"),
				rs.getString("pancard"), rs.getString("emailId"), 
				
				new Salary(rs.getInt("basicSalary"),rs.getInt("hra"),rs.getInt("conveyanceAllowance"),rs.getInt("otherAllowance"),
						rs.getInt("personalAllowance"),rs.getInt("monthlyTax"),rs.getInt("epf"),rs.getInt("companyPf"),
						rs.getInt("gratuity"),rs.getInt("grossSalary"),rs.getInt("netSalary")), 
				
				new BankDetails(
						rs.getInt("accountNo"), rs.getString("bankName"),
						rs.getString("ifscCode")));

		return associate;
	}

	@Override
	public List<Associate> getAssociates() throws SQLException,
			PayrollServicesDownException {
		List<Associate> associateList = new ArrayList<Associate>();
		Connection con = ServiceProvider.getDBConnection();
		String sql = "select * from associate a,salary s,bankdetails b where a.associateId = s.associateId and a.associateId = b.associateId";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Associate associate = new Associate(rs.getInt("associateId"),
					rs.getInt("yearlyInvestmentUnder8oC"),
					rs.getString("firstName"), rs.getString("lastName"),
					rs.getString("department"), rs.getString("designation"),
					rs.getString("pancard"), rs.getString("emailId"),
					new Salary(rs.getInt("basicSalary"), rs.getInt("epf"), rs
							.getInt("companyPf")), new BankDetails(
							rs.getInt("accountNo"), rs.getString("bankName"),
							rs.getString("ifscCode")));

			associateList.add(associate);
		}
		return associateList;
	}
}
