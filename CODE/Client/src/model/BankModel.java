package model;

import data.RegisterData;
import network.RemoteModel;

import java.beans.PropertyChangeListener;

public interface BankModel extends PropertyChangeWorker, PropertyChangeListener
{
    public RemoteModel getRemoteModel();
}
