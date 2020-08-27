import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		RBBST Treeson = new RBBST(); ArrayList<String> toPrint = new ArrayList<String>();
		Scanner s = new Scanner(System.in);
		boolean done = false;
		while (!done && s.hasNextLine()) {
			String line = s.nextLine();
			Scanner ls = new Scanner(line);
			String token = ls.next();
			switch(token.toUpperCase()) {
				case "INSERT":
					RBBST.Node insertMe = new RBBST.Node(ls.nextInt(), ls.next().charAt(0));
				while (ls.hasNext()) {
					RBBST.Insert(Treeson, insertMe);
				} break;
				case "DELETE":
				while (ls.hasNext()) {
					RBBST.Node delete = null;
					while (ls.hasNext()) {
						delete = RBBST.getNode(RBBST.getRoot(Treeson), ls.nextInt());
						if (delete != null) {
							RBBST.Delete(Treeson, delete);
						}
					}
				} break;
				case "PREORDER":
					toPrint.add(RBBST.preOrder(RBBST.getRoot(Treeson)));
					break;
				case "PREORDERRED":
					toPrint.add(RBBST.preOrder(RBBST.getRoot(Treeson), 'r'));
					break;
				case "PREORDERBLACK":
					toPrint.add(RBBST.preOrder(RBBST.getRoot(Treeson), 'b'));
					break;
				case "INORDER":
					toPrint.add(RBBST.inOrder(RBBST.getRoot(Treeson)));
					break;
				case "INORDERRED":
					toPrint.add(RBBST.inOrder(RBBST.getRoot(Treeson), 'r'));
					break;
				case "INORDERBLACK":
					toPrint.add(RBBST.inOrder(RBBST.getRoot(Treeson), 'b'));
					break;
				case "POSTORDER":
					toPrint.add(RBBST.postOrder(RBBST.getRoot(Treeson)));
					break;
				case "POSTORDERRED":
					toPrint.add(RBBST.postOrder(RBBST.getRoot(Treeson), 'r'));
					break;
				case "POSTORDERBLACK":
					toPrint.add(RBBST.postOrder(RBBST.getRoot(Treeson), 'b'));
					break;
				case "EXIT":
					for (int i = 0; i < toPrint.size(); i++) {
						System.out.println(toPrint.get(i));
					}
					done = true;
					ls.close();
					break;
				default:
					break;
			}
		}
		s.close();
	}
	
}

class RBBST {
	
	static class Node{
		
		private Node p = NIL;
		private Node l = NIL;
		private Node r = NIL;
		private char data;
		private char color;
		private int key;
		
		public Node(int key) {
			this.key = key; this.color = 'b';
		}
		
		public Node(int key, char data) {
			this.key = key; this.data = data; this.color = 'b';
		}
	}
	
	private static Node NIL = new Node(-1);
	private Node root = NIL;
	
	public RBBST() {
	}
	
	public static Node getRoot(RBBST T) {
		return T.root;
	}
	
	public static Node getNode(Node root, int key) {
		if (root == null || key == root.key) {
			return root;
		}
		else if (key < root.key) {
			return getNode(root.l, key);
		}
		else {
			return getNode(root.r, key);
		}
	}
	
	public static void LeftRotate(RBBST T, Node x) {
		Node y = x.r;
		x.r = y.l;
		if (y.l != T.NIL) {
			y.l.p = x;
		}
		y.p = x.p;
		if (x.p == T.NIL) {
			T.root = y;
		}
		else if (x == x.p.l) {
			x.p.l = y;
		}
		else {
			x.p.r = y;
		}
		y.l = x;
		x.p = y;
	}
	
	public static void RightRotate(RBBST T, Node x) {
		Node y = x.l;
		x.l = y.r;
		if (y.r != T.NIL) {
			y.r.p = x;
		}
		y.p = x.p;
		if (x.p == T.NIL) {
			T.root = y;
		}
		else if (x == x.p.r) {
			x.p.r = y;
		}
		else {
			x.p.l = y;
		}
		y.r = x;
		x.p = y;
	}
	
	public static Node Minimum(Node root) {
		while (root.l != null) {
			root = root.l;
		}
		return root;
	}
	
	public static Node Maximum(Node root) {
		while (root.r != null) {
			root = root.r;
		}
		return root;
	}
	
	public static void Insert(RBBST T, Node nn) {
		Node y = T.NIL;
		Node x = T.root;
		while (x != T.NIL) {
			y = x;
			if (nn.key < x.key) {
				x = x.l;
			}
			else {
				x = x.r;
			}
		}
		nn.p = y;
		if (y == T.NIL) {
			T.root = nn;
		}
		else if (nn.key < y.key) {
			y.l = nn;
		}
		else {
			y.r = nn;
		}
		nn.l = T.NIL;
		nn.r = T.NIL;
		nn.color = 'r';
		InsertFixup(T, nn);
	}
	
	public static void InsertFixup(RBBST T, Node nn){
       while (nn.p.color == 'r') {
	    	   if (nn.p == nn.p.p.l) {
	    		   Node y = nn.p.p.r;
	    		   if (y.color == 'r') {
	    			   nn.p.color = 'b';
	    			   y.color = 'b';
	    			   nn.p.p.color = 'r';
	    			   nn = nn.p.p;
	    		   }
	    		   else if (nn == nn.p.r) {
	    			   nn = nn.p;
	    			   LeftRotate(T, nn);
	    		   }
	    		   nn.p.color = 'b';
	    		   nn.p.p.color = 'r';
	    		   RightRotate(T, nn.p.p);
	    	   }
	    	   else {
	    		   Node y = nn.p.p.l;
	    		   if (y.color == 'r') {
	    			   nn.p.color = 'b';
	    			   y.color = 'b';
	    			   nn.p.p.color = 'r';
	    			   nn = nn.p.p;
	    		   }
	    		   else if (nn == nn.p.l) {
	    			   nn = nn.p;
	    			   RightRotate(T, nn);
	    		   }
	    		   nn.p.color = 'b';
	    		   nn.p.p.color = 'r';
	    		   LeftRotate(T, nn.p.p);
	    	   }
       }
       T.root.color = 'b';
    }

	
	public static void Delete(RBBST T, Node remove) {
		Node y = remove;
		Node x;
		char y_orig_color = y.color;
		if (remove.l == T.NIL) {
			x = remove.r;
			Transplant(T, remove, remove.r);
		}
		else if (remove.r == T.NIL) {
			x = remove.l;
			Transplant(T, remove, remove.r);
		}
		else	 {
			y = Minimum(remove.r);
			y_orig_color = y.color;
			x = y.r;
			if (y.p == remove) {
				x.p = y;
			}
			else {
				Transplant(T, y, y.r);
				y.r = remove.r;
				y.r.p = y;
			}
			Transplant(T, remove, y);
			y.l = remove.l;
			y.l.p = y;
			y.color = remove.color; 
		}
		if (y_orig_color == 'b') {
			DeleteFixup(T, x);
		}
	}
	
	public static void DeleteFixup(RBBST T, Node x){
		Node w;
		while (x != T.root && x.color == 'b') {
			if (x == x.p.l) {
				w = x.p.r;
				if (w.color == 'r') {
					w.color = 'b';
					x.p.color = 'r';
					LeftRotate(T, x.p);
					w = x.p.r;
				}
				if (w.l.color == 'b' && w.r.color == 'b') {
					w.color = 'r';
					x = x.p;
				}
				else if (w.r.color == 'b') {
					w.l.color = 'b';
					w.color = 'r';
					RightRotate(T, w);
					w = x.p.r;
				}
				w.color = x.p.color;
				x.p.color = 'b';
				w.r.color = 'b';
				LeftRotate(T, x.p);
				x = T.root;
			}
			else {
				w = x.p.l;
				if (w.color == 'r') {
					w.color = 'b';
					x.p.color = 'r';
					RightRotate(T, x.p);
					w = x.p.l;
				}
				if (w.r.color == 'b' && w.l.color == 'b') {
					w.color = 'r';
					x = x.p;
				}
				else if (w.l.color == 'b') {
					w.r.color = 'b';
					w.color = 'r';
					LeftRotate(T, w);
					w = x.p.l;
				}
				w.color = x.p.color;
				x.p.color = 'b';
				w.l.color = 'b';
				RightRotate(T, x.p);
				x = T.root;
			}
		}
		x.color = 'b';	
	}
	
	public static void Transplant(RBBST T, Node remove, Node plant) {
		if (remove.p == T.NIL) {
			T.root = plant;
		}
		else if (remove == remove.p.l) {
			remove.p.l = plant;
		}
		else {
			remove.p.r = plant;
		}
		plant.p = remove.p;
	}
	
	public static String inOrder(Node root) {
		String inOrder = "";
		if (root != NIL) {
			inOrder += inOrder(root.l);
			inOrder += root.data;
			inOrder += inOrder(root.r);
		}
		return inOrder;
	}
	
	public static String inOrder(Node root, char color) {
		String inOrder = "";
		if (root != NIL) {
			inOrder += inOrder(root.l);
			if (root.color == color) {
				inOrder += root.data;
			}
			inOrder += inOrder(root.r);
		}
		return inOrder;
	}
	
	public static String preOrder(Node root) {
		String preOrder = "";
		if (root != NIL) {
			preOrder += root.data;
			preOrder += preOrder(root.l);
			preOrder += preOrder(root.r);
		}
		return preOrder;
	}
	
	public static String preOrder(Node root, char color) {
		String preOrder = "";
		if (root != NIL) {
			if (root.color == color) {
				preOrder += root.data;
			}
			preOrder += preOrder(root.l);
			preOrder += preOrder(root.r);
		}
		return preOrder;
	}
	
	public static String postOrder(Node root) {
		String postOrder = "";
		if (root != NIL) {
			postOrder += postOrder(root.l);
			postOrder += postOrder(root.r);
			postOrder += root.data;
		}
		return postOrder;
	}
	
	public static String postOrder(Node root, char color) {
		String postOrder = "";
		if (root != NIL) {
			postOrder += postOrder(root.l);
			postOrder += postOrder(root.r);
			if (root.color == color) {
				postOrder += root.data;
			}
		}
		return postOrder;
	}
	
}
