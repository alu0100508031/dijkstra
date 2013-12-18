/*
 * 
 * 	Algoritmo: Dijkstra
 * 	Laboratorio de Desarrollo y Herramientas
 *  Autor: David Rodríguez-Pastrana Parareda
 *  Fecha: 13/12/2014
 *  
 */

package alu4035.alu4035;

import java.io.File;
import java.util.Scanner;

/*
 * 
 * Calcula los caminos mínimos de un grafo mediante el algoritmo de Dijkstra.
 * Encuentra los costes mínimos de un nodo inicial X hasta todos los demás nodos del grafo.
 * 
 */

public class Dijkstra {
	static int[][] coste;
	static int[] distancia;

	public static void main(String[] args) throws Exception {

		Scanner sc = new Scanner(new File("grafo.txt"));

		int n = 0;
		while (sc.hasNext()) {
			sc.nextLine();
			n++;
		}
		sc.close();

		coste = new int[n][n];
		distancia = new int[n];

		//Inicializamos los costes para cada vértice
		//0 mismo vértice y 99999 como inalcanzable
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					coste[i][j] = 0;
				} else {
					coste[i][j] = 99999;
				}
			}
		}

		//Leemos cada fila y para cada columna
		//v es el vértice, ls[0] vértice adyacente y ls[1] coste de v hasta ls[0]
		sc = new Scanner(new File("grafo.txt"));
		System.out.print("\nGrafo:");
		for (int i = 0; i < n; i++) {
			String[] s = sc.nextLine().trim().split("\t");
			int v = Integer.parseInt(s[0]);
			System.out.print("\n");
			for (int j = 1; j < s.length; j++) {
				String[] ls = s[j].split(",");
				coste[v - 1][Integer.parseInt(ls[0]) - 1] = Integer.parseInt(ls[1]);
				System.out.print("v[" + v + "-" + Integer.parseInt(ls[0]) + "]" + ": " + Integer.parseInt(ls[1]) + "\t");
			}
		}
		sc.close();

		//Calculamos el coste mínimo desde v1 hasta cada v de N
		calculoCaminoMinimo(0, n);

		System.out.println("\n\nCostes mínimos:");
		int v = 0, min = 99999;
		for (int i = 1; i < n; i++) {
			System.out.print("v[1-" + (i + 1) + "]" + ": " + distancia[i] + "\t");
			if (distancia[i] < min) {
				min = distancia[i];
				v = i;
			}
		}
		System.out.print("\n\nMejor coste: v[1-" + (v + 1) + "]" + ": " + min + "\t");
	}

	static void calculoCaminoMinimo(int v, int n) {
		int[] s = new int[n];
		for (int i = 0; i < n; i++) {
			s[i] = 0;
			distancia[i] = coste[v][i];
		}
		s[v] = 1;
		distancia[v] = 0;
		for (int i = 1; i < n - 1; i++) {
			int u = 0, dis = 0;
			for (int j = 0; j < s.length; j++) {
				if (s[j] == 0) {
					dis = distancia[j];
					u = j;
					for (int k = j + 1; k < s.length; k++) {

						if (dis > distancia[k] && s[k] == 0) {
							dis = distancia[k];
							u = k;
						}
					}
					break;
				}
			}
			s[u] = 1;
			for (int j = 1; j < n; j++) {
				if (s[j] == 0) {
					if (distancia[j] > distancia[u] + coste[u][j]) {
						distancia[j] = distancia[u] + coste[u][j];
					}
				}
			}
		}
	}
}