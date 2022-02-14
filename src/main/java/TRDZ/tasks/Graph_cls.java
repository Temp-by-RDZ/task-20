package TRDZ.tasks;

import java.util.*;

public class Graph_cls {
	//Воспроизведение задачи
	private final List<My_Node> TrueList;
	private final int[][] Matrix;
	private final int[][] seen;

	public Graph_cls(int max) {
		this.TrueList = new ArrayList<>(max);
		this.Matrix = new int[max][max];
		this.seen = new int[max][max];
		for (int[] matrix : Matrix) Arrays.fill(matrix, -1);
		}

	public void add_Node(String label) {
		TrueList.add(new My_Node(label));
		}

	public boolean bind(String from, String to, int length) {
		try	{Matrix[indexOf(from)][indexOf(to)] = length; return true;}
		catch (NullPointerException ignored) {return false;}
		}

	private int indexOf(String label) {
		for (int i = 0; i < TrueList.size(); i++) {
			if (TrueList.get(i).get_Name().equals(label)) return i;
			}
		return -1;
		}

	public int get_Size() {
		return TrueList.size();
		}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < get_Size(); i++) {
			sb.append("===============================\n");
			sb.append(TrueList.get(i));
			for (int j = 0; j < get_Size(); j++) {
				if (Matrix[i][j]>0) {
					sb.append("\n").append(" -> ").append(Matrix[i][j]).append(" -> ").append(TrueList.get(j));
					}
				}
			sb.append("\n");
			}
		sb.append("===============================\n");
		return sb.toString();
		}

	public void start_search(String start, String finish) {
		int S_id=indexOf(start);
		int E_id=indexOf(finish);
		if (S_id == -1 || E_id == -1) {
			throw new IllegalArgumentException("Неверная вершина" + start);
			}
		int[] line=new int[TrueList.size()* TrueList.size()];
		Arrays.fill(line,-1);
		int[] best=new int[TrueList.size()* TrueList.size()];
		Arrays.fill(best,-1);
		line[0]=S_id;
	//region Первый
		int count=1;
		int deep=0;
		Stack<My_Node> stack = new Stack<>();
		My_Node tek = TrueList.get(S_id);
		mark(stack, tek, deep, S_id);
		deep++;
	//endregion
		while (!stack.isEmpty()) {
			tek = get_Near(stack.peek(),deep);
			if (tek != null) {
				line[count]=indexOf(tek.get_Name());
				count++;
				if (Objects.equals(tek.get_Name(), finish)) {
					System.out.print("\nНайден перечень: [");
					for (int elm : line ) if (elm>-1) System.out.print(" "+TrueList.get(elm));
					System.out.print(" ] длинною в "+total(line)+"\n");
					if (total(line)<total(best)||best[0]==-1) {
						best=line.clone();
						}
					}
				mark(stack, tek, deep, indexOf(tek.get_Name()));
				deep++;}
			else {
				count--;
				deep--;
				seen[deep][line[count]]=1;
				line[count]=-1;
				stack.pop();}
			}
		System.out.println();
		System.out.print("\nИтог: [");
		for (int elm : best ) if (elm>-1) System.out.print(" "+(elm+1));
		System.out.print(" ] длинною в "+total(best)+"\n");
		}

	public int total(int[] list) {
		int summ=0;
		for (int i = 0; i < list.length-1; i++) {
			if (list[i+1]<0) break;
			if (Matrix[list[i]][list[i+1]]>0) summ+=Matrix[list[i]][list[i+1]];
			}
		return summ;
		}

	private My_Node get_Near(My_Node vertex, int deep) {
		int currentIndex = TrueList.indexOf(vertex);
		for (int i = 0; i < get_Size(); i++) {
			if (Matrix[currentIndex][i]>0 && seen[deep][i]!=1 ) {
				return TrueList.get(i);
				}
			}
		return null;
		}

	private void mark(Stack<My_Node> stack, My_Node vertex, int deep, int index) {
		System.arraycopy(seen[deep], 0, seen[deep + 1], 0, seen[deep].length);
		seen[deep+1][index]=1;
		stack.push(vertex);
		}

	public class My_Node {
		private final String name;

		public My_Node(String name) {
			this.name = name;
			}

		public String get_Name() {
			return name;
			}

		@Override
		public String toString() {
			return name;
			}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			My_Node vertex = (My_Node) o;
			return Objects.equals(name, vertex.name);
			}

		@Override
		public int hashCode() {
			return Objects.hash(name);
			}
		}

	}
