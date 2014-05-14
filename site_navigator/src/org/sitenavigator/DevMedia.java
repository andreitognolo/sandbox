package org.sitenavigator;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DevMedia {

	private static String nomeRevista;
	
	public static void extractInformation(int revistaId, String pNomeRevista) throws ClientProtocolException,
			IOException {
		
		nomeRevista = pNomeRevista;
		Map<Integer, Integer> map = new HashMap<>();

		int pageNumber = 0;
		while (true) {
			pageNumber++;
			String url = "http://www.devmedia.com.br/busca/?page=" + pageNumber + "&o=&lido=&keyword=&p=&tipo=0&site=" + revistaId + "&txtsearch=&notsite=0&vis=-1&codigobanca=&mentoring=0#result";
			String conteudo = new String(Request.Get(url).execute()
					.returnContent().asBytes());
			if (conteudo.contains("Nenhum")) {
				break;
			}

			count(map, conteudo);
		}

		showResult(map);
	}

	public static void showResult(Map<Integer, Integer> map) {
		List<Entry<Integer, Integer>> entryList = new ArrayList<>(
				map.entrySet());

		Collections.sort(entryList, new Comparator<Entry<Integer, Integer>>() {
			public int compare(Entry<Integer, Integer> o1,
					Entry<Integer, Integer> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});

		for (Entry<Integer, Integer> entry : entryList) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}

	public static void count(Map<Integer, Integer> map, String conteudo)
			throws ClientProtocolException, IOException {
		Document doc = Jsoup.parse(conteudo);
		Elements elements = doc.select("#busca_result");
		for (Element element : elements) {
			if (!element.select(".busca_titulo").text().trim().toUpperCase()
					.startsWith(nomeRevista)) {
				continue;
			}

			Integer year = getYear(element.select(".busca_data").text());

			String revistaUrl = element.select("a").attr("href");
			String revistaConteudo = new String(Request.Get(revistaUrl)
					.execute().returnContent().asBytes());

			Integer ocorrencias = map.get(year);
			if (ocorrencias == null) {
				ocorrencias = 0;
			}

			System.out
					.println(Jsoup.parse(revistaConteudo).select("title")
							.text()
							+ " - "
							+ (revistaConteudo.toUpperCase().split("TESTE").length - 1));
			ocorrencias += revistaConteudo.toUpperCase().split("TESTE").length - 1;
			map.put(year, ocorrencias);
		}
	}

	public static int countOccurrences(String haystack, String needle) {
		return haystack.split(needle).length - 1;
	}

	public static int getYear(String dataString) {
		dataString = dataString.trim();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = sdf.parse(dataString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.YEAR);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
