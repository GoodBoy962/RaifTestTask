package com.pliskin;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
		HashTable<String, Integer> hashTable = new HashTable<>();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			hashTable.put("s" + i, i);
		}
		System.out.println(System.currentTimeMillis() - start);

		start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			hashTable.get("s");
		}
		System.out.println(System.currentTimeMillis() - start);

		start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			hashTable.remove("s");
		}
		System.out.println(System.currentTimeMillis() - start);


		HashMap<String, Integer> hashMap = new HashMap<>();
		start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			hashMap.put("s" + i, i);
		}
		System.out.println(System.currentTimeMillis() - start);

		start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			hashMap.get("s");
		}
		System.out.println(System.currentTimeMillis() - start);

		start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			hashMap.remove("s");
		}
		System.out.println(System.currentTimeMillis() - start);
	}
}
