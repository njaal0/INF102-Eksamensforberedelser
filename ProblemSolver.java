import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Discussed possible solutions to task 1 and 2 with
 * Martin Styve Pedersen.
 * 
 * @author Njål Nordal
 */
public class ProblemSolver implements IProblem {

	@Override
	public <V, E extends Comparable<E>> ArrayList<Edge<V>> mst(WeightedGraph<V, E> g) { // O(m log m)
		Set<V> found = new HashSet<>(); // O(1)
		PriorityQueue<Edge<V>> toSearch = new PriorityQueue<>(g); // O(1)
		ArrayList<Edge<V>> mst = new ArrayList<>(); // O(1)

		found.add(g.getFirstNode()); // O(1) to add the first node to the set
		addNeighborsToQueue(toSearch, g, g.getFirstNode()); // O(degree of first node) ≈ O(n) in worst case (add all
															// neighbours)

		while (!toSearch.isEmpty()) { // O(m) iterations, process every edge at most once
			Edge<V> current = toSearch.poll(); // O(log m), remove the minimum edge from the priority queue
			V u = current.a; // O(1) to access vertex 'u'
			V v = current.b; // O(1) to access vertex 'v'

			if (found.contains(u) && found.contains(v)) { // O(1) check if found contains both u and v
				continue; // O(1) if true
			}

			mst.add(current); // O(1) to add the edge to the MST (ArrayList)

			V vertexToSearch = found.contains(u) ? v : u; // O(1) if found contains u: search v, else search u.
			found.add(vertexToSearch); // O(1) add vertex to set

			addNeighborsToQueue(toSearch, g, vertexToSearch); // O(degree of vertexToSearch * log m) ≈ O(m log m) total
		}

		return mst; // O(1)
	}

	/**
	 * Adds neighbors of a given vertex 'v' to the priority queue.
	 *
	 * @param <V>   The type of vertices in the graph.
	 * @param <E>   The type of the edge weights, which must be comparable.
	 * @param queue The priority queue to which neighbors will be added.
	 * @param g     The weighted graph.
	 * @param v     The vertex whose neighbors will be added.
	 */
	private <V, E extends Comparable<E>> void addNeighborsToQueue(PriorityQueue<Edge<V>> queue, WeightedGraph<V, E> g,
			V v) {
		for (V neighbour : g.neighbours(v)) { // O(degree of v) per call, summed to O(m) across all vertices
			queue.add(new Edge<V>(v, neighbour)); // O(log m) to add an edge to the priority queue
		}
	}

	@Override
	public <V> V lca(Graph<V> g, V root, V u, V v) { // Total time complexity: O(n).
		if (u.equals(v)) { // O(1) to check if u equals v
			return u; // O(1) to return u
		}
		if (root.equals(u) || root.equals(v)) { // O(1) to check if root equals u or v
			return root; // O(1) to return root
		}

		ArrayList<V> pathU = new ArrayList<>(); // O(1), initialization
		ArrayList<V> pathV = new ArrayList<>(); // O(1), initialization

		Set<V> visitedU = new HashSet<>(); // O(1), initialization
		Set<V> visitedV = new HashSet<>(); // O(1), initialization

		// O(n + m) for each `findPath` call (DFS traversal). Overall O(2 * (n + m)) =
		// O(n + m), however m = n - 1, so O(2 * n) = O(n).
		if (!findPath(g, root, u, pathU, visitedU) || !findPath(g, root, v, pathV, visitedV)) {
			return null; // O(1) if one or both paths are not found
		}

		int i; // O(1)
		for (i = 0; i < pathU.size() && i < pathV.size(); i++) { // O(n)
			if (!pathU.get(i).equals(pathV.get(i))) { // O(1), comparison
				break; // O(1)
			}
		}

		return pathU.get(i - 1); // O(1) to access lca at index i-1
	}

	/**
	 * Finds a path from the root to the target using a DFS traversal.
	 *
	 * @param <V>     The type of vertices in the graph.
	 * @param g       The graph.
	 * @param root    The starting node.
	 * @param target  The target node.
	 * @param path    The list of nodes forming the path to the target.
	 * @param visited The set of visited nodes.
	 * @return True if the path to the target is found, false
	 *         otherwise.
	 */
	private <V> boolean findPath(Graph<V> g, V root, V target, ArrayList<V> path, Set<V> visited) { // O(n + m).
																									// m = n - 1.
																									// O(2 * n)
																									// O(n) final.
		if (root == null) { // O(1), check if root is null
			return false; // O(1), return false
		}

		path.add(root); // O(1), add the current root to path
		visited.add(root); // O(1), mark the root as visited in the set

		if (root.equals(target)) { // O(1), check if the root is the target
			return true; // O(1), return true if the target is found
		}

		// Iterate over all neighbors of the current node
		// O(degree(root)) for each call, summed = O(m)
		for (V neighbour : g.neighbours(root)) {
			if (!visited.contains(neighbour)) { // O(1), check if neighbor has been visited
				if (findPath(g, neighbour, target, path, visited)) { // Recursive call
					return true; // O(1)
				}
			}
		}

		path.remove(path.size() - 1); // O(1), backtrack by removing the last element in the path
		return false; // O(1)
	}

	@Override
	public <V> Edge<V> addRedundant(Graph<V> g, V root) { // O(n)

		// Check if the root has only one child, O(1)
		if (singleChild(root, g)) {
			// Find the only child of the root, O(1)
			V singleChildVertex = singleChildRootNeighbour(root, g);

			// Find the largest subtree from the single child, O(n)
			V largestSubtreeRoot = findLargestPathRoot(g, singleChildVertex);

			// Create and return the redundant edge, O(1)
			return createEdge(root, largestSubtreeRoot);
		}

		// Create all subtrees rooted at the children of the root, O(n)
		ArrayList<ArrayList<V>> allSubTrees = createPaths(g, root);

		// Find and create the redundant edge from the subtrees, O(k)
		return findAndCreateRedundantEdge(g, allSubTrees);
	}

	/**
	 * Finds the root of the largest subtree from a given node in the graph.
	 *
	 * @param <V>  The type of vertices in the graph.
	 * @param g    The graph.
	 * @param node The starting node to find the largest subtree.
	 * @return The root of the largest subtree.
	 */
	private <V> V findLargestPathRoot(Graph<V> g, V node) { // O(n + m). m = n - 1. O(2 * n). O(n).
		ArrayList<ArrayList<V>> allSubTrees = createPaths(g, node); // O(n)

		int maxSize = 0;
		V largestSubtreeRoot = null;

		// Iterate through all subtrees to find the largest one, O(k)
		for (ArrayList<V> subtree : allSubTrees) {
			if (subtree.size() > maxSize) { // O(1), comparison
				maxSize = subtree.size();
				largestSubtreeRoot = subtree.get(0); // Root of the subtree
			}
		}

		return largestSubtreeRoot;
	}

	/**
	 * Creates all paths rooted at a given start node, to the bottom
	 * of the tree using DFS traversal.
	 *
	 * @param <V>       The type of vertices in the graph.
	 * @param g         The graph.
	 * @param startNode The starting node for the DFS.
	 * @return A list of subtrees represented as lists of vertices.
	 */
	private <V> ArrayList<ArrayList<V>> createPaths(Graph<V> g, V startNode) { // O(n + m). m = n - 1. O(2 * n). O(n).
		ArrayList<ArrayList<V>> allSubTrees = new ArrayList<>();

		// Iterate through all neighbors of the starting node, O(m)
		for (V neighbour : g.neighbours(startNode)) {
			Set<V> seen = new HashSet<>();
			seen.add(startNode); // O(1)

			// Create a subtree using DFS, O(n) for this traversal
			createPathDFS(g, neighbour, seen, new ArrayList<>(), allSubTrees);
		}

		return allSubTrees;
	}

	/**
	 * Performs a DFS to create subtrees from the current node.
	 *
	 * @param <V>         The type of vertices in the graph.
	 * @param g           The graph.
	 * @param node        The current node in the DFS.
	 * @param seen        A set of visited nodes.
	 * @param currentPath The current path being followed in the DFS.
	 * @param allSubTrees The list of all subtrees being created.
	 */
	private <V> void createPathDFS(Graph<V> g, V node, Set<V> seen, ArrayList<V> currentPath,
		ArrayList<ArrayList<V>> allSubTrees) { // Total O(n + m). m = n - 1. O(2 * n). O(n).
		seen.add(node); // O(1)
		currentPath.add(node); // O(1)

		// Record the current path as a subtree
		allSubTrees.add(new ArrayList<>(currentPath)); // O(n) - Copying current path to new ArrayList

		// Explore unvisited neighbors
		for (V neighbour : g.neighbours(node)) { // O(m)
			if (!seen.contains(neighbour)) { // O(1)
				createPathDFS(g, neighbour, seen, currentPath, allSubTrees); // Each node is visited once,
																				// contributing to O(n + m)
			}
		}

		// Backtrack if exit from loop
		currentPath.remove(currentPath.size() - 1); // O(1)
	}

	/**
	 * Finds and creates a redundant edge from the subtrees.
	 *
	 * @param <V>         The type of vertices in the graph.
	 * @param g           The graph.
	 * @param allSubTrees The list of all subtrees.
	 * @return The redundant edge created.
	 */
	private <V> Edge<V> findAndCreateRedundantEdge(Graph<V> g, ArrayList<ArrayList<V>> allSubTrees) { // O(k)
		// Find a node at the second deepest level, O(k)
		V node1 = findNodeAtSecondDeepestLevel(allSubTrees, g);

		// Get the node used for subtree removal, O(k)
		int maxDepth = getMaxDepth(allSubTrees);
		V rootNodeForRemovingPaths = getNodeForRemovingPaths(allSubTrees, maxDepth, node1);

		// Remove subtrees containing node1, O(k)
		ArrayList<ArrayList<V>> updatedSubTrees = removeSubTrees(allSubTrees, rootNodeForRemovingPaths);

		// Find a second node at the second deepest level, O(k)
		V node2 = findNodeAtSecondDeepestLevel(updatedSubTrees, g);

		// Create and return the redundant edge, O(1)
		return createEdge(node1, node2);
	}

	/**
	 * Finds the node at the second deepest level of the subtrees.
	 *
	 * @param <V>      The type of vertices in the graph.
	 * @param subtrees The list of all subtrees.
	 * @param g        The graph.
	 * @return The node found at the second deepest level.
	 */
	private <V> V findNodeAtSecondDeepestLevel(ArrayList<ArrayList<V>> subtrees, Graph<V> g) { // O(k)
		// Get the maximum depth of the subtrees, O(k)
		int maxDepth = getMaxDepth(subtrees);

		// Get the node with the highest degree at the second deepest level, O(k)
		return getNodeWithHighestDegreeAtDepth(subtrees, g, maxDepth);
	}

	/**
	 * Gets the maximum depth from all subtrees.
	 *
	 * @param <V>      The type of vertices in the graph.
	 * @param subtrees The list of all subtrees.
	 * @return The maximum depth found.
	 */
	private <V> int getMaxDepth(ArrayList<ArrayList<V>> subtrees) { // O(k)
		int maxDepth = 1;

		// Iterate through all subtrees to find the maximum depth, O(k)
		for (ArrayList<V> list : subtrees) {
			if (list.size() > maxDepth) {
				maxDepth = list.size();
			}
		}

		return maxDepth;
	}

	/**
	 * Gets the node with the highest degree at a given depth.
	 *
	 * @param <V>         The type of vertices in the graph.
	 * @param subtrees    The list of all subtrees.
	 * @param g           The graph.
	 * @param maxDepth    The maximum depth found from paths
	 * @return The node with the highest degree at the given depth.
	 */
	private <V> V getNodeWithHighestDegreeAtDepth(ArrayList<ArrayList<V>> subtrees, Graph<V> g, int maxDepth) { // O(k)
		V highestDegreeNode = null;
		int highestDegree = -1;

		// Iterate through all subtrees to find nodes at the target depth. O(k)
		for (ArrayList<V> list : subtrees) {
			if (list.size() == maxDepth) {
				V node = list.get(maxDepth - 2); // Node at the target depth. (second to last node).
				int nodeDegree = g.degree(node);
				if (nodeDegree > highestDegree) {
					highestDegreeNode = node;
					highestDegree = nodeDegree;
				}
			}
		}

		return highestDegreeNode;
	}

	/**
	 * Finds the node used for subtree removal.
	 *
	 * @param <V>         The type of vertices in the graph.
	 * @param allSubTrees The list of all subtrees.
	 * @param node1       The node to be removed.
	 * @return The root of the subtree containing node1.
	 */
	private <V> V getNodeForRemovingPaths(ArrayList<ArrayList<V>> allSubTrees, int maxDepth, V node1) { // O(k)
		// Find the subtree containing node1, O(k)
		for (ArrayList<V> list : allSubTrees) {
			if (list.size() == maxDepth) { //O(1)
				if (list.get(maxDepth - 2).equals(node1)) { //O(1)
					return list.get(0); //O(1)
				}
			}
		}

		return null;
	}

	/**
	 * Removes all subtrees that contain a particular node.
	 *
	 * @param <V>      The type of vertices in the graph.
	 * @param subtrees The list of all subtrees.
	 * @param node     A Set containing the node used for removal
	 * @return A new list of subtrees without the subtrees containing the node.
	 */
	private <V> ArrayList<ArrayList<V>> removeSubTrees(ArrayList<ArrayList<V>> subtrees, V node) { // O(k)
		ArrayList<ArrayList<V>> newSubTrees = new ArrayList<>();

		// Add subtrees that don't contain the node O(k)
		for (ArrayList<V> list : subtrees) {
			if (!(list.get(0) == node)) {
				newSubTrees.add(list); // Add all trees that still contain nodes that can be a part of the redundant edge
			}
		}

		return newSubTrees;
	}

	/**
	 * Creates an edge between two nodes in the graph.
	 *
	 * @param <V>   The type of vertices in the graph.
	 * @param node1 The first node of the edge.
	 * @param node2 The second node of the edge.
	 * @return The created edge.
	 */
	private <V> Edge<V> createEdge(V node1, V node2) { // O(1)
		return new Edge<>(node1, node2); // Creating an edge is constant time
	}

	/**
	 * Checks if a given root has only one child.
	 *
	 * @param <V>  The type of vertices in the graph.
	 * @param root The root to check.
	 * @param g    The graph.
	 * @return True if the root has only one child, otherwise false.
	 */
	private <V> boolean singleChild(V root, Graph<V> g) { // O(1)
		return g.neighbours(root).size() == 1; // Checking the size of neighbors is O(1)
	}

	/**
	 * Gets the only child of a root node.
	 *
	 * @param <V>  The type of vertices in the graph.
	 * @param root The root whose child is to be found.
	 * @param g    The graph.
	 * @return The only child of the root.
	 */
	private <V> V singleChildRootNeighbour(V root, Graph<V> g) { // O(1)
		return g.neighbours(root).iterator().next(); // Return the only child
	}

}
