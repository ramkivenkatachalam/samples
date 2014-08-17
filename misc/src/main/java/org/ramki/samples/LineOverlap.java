package org.ramki.samples;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.ramki.samples.LineOverlap.Point.state;

public class LineOverlap {
	static class Point implements Comparable<Point>{
		public static enum state {
			START,
			END
		}
		int lineID;
		state st;
		int  xValue;
		
		public Point(int lineID, int  xValue, state st) {
			this.lineID = lineID;
			this.xValue = xValue;
			this.st = st;
		}
		
		public int compareTo(Point o) {
			if (this.xValue == o.xValue) return this.lineID - o.lineID;
			else return (this.xValue - o.xValue);
		}
		
	}
	static class Line {
		int  startX;
		int  endX;
		
		public Line(int  startX, int  endX) {
			this.startX = startX;
			this.endX = endX;
		}
	}
	private static int noOfNonInterest(List<Line> input) {
		PriorityQueue<Point> queue = new PriorityQueue<LineOverlap.Point>();
		for (int i = 0; i < input.size(); i++) {
			Line l = input.get(i); 
			queue.add(new Point(i, l.startX, state.START));
			queue.add(new Point(i, l.endX, state.END));
		}
		int count = 0;
		int prevLineID = -1;
		int nLines = 0;
		while (!queue.isEmpty()) {
			Point i = queue.remove();
			if (i.st == state.END) nLines++;
			else nLines--;
			if (prevLineID == i.lineID && nLines == 0) count++;
			prevLineID = i.lineID;
		}
		return count;
		
	}
	
	public static void main(String[] args) {
		List<Line> lines = new ArrayList<LineOverlap.Line>();
		lines.add(new Line(1, 2));
		lines.add(new Line(2, 5));
		lines.add(new Line(1, 3));
		lines.add(new Line(9, 11));
		System.out.println("Result: " + noOfNonInterest(lines));
	}

}
