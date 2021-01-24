package model;

import javafx.beans.property.StringProperty;

public interface StaffWindowModel
{
    void staffBackButton();

    void getNewStaffData();

    void staffAcceptClient(String cpr);

    void staffChangeClientPassword(String value, String value1, String value2);

    void retriveClientDataPressed(String retriveClientDataField);

    void changeStaffPassword(String value, String value1, String value2);

    void managerChangeStaffPassword(String staffCPR, String value, String value1);

    void createNewStaff(String cpr, String name, String pass, String position);

    void deleteStaff(String cpr);

    void staffAcceptLoan(String id);

    void staffCloseServer();

    void managerChangeRate(String currency, String value);
}
