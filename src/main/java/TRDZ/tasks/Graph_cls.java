package TRDZ.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class Graph_cls
	{
	//Воспроизведение лекции
	private final List<Vertex> vertexList;
	private final int[][] Matrix;
	private final int[][] seen;

	public Graph_cls(int maxVertexCount) {
		this.vertexList = new ArrayList<>(maxVertexCount);
		this.Matrix = new int[maxVertexCount][maxVertexCount];
		this.seen = new int[maxVertexCount][maxVertexCount];
		}

	public void add_Node(String label) {
		vertexList.add(new Vertex(label));
		}

	public boolean bind(String startLabel, String secondLabel, int length) {
		int startIndex = indexOf(startLabel);
		int endIndex = indexOf(secondLabel);

		if (startIndex == -1 || endIndex == -1) return false;

		Matrix[startIndex][endIndex] = length;

		return true;
		}

	private int indexOf(String label) {
		for (int i = 0; i < vertexList.size(); i++) {
			if (vertexList.get(i).getLabel().equals(label)) return i;
			}
		return -1;
		}

	public int get_Size() {
		return vertexList.size();
		}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < get_Size(); i++) {
		sb.append("===============================\n");
		sb.append(vertexList.get(i));
		for (int j = 0; j < get_Size(); j++) {
		if (Matrix[i][j]>0) {
		sb.append("\n").append(" -> ").append(Matrix[i][j]).append(" -> ").append(vertexList.get(j));
		}
		}
		sb.append("\n");
		}
		sb.append("===============================\n");
		return sb.toString();
		}

	public void start_search(String startLabel, String finish) {
		int startIndex = indexOf(startLabel);
		if (startIndex == -1) {
			throw new IllegalArgumentException("Неверная вершина" + startLabel);
			}

		Stack<Vertex> stack = new Stack<>();
		Vertex vertex = vertexList.get(startIndex);

		visitVertex(stack, vertex);
		while (!stack.isEmpty()) {
			vertex = getNearUnvisitedVertex(stack.peek());

			if (vertex != null) visitVertex(stack, vertex);
			else stack.pop();
			}
		System.out.println();
		}

	private Vertex getNearUnvisitedVertex(Vertex vertex) {
		int currentIndex = vertexList.indexOf(vertex);
		for (int i = 0; i < get_Size(); i++) {
			if (Matrix[currentIndex][i]>0 && !vertexList.get(i).isVisited() ) {
				return vertexList.get(i);
				}
			}
		return null;
		}

	private void visitVertex(Stack<Vertex> stack, Vertex vertex) {
		System.out.print(vertex.getLabel() + " ");
		stack.push(vertex);
		vertex.setVisited(true);
		}

	public class Vertex {
		private final String label;
		private boolean isVisited;

		public boolean isVisited() {
			return isVisited;
			}

		public void setVisited(boolean visited) {
			isVisited = visited;
			}

		public Vertex(String label) {
			this.label = label;
			}

		public String getLabel() {
			return label;
			}

		@Override
		public String toString() {
			return label;
			}

		@Override
		public boolean equals(Object o) {
		if (this == o) {
			return true;
			}
		if (o == null || getClass() != o.getClass()) {
			return false;
			}
		Vertex vertex = (Vertex) o;
			return Objects.equals(label, vertex.label);
			}

		@Override
		public int hashCode() {
			return Objects.hash(label);
			}
		}

	}
