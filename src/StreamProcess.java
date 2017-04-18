
public class StreamProcess {
	private static int SPACE = 4;    //功能部件大小
	private static int INSTRUCT_LENGTH = 5; //指令的数量
	private static int SLICE_LENGTH = INSTRUCT_LENGTH+SPACE-1; //整个流水先的长度
	private String instruct[] = {"ED","DA","MA","NL","BF"};  //每一种指令的类型
	
	public static void main(String[] args){
		StreamProcess sp = new StreamProcess();
		sp.fillStream();
	}
	/**
	 * 流水线调度的主要算法，把流水线的全部过程填入一个二维数组中
	 */
	public void fillStream(){
		int[][] stream = new int[SPACE][SLICE_LENGTH];  //定义流水主要线的时空分布
		int tempSapce = 1;    //流水线的指令号
		int tempTime = 0;  //流水线的功能部件
		for(int i=SPACE-1;i>=0;i--){  //先从最底层开始算
			tempSapce=1;          //
			for(int j=tempTime;j<tempTime+INSTRUCT_LENGTH;j++){
				stream[i][j] = tempSapce++;  
			}
			tempTime++; //转移到另外一个功能部件
		}
		display(stream);
	}
	
	/**
	 * 演示流水线的时空图，显示一个二维数组
	 * @param stream 传入存有流水线过程的二维数组
	 */
	public void display(int stream[][]){
		double num1 =0,num2 = 0,num3 = 0;
		for(int i=0;i<stream.length;i++){
			for(int j=0;j<stream[i].length;j++){
				if(stream[i][j]==0){
					System.out.print("\t");
				}else{
					num1++;
					System.out.print(instruct[i]+stream[i][j]+"\t");
				}
			}
			System.out.println();   
		}
		System.out.println("吞吐率："+num1/(SPACE*SLICE_LENGTH)+"Δt");
		System.out.println("加速比："+(double)SPACE*INSTRUCT_LENGTH/SLICE_LENGTH);
		System.out.println("效率："+num1/(SPACE*SLICE_LENGTH));
	}
}
