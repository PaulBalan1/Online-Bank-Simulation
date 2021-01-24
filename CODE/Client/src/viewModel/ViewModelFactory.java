package viewModel;

import model.*;

public class ViewModelFactory
{
    private ClientWindowViewModel clientWindowViewModel;
    private LogInViewModel logInViewModel;
    private RegisterViewModel registerViewModel;
    private StaffWindowViewModel staffWindowViewModel;

    public ViewModelFactory(BankModel model)
    {
        clientWindowViewModel = new ClientWindowViewModel((ClientWindowModel) model);
        model.addListener("ClientOpenLogInWindow", clientWindowViewModel);
        model.addListener("NewClientData", clientWindowViewModel);
        model.addListener("ClientHomeSuccess", clientWindowViewModel);
        model.addListener("ClientHomeError", clientWindowViewModel);
        model.addListener("ClientAccountsSuccess", clientWindowViewModel);
        model.addListener("ClientAccountsError", clientWindowViewModel);

        model.addListener("ClientDeleteSuccess", clientWindowViewModel);
        model.addListener("ClientSettingsError", clientWindowViewModel);
        model.addListener("ClientSettingsSuccess", clientWindowViewModel);

        logInViewModel = new LogInViewModel((LogInWindowModel)model);
        model.addListener("OpenRegisterWindow", logInViewModel);
        model.addListener("OpenClientWindow", logInViewModel);
        model.addListener("OpenStaffWindow", logInViewModel);
        model.addListener("CloseApp", logInViewModel);
        model.addListener("LogInError", logInViewModel);

        registerViewModel = new RegisterViewModel((RegisterWindowModel) model);
        model.addListener("RegisterSuccess", registerViewModel);
        model.addListener("RegisterError", registerViewModel);
        model.addListener("RegisterOpenLogInWindow", registerViewModel);

        staffWindowViewModel = new StaffWindowViewModel((StaffWindowModel) model);
        model.addListener("StaffOpenLogInWindow", staffWindowViewModel);
        model.addListener("NewStaffData", staffWindowViewModel);
        model.addListener("StaffManageUsersSuccess", staffWindowViewModel);
        model.addListener("StaffManageUsersError", staffWindowViewModel);
        model.addListener("StaffManageNewData", staffWindowViewModel);
        model.addListener("StaffSettingsSuccess", staffWindowViewModel);
        model.addListener("StaffSettingsError", staffWindowViewModel);
        model.addListener("StaffManagerSuccess", staffWindowViewModel);
        model.addListener("StaffManagerError", staffWindowViewModel);


    }

    public ClientWindowViewModel getClientWindowViewModel()
    {
        return this.clientWindowViewModel;
    }
    public LogInViewModel getLogInViewModel()
    {
        return this.logInViewModel;
    }
    public RegisterViewModel getRegisterViewModel()
    {
        return this.registerViewModel;
    }
    public StaffWindowViewModel getStaffWindowViewModel()
    {
        return this.staffWindowViewModel;
    }

}
