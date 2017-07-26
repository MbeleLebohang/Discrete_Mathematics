package graph;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * 
 * An AdjacencyMapVertex<V, E> is the concrete type of Vertex<V> object
 * used in the AdjacencyMapGraph<V, E> implementation of Graphh<V,E>.
 * <p>
 * As its name suggests, an AdjacencyMapVertex stores a map of the edges
 * incident upon it. 
 * <p>
 * The map contains (key, value) pairs of AdjacencyMapVertex
 * and AdjacencyMapEdge. The key is a neighbour, and the value is the
 * edge that connects them. 
 * 
 * 
 * @author Stephan Jamieson 
 * @version 1/4/2016
 */
public class AdjacencyMapVertex<V, E> extends Vertex<V> {
    
    private final Map<AdjacencyMapVertex<V, E>, AdjacencyMapEdge<E, V>> edges;

	/**
	 * Create an AdjacencyMapVertex with the given value.
	 */
    public AdjacencyMapVertex(final V value) { 
		super(value, null);
		this.edges = new HashMap<>();
	}
	     
	/**
	 * Obtain the edges incident on this vertex.
	 */
	Iterable<AdjacencyMapEdge<E, V>> getIncidentOn() { return edges.values(); }
	
	/**
	 * Obtain this vertices neighbours (i.e. those vertices to which it is
	 * connected by an edge.)
	 */
	Iterable<AdjacencyMapVertex<V, E>> getNeighbours() { return edges.keySet(); }
	
	/**
	 * Determine whether this vertex is adjacent to that given. (i.e. that the
	 * given vertex is in the adjacency map.)
	 */
	boolean isAdjacentTo(final AdjacencyMapVertex<V, E> other) { 
		// Your code here
		for(AdjacencyMapVertex<V, E> item: edges.keySet()){
			if(item.getValue().equals(other.getValue())){
				return true;
			}
		}
		return false;
	}

	/**
	 * Insert the given edge into the adjacency map.
	 */
	void insert(final AdjacencyMapEdge<E, V> edge) { 
		// Your code here
		Pair<AdjacencyMapVertex<V, E>> foo = edge.getVertices();

		if(this.getValue() != foo.getRight().getValue()){
			if(this.getValue().equals(foo.getLeft().getValue())){
				// This means that it is the left one
				// So what we want to insert is the right one
				edges.put(foo.getRight(), edge);
			}
		}
		else{
			if(!this.getValue().equals(foo.getLeft().getValue())){
				// We want the LEft now
				edges.put(foo.getLeft(), edge);
			}
		}
	}

	/**
	 * Remove the given edge (and associated vertex) from the adjacency
	 * map. 
	 */
	void remove(final AdjacencyMapEdge<E, V> edge) { 
		// Your code here
		/**
		 * First we check if the edge exist in the Map.
		 * if true, remove it in both this Map<k,v> and the opposite vertex  Map<k,v>
		 * If false, stop.
		 */

		if(!edges.containsValue(edge)){return;}

		AdjacencyMapVertex<V, E> foo = null;

		for(AdjacencyMapVertex<V, E> key :edges.keySet())
                {
			if(edges.get(key).equals(edge)){
				foo = key;
			}
                }
		if(foo !=null){
			edges.remove(foo);
		}

	}
	/**
	 * Remove the given vertex (and associated edge) from the adjacency
	 * map.
	 */
	void remove(final AdjacencyMapVertex<V, E> vertex) {
		// Your code here

		for(AdjacencyMapEdge<E, V> value: vertex.getIncidentOn()){
			vertex.remove(value);
		}
	}

	/**
	 * Obtain a String representation of this vertex of the form 'vertex(value)'.
	 */
    @Override
	public String toString() { return "vertex("+getValue()+")"; }
	
	/**
	 * Obtain a dump (for debugging purposes) of the internal representation
	 * of this vertex.
	 */
	public void dump() {
		System.out.print("\nVertex: value="+getValue()+" edges=[");
		
		Iterator<AdjacencyMapVertex<V, E>> iterator = edges.keySet().iterator();
		if (iterator.hasNext()) {
			System.out.print(edges.get(iterator.next()));
			while (iterator.hasNext()) {
				System.out.print(", "+edges.get(iterator.next()));
			}
		}
		System.out.print(']');
	}
		
}
 
