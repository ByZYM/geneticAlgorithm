package genetic;

import java.util.Random;
import java.util.Scanner;

public class solve {
	double varyChance = 0.5;// 变异概率
	int data[] = new int[24];
	int data2[] = new int[24];
	Random r = new Random();
	// final int geneLen=144;//基因长度 6条链总长度
	int gene[][][] = new int[4][6][24];

	public int getRandom(int i, int j) {// 随机产生区间随机数 不包含上限区间
		return Math.abs(r.nextInt() % (j - i)) + i;
	}

	/*
	 * 
	 * System.out.println(getFit(0)); System.out.println(getFit(1));
	 * System.out.println(getFit(2)); System.out.println(getFit(3));
	 */

	public solve() {
		Scanner s = new Scanner(System.in);
		System.out.println("重量");
		for (int i = 0; i < 24; i++) {
			data[i] = s.nextInt();
		}
		System.out.println("频率");
		for (int i = 0; i < 24; i++) {
			data2[i] = s.nextInt();
		}
		INIT();
		boolean is = false;
		int pre[][][] = new int[4][6][24];
		double max = 0;
		int ri = 0;
		for (int ii = 0; ii < 10000; ii++) {// 预计300代进化
			is = false;
			System.out.println("选择");
			select();

			System.out.println("交叉");
			exchange();

			if (new Random().nextDouble() <= varyChance) {
				System.out.println("变异");
				vary();
			}
			if (getFit(0) > max) {
				ri = 0;
				max = getFit(ri);
				is = true;
			}
			if (getFit(1) > max) {
				ri = 1;
				max = getFit(ri);
				is = true;
			}
			if (getFit(2) > max) {
				ri = 2;
				max = getFit(ri);
				is = true;
			}
			if (getFit(3) > max) {
				ri = 3;
				max = getFit(ri);
				is = true;
			}
			System.out.println(getFit(0)+" "+getFit(1)+" "+getFit(2)+" "+getFit(3));

			if (is == true) {
				for (int iii = 0; iii < 6; iii++) {// 6个象限
					for (int jj = 0; jj < 24; jj++) {
						pre[0][iii][jj] = gene[ri][iii][jj];
					}
				}
			}
		}

		System.out.println(max);
		for (int k = 1; k < 25; k++) {
			System.out.print(k + " ");
		}

		System.out.print("\n");
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 6; j++) {
				int sum = 0;
				int f=0;
				for (int k = 0; k < 24; k++) {
					System.out.print(pre[i][j][k] + " ");
					sum += (pre[i][j][k] * data[k]);
					f+= (pre[i][j][k] * data2[k]);
				}
				System.out.println(sum+" "+f);
			}
			System.out.println();
		}
		System.out.println();
		
		outPutFit(pre);
		new solveD(pre[0],data2);
		s.close();
	}

	public void INIT() {// 初始化种群 4个个体 //随机产生基因
		int column[] = new int[24];
		int list[] = { -1, -1, -1, -1 };
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 6; j++) {
				for (int l = 0; l < 4; l++) {
					int k = getRandom(0, 24);
					list[l] = k;
					for (int ii = 0; ii < 4;) {
						if (column[k] >= 1 || list[ii] == k) {// 不满足行4列1
							k = getRandom(0, 24);
							ii = 0;
							continue;
						}
						ii++;
					}
					column[k] += 1;
					gene[i][j][k] = 1;

					list[0] = -1;
					list[1] = -1;
					list[2] = -1;
					list[3] = -1;
				}
			}
			column = new int[24];
		}
		/*
		 * for(int i=0;i<4;i++){ for(int j=0;j<6;j++){ for(int k=0;k<24;k++){
		 * System.out.print(gene[i][j][k]+" "); } System.out.println(); }
		 * System.out.println(); } System.out.println();
		 */
	}
	
	
	public void outPutFit(int a[][][]) {// 获取第i个个体适应度
		// 取相邻象限的差值的最大值的倒数 越大 适应度越大
		int max = 0;
		int min=10000;
		for (int i = 0; i < 6; i++) {// 6个象限
			int sum1 = 0;
			int f1=0;
			for (int j = 0; j < 24; j++) {
				sum1 += (a[0][i][j] * data[j]);
				f1+=(a[0][i][j] * data2[j]);
			}
			// System.out.println("sum1="+sum1);
			int sum2 = 0;
			int f2=0;
			for (int j = 0; j < 24; j++) {
				sum2 += (a[0][(i + 1) % (6)][j] * data[j]);
				f2+=(a[0][(i + 1) % (6)][j] * data2[j]);
			}
			// System.out.println("sum2="+sum2);
			max = max > Math.abs(sum1 - sum2) ? max : Math.abs(sum1 - sum2);
			min = min < Math.abs(f1 - f2) ? min : Math.abs(f1 - f2);
			//if(min==0)System.out.println("有毒");
		}
		System.out.println("重量差值最大值："+max);
		System.out.println("频率差值最小值："+min);
	}


	public double getFit(int a) {// 获取第i个个体适应度
		// 取相邻象限的差值的最大值的倒数 越大 适应度越大
		int max = 0;
		int min=10000;
		for (int i = 0; i < 6; i++) {// 6个象限
			int sum1 = 0;
			int f1=0;
			for (int j = 0; j < 24; j++) {
				sum1 += (gene[a][i][j] * data[j]);
				f1+=(gene[a][i][j] * data2[j]);
			}
			// System.out.println("sum1="+sum1);
			int sum2 = 0;
			int f2=0;
			for (int j = 0; j < 24; j++) {
				sum2 += (gene[a][(i + 1) % (6)][j] * data[j]);
				f2+=(gene[a][(i + 1) % (6)][j] * data2[j]);
			}
			// System.out.println("sum2="+sum2);
			max = max > Math.abs(sum1 - sum2) ? max : Math.abs(sum1 - sum2);
			min = min < Math.abs(f1 - f2) ? min : Math.abs(f1 - f2);
			//if(min==0)System.out.println("有毒");
		}
		return 1.0/max * 1000;//*min/101.0 /     *min/10  
	}

	public void select() {// 个体选择 //根据适应度选择出4个个体
		double sumFit = getFit(0) + getFit(1) + getFit(2) + getFit(3);
		double fit[] = { getFit(0), getFit(1), getFit(2), getFit(3) };
		int pre[][][] = new int[4][6][24];

		for (int i = 0; i < 4; i++) {
			for (int ii = 0; ii < 6; ii++) {// 6个象限
				for (int jj = 0; jj < 24; jj++) {
					pre[i][ii][jj] = gene[i][ii][jj];
				}
			}
		}
		int ran;
		int s = 0;
		for (int i = 0; i < 4; i++) {
			ran = getRandom(0, (int) (sumFit + 1));
			if (ran <= fit[0]) {
				s = 0;
				System.out.print(0 + " ");
			} else if (ran <= fit[1] + fit[0]) {
				s = 1;
				System.out.print(1 + " ");
			} else if (ran <= fit[2] + fit[1] + fit[0]) {
				s = 2;
				System.out.print(2 + " ");
			} else if (ran <= fit[3] + fit[2] + fit[1] + fit[0]) {
				s = 3;
				System.out.print(3 + " ");
			}

			for (int ii = 0; ii < 6; ii++) {// 6个象限
				for (int jj = 0; jj < 24; jj++) {
					gene[i][ii][jj] = pre[s][ii][jj];
				}
			}
		}
		System.out.print("\n");
	}

	public void change(int i, int j, int k) {// 变换个体某个基因 //0变为1 1变为0
		int a = 0;
		if (gene[i][j][k] == 0) {
			a = 1;
		} else {
			a = 0;
		}
		gene[i][j][k] = a;
		if (a == 0) {
			int rr1 = getRandom(0, 24);
			while (gene[i][j][rr1] != 0 || rr1 == k) {
				rr1 = getRandom(0, 24);
			}
			for (int jj = 0; jj < 6; jj++) {
				if (gene[i][jj][rr1] == 1) {
					gene[i][jj][rr1] = 0;
					gene[i][j][rr1] = 1;
					gene[i][jj][k] = 1;
					break;
				}
			}

		} else if (a == 1) {
			int rr1 = getRandom(0, 24);
			while (gene[i][j][rr1] != 1 || rr1 == k) {
				rr1 = getRandom(0, 24);
			}
			gene[i][j][rr1] = 0;

			for (int jj = 0; jj < 6; jj++) {
				if (gene[i][jj][k] == 1 && jj != j) {
					gene[i][jj][k] = 0;
					gene[i][jj][rr1] = 1;
					break;
				}
			}

		}
	}

	public void exchange() {// 两两个体交叉
		int r1 = getRandom(0, 4);
		int r2 = getRandom(0, 4);
		int r3 = getRandom(0, 4);
		int r4 = getRandom(0, 4);
		int rrj1 = getRandom(0, 6);
		int rrk1 = getRandom(0, 24);
		int rrj2 = getRandom(0, 6);
		int rrk2 = getRandom(0, 24);
		while (r2 == r1) {
			r2 = getRandom(0, 4);
		}
		while (r3 == r2 || r3 == r1) {
			r3 = getRandom(0, 4);
		}
		while (r4 == r3 || r4 == r2 || r4 == r1) {
			r4 = getRandom(0, 4);
		}
		if (gene[r1][rrj1][rrk1] != gene[r2][rrj1][rrk1]) {
			change(r1, rrj1, rrk1);
			change(r2, rrj1, rrk1);
		}
		if (gene[r3][rrj2][rrk2] != gene[r4][rrj2][rrk2]) {
			change(r3, rrj2, rrk2);
			change(r4, rrj2, rrk2);
		}
		// System.out.println(r1 + " " + r2 + " " + r3 + " " + r4);
	}

	public void vary() {// 个体变异
		int rrj1 = getRandom(0, 6);
		int rrk1 = getRandom(0, 24);
		change(0, rrj1, rrk1);
		rrj1 = getRandom(0, 6);
		rrk1 = getRandom(0, 24);
		change(1, rrj1, rrk1);
		rrj1 = getRandom(0, 6);
		rrk1 = getRandom(0, 24);
		change(2, rrj1, rrk1);
		rrj1 = getRandom(0, 6);
		rrk1 = getRandom(0, 24);
		change(3, rrj1, rrk1);
	}

	public static void main(String args[]) {
		//new solve();
		new solve();
	}
}
