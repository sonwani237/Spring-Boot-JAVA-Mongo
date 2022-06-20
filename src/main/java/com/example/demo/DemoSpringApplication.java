package com.example.demo;

import com.opencsv.CSVWriter;
import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class DemoSpringApplication {

	static int mb = 1024 * 1024;
	static int gb = 1024 * 1024 * 1024;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(DemoSpringApplication.class, args);
//		System.out.println("*****************************************");
//		String name = System.getProperty("os.name");
//		System.out.println("OS Name:-" +name);
//		System.out.println("*****************************************");
//		System.getProperties().list(System.out);
//		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
//		for (int i = 0; i<threadSet.size(); i++) {
//			System.out.println(">>>>"+threadSet);
//		}
//		for (Thread t : Thread.getAllStackTraces().keySet()) {
//			System.out.println("Name: "+t.getName() + "   						 Daemon: " + t.isDaemon()+ "    <> isAlive: " + t.isAlive());
//		}

//		CSVWriter writer = new CSVWriter(new FileWriter("C://Divyanshu_Sonwani/Java/output.csv"));
//
//		List<ProcessInfo> processesList = JProcesses.getProcessList();
//
//		String[] Header = {"Process PID", "Process Name", "Process Time", "Process User", "Virtual Memory", "Physical Memory", "CPU usage", "Start Time", "Priority", "Full command"};
//		writer.writeNext(Header);
//
//		for (final ProcessInfo processInfo : processesList) {
//			long virtualMemory = Long.parseLong(processInfo.getVirtualMemory())/mb;
//			long physicalMemory = Long.parseLong(processInfo.getPhysicalMemory())/mb;
//			String[] data = {processInfo.getPid(), processInfo.getName(), processInfo.getTime(), processInfo.getUser(), String.valueOf(virtualMemory),
//					String.valueOf(physicalMemory), processInfo.getCpuUsage(), processInfo.getStartTime(), processInfo.getPriority(), processInfo.getCommand()};
//			writer.writeNext(data);
//		}
//		writer.flush();
//		System.out.println("Data entered");

	}

}
