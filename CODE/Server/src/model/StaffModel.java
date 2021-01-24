package model;

import data.RegisterData;
import data.StaffData;

import java.beans.PropertyChangeListener;

public interface StaffModel
{
    public void closeByIP(String ip);
    public StaffData getStaffData(String cpr, Boolean isManager);

    public String staffAcceptClient(String value);

    String staffChangeClientPassword(String firstField, String secondField, String thirdField, Boolean bai);

    RegisterData staffGetCLientData(String value);

    String changePasswordPressed(String firstField, String secondField, String thirdField, String cpr);

    String managerCreateNewStaff(String firstField, String secondField, String thirdField, String fourthField);

    String deleteStaff(String value);

    String staffAcceptLoan(String value);

    public void closeAll();

    public void addListener(String propertyName, PropertyChangeListener listener);

    String staffChangeRate(String firstField, String secondField);
}

