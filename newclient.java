import java.util.*;
import java.net.*;
import java.io.*;

class newclient{


	public static void main(String[]args){
		InputStreamReader is=null;
		Scanner input=new Scanner(System.in);
		BufferedReader br=null;
		int total=0,liststatus=2,count;
          	int xx5=0;
		String givecard="";
		Boolean ask=true;
		int limit=0;
		int listcount=0;
		PrintWriter pw=null;
		int askcount=0;
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
				}
				while(ask){
				
				if(limit==0){
					total = counttotal(mycard);
					System.out.print("總和:"+total+"\n");
					System.out.println("要牌嗎?");
					givecard=input.next();
					xx5=0;
					while((givecard.equals("Y")||givecard.equals("y"))){
						switch(givecard){
							case "Y":
							case "y":
							 xx5++;
								pw.write("Y"+"\n");
								pw.flush();
								mycard.add(br.readLine());
								System.out.print("你的手牌:");
								for(int k=0;k<mycard.size();k++){
									System.out.print(mycard.get(k));
								}
								total=counttotal(mycard);	
								System.out.print("現在張數:"+(xx5+2));
								System.out.print("總和:"+total+"\n");
								if(xx5==3&&total<=21){
							    	break;
							    }
								//liststatus++;
						
								break;
							default:
								//ask=false;
								System.out.print("你的手牌:"+"\t");
								for(int k=0;k<mycard.size();k++){

									System.out.println(mycard.get(k)+"\t");	

								}
								pw.write("N"+"\n");
								pw.flush();
								pw.write(String.valueOf(total)+"\n");
								pw.flush();
								
						}//switch
						if(xx5==3&&total<=21){
					    	System.out.println("過五張!!");
					    	break;
					    }
						if(total>=21){
							break;
						}
						System.out.println("要牌嗎");
						givecard = input.next();
					}//while
					
						System.out.println("請等待結束");	
						pw.write("N"+"\n");
						pw.flush();
						pw.write(String.valueOf(total)+"\n");
						pw.flush();	
						
					}//if(limit)

					askcount++;
					limit++;
					if(askcount==2){
						ask=false;
					}
								
				}//while(ASK)
				//System.out.println("等待結果"+"\n"+br.readLine());
					System.out.println("由"+br.readLine()+"獲勝\n");
						/*System.out.println("下一局嗎?");
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
						}*/
						total=0;
						ask=true;
						askcount=0;
						limit=0;
							//ArrayList<String>mycard=new ArrayList<>();
						mycard.clear();
						
						for(int i=0;i<mycard.size();i++){
							System.out.println("\n牌"+mycard.get(i));
						}
			}

		}catch(Exception ex){	
			System.out.println(ex.toString());
		}

	}
	public static int counttotal(ArrayList<String> card){
		int total = 0;
		int total2;
		String temp = "";
		int temp2 = card.size();
		//System.out.println(temp2);
		//System.out.println(card.get(0) + "\t" + card.get(1));
		for(int a=0;a<temp2;a++){
			if(card.get(a).charAt(2) == 'A'){
				temp = card.get(a);
				card.remove(a);
				card.add(temp);
			}
		}
		
		
		for(int a=0;a<card.size();a++){
			if (card.get(a).length() == 4) {
				total2 = 10;
				total = total + total2;
				//card.remove(a);
				// System.out.println("2");
			} else if (card.get(a).charAt(2) == 'J' || card.get(a).charAt(2) == 'Q'
					|| card.get(a).charAt(2) == 'K') {
				total2 = 10;
				total = total + total2;
				//card.remove(a);
				// System.out.println("3");
			} else if (card.get(a).charAt(2) == 'A') {
								

				if (total + 11 <= 21) {
					total2 = 11;
					total = total + total2;

				} else {
					total2 = 1;
					total = total + total2;

				}

			} else{
				total2 = card.get(a).charAt(2) - '0';
				total = total + total2;
				//card.remove(a);
				// System.out.println("5"+total);
			}

		}

		return total;
	}
}