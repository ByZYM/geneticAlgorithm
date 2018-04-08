package genetic;

import java.util.Scanner;

public class solveD {
	int gene[][] = new int[6][24];
	int data[] = new int[24];
	int data2[] = new int[24];

	public solveD(int input[][],int data2[]){
		Scanner s=new Scanner(System.in);
		//需要频率排序的数据
		gene=input;
		/*
		for(int i=0;i<6;i++){
			for(int j=0;j<24;j++){
				System.out.print(gene[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();*/
		
		/*
		System.out.println("重量");
		for (int i = 0; i < 24; i++) {
			data[i] = s.nextInt();
		}*/
		this.data2=data2;
		String a[]=new String[4];
		String output[]=new String[24];
		int amount1=0;
		for(int i=0;i<6;i++){
			//System.out.println("第"+(i+1)+"象限:");
			int amount=0;
			for(int j=0;j<24;j++){
				if(gene[i][j]==1){
					
					a[amount]=""+(j+1)+"-"+data2[j];
					amount++;
				}
			}
			String cc[]=sort(a);
			
			
			for(int k=0;k<4;k++){
				//System.out.print("["+"频率"+cc[k]+"]"+" ");
				output[amount1]=cc[k];
				amount1++;
			}
			System.out.println();
		}

		for(int i=0;i<24;i++){
			System.out.print("象限:"+(i/4+1)+" "+output[i].split("-")[0]+" ");
			if((i+1)%4==0){
				System.out.println();
			}
		}
		int min=10000;
		for(int i=0;i<24;i++){
			int c=Math.abs(Integer.valueOf(output[i].split("-")[1])-Integer.valueOf(output[(i+1)%24].split("-")[1]));
			if(c<min){
				min=c;
			}
		}
		System.out.println("频率最小差值："+min);
		s.close();
	}

	public String[] sort(String s[]){
		int data0[]=new int[4];
		int data1[]=new int[4];
		for(int i=0;i<4;i++){
			data0[i]=Integer.valueOf(s[i].split("-")[0]);
			data1[i]=Integer.valueOf(s[i].split("-")[1]);
			//System.out.println(data0[i]+"-"+data1[i]);
		}
		String a[]=new String[4];
		for(int i=0;i<4;i++){
			for(int j=i+1;j<4;j++){
				if(data1[i]>data1[j]){
					int t=data1[i];
					data1[i]=data1[j];
					data1[j]=t;
					
					t=data0[i];
					data0[i]=data0[j];
					data0[j]=t;
				}
			}
		}
		a[0]=data0[0]+"-"+data1[0];
		a[1]=data0[2]+"-"+data1[2];
		a[2]=data0[1]+"-"+data1[1];
		a[3]=data0[3]+"-"+data1[3];
		return a;
		
	}

}
