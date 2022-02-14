package TRDZ.tasks;

public class Initialization {
	public static void main(String[] args) {

		Graph_cls graph = new Graph_cls(10);
		graph.add_Node("Москва");
		graph.add_Node("Питер");
		graph.add_Node("Киев");
		graph.add_Node("Токио");
		graph.bind("Москва","Питер",1);
		graph.bind("Москва","Киев",2);
		graph.bind("Москва","Токио",9);
		graph.bind("Питер","Москва",1);
		graph.bind("Питер","Москва",10);
		graph.bind("Токио","Москва",3);
		graph.bind("Токио","Питер",5);
		graph.bind("Киев","Токио",99);
		System.out.println(graph);

		graph.start_search("Токио","Питер");
		//graph.start_search("Питер","Токио");
		//graph.start_search("Питер","Москва");
		//graph.start_search("Питер","Питер");

		}
	}
