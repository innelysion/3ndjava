package tnw.game2.g12;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AStarFindPath {

	private int[][] NODES;
	private int STEP = 10;
	private ArrayList<Node> openList = new ArrayList<Node>();
	private ArrayList<Node> closeList = new ArrayList<Node>();

	AStarFindPath(){
	}

	public Stack<Node> search(int[][]hitdata, Node startNode, Node endNode){
		NODES = hitdata;
		openList.clear();
		closeList.clear();
		Node parent = findPath(startNode, endNode);
		Stack<Node> arrayList = new Stack<Node>();

		while (parent != null) {
			arrayList.push(new Node(parent.x, parent.y));
			parent = parent.parent;
		}
		return arrayList;

	}

	private Node findMinFNodeInOpneList() {
		Node tempNode = openList.get(0);
		for (Node node : openList) {
			if (node.F < tempNode.F) {
				tempNode = node;
			}
		}
		return tempNode;
	}

	private ArrayList<Node> findNeighborNodes(Node currentNode) {
		ArrayList<Node> arrayList = new ArrayList<Node>();
		// 只考虑上下左右，不考虑斜对角
		int topX = currentNode.x;
		int topY = currentNode.y - 1;
		if (canReach(topX, topY) && !exists(closeList, topX, topY)) {
			arrayList.add(new Node(topX, topY));
		}
		int bottomX = currentNode.x;
		int bottomY = currentNode.y + 1;
		if (canReach(bottomX, bottomY) && !exists(closeList, bottomX, bottomY)) {
			arrayList.add(new Node(bottomX, bottomY));
		}
		int leftX = currentNode.x - 1;
		int leftY = currentNode.y;
		if (canReach(leftX, leftY) && !exists(closeList, leftX, leftY)) {
			arrayList.add(new Node(leftX, leftY));
		}
		int rightX = currentNode.x + 1;
		int rightY = currentNode.y;
		if (canReach(rightX, rightY) && !exists(closeList, rightX, rightY)) {
			arrayList.add(new Node(rightX, rightY));
		}
		return arrayList;
	}

	private boolean canReach(int x, int y) {
		if (x >= 0 && x < NODES.length && y >= 0 && y < NODES[0].length) {
			return NODES[x][y] == 0;
		}
		return false;
	}

	private Node findPath(Node startNode, Node endNode) {

		// 把起点加入 open list
		openList.add(startNode);

		while (openList.size() > 0) {
			// 遍历 open list ，查找 F值最小的节点，把它作为当前要处理的节点
			Node currentNode = findMinFNodeInOpneList();
			// 从open list中移除
			openList.remove(currentNode);
			// 把这个节点移到 close list
			closeList.add(currentNode);

			ArrayList<Node> neighborNodes = findNeighborNodes(currentNode);
			for (Node node : neighborNodes) {
				if (exists(openList, node)) {
					foundPoint(currentNode, node);
				} else {
					notFoundPoint(currentNode, endNode, node);
				}
			}
			if (find(openList, endNode) != null) {
				return find(openList, endNode);
			}
		}

		return find(openList, endNode);
	}

	private void foundPoint(Node tempStart, Node node) {
		int G = calcG(tempStart, node);
		if (G < node.G) {
			node.parent = tempStart;
			node.G = G;
			node.calcF();
		}
	}

	private void notFoundPoint(Node tempStart, Node end, Node node) {
		node.parent = tempStart;
		node.G = calcG(tempStart, node);
		node.H = calcH(end, node);
		node.calcF();
		openList.add(node);
	}

	private int calcG(Node start, Node node) {
		int G = STEP;
		int parentG = node.parent != null ? node.parent.G : 0;
		return G + parentG;
	}

	private int calcH(Node end, Node node) {
		int step = Math.abs(node.x - end.x) + Math.abs(node.y - end.y);
		return step * STEP;
	}

	private Node find(List<Node> nodes, Node point) {
		for (Node n : nodes)
			if ((n.x == point.x) && (n.y == point.y)) {
				return n;
			}
		return null;
	}

	private boolean exists(List<Node> nodes, Node node) {
		for (Node n : nodes) {
			if ((n.x == node.x) && (n.y == node.y)) {
				return true;
			}
		}
		return false;
	}

	private boolean exists(List<Node> nodes, int x, int y) {
		for (Node n : nodes) {
			if ((n.x == x) && (n.y == y)) {
				return true;
			}
		}
		return false;
	}

}

class Node {
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int x;
	public int y;

	public int F;
	public int G;
	public int H;

	public void calcF() {
		this.F = this.G + this.H;
	}
	public Node parent;
}