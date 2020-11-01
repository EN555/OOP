package ex1;

public class edge {
	node_info dest;
	double weight;
	
	public edge(node_info dest,double weight){
		this.dest=dest;
		this.weight=weight;
	}
	public edge() {
		this.dest=null;
		this.weight=0;
	}
}
