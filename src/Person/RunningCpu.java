package Person;

public class RunningCpu {
	private ChannalManager channalMg;
	
	public RunningCpu()
	{
		this.channalMg = new ChannalManager();
		
	}
	public void setChannalMg(ChannalManager channalMg) {
		this.channalMg = channalMg;
	}

	public ChannalManager getChannalMg() {
		return channalMg;
	}
	
	public void runCpu()
	{
		while(true)
		{
			if(channalMg.getInterrupt().equals("none"))
			{
				System.out.println("the cpu is running user's program...");
				System.out.println("the cpu is running user's program...");
				channalMg.setInterrupt("noned");
			}
			if(channalMg.getInterrupt().equals("init"))
			{
				System.out.println("CPU is interrupted");
				System.out.println("This is a I/0 Init instruction,The channalManager is init the device...");
				channalMg.setInterrupt("none");
				
				Thread t = new Thread(this.channalMg);
				t.start();
			}
			if(channalMg.getInterrupt().equals("finish"))
			{
				System.out.println("CPU is interrupted");
				System.out.println("This is a I/0 finish instruction,The channalManager is finish the device...");
				channalMg.setInterrupt("none");
			}
		}
	}
	public static void main(String [] args)
	{
		RunningCpu cpu = new RunningCpu();
		cpu.getChannalMg().setInterrupt("init");
		cpu.runCpu();
	}
}
