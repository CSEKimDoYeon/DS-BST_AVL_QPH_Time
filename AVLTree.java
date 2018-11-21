public class AVLTree {

	private int key, height; // AVLTree의 key 값과 높이를 저장할 int 자료형.
	private AVLTree left, right; // 좌측 AVLTree, 우측AVLTree.

	public static final AVLTree NIL = new AVLTree(); // 아무것도 없는 AVLTree는 NIL로 선언.

	public AVLTree(int key) { // AVL 트리 생성자.
		this.key = key;
		this.left = this.right = NIL;
	}

	public AVLTree() { // AVL 트리 생성자.
		this.left = this.right = this;
		this.height = -1;
	}

	public AVLTree(int key, AVLTree left, AVLTree right) { // AVL 트리 생성자.
		this.key = key;
		this.left = left;
		this.right = right;
		this.height = 1 + Math.max(this.left.height, this.right.height);
	}

	public boolean add(int Key) { // AVL트리 삽입 메소드.
		int oldsize = this.size();
		this.grow(Key); // Key값에 해당하는 AVLTree를 삽입하고 grow 실행.
		return this.size() > oldsize;
	}

	public AVLTree grow(int Key) {
		if (this == NIL)
			return new AVLTree(Key); // AVL트리를 처음 생성할 경우.
		if (Key == this.key)
			return this; // 키가 갇으면 자기 자신.
		if (Key < this.key)
			this.left = this.left.grow(Key); // 키가 작으면 왼쪽에 트리를 붙힌다.
		else
			this.right = this.right.grow(Key); // 키가 크면 오른쪽에 트리를 붙힌다.

		this.rebalance();
		this.height = 1 + Math.max(this.left.height, this.right.height);
		return this;
	}

	public int size() { // AVL트리의 사이즈를 출력하는 메소드.
		if (this == NIL)
			return 0;
		return 1 + this.left.size() + this.right.size();
	}

	public String toString() { // AVL트리에 연결되어있는 모든 노드 출력.
		if (this == NIL)
			return "";
		return left + "(" + key + "," + (left.height - right.height) + ") " + right;// AVL 트리 프린트 메소드
	}

	private void rebalance() { // AVL트리가 재 정렬이 필요할 경우 정렬하는 메소드.
		if (this.right.height > this.left.height + 1) {
			if (this.right.left.height > this.right.right.height) {
				this.right.rotateRight(); // 왼쪽에 치우쳐져 있을 경우.
			}
			this.rotateLeft();
		} else if (this.left.height > this.right.height + 1) {
			if (this.left.right.height > this.left.left.height) {
				this.left.rotateLeft(); // 오른쪽에 치우쳐져 있을 경우.
			}
			this.rotateRight();
		}
	}

	private void rotateLeft() { // AVL Left rotate 메소드.
		this.left = new AVLTree(this.key, this.left, this.right.left);
		this.key = this.right.key;
		this.right = this.right.right;
	}

	private void rotateRight() { // AVL Right rotate 메소드.
		this.right = new AVLTree(this.key, this.left.right, this.right);
		this.key = this.left.key;
		this.left = this.left.left;
	}

	public boolean remove(int Key) { // AVL 트리에서 Key값에 해당하는 AVL 트리를 삭제.
		int oldSize = size();
		decrease(Key); // grow와 반대로 사이즈를 decrease해준다.
		return size() < oldSize;
	}

	private AVLTree decrease(int deleteKey) { // AVL 트리의 사이즈를 감소시키는 메소드.
		if(search(this,deleteKey)==true) {
		if (deleteKey == this.key) {
			if (this.left == NIL && this.right == NIL)
				return NIL;
			if (this.left == NIL)
				return this.right;
			if (this.right == NIL)
				return this.left;
			else { // 양쪽 모두에 자식 노드가 존재하는 경우 실행.
				if (this.right.left == NIL)
					return new AVLTree(this.right.key, this.left, this.right.right);
				else { 
					AVLTree leftMostOfRightTree;
					for (leftMostOfRightTree = this.right; leftMostOfRightTree.left != NIL; leftMostOfRightTree = leftMostOfRightTree.left)
						;
					int replaceKey = leftMostOfRightTree.key;
					this.decrease(leftMostOfRightTree.key);
					this.key = replaceKey;
					return new AVLTree(replaceKey, this.left, this.right);
				}
			}
		}
		if (deleteKey < this.key)
			this.left = this.left.decrease(deleteKey);
		else
			this.right = this.right.decrease(deleteKey);
		}
		this.rebalance();
		this.height = 1 + Math.max(this.left.height, this.right.height);
		return this;
	}

	public boolean search(AVLTree t, int x) { // AVLTree에서 Key값에 해당하는 노드를 찾는 메소드.
		if (t != NIL) { // AVL 트리가 NIL이 아닐 경우 실행.
			if (x == t.key) { // 찾으려는 값이 key와 같을 경우.
				//System.out.println(x + " 검색결과 : true");
				return true;
			} else if (x < t.key) { // 키보다 작으면 왼쪽에 붙어있으므로 왼쪽 한번 더 탐색.
				return search(t.left, x);

			} else if (x > t.key) { // 키보다 크면 오른쪽에 붙어있으므로 오른쪽 한번 더 탐색.
				return search(t.right, x);
			} else
				return false;
		} else
			//System.out.println(x + " 검색결과 : false");
		return false;
	}
}