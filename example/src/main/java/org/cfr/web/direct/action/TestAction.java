package org.cfr.web.direct.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.cfr.direct.action.IDirectAction;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.softwarementors.extjs.djn.config.annotations.DirectMethod;

@Component
public class TestAction implements IDirectAction {


	@DirectMethod
	public String doEcho( String data ) {
		return data;
	}

	@DirectMethod
	public double multiply( String num ) {
		double num_ = Double.parseDouble(num);
		return num_ * 8.0;
	}

	public static class Data {
		public String firstName;
		public String lastName;
		public int age;
	}

	@DirectMethod
	public String showDetails( Data data ) {
		return "Hi " + data.firstName + " " + data.lastName + ", you are " + data.age + " years old.";
	}

	public static class Node {
		public String id;
		public String text;
		public boolean leaf;
	}

	@DirectMethod
	public List<Node> getTree( String id) {
		List<Node> result = new ArrayList<Node>();
		if( id.equals("root")) {
			for( int i = 1; i <= 5; ++i ) {
				Node node = new Node();
				node.id = "n" + i;
				node.text = "Node " + i;
				node.leaf = false;
				result.add(node);
			}
		}
		else if( id.length() == 2) {
			String num = id.substring(1);
			for( int i = 1; i <= 5; ++i ) {
				Node node = new Node();
				node.id = id + i;
				node.text = "Node " + num + "." + i;
				node.leaf = true;
				result.add(node);
			}
		}
		return result;
	}

	public static class GridRow {
		public GridRow( String name, int turnover) {
			this.name = name;
			this.turnover = turnover;
		}

		public String name;
		public int turnover;
	}

	public static class SortInfo {
		public String property;
		public String direction;
	}

	@DirectMethod
	public List<GridRow> getGrid( JsonArray params ) {
		// We know this is the structure, but the 'right' way to do this is
		// to define a Java class that maps the information we are receiving
		JsonObject sortInfo = (JsonObject)params.get(0).getAsJsonObject().get("sort").getAsJsonArray().get(0);

		assert sortInfo != null;
		List<GridRow> data = new ArrayList<GridRow>();
		String field = sortInfo.get("property" ).getAsString();
		String direction = sortInfo.get("direction" ).getAsString();

		if( field.equals("name")) {
			data.add( new GridRow("ABC Accounting", 50000));
			data.add( new GridRow("Ezy Video Rental", 106300));
			data.add( new GridRow("Greens Fruit Grocery", 120000));
			data.add( new GridRow("Icecream Express", 73000));
			data.add( new GridRow("Ripped Gym", 88400));
			data.add( new GridRow("Smith Auto Mechanic", 222980));
		}
		else {
			data.add( new GridRow("ABC Accounting", 50000));
			data.add( new GridRow("Icecream Express", 73000));
			data.add( new GridRow("Ripped Gym", 88400));
			data.add( new GridRow("Ezy Video Rental", 106300));
			data.add( new GridRow("Greens Fruit Grocery", 120000));
			data.add( new GridRow("Smith Auto Mechanic", 222980));
		}

		if( direction.equals( "DESC")) {
			Collections.reverse(data);
		}

		return data;
	}
}
