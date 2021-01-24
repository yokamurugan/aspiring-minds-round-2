package aspiringminds;

public class DependencyInjection {
	
	public static void main(String args[]) {
		Service s1=new Service1();
		Service s2=new Service2();
		
		
		System.out.println("Client1 DI using constructor");
		Client c1=new Client1(s1);
		c1.getService();
		c1=new Client1(s2);
		c1.getService();
		
		System.out.println("Client2 DI using Setter");
		Client2 c2=new Client2();
		c2.setService(s1);
		c2.getService();
		c2.setService(s2);
		c2.getService();
		
	}

}

interface Client {
    void getService();
}
 
interface Service {
    String getInfo();
}

class Client1 implements Client{
	private Service service;
	
	public Client1(Service service) {
		this.service=service;
	}

	public void getService() {
		// TODO Auto-generated method stub
		System.out.println(service.getInfo());
	}
	
	
}

class Client2 implements Client{
	
	private Service service;
	
	public void setService(Service service) {
		this.service=service;
	}

	public void getService() {
		// TODO Auto-generated method stub
		System.out.println(service.getInfo());
	}
	
}

class Service1 implements Service{

	public String getInfo() {
		// TODO Auto-generated method stub
		return "Service 1";
	}
	
}

class Service2 implements Service{

	public String getInfo() {
		// TODO Auto-generated method stub
		return "Service 2";
	}
	
}