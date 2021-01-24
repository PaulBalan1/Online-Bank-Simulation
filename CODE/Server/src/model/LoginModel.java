package model;

import mediator.LogInClientHandler;

import java.beans.PropertyChangeListener;

public interface LoginModel
{
    public String checkLogInData(String email, String password, String ip);
    public void closeByIP(String ip);
    public void addListener(String propertyName, PropertyChangeListener listener);
}
