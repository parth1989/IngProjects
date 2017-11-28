package com.ing.payroll.daoservices;
import java.sql.SQLException;
import java.util.List;

import com.ing.payroll.beans.Associate;
import com.ing.payroll.exception.PayrollServicesDownException;

public interface PayrollDAOServices {
	int insertAssociate(Associate associate)throws SQLException, PayrollServicesDownException;
	boolean updateAssociate(Associate associate)throws SQLException, PayrollServicesDownException;
	boolean deleteAssciate(int associateId)throws SQLException, PayrollServicesDownException;
	Associate getAssociate(int associateId)throws SQLException, PayrollServicesDownException;
	List<Associate> getAssociates()throws SQLException, PayrollServicesDownException;
}
