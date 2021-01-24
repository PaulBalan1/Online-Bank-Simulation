package networkPackages;

public enum NetworkType
{
    NewClientData, LogInError, RegisterError, OpenRegisterWindow, OpenRegisterRequest, OpenLogInRequest, RegisterOpenLogInWindow, OpenClientWindowRequest,
    CreateAccountRequest, RegisterSuccess,
    CloseSocketRequest, CloseApp, RegisterOpenLogInRequest, OpenClientWindow, ClientOpenLogInWindow, ClientOpenLogInRequest, NewClientDataRequest, ClientRequestLoanRequest, ClientRequestTransferRequest,
    ClientHomeSuccess, ClientHomeError, ClientAddFundsRequest, ClientCloseAccountRequest, ClientOpenAccountRequest, ClientWithdrawFundsRequest, ClientAccountsSuccess, ClientAccountsError,
    ClientNewPasswordRequest, ClientNewNumberRequest, ClientNewAdressRequest, ClientDeleteAccountRequest, ClientSettingsSuccess, ClientSettingsError, ClientDeleteSuccess, OpenStaffWindow,
    StaffOpenLogInRequest, StaffOpenLogInWindow, StaffGetNewDataRequest, NewStaffData, StaffAcceptClientRequest, StaffChangeClientPasswordRequest, StaffManageUsersSuccess, StaffManageUsersError, StaffRetrieveClientDataRequest
    ,StaffManageNewData, ChangeOwnStaffPasswordRequest, StaffSettingsSuccess, StaffSettingsError, ManagerChangeStaffPasswordRequest , StaffManagerSuccess, StaffManagerError, ManagerCreateNewStaffRequest, DeleteStaffRequest,
    StaffAcceptLoanRequest, StaffCloseServerRequest, ManagerChangeRateRequest;
}
