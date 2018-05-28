package cn.edu.nuaa.aadl2.generator.utils;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class ShowMessage {
	static MessageConsole console = null;
	static MessageConsoleStream consoleStream = null;
	static IConsoleManager consoleManager = null;
	
	private static void initConsole(){
		consoleManager = ConsolePlugin.getDefault().getConsoleManager();
		IConsole[] consoles = consoleManager.getConsoles();
		if(consoles.length > 0){
			console = (MessageConsole)consoles[0];
		}else{
			console = new MessageConsole("Console",null);
			consoleManager.addConsoles(new IConsole[]{ console });
		}
		consoleStream = console.newMessageStream();
	}
	public static void printMessage(String message){
		if(console == null){
			initConsole();
		}
		consoleManager.showConsoleView(console);
		consoleStream.println(message);
	}
}
