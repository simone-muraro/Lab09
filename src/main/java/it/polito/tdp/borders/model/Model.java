package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private BordersDAO dao;
	private Map<Integer, Country> idMap;
	private Graph<Country, DefaultEdge> grafo;
	//private Map<Country, Country> predecessore; serve se uso metodo ricorsivo
	
	public Model() {
		
		dao= new BordersDAO();
		idMap=new HashMap<Integer, Country>();
		dao.loadAllCountries(idMap);
		
	}
	
	public void createGraph(int anno) {
		this.grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		
		//aggiungo i vertici filtrati
		Graphs.addAllVertices(grafo, dao.getVertici(idMap, anno));
		
		//aggiungo gli archi
		for(Border b : dao.getCountryPairs(idMap, anno)) {
			if(this.grafo.containsVertex(b.getC1()) && this.grafo.containsVertex(b.getC2())) {
				DefaultEdge e = this.grafo.getEdge(b.getC1(), b.getC2());
				if(e == null) {
					Graphs.addEdgeWithVertices(grafo, b.getC1(), b.getC2());
				}
			}
		}
		System.out.println("Grafo creato");
		System.out.println("#Vertici: " + grafo.vertexSet().size());
		System.out.println("#Archi: " + grafo.edgeSet().size());
	}
	
	public String getGradoVertici() {
		String s = "";
		for(Country c : this.grafo.vertexSet()) {
			s += c.getStateNme() + ": " + this.grafo.degreeOf(c) + " stato/i confinante/i\n";
		}
		return s;
	}

	public int getNumberOfConnectedComponents() {
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<>(grafo);
		return ci.connectedSets().size();
	}

	public Set<Country> getCountries() {
		// TODO Auto-generated method stub
		return grafo.vertexSet();
	}
	
	
	//parte 2
	
	public Set<Country> getRaggiungibiliCI(Country c){
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<>(grafo);
		System.out.println(ci.connectedSetOf(c).size());
		return ci.connectedSetOf(c);
	}
}
