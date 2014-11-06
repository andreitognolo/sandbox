package com.andreitognolo.passiveinterruption;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class Params implements Jsonable {

	public static class Entry {
		private final String name;
		private final Object value;

		public Entry(String name, Object value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public Object getValue() {
			return value;
		}

	}

	private List<Entry> entries = new ArrayList<Entry>();

	public static Params parseURI(String url) {
		try {
			Params ret = new Params();
			List<NameValuePair> parsed = URLEncodedUtils.parse(new URI(url), "UTF-8");
			for (NameValuePair pair : parsed) {
				ret.entries.add(new Entry(pair.getName(), pair.getValue()));
			}
			return ret;
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public static Params parseURIAnchor(String url) {
		return parseURI(url.replaceAll("^[^#]*#", ""));
	}

	public Object param(String name) {
		for (Entry entry : entries) {
			if (name.equals(entry.name)) {
				return entry.value;
			}
		}
		return null;
	}

	public Long paramLong(String name) {
		for (Entry entry : entries) {
			if (name.equals(entry.name)) {
				if (entry.value instanceof String) {
					return new Long((String) entry.value);
				}
				return (Long) entry.value;
			}
		}
		return null;
	}

	public String paramString(String name) {
		for (Entry entry : entries) {
			if (name.equals(entry.name)) {
				return (String) entry.value;
			}
		}
		return null;
	}

	public List<Object> params(String name) {
		List<Object> ret = new ArrayList<Object>();
		for (Entry entry : entries) {
			if (name.equals(entry.name)) {
				ret.add(entry.value);
			}
		}
		return ret;
	}

	public Params add(String key, Object value) {
		if (value != null) {
			entries.add(new Entry(key, value));
		}
		return this;
	}

	public Params set(String key, Object value) {
		remove(key);
		add(key, value);
		return this;
	}

	private void remove(String key) {
		Iterator<Entry> it = entries.iterator();
		while (it.hasNext()) {
			Entry entry = it.next();
			if (key.equals(entry.name)) {
				it.remove();
			}
		}
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public int size() {
		return entries.size();
	}

	public Object first(String name) {
		Iterator<Entry> it = entries.iterator();
		while (it.hasNext()) {
			Entry entry = it.next();
			if (name.equals(entry.name)) {
				return entry.value;
			}
		}
		return null;
	}

	public String toURLParams() {
		StringBuilder ret = new StringBuilder();
		Iterator<Entry> it = entries.iterator();
		while (it.hasNext()) {
			Entry entry = it.next();
			Object value = entry.getValue();
			ret.append(entry.getName()).append("=").append(Util.encodeURI(value == null ? "null" : value.toString()));
			if (it.hasNext()) {
				ret.append("&");
			}
		}
		return ret.toString();
	}
}
