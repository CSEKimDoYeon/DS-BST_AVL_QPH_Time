import java.util.Random;

public class TestMain {

	static long start;
	static long end;
	int min = 0;
	int max = 0;
	static int randomNum[];

	static BinarySearchTree bst = new BinarySearchTree();
	static AVLTree avl ;
	static QuadraticProbingHashTable quadraticHashTable = new QuadraticProbingHashTable(); 

	public static void main(String args[]) {

		System.out.println("n=10000");
		randomNum = getRandomNum(10000);
		System.out.println("***** Insert *****");
		getInsertTime(10000);
		System.out.println("\n***** Search *****");
		getSearchTime(10000);
		
		System.out.println("n=100000");
		randomNum = getRandomNum(10000);
		System.out.println("***** Insert *****");
		getInsertTime(100000);
		System.out.println("\n***** Search *****");
		getSearchTime(100000);
		
		System.out.println("n=1000000");
		randomNum = getRandomNum(1000000);
		System.out.println("***** Insert *****");
		getInsertTime(1000000);
		System.out.println("\n***** Search *****");
		getSearchTime(1000000);
		
	}

	/////////// 랜덤상수 발생///////////////
	public static int[] getRandomNum(int max) {
		int randomNum[] = new int[max];
		for (int i = 0; i < randomNum.length; i++) {
			randomNum[i] = (int) (Math.random() * max + 1);
			for (int j = 0; j < i; j++) {
				if (randomNum[i] == randomNum[j]) {
					// 같은 수가 존재한다면 다시 랜덤 수 생성
					i--;
				}
			}
		}
		return randomNum;
	}

	public static void getInsertTime(int max) {
		start = System.currentTimeMillis();
		
		for (int a = 0; a < randomNum.length; a++) {
			bst.insertBST(randomNum[a]);
		}

		end = System.currentTimeMillis();
		System.out.println("BST insert : " + (end - start) + "ms");
		///////////////////////////////////////////////
		start = System.currentTimeMillis();
		
		avl = new AVLTree(randomNum[0]);
		for (int a = 1; a < randomNum.length; a++) {
			avl.add(randomNum[a]);
		}

		end = System.currentTimeMillis();
		System.out.println("AVL insert : " + (end - start) + "ms");
		///////////////////////////////////////////////
		start = System.currentTimeMillis();

		for (int a = 0; a < randomNum.length; a++) {
			quadraticHashTable.putNums(randomNum[a]);
		}

		end = System.currentTimeMillis();
		System.out.println("QPH insert : " + (end - start) + "ms");
	}
	
	public static void getSearchTime(int max) {
		start = System.currentTimeMillis();

		for (int a = 0; a < randomNum.length; a++) {
			bst.searchBST(randomNum[a]);
		}

		end = System.currentTimeMillis();
		System.out.println("BST search : " + (end - start) + "ms");
		///////////////////////////////////////////////
		start = System.currentTimeMillis();

		for (int a = 0; a < randomNum.length; a++) {
			avl.search(avl, randomNum[a]);
		}

		end = System.currentTimeMillis();
		System.out.println("AVL search : " + (end - start) + "ms");
		///////////////////////////////////////////////
		start = System.currentTimeMillis();
		
		for (int a = 0; a < randomNum.length; a++) {
			quadraticHashTable.get(randomNum[a]);
		}

		end = System.currentTimeMillis();
		System.out.println("QPH search : " + (end - start) + "ms");
	}

}
