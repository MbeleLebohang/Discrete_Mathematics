
package graph;
import java.util.HashSet;
import java.util.*;
/**
 *
 * An implementation of Graph that uses a linked representation 
 * (vertex and edge objects). 
 * <p>
 * It is called 'AdjacencyMapGraph' because each vertex stores an adjacency 
 * Map of edges incident upon it (as opposed to an adjacency list).
 *<p>
 * Operations such as inserting or deleting an edge involve updating the
 * adjacency maps of the vertices incident upon it.
 *  
 * @author Stephan Jamieson
 * @version 1/4/2016
 */
 public class AdjacencyMapGraph<V, E> implements Graph<V, E> {
	 
	private Set<AdjacencyMapVertex<V, E>> vertices;
	private Set<AdjacencyMapEdge<E, V>> edges;
	
	/**
	 * Create a new graph.
	 */
	public AdjacencyMapGraph() {
		this.vertices = new HashSet<AdjacencyMapVertex<V, E>>();
		this.edges = new HashSet<AdjacencyMapEdge<E, V>>();
	}
	
	public Iterable<AdjacencyMapVertex<V, E>> getVertices() { return vertices; }

	public Iterable<AdjacencyMapEdge<E, V>> getEdges() { return edges; }
   
	public Iterable<AdjacencyMapEdge<E, V>> getIncidentOn(final Vertex<V> vertex) {
		// Your code here
		for (AdjacencyMapVertex<V, E> item : vertices) {
    			if(item == vertex){
        			return item.getIncidentOn();
		        }
		}
		return null;
	}

	public Iterable<AdjacencyMapVertex<V, E>> getNeighbours(Vertex<V> v) {
		// Your code here
		//Iterator<AdjacencyMapEdge<E, V>> loo = getIncidentOn(v).iterator();
		//while(loo.hasNext()){System.out.println(loo.next());}


		for (AdjacencyMapVertex<V, E> item : vertices) {
		      	if(item == v){
        			return item.getNeighbours();
      			}
    		}
		return null;
	}

	public Pair<AdjacencyMapVertex<V, E>> getVertices(final Edge<E> edge) {
		// Your code here
		for (AdjacencyMapEdge<E, V> item : edges) {
	        	if(item == edge){
         			return item.getVertices();
       			}
    		}
		return null;
	}
    
	public AdjacencyMapVertex<V, E> getOpposite(final Vertex<V> vertex, final Edge<E> edge){
		// Your code here
		for (AdjacencyMapEdge<E, V> item : edges) {
      			if(item.equals(edge)){
				/* Obtain the pair of the given edge */
				Pair<AdjacencyMapVertex<V, E>> foo = getVertices(item);

         			if(foo.getLeft().getValue().equals(vertex.getValue())){
					return foo.getRight();
				}else{
					return foo.getLeft();
				}
      			}
   		}
		return null;
	}

 	/**
   	 * Check if vertex v and w are adjacent
   	 * @param v
   	 * @param w
   	 * @return boolean
   	 */
	public boolean areAdjacent(final Vertex<V> v, final Vertex<V> w) {
		// Your code here
    		for (AdjacencyMapVertex<V, E> item : vertices) {
     			if(item.getValue().equals(v.getValue())){
    				return item.isAdjacentTo(createVertex(w.getValue()));
     			}
     			else if(item.getValue().equals(w.getValue())){
         			return item.isAdjacentTo(createVertex(v.getValue()));
    			}
    		}
		return false;
	}
   
        /**
         * Replace the value of vertex with Value
         * @param vertex
         * @param value
         * @return old V
         */
	public V replace(final Vertex<V> vertex, final V value) {
            	// Your code here
            	for (AdjacencyMapVertex<V, E> item : vertices) {
               		if(item == vertex){
                 		return item.setValue(value);
                	}
            	}
		return null;
	}

        /**
         * Replace the cost of the edge with the new cost
         * @param edge
         * @param value
         * @return old E
         */
	public E replace(final Edge<E> edge, final E value) {
            // Your code here
            for (AdjacencyMapEdge<E, V> item : edges) {
                if(item == edge){
                    return item.setValue(value);
                }
            }
            return null;
	}

	public AdjacencyMapVertex<V, E> insert(final V value) {
		// Your code here
    		AdjacencyMapVertex<V, E> foo = createVertex(value);
    		vertices.add(foo);
		return (AdjacencyMapVertex<V, E>) foo;
	}

	public AdjacencyMapEdge<E, V> insert(Vertex<V> v, Vertex<V> w, E value) {
            	// Your code here

            	if(areAdjacent(v,w)){
                	throw new IllegalArgumentException("Vertices are incident.");
            	}
		
		AdjacencyMapVertex<V, E> vBar = createVertex(v.getValue());
		AdjacencyMapVertex<V, E> wBar = createVertex(w.getValue());

		boolean vIn = false,wIn = false;
		for(AdjacencyMapVertex<V, E> item: vertices){
			if(item.getValue().equals(vBar.getValue())){
				vIn = true;
			}
			if(item.getValue().equals(wBar.getValue())){
				wIn = true;
			}
		}
		if(!vIn){
			vertices.add(vBar);
		}
		if(!wIn){
			vertices.add(wBar);
		}

		AdjacencyMapEdge<E, V> foo = new AdjacencyMapEdge<>(vBar, wBar, value);

		for(AdjacencyMapVertex<V, E> item: vertices){
			if(item.getValue().equals(vBar.getValue())){
				item.insert(foo);

			}else if(item.getValue().equals(wBar.getValue())){
				item.insert(foo);
			}
		}

            	edges.add(foo);
            	return foo;
	}

   
	public V remove(Vertex<V> vertex) {
		// Your code here
		Pair<AdjacencyMapVertex<V,E>> pair;
		AdjacencyMapVertex<V,E> foo = null;
		List<AdjacencyMapEdge<E, V>> bar = new ArrayList<>();

    		for(AdjacencyMapVertex item: vertices){
      			if(item.getValue().equals(vertex.getValue())){
				foo = item;
				for(AdjacencyMapEdge<E, V> link :edges){
					pair = getVertices(link);

					if(pair.getLeft().getValue().equals(item.getValue()) || pair.getRight().getValue().equals(item.getValue())){
						bar.add(link);

					}
				}
     			}
		}
		if(foo != null){vertices.remove(foo);}
		for(AdjacencyMapEdge<E, V> item: bar){edges.remove(item);}

		return vertex.getValue();
	}


	public E remove(Edge<E> edge)  {
		// Your code here
    		AdjacencyMapEdge<E, V> foo = null;
    		/*Pair<AdjacencyMapVertex<V, E>> bar = foo.getVertices();
    		bar.getLeft().remove(foo);
		*/
		
		for(AdjacencyMapEdge<E, V> item: edges){
			if(item.equals(edge)){
                                /* Obtain the pair of the given edge */
                                //Pair<AdjacencyMapVertex<V, E>> foo = getVertices(item);
//				foo.getLeft().remove(item);
//				foo.getRight().remove(item);
				foo = item;
			}
		}
		if(foo!=null){edges.remove(foo);}
		return edge.getValue();
	}
	
	public void clearMarks() {
    		// Your code here
    		for(Edge<E> item : edges) {
      			item.clearMark();
    		}
    		for(Vertex<V> item: vertices) {
      			item.clearMark();
    		}
   	}
	/*
	 * The following methods should be used to create edges and vertices.
	 * They ensure that the objects created are marked as belonging to
	 * this graph object.
	 */
	 
	 
	private AdjacencyMapVertex<V, E> createVertex(final V value) {
		final AdjacencyMapVertex<V, E> vertex = new AdjacencyMapVertex<V, E>(value);
		vertex.setOwner(this);
		return vertex;
	}

	private AdjacencyMapEdge<E, V> createEdge(final AdjacencyMapVertex<V,E> v, 
		final AdjacencyMapVertex<V, E> w, final E value) {
			final AdjacencyMapEdge<E, V> edge = new AdjacencyMapEdge<E, V>(v, w, value);
			edge.setOwner(this);
			return edge;
	}

	/*
	 * The Graph<V, E> interface uses Edge<E> and Vertex<V> data types.
	 * Internally, an AdjacencyMapGraph<V, E> uses AdjacencyMapVertex<V, E>
	 * and AdjacencyMapEdge<E, V> data types. The following methods
	 * may be used to safely cast from one to the other.
	 */
	 
	@SuppressWarnings("unchecked")
	private AdjacencyMapEdge<E, V> cast(final Edge<E> edge) {
		assert(edge.belongsTo(this));
		return (AdjacencyMapEdge<E, V>)edge;		
	}

	/**
	 *
	 */
	@SuppressWarnings("unchecked")	
	private AdjacencyMapVertex<V, E> cast(final Vertex<V> vertex) {
		assert(vertex.belongsTo(this));
		return (AdjacencyMapVertex<V, E>)vertex;
	}
	
	/**
	 * Obtain a dump (for debugging purposes) of the graph representation.
	 */ 
	public void dump() {
		dumpVertices();
		dumpEdges();
		System.out.println();
	} 
	
	/**
	 * Obtain a dump (for debugging purposes) of the vertex representations.
	 */ 
	public void dumpVertices() {
		System.out.print("\nVERTICES");
		for (AdjacencyMapVertex<V, E> vertex : vertices) {
			vertex.dump();
		}
	}
	
	/**
	 * Obtain a dump (for debugging purposes) of the edge representations.
	 */ 
	public void dumpEdges() {
		System.out.print("\nEDGES");
		for (AdjacencyMapEdge<E, V> edge : edges) {
			System.out.print("\n"+edge);
		}
	}	
	
 }
 
