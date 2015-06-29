import java.util.*;
import java.io.*;
import java.net.*;

class newcardserver{


	public static void main(String[]args){

		try{
			ServerSocket ss=new ServerSocket(8787);
			Socket []cs=new Socket[2];
			playgame pg=null;	
			int tag=0;
			String deck[]=new String[52];
			createdeck cd=new createdeck();
			deck=cd.createcard(deck);
			for(int i=0;i<52;i++){
				System.out.print(deck[i]+"\t");
				if(i%13==0&&i>0)
					System.out.println();
			}
			while(true){
				for( tag=0;tag<2;tag++){
				cs[tag]=ss.accept();				
				
				}
				pg=new playgame(cs,tag,deck);
				pg.start();
			}			

		}
		catch(Exception ex){

			

		}


	}
}
class playgame extends Thread{

Socket cs[]=null;
int tag;
String deck[]=null;
PrintWriter pw=null;
InputStreamReader is=null;
BufferedReader br=null;
String message="",winmessage="";
String []againmessage=new String[2];
ArrayList<String>mylist=new ArrayList<>();
Socket tempsocket=null;
int againcount=0;
int k=0;
Boolean again=true;
	playgame(Socket[]cs,int tag,String[]deck){
		this.cs=cs;
		this.tag=tag;
		this.deck=deck;
	}

	public void run(){
		givecard(cs,tag,deck);
	
	}
	void givecard(Socket[]cs,int tag,String[]deck){
	
	try{	
		playercontrol pc=new playercontrol();			
		while(again){
			winplaygame wpg=new winplaygame();
			for(int i=0;i<tag;i++){
				
				for(int j=0;j<2;j++){
						pw=new PrintWriter(cs[pc.tagg].getOutputStream());
						pw.write(deck[pc.cardcount]+"\n");
						
						pw.flush();
						System.out.println("\n"+"發出了"+deck[pc.cardcount]+"向玩家"+j+"\t");
						pc.givecardcount();
					
						
						
				}//起手發2
						
					pc.givetag();
						
			}
			pc.cleartag();
			for(int  i=0;i<tag;i++){
				is=new InputStreamReader(cs[pc.tagg].getInputStream());
				br=new BufferedReader(is);
				tempsocket=cs[pc.tagg];
				if(tempsocket==cs[pc.tagg]){
					while((message=br.readLine()).equals("Y")){
						pw=new PrintWriter(cs[pc.tagg].getOutputStream());
						pw.write(deck[pc.cardcount]+"\n");
						
						pw.flush();
						System.out.println("\n"+"發出了"+deck[pc.cardcount]+"向玩家"+i+"\t");
						pc.givecardcount();
					}
				wpg.win(Integer.valueOf(br.readLine()));
				pc.givetag();
				}
			}
			
			winmessage=wpg.whowin(pc.tagg);
			System.out.println(pc.tagg);
			for(int i=0;i<=pc.tagg;i++){
				pw=new PrintWriter(cs[i].getOutputStream());
				is=new InputStreamReader(cs[i].getInputStream());
				br=new BufferedReader(is);
				pw.write(winmessage+"\n");
				pw.flush();
				//againmessage[i]=br.readLine();
				//System.out.println(againmessage[i]);
				
				
			}//給獲勝訊息
			/*for(int i=0;i<2;i++){
				
				if(againmessage[i].equals("Y")){
					againcount++;
					
				}
				
			}*/
			if(againcount==0){
					again=true;
					tempsocket=null;
					pc.cleartag();
					againcount=0;
					//for(int i=0;i<2;i++)
						//againmessage[i]="";
				}else{
					again=false;
					
				}
		}
		
	}catch(Exception ex){
		System.out.println(ex.toString());
	}
	}

}
class playercontrol{
int tagg=0;
int cardcount=0;
	
	synchronized void givetag(){
		if(this.tagg<1)
			this.tagg++;
		 
	}
	synchronized void givecardcount(){
		
			this.cardcount++;
		 
	}
	void cleartag(){
		this.tagg=0;
	}

}
class createdeck{

	public String[] createcard(String[] card) {
		Random rd = new Random();
		String temp;
		int a = 0, b = 0;

		String[] sign = new String[] { "黑桃", "梅花", "方塊", "紅心" };
		String[] value = new String[] { "A", "2", "3", "4", "5", "6", "7", "8",
				"9", "10", "J", "Q", "K" };
		int count = 0;
		String[] card2 = new String[52];
		for (int i = 0; i < 4; i++) {

			for (int j = 0; j < 13; j++) {

				card2[count] = sign[i] + value[j];
				count++;
			}

		}
		for (int j = 0; j < 52; j++) {
			b = rd.nextInt(52);

			temp = card2[j];
			card2[j] = card2[b];
			card2[b] = temp;
		}
		for (int j = 0; j < 52; j++) {
			// System.out.print(card2[j]);
		}
		return card2;
	}

}
class winplaygame{
ArrayList<Integer>mylist=new ArrayList<>();
int max=0,a=0;
	void win(int total){
		mylist.add(total);
	}
	String whowin(int tag){
		System.out.println("manger tag"+tag);
		for(int i=0;i<=tag;i++){
			if(((int)mylist.get(i)>max&&(int)mylist.get(i)<=21)){
				max=(int)mylist.get(i);
				a=i;
			}
		}
		return "玩家" + String.valueOf(a + 1) + "贏了";
	}
}
