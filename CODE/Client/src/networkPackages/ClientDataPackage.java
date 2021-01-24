package networkPackages;

import data.ClientData;

public class ClientDataPackage extends NetworkPackage
{
  private ClientData value;

  public ClientDataPackage(NetworkType type, ClientData value)
  {
    super(type);
    this.value = value;
  }

  public ClientData getValue()
  {
    return value;
  }

  public String toString()
  {
    return getType().toString();
  }

}
