import java.util.*;
import java.net.*;
import java.io.*;

class newclient{


	public static void main(String[]args){
		InputStreamReader is=null;
		Scanner input=new Scanner(System.in);
		BufferedReader br=null;
		int total=0,liststatus=2,count;
		String givecard="";
		Boolean ask=true;
		int listcount=0;
		PrintWriter pw=null;
		String againmessage="";
		ArrayList<String>mycard=new ArrayList<>();
		try{
			Socket cs=new Socket("127.1.1.1",8787);
			while(true){
				is=new InputStreamReader(cs.getInputStream());
				br=new BufferedReader(is);	
				pw=new PrintWriter(cs.getOutputStream());
				for(int i=0;i<2;i++){
					mycard.add(br.readLine());
					System.out.print(mycard.get(i)+"\t");
					total+=checkcard(total,(String)mycard.get(i));
				}
				while(ask){
					System.out.println("要牌嗎?");
					givecard=input.next();
					if(total<=21){
						switch(givecard){
							case "Y":
							case "y":
						
								pw.write("Y"+"\n");
								pw.flush();
								mycard.add(br.readLine());
							
								total+=checkcard(total,(String)mycard.get(liststatus));	
								System.out.println("你的總手牌 "+mycard.get(0)+" "+mycard.get(1)+" "+mycard.get(liststatus)+"\t"+"你現在的點數"+total+"\n");
							
								liststatus++;
						
								break;
							default:
								ask=false;
								pw.write("N"+"\n");
								pw.flush();
								pw.write(String.valueOf(total)+"\n");
								pw.flush();
								
						}//switch
					}//if
					else{
						System.out.println("請等待結束");	
						pw.write("N"+"\n");
						pw.flush();
						pw.write(String.valueOf(total)+"\n");
						pw.flush();	
						ask=false;
						
					}
								
				}//while(ASK)
				//System.out.println("等待結果"+"\n"+br.readLine());
					System.out.println("由"+br.readLine()+"獲勝\n");
						System.out.println("下一局嗎?");
						 againmessage=input.next();
						if(againmessage.equals("Y")||againmessage.equals("y"))
							{
							
							pw.write("Y"+"\n");
							pw.flush();
							
							
						
						}
						else
						{
						pw.write("N"+"\n");
							pw.flush();
						}
						for(int i=0;i<=mycard.size();i++){

							mycard.remove(listcount);
						}
						total=0;
						ask=true;
							//ArrayList<String>mycard=new ArrayList<>();
			}

		}catch(Exception ex){	

		}

	}
	public static int checkcard(int total,String temp){
	int total2=0;
		if(temp.length()==4){
			total2=10;
			//System.out.println("2");
		}
		else if(temp.charAt(2)=='J'||temp.charAt(2)=='Q'||temp.charAt(2)=='K'){
			total2=10;
			//System.out.println("3");
		}
		else if(temp.charAt(2)=='A'){
			if(21-total<=10){
				total2=11;
			
			}else{
				total2=1;
			
			}

		}else{
			total2=temp.charAt(2)-'0';
			//System.out.println("5"+total);
			
		}
		return total2;

	}
}