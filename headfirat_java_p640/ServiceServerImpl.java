
import java.rmi.*;
import java.util.*;
import java.rmi.server.*;

public class ServiceServerImpl extends UnicasRemoteObject implements ServiceServer{
	HashMap serviceList;

	public ServiceServerImpl() throw RemoteException {
		setUpServices();
	}

	private void setUpServices() {
		serviceList = new HashMap();
		serviceList.put("Dice Rolling Service", new DiceService());
		serviceList.put("Day of the Week Service", new DayOfTheWeekService());
		serviceList.put("Visual music Service", new MiniMusicService());

	}


	public Object[] getServiceList() {
		System.out.println("in remote");
		return serviceList.keySet().toArray();
	}

	public Service getService(Object serviceKey) throws RemoteException {
		Service theService = (Service) serviceList.get(serviceKey);
		return theService;
	}

	public static void main(String[] args) {
		try {
			Naming.rebind("ServiceServer", new ServiceServerImpl());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("Remote service is running");
	}
}