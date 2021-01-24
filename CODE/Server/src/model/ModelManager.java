package model;

import data.*;

import java.sql.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModelManager implements Model, LoginModel, RegisterModel, ClientModel, StaffModel
{
    private PropertyChangeSupport support;
    int counter;
    String dateField = "";
    String time = "";
    String invertedDate = "";
    //////////
    Connection con;


    public ModelManager() throws ClassNotFoundException, SQLException
    {
        support = new PropertyChangeSupport(this);
        counter = 0;

        Class.forName("org.postgresql.Driver");
        String schemaName = "\"bbb_dbs\"";
        String TableName = "\"user\"";
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BBB-DataBase", "postgres", "MuiePSD2020");
        PreparedStatement showEverything = con.prepareStatement("SELECT * FROM " + schemaName + "." + TableName);
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatterDate = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat formatterDateInverted = new SimpleDateFormat("dd/mm/yyyy");

        Runnable runnable = () -> {
            while (true) {
                java.util.Date date = new Date();
                time = formatterTime.format(date);
                dateField = formatterDate.format(date);
                invertedDate = formatterDateInverted.format(date);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        t.start();

        Runnable runnable2 = () -> {
            while (true) {

                try {
                    con.prepareStatement("DELETE FROM bbb_dbs.user WHERE deletion_date = '" + invertedDate + "';").executeUpdate();
                    Thread.sleep(1000 * 60 * 60 * 24);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t2 = new Thread(runnable2);
        t2.setDaemon(true);
        t2.start();
    }

    public synchronized void closeByIP(String ip)
    {
        support.firePropertyChange("ClientDisconnected", "", ip);
    }



    //LOGIN --------

    @Override
    public String checkLogInData(String email, String password, String ip)
    {
        try {
            ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.user;").executeQuery();
            while (rs.next()) {
                if (rs.getString(5).equals(email) && rs.getString(6).equals(password) ||
                        rs.getString(1).equals(email) && rs.getString(6).equals(password))
                {
                    if (!rs.getBoolean(7))
                        return "User has not been approved yet";
                    if(rs.getString(9).equals("client"))
                    {
                        con.prepareStatement("UPDATE bbb_dbs.user SET deletion_date = '" + "01/01/1800" +
                                "' WHERE cpr = '" + rs.getString(1) + "';").executeUpdate();
                        con.prepareStatement("INSERT INTO bbb_dbs.history VALUES(DEFAULT, '" + dateField +
                                "', '" + time + "', 'LOGGED IN WITH IP: " + ip + "', '" + rs.getString(1) + "')").executeUpdate();
                    }
                    return rs.getString(1) + rs.getString(9);
                }
            }
            return "Wrong email/password";
        } catch (SQLException e)
        {
            e.printStackTrace();
            return "Something went wrong";
        }
    }

    //LOGIN ---------


    //Register -----------

    @Override
    public String registerClient(RegisterData registerData)
    {
        try {
            if (registerData.getCPR().length() != 10 || !registerData.getCPR().chars().allMatch(Character::isDigit))
                return "Invalid CPR";

            if (!registerData.getPhoneNumber().chars().allMatch(Character::isDigit))
                return "Invalid Phone Number, use digits only";
            ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.user;").executeQuery();

            while(rs.next())
            {
                if(registerData.getCPR().equals(rs.getString(1)))
                    return "CPR already used";

                if(registerData.getPhoneNumber().equals(rs.getString(4)))
                    return "Phone number already used";

                if(registerData.getEmail().equals(rs.getString(5)))
                    return "Email already used";
            }

            if(registerData.getPassword().length() < 8)
                return "Password too short";

            if (!registerData.getPassword().equals(registerData.getConfirmPassword()))
                return "Passwords dont match";

            con.prepareStatement("INSERT INTO bbb_dbs.user VALUES ('" + registerData.getCPR() + "', '" + registerData.getFullName() + "', '" + registerData.getAdress() + "', '" + registerData.getPhoneNumber() + "', '" + registerData.getEmail() + "', '" + registerData.getPassword() + "', '" + false + "', '" + "01/01/1800" + "', '" + "client')").executeUpdate();
            rs = con.prepareStatement("SELECT * FROM bbb_dbs.exchange_rate;").executeQuery();
            int aux = 1;
            while (rs.next()) {
                if (rs.getString(1).equals("DKK") || rs.getString(1).equals("dkk")) {
                    System.out.println("--" + rs.getString(3));
                    aux = Integer.parseInt(rs.getString(3));
                    aux++;
                }
            }
            con.prepareStatement("UPDATE bbb_dbs.exchange_rate SET number_of_accounts = '" + aux + "' WHERE name = 'DKK'; ").executeUpdate();
            con.prepareStatement("INSERT INTO bbb_dbs.account VALUES ('DKK" + String.format("%017d", aux) + "',  '-Main account-----Cannot be deleted-----Do not use this name-', '00.00', '" + registerData.getCPR() + "')").executeUpdate();
            con.prepareStatement("INSERT INTO bbb_dbs.history VALUES(DEFAULT, '" + dateField + "', '" + time + "', 'ACCOUNT CREATED', '" + registerData.getCPR() + "')").executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return "SOMETHING WENT WRONG";
        }
        return "NEW ACCOUNT CREATED";
    }

    //Register end------


    //Client ---------


    @Override
    public ClientData getClientData(String cpr)
    {
        ClientData clientData2;
        try {
            ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.user WHERE cpr = '" + cpr + "'").executeQuery();

            rs.next();
            clientData2 = new ClientData();
            clientData2.setPhoneNumber(rs.getString(4));
            clientData2.setEmailAdress(rs.getString(5));
            clientData2.setAdress(rs.getString(3));

            rs = con.prepareStatement("SELECT t.* FROM bbb_dbs.user e, bbb_dbs.account t WHERE e.cpr = t.cpr AND e.cpr = '" + cpr + "'").executeQuery();
            while (rs.next()) {
                clientData2.addAccountData(new AccountData(rs.getString(1).toString().substring(0, 3), rs.getString(1), rs.getString(2), String.valueOf(rs.getDouble(3))));
            }

            rs = con.prepareStatement("SELECT t.* FROM bbb_dbs.user e, bbb_dbs.history t WHERE e.cpr = t.cpr AND e.cpr = '" + cpr + "'").executeQuery();
            while (rs.next()) {
                clientData2.addHistoryData(new HistoryData(rs.getString(2), rs.getString(3), rs.getString(4)));
            }

            rs = con.prepareStatement("SELECT * FROM bbb_dbs.exchange_rate;").executeQuery();
            while (rs.next()) {
                clientData2.addExchangeCurrency(rs.getString(1) + " - " + rs.getString(2));
            }

            return clientData2;
        } catch (Exception e) {
            e.printStackTrace();
        }
//
        return null;
    }

    @Override
    public String transferFunds(String from, String to, String sum, String cpr)
    {
        sum = sum.replace(',', '.');

        try {
            ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.account WHERE account_nr = '" + from + "';").executeQuery();
            rs.next();
            double aux = rs.getDouble(3);
            if (aux - Double.parseDouble(sum) < 0)
                return "Not enough funds in selected account";
            else aux = aux - Double.parseDouble(sum);
            con.prepareStatement("UPDATE bbb_dbs.account SET balance = '" + aux + "' WHERE account_nr = '" + from + "';").executeUpdate();
            con.prepareStatement("INSERT INTO bbb_dbs.history VALUES(DEFAULT, '" + dateField + "', '" + time + "', 'Tranfered from account" + from + " amount: " + sum + " into account " + to + "', '" + cpr + "')").executeUpdate();

            double aux2 = Double.parseDouble(sum);

            rs = con.prepareStatement("SELECT * FROM bbb_dbs.exchange_rate;").executeQuery();
            while (rs.next()) {
                if(rs.getString(1).equals(from.substring(0, 3)))
                    aux2 = aux2 * rs.getDouble(2);
                else if(rs.getString(1).equals(to.substring(0, 3)))
                    aux2 = aux2 / rs.getDouble(2);
            }


            rs = con.prepareStatement("SELECT * FROM bbb_dbs.account WHERE account_nr = '" + to + "';").executeQuery();
            rs.next();
            con.prepareStatement("UPDATE bbb_dbs.account SET balance = '" + (rs.getDouble(3) + aux2) + "' WHERE account_nr = '" + to + "';").executeUpdate();
            if(!rs.getString(4).equals(cpr))
                con.prepareStatement("INSERT INTO bbb_dbs.history VALUES(DEFAULT, '" + dateField + "', '" + time + "', 'Received funds from" + from + " amount: " + aux2 + " into account " + to + "', '" + rs.getString(4) + "')").executeUpdate();

            return "Operation successful";

        } catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong, CHECK ACCOUNT NUMBER";
        }


    }

    @Override
    public String requestLoan(String sum, String cpr)
    {
        sum = sum.replace(',', '.');
        if(sum.equals(""))
            return "Cant take a loan with 0 money";
        try {
            con.prepareStatement("INSERT INTO bbb_dbs.loan VALUES(DEFAULT, '" + sum + "', '" + cpr + "')").executeUpdate();
            con.prepareStatement("INSERT INTO bbb_dbs.history VALUES(DEFAULT, '" + dateField + "', '" + time + "', 'LOAN REQUESTED AMOUNT: " + sum + "', '" + cpr + "')").executeUpdate();
            return "Operation successful";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Operation failed";
    }

    @Override
    public String closeAccount(String fromAccount, String cpr)
    {

        try {
            ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.account WHERE account_nr = '" + fromAccount + "';").executeQuery();
            rs.next();

            double aux = rs.getDouble(3);
            if (aux != 0) {
                return "ACCOUNT NOT EMPTY!";
            }

            con.prepareStatement("DELETE FROM bbb_dbs.account WHERE account_nr = '" + fromAccount + "';").executeUpdate();
            con.prepareStatement("INSERT INTO bbb_dbs.history VALUES(DEFAULT, '" + dateField + "', '" + time + "', 'Deleted account - " + fromAccount + "', '" + cpr + "')").executeUpdate();
            return "Operation successful";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Something went wrong";
    }

    @Override
    public String openAccount(String description, String currency, String cpr)
    {
        try {
            if(description.equals("-Main account-----Cannot be deleted-----Do not use this name-"))
                return "Invalid description";
            currency = currency.substring(0, 3);
            ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.exchange_rate;").executeQuery();
            int aux = 1;
            while (rs.next()) {
                if (rs.getString(1).toLowerCase().equals(currency.toLowerCase())) {
                    aux = Integer.parseInt(rs.getString(3));
                    aux++;
                }
            }
            con.prepareStatement("UPDATE bbb_dbs.exchange_rate SET number_of_accounts = '" + aux + "' WHERE name = '" + currency + "'; ").executeUpdate();
            con.prepareStatement("INSERT INTO bbb_dbs.account VALUES ('" + currency + "" + String.format("%017d", aux) + "',  '" + description + "', '00.00', '" + cpr + "')").executeUpdate();
            con.prepareStatement("INSERT INTO bbb_dbs.history VALUES(DEFAULT, '" + dateField + "', '" + time + "', 'NEW ACCOUNT CREATED: " + currency + "', '" + cpr + "')").executeUpdate();
            return "Operation successful";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAILED";
    }

    @Override
    public String withdrawFunds(String account, String amount, String cpr)
    {
        amount = amount.replace(',', '.');

        try {
            ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.account WHERE account_nr = '" + account + "';").executeQuery();
            rs.next();
            double aux = rs.getDouble(3);
            if (aux - Double.parseDouble(amount) < 0)
                return "Not enough funds in selected account";
            else aux = aux - Double.parseDouble(amount);
            con.prepareStatement("UPDATE bbb_dbs.account SET balance = '" + aux + "' WHERE account_nr = '" + account + "';").executeUpdate();
            con.prepareStatement("INSERT INTO bbb_dbs.history VALUES(DEFAULT, '" + dateField + "', '" + time + "', 'WITHDRWE from account-" + account + "-amount: " + aux + "', '" + cpr + "')").executeUpdate();
            return "Operation successful";
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "Operation failed";
    }

    @Override
    public String addFunds(String account, String amount, String cpr)
    {
        amount = amount.replace(',', '.');

        try {
            ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.account WHERE account_nr = '" + account + "';").executeQuery();
            rs.next();
            double aux = rs.getDouble(3) + Double.parseDouble(amount);
            con.prepareStatement("UPDATE bbb_dbs.account SET balance = '" + aux + "' WHERE account_nr = '" + account + "';").executeUpdate();
            con.prepareStatement("INSERT INTO bbb_dbs.history VALUES(DEFAULT, '" + dateField + "', '" + time + "', 'Added to account-" + account + "-amount: " + aux + "', '" + cpr + "')").executeUpdate();
            return "Operation successful";
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "Operation failed";
    }

    @Override
    public String changePasswordPressed(String oldPassword, String newPassword, String confirmNewPassword, String cpr)
    {
        try {
                ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.user WHERE cpr = '" + cpr + "'").executeQuery();
                rs.next();

                if(!rs.getString(6).equals(oldPassword))
                    return "Wrong old password";

                if(newPassword.length() < 8)
                    return "Password too short";
                if(oldPassword.equals(newPassword))
                    return "New passwords cant be the same as old one";

                if(!confirmNewPassword.equals(newPassword))
                    return "New passwords don't match";

                con.prepareStatement("UPDATE bbb_dbs.user SET password = '" + newPassword + "' WHERE cpr = '" + cpr + "';").executeUpdate();

                if(rs.getString(9).equals("client"))
                    con.prepareStatement("INSERT INTO bbb_dbs.history VALUES(DEFAULT, '" + dateField + "', '" + time + "', 'PASSWORD CHANGED', '" + cpr + "')").executeUpdate();

            }catch (Exception e)
            {
               e.printStackTrace();
               return "Something went wrong";
            }

        return "Operation successful";
    }


    @Override
    public String changeNumberPressed(String newNumber, String cpr)
    {
        try {
            ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.user WHERE cpr = '" + cpr + "'").executeQuery();
            rs.next();
            if(newNumber.equals(rs.getString(4)))
                return "New number cant be the same as old one";

            con.prepareStatement("UPDATE bbb_dbs.user SET phone_nr = '" + newNumber + "' WHERE cpr = '" + cpr + "';").executeUpdate();

            con.prepareStatement("INSERT INTO bbb_dbs.history VALUES(DEFAULT, '" + dateField + "', '" + time + "', 'PHONE NUMBER CHANGED', '" + cpr + "')").executeUpdate();

        }catch (Exception e)
        {
            e.printStackTrace();
            return "Something went wrong";
        }

        return "Operation successful";
    }

    @Override
    public String changeAdressPressed(String newAdreess, String cpr)
    {
        try {
            ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.user WHERE cpr = '" + cpr + "'").executeQuery();
            rs.next();
            if(newAdreess.equals(rs.getString(3)))
                return "New adress cant be the same as old one";

            con.prepareStatement("UPDATE bbb_dbs.user SET address = '" + newAdreess + "' WHERE cpr = '" + cpr + "';").executeUpdate();

            con.prepareStatement("INSERT INTO bbb_dbs.history VALUES(DEFAULT, '" + dateField + "', '" + time + "', 'ADRESS CHANGED', '" + cpr + "')").executeUpdate();

        }catch (Exception e)
        {
            e.printStackTrace();
            return "Something went wrong";
        }

        return "Operation successful";
    }

    @Override
    public String deleteAccountPressed(String cpr)
    {
        System.out.println("---" + dateField);
        String aux;
        aux = "12/" + dateField.substring(3);
        System.out.println("-------" + aux);
        try {
            con.prepareStatement("UPDATE bbb_dbs.user SET deletion_date = '" + aux + "' WHERE cpr = '" + cpr + "';").executeUpdate();
            return "Operation successful";
        } catch (SQLException e) {
            e.printStackTrace();
            return  "Something went wrong";
        }
    }


    //Client end------


    //Staff start------

    @Override
    public StaffData getStaffData(String cpr, Boolean isManager)
    {
        StaffData staffData = new StaffData();

        RegisterData bai3 = new RegisterData("afewer", "5234567890", "BLYAT 33", "52638232");

        try {
            ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.user WHERE approve ='false'").executeQuery();
            while (rs.next())
            {
                RegisterData bai = new RegisterData(rs.getString(2), rs.getString(1), rs.getString(3), rs.getString(4));
                bai.setRest(rs.getString(5), "", "2");
                staffData.addUsersToBeAccepted(bai);
            }

            rs = con.prepareStatement("SELECT * FROM bbb_dbs.loan;").executeQuery();
            while (rs.next())
            {
                staffData.addLoan(new LoanData(rs.getString(1), rs.getString(3), rs.getString(2)));
            }


            if(isManager)
            {
                rs = con.prepareStatement("SELECT * FROM bbb_dbs.user WHERE user_type ='staff' OR user_type ='manager';").executeQuery();
                while (rs.next())
                {
                    staffData.addStaffAccountData(new StaffAccountData(rs.getString(2), rs.getString(1), rs.getString(9)));
                }

                rs = con.prepareStatement("SELECT * FROM bbb_dbs.exchange_rate;").executeQuery();
                while (rs.next())
                {
                    staffData.addExchangeCurrency(rs.getString(1) + " - " + rs.getString(2));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



        return staffData;
    }

    @Override
    public String staffAcceptClient(String value)
    {

        try
        {
            ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.user WHERE cpr = '" + value + "'").executeQuery();
            rs.next();
            if(rs.getString(9).equals("staff") || rs.getString(9).equals("manager"))
            {
                return "FAIL";
            }

            con.prepareStatement("UPDATE bbb_dbs.user SET approve = 'true' WHERE cpr = '" + value + "';").executeUpdate();
            return "Operation successful";
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return "Operation successful";
    }

    @Override
    public String staffChangeClientPassword(String firstField, String secondField, String thirdField, Boolean bai)
    {
        try
        {
            ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.user WHERE cpr = '" + firstField + "'").executeQuery();
            rs.next();
            if(rs.getString(9).equals("manager") || rs.getString(9).equals("staff"))
                if(!bai)
                    return "Can't change staff passwords here";
            if(secondField.length() < 8)
                return "Password too short";
            if(!secondField.equals(thirdField))
                return "New passwords don't match!";

            con.prepareStatement("UPDATE bbb_dbs.user SET password = '" + secondField + "' WHERE cpr = '" + firstField + "';").executeUpdate();

            con.prepareStatement("INSERT INTO bbb_dbs.history VALUES(DEFAULT, '" + dateField + "', '" + time + "', 'PASSWORD CHANGED', '" + firstField + "')").executeUpdate();

            return "Operation successful";
        }
        catch (SQLException e)
        {
            return  "SOMETHING WENT WRONG CHECK CPR";
        }
        //return "Operation successful";
    }

    @Override
    public RegisterData staffGetCLientData(String value)
    {
        try
        {
            ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.user WHERE cpr = '" + value + "'").executeQuery();
            rs.next();
            RegisterData bai = new RegisterData(rs.getString(2), rs.getString(1), rs.getString(3), rs.getString(4));
            bai.setRest(rs.getString(5),"", "");
            return bai;
        }
        catch (SQLException e)
        {
            System.out.println("CANT FIND THIS CLIENT");
            return  null;
        }
    }

    @Override
    public String managerCreateNewStaff(String firstField, String secondField, String thirdField, String fourthField)
    {
        try {
            if(fourthField == null)
                return "SELECT USER TYPE";
            if (firstField.length() != 10 || !firstField.chars().allMatch(Character::isDigit))
                return "Invalid CPR";

            ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.user;").executeQuery();

            while(rs.next())
            {
                if(firstField.equals(rs.getString(1)))
                    return "CPR already used";

            }

            if(thirdField.length() < 8)
                return "Password too short";
            con.prepareStatement("INSERT INTO bbb_dbs.user VALUES ('" + firstField + "', '" + secondField + "', ' ', ' ', ' ', '" + thirdField + "', '" + true + "', '" + "01/01/1800" + "', '" + fourthField +"')").executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return "SOMETHING WENT WRONG";
        }
        return "Operation successful";
    }

    @Override
    public String deleteStaff(String value)
    {
        if(value == null)
            return "Select an account";
        try {
            con.prepareStatement("DELETE FROM bbb_dbs.user WHERE cpr = '" + value +"'").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Operation successful";
    }

    @Override
    public String staffAcceptLoan(String value)
    {
        try {
            ResultSet rs = con.prepareStatement("SELECT * FROM bbb_dbs.loan WHERE loan_id='"+ value +"';").executeQuery();
            rs.next();
            String cpr = rs.getString(3);
            String amount = rs.getString(2);
            rs = con.prepareStatement("SELECT * FROM bbb_dbs.account WHERE cpr = '" + cpr +"' AND description = '-Main account-----Cannot be deleted-----Do not use this name-'").executeQuery();
            rs.next();
            this.addFunds(rs.getString(1), amount, cpr);
            con.prepareStatement("DELETE FROM bbb_dbs.loan WHERE loan_id = '" + value + "'").executeUpdate();

            return "Operation successful";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Something went wrong";
        }

    }


    //Staff end

    @Override
    public void addListener(String propertyName, PropertyChangeListener listener)
    {
        this.support.addPropertyChangeListener(propertyName, listener);
    }

    @Override
    public String staffChangeRate(String firstField, String secondField)
    {
        secondField = secondField.replace(',', '.');
        try {
            con.prepareStatement("UPDATE bbb_dbs.exchange_rate SET value = '" + secondField+"' WHERE name = '"+ firstField +"';").executeUpdate();
            return "Operation successful";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Something went wrong";
        }
    }

    @Override
    public void removeListener(String propertyName, PropertyChangeListener listener)
    {
        this.support.removePropertyChangeListener(propertyName, listener);
    }


    @Override
    public void closeAll()
    {
        Runtime.getRuntime().exit(0);
    }
}
