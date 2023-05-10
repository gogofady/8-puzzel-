package AI;
import static java.lang.System.exit;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Scanner;

import AI.BFS;
import AI.DFS;
import AI.heuristic.State;

public class main {
	static final long MEGABYTE_FACTOR = 1024L * 1024L;
	 
	public static void main (String []args) {
		System.out.println("Enter your choice:") ; 
		System.out.println("1)bfs");
		System.out.println("2)dfs");
		System.out.println("3)A*");		
		Scanner s = new Scanner(System.in);
		int x=s.nextInt();
		
		switch (x) {
		case 1:
			bfsmain();
			break;
		case 2:
			dfsmain();
			break;
		case 3:
			astarmain();
			break;
			default:
				System.out.println("wrong input");
		}
	}

	private static void bfsmain() {
		

		System.out.println("Starting bfs to solve 8-puzzle");
		Scanner s = new Scanner(System.in);
		//long start = System.currentTimeMillis();
		LocalDateTime startTime = LocalDateTime.now();
                		System.out.println("ENter the numbers that will put in the 8-puzzel");
                                String a=s.nextLine();
                                String b=s.nextLine();
                                String c=s.nextLine();
                                String d=s.nextLine();
                                String e=s.nextLine();
                                String f=s.nextLine();
                                String g=s.nextLine();
                                String h=s.nextLine();
                                String i=s.nextLine();


		BFS bfs = new BFS(a,b,c,d,e,f,g,h,i);
                        
		boolean success = bfs.solve();

		LocalDateTime endTime = LocalDateTime.now();
		
		if(success)
		{
			System.out.println("\t\t\tStatistics:");
			System.out.println("Path to goal: " + bfs.getCurrentNode().getPathToGoal());
			System.out.println("Cost to goal: " + bfs.getCurrentNode().getCostOfPath());
			System.out.println("Nodes expanded: "+bfs.getExploredNodes().size());
			System.out.println("Search depth: " + bfs.getCurrentNode().getSearchDepth());
			System.out.println("Max Search depth: " + bfs.getCurrentNode().getMaxSearchDepth());
			
			
			//long hours = startTime.until( endTime, ChronoUnit.HOURS);
			long seconds = startTime.until( endTime, ChronoUnit.SECONDS);
			if(seconds == 0)
			{
				long milliSeconds = startTime.until( endTime, ChronoUnit.MILLIS);
				System.out.println("running time:  "+ milliSeconds+" MILLISECONDS");
			}
			else
			{
				System.out.println("running time:  "+ seconds+" SECONDS");
			}
			final long MEGABYTE_FACTOR = 1024L * 1024L;
			final DecimalFormat ROUNDED_DOUBLE_DECIMALFORMAT;
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
		    otherSymbols.setDecimalSeparator('.');
		    otherSymbols.setGroupingSeparator(',');
		    ROUNDED_DOUBLE_DECIMALFORMAT = new DecimalFormat("####0.00", otherSymbols);
		    ROUNDED_DOUBLE_DECIMALFORMAT.setGroupingUsed(false);
		    double totalMiB = bytesToMiB(getUsedMemory());
		    System.out.println(String.format("Max memory usage: %s Megabytes", totalMiB)); ;
			
		}
		else
		{
			System.err.println("no solution found!");
		}
		

	}

	private static void dfsmain() {
		System.out.println("Starting DFS to solve 8-puzzle");
				Scanner s = new Scanner(System.in);

		//long start = System.currentTimeMillis();
		LocalDateTime startTime = LocalDateTime.now();
                
                		System.out.println("ENter the numbers that will put in the 8-puzzel");
                                String a=s.nextLine();
                                String b=s.nextLine();
                                String c=s.nextLine();
                                String d=s.nextLine();
                                String e=s.nextLine();
                                String f=s.nextLine();
                                String g=s.nextLine();
                                String h=s.nextLine();
                                String i=s.nextLine();
                                
		DFS bfs = new DFS(a,b,c,d,e,f,g,h,i);
		boolean success = bfs.solve();
		LocalDateTime endTime = LocalDateTime.now();
		
		if(success)
		{
			System.out.println("\t\t\tStatistics:");
			System.out.println("Path to goal: " + bfs.getCurrentNode().getPathToGoal());
			System.out.println("Cost to goal: " + bfs.getCurrentNode().getCostOfPath());
			System.out.println("Nodes expanded: "+bfs.getExploredNodes().size());
			System.out.println("Search depth: " + bfs.getCurrentNode().getSearchDepth());
			System.out.println("Max Search depth: " + bfs.getCurrentNode().getMaxSearchDepth());
			
			
			//long hours = startTime.until( endTime, ChronoUnit.HOURS);
			long seconds = startTime.until( endTime, ChronoUnit.SECONDS);
			if(seconds == 0)
			{
				long milliSeconds = startTime.until( endTime, ChronoUnit.MILLIS);
				System.out.println("running time:  "+ milliSeconds+" MILLISECONDS");
			}
			else
			{
				System.out.println("running time:  "+ seconds+" SECONDS");
			}
			final long MEGABYTE_FACTOR = 1024L * 1024L;
			final DecimalFormat ROUNDED_DOUBLE_DECIMALFORMAT;
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
		    otherSymbols.setDecimalSeparator('.');
		    otherSymbols.setGroupingSeparator(',');
		    ROUNDED_DOUBLE_DECIMALFORMAT = new DecimalFormat("####0.00", otherSymbols);
		    ROUNDED_DOUBLE_DECIMALFORMAT.setGroupingUsed(false);
		    double totalMiB = bytesToMiB(getUsedMemory());
		    System.out.println(String.format("Max memory usage: %s Megabytes", totalMiB)); ;
			
		}
		else
		{
			System.err.println("no solution found!");
		}
		

	}
	public static long getTotalMemory() {
	    return Runtime.getRuntime().totalMemory();
	}

	private static double bytesToMiB(long bytes) {
	    return ((double) bytes / MEGABYTE_FACTOR);
	}

	public static long getMaxMemory() {
	    return Runtime.getRuntime().maxMemory();
	}

	public static long getUsedMemory() {
	    return getTotalMemory() - getFreeMemory();
	}


	public static long getFreeMemory() {
	    return Runtime.getRuntime().freeMemory();
	}
	private static void astarmain() {
		new heuristic();
	}
	}






