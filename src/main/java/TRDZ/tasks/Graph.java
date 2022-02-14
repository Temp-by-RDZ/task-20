package TRDZ.tasks;

import java.util.*;

public class Graph {
	private final HashMap<String,Integer> List;
	private final ArrayList<My_Node> TrueList;
	private final int[][] Matrix;
	private final int[] My_Math;
	private int[] Bs_Math;
	private int[][] Free;
	private int[] Math;
	boolean found;
	int Space;
	int lesser;

	public Graph(int max) {
		List = new HashMap<>(max);
		TrueList = new ArrayList<>(max);
		Matrix = new int[max][max];

		Free = new int[max][max];
		My_Math = new int[max];
		Bs_Math = new int[max];
		Math = new int[max];
		Space=0;
		lesser=0;
		for (int[] matrix : Matrix) Arrays.fill(matrix, -1);
		Arrays.fill(My_Math, -1);
		Arrays.fill(Math, -1);
		}

	public void add_Node(String name) {
		List.put(name,List.size());
		TrueList.add(new My_Node(name));
		}

	public boolean bind(String from, String to, int length) {
		try	{Matrix[indexOf(from)][indexOf(to)] = length; return true;}
		catch (NullPointerException ignored) {return false;}
		}

	private int indexOf(String name) {
		try	{return List.get(name);}
		catch (NullPointerException ignored) {return -1;}
		}

	public int get_Size() {
		return List.size();
		}

	public void start_search(String start, String finish) {
		System.out.println("Путь от "+start+" до "+finish);
		int S_id=indexOf(start);
		int E_id=indexOf(finish);
		My_Math[0]=S_id;
		System.out.print("Перечень "+S_id+" ");
		if (S_id>=0&&E_id>=0) find(S_id,E_id,0,-1);
		else {System.out.println("Некорректная точка"); return;}
		System.out.print("\nПервый из "+Space+" путь : [");
		for (int elm:Math ) if (elm>-1) System.out.print(" "+elm);
		System.out.print(" ] длинною в "+total(Math)+"\n");
		if (Space>1) {
			Bs_Math=Math.clone();
			for (int i=1; i<Bs_Math.length; i++) {
				System.out.print("Перечень "+S_id+" ");
				Free = new int[My_Math.length][My_Math.length];
				Arrays.fill(My_Math, -1);
				Arrays.fill(Math, -1);
				My_Math[0]=S_id;
				lesser=0;
				found=false;
				find(S_id,E_id,0,Bs_Math[i]);
				if (found && total(Math)!=total(Bs_Math)) {
					found=false;
					System.out.print("\nАльтернативный путь : [");
					for (int elm:Math ) if (elm>-1) System.out.print(" "+elm);
					System.out.print(" ] длинною в "+total(Math)+"\n");
					if (total(Math)<total(Bs_Math)) System.out.print("Это наиболее актуальный путь");
					}
				System.out.println("");
				}
			}
		System.out.print("Нет лучше пути");
		}

	public void find(int from,int to, int deep, int block) {
		System.arraycopy(Free[deep], 0, Free[deep + 1], 0, Free[deep].length);
		Free[deep+1][from]=1;
		int[] tek = Matrix[from];
		for (int i=0; i<tek.length; i++) {
			if (from==to) {Space++; if (Math[0]==-1) Math=My_Math.clone(); found=true; return;}
			if (tek[i]>-1 && Free[deep][i]!=1 && i!=block) {
				lesser++;
				My_Math[lesser]=i;
				System.out.print(i+" ");
				find(i, to, deep+1,block);
				}
			}
		}

	public int total(int[] list)
		{
		int summ=0;
		for (int i = 0; i < list.length-1; i++)
			{
			if (list[i+1]<0) break;
			if (Matrix[list[i]][list[i+1]]>0) summ+=Matrix[list[i]][list[i+1]];
			}
		return summ;
		}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < get_Size(); i++) {
			sb.append("===============================\n");
			sb.append(TrueList.get(i).get_Name());
			for (int j = 0; j < get_Size(); j++) {
				if (Matrix[i][j]>-1) {
					sb.append("\n").append(" -> ").append(Matrix[i][j]).append(" -> ").append(TrueList.get(j).get_Name());
					}
				}
			sb.append("\n");
			}
		sb.append("===============================\n");
		return sb.toString();
		}


	private static class My_Node {
		static int index;
		private String name;

		{//def initialize
			index++;
			}

		private My_Node() {
			name=index+". Unknown";
			}

		private My_Node(String name) {
			this.name=index+". "+name;
			}

		public String get_Name() {
			return name;
			}

		public void set_Name(String name) {
			this.name = name;
			}
		}


	}
