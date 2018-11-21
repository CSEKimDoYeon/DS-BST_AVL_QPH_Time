import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class QuadraticProbingHashTable {

	public ArrayList<Integer> usedNum = new ArrayList<Integer>();
	public ArrayList<Integer> result = new ArrayList<Integer>();

	private Entry[] entries;
	private int size, used;
	private float loadFactor;
	private final Entry NIL = new Entry(null, null);

	private int Records;
	private int Collisons;
	private StringBuffer QuadraticProbingBuf;

	public QuadraticProbingHashTable(int capacity, float loadFactor) {
		this.size = 0;
		this.used = 0;
		this.entries = new Entry[capacity];
		this.loadFactor = loadFactor;
		this.Records = 0;
		this.Collisons = 0;
		this.QuadraticProbingBuf = new StringBuffer("<<<<제곱 조사 실행>>>>\n");
	}

	public QuadraticProbingHashTable(int capacity) {
		this(capacity, 0.75F);
	}

	public QuadraticProbingHashTable() {
		this(100);
	}

	public Object get(Object key) {
		int h = hash(key);
		for (int i = 0; i < entries.length; i++) {
			int j = nextProbe(h, i);
			Entry entry = entries[j];
			if (entry == null)
				break;
			if (entry == NIL)
				continue;
			if (entry.key.equals(key))
				return entry.value;
		}
		return null;
	}

	public void printAll() {
		for (int i = 0; i < usedNum.size(); i++) {
			System.out.println(usedNum.get(i));
		}
	}

	public void deleteOverlap(ArrayList ar) {
		for (int i = 0; i < usedNum.size(); i++) {
			if (!result.contains(usedNum.get(i))) {
				result.add(usedNum.get(i));
			}
		}
	}

	public Object put(Object key, Object value) {
		if (used > loadFactor * entries.length)
			rehash();
		int h = hash(key);
		this.QuadraticProbingBuf.append((++this.Records) + "번째 Data >> " + key + " (hashcode : " + h + ")" + "\n");
		for (int i = 0; i < entries.length; i++) {
			int j = nextProbe(h, i);
			Entry entry = entries[j];
			if (entry == null) {
				entries[j] = new Entry(key, value);
				this.QuadraticProbingBuf.append(" -> " + j + "\n");
				++size;
				++used;
				return null;
			}
			if (entry == NIL)
				continue;
			if (entry.key.equals(key)) {
				Object oldValue = entry.value;
				entries[j].value = value;
				return oldValue;
			}
			this.Collisons++;
			this.QuadraticProbingBuf.append(" -> " + j + "\n");
		}
		return null;
	}

	public void putNums(int input)  {
		this.put(input, new Num(input));
	}

	public Object remove(Object key) {
		int h = hash(key);
		for (int i = 0; i < entries.length; i++) {
			int j = nextProbe(h, i);
			Entry entry = entries[j];
			if (entry == null)
				break;
			if (entry == NIL)
				continue;
			if (entry.key.equals(key)) {
				Object oldValue = entry.value;
				entries[j] = NIL;
				--size;
				return oldValue;
			}
		}
		return null;
	}

	public int size() {
		return size;
	}

	private class Entry {
		Object key, value;

		Entry(Object k, Object v) {
			key = k;
			value = v;
		}
	}

	private int hash(Object key) {
		if (key == null)
			throw new IllegalArgumentException();
		return (key.hashCode() & 0x7FFFFFFF) % entries.length;
	}

	private int nextProbe(int h, int i) {
		return (h + i * i) % entries.length; 
	}

	private void rehash() {
		Entry[] oldEntries = entries;
		entries = new Entry[2 * oldEntries.length + 1];
		for (int k = 0; k < oldEntries.length; k++) {
			Entry entry = oldEntries[k];
			if (entry == null || entry == NIL)
				continue;
			int h = hash(entry.key);
			for (int i = 0; i < entries.length; i++) {
				int j = nextProbe(h, i);
				if (entries[j] == null) {
					entries[j] = entry;
					break;
				}
			}
		}
		used = size;
	}

}

class Num {

	public int num;

	public Num(int num) {
		this.num = num;
	}

	public String toString() {
		return String.valueOf(num);
	}

	public int getWord() {
		return num;
	}
}
