package model;

import data.RegisterData;

import java.beans.PropertyChangeListener;

public interface RegisterModel
{
    public String registerClient(RegisterData registerData);
    public void closeByIP(String ip);
    public void addListener(String propertyName, PropertyChangeListener listener);
}
