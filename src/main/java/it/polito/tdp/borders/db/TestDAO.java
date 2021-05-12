package it.polito.tdp.borders.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Border;

public class TestDAO {

	public static void main(String[] args) {

		BordersDAO dao = new BordersDAO();

		int i=0;
		int j=0;
		int n=0;
		System.out.println("Lista di tutte le nazioni:");
		//List<Country> countries = dao.loadAllCountries();
		Map<Integer,Country> m=new HashMap<Integer,Country>();
		dao.loadAllCountries(m);
		for (Country c: m.values()) {
			i++;
		System.out.print(c);
		System.out.println(i);

		}
		
		List<Border> l=dao.getCountryPairs(m, 2000);
		for(Border b: l) {
			System.out.println(b);
			j++;
		}
		System.out.println(j);
		
		List<Country> p=dao.getVertici(m, 2000);
		for(Country c: p) {
			System.out.println(c);
			n++;
		}
		System.out.println(n);

	}
}
