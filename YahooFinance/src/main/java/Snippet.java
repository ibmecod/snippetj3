/*
 * Created on 12.12.2009
 *
 * (c) Copyright Christian P. Fries, Germany. All rights reserved. Contact: email@christian-fries.de.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Snippet {

	private static HashMap stocks = new HashMap();
	private long updateIntervall;

	public static void main(String[] args) {
		try {
			YahooQuoteFetcher fetcher = new YahooQuoteFetcher(5.0);
			fetcher.getData("AAPL");
			Set key = stocks.keySet();
			Iterator itr = key.iterator();
			while (itr.hasNext()) {
				MarketDataBean mdb = (MarketDataBean) stocks.get(itr.next());
				System.out.println(mdb.getLastUpdated() + "  " + mdb.getSymbol() + "  " + mdb.getPrice() + "  "
						+ mdb.getVolume() + "  " + mdb.getChange());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param updateIntervall
	 */
	public YahooQuoteFetcher(double updateIntervallInSeconds) {
		super();
		this.updateIntervall = (long) (updateIntervallInSeconds * 1000.0);
	}

	public MarketDataBean getData(String symbol) throws IOException {
		fetchData(symbol);
		return (MarketDataBean) stocks.get(symbol);
	}

	public synchronized void fetchData(String symbol) throws IOException {
		/*
		 * Fetch CSV data from Yahoo. The format codes (f=) are: s=symbol, l1 =
		 * last, c1 = change, d1 = trade day, t1 = trade time, o = open, h =
		 * high, g = low, v = volume
		 */
		URL ulr = new URL("http://finance.yahoo.com/d/quotes.csv?s=" + symbol + "&f=sl1c1vd1t1ohg&e=.csv");
		URLConnection urlConnection = ulr.openConnection();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				String[] yahooStockInfo = inputLine.split(",");
				MarketDataBean stockInfo = new MarketDataBean();
				stockInfo.setSymbol(yahooStockInfo[0].replaceAll("\"", ""));
				stockInfo.setPrice(Double.valueOf(yahooStockInfo[1]));
				stockInfo.setChange(Double.valueOf(yahooStockInfo[2]));
				stockInfo.setVolume(Double.valueOf(yahooStockInfo[3]));
				stockInfo.setLastUpdated(System.currentTimeMillis());
				stocks.put(symbol, stockInfo);
				break;
			}
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	public class MarketDataBean {

		String symbol;
		double price;
		double change;
		double volume;
		long lastUpdate;

		public String getSymbol() {
			return symbol;
		}

		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public double getChange() {
			return change;
		}

		public void setChange(double change) {
			this.change = change;
		}

		/**
		 * @return the volume
		 */
		public double getVolume() {
			return volume;
		}

		/**
		 * @param volume
		 *            the volume to set
		 */
		public void setVolume(double volume) {
			this.volume = volume;
		}

		public long getLastUpdated() {
			return lastUpdate;
		}

		public void setLastUpdated(long lastUpdate) {
			this.lastUpdate = lastUpdate;
		}
	}
}
