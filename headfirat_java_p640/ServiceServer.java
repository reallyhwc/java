
import java.rmi.*;
public interface ServiceServer extends Remote {
	Object[] getServericeList() throw RemoteException;
	Service getService(Object ServiceKey) throw RemoteException;
}