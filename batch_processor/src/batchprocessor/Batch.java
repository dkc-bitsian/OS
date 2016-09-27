package batchprocessor;
/** CS 5338.001 Operating Systems Concepts
 * Krishna Chaitanya Reddy Dodda
 
*/

import java.util.LinkedHashMap;
import java.util.Map;


public class Batch {
	
	//creating instance variables of the batch. workingDir stores the working directory of the batch 
	//and commands stores a list of all the commands to be executed .
	private String workingDir;
	private Map<String, Command> commands;

	
	public Batch(){
		this.commands = new LinkedHashMap<String,Command>();
		
	}
	
	//method to add command list to the batch
	public void addCommand(Command command){
		this.commands.put(command.getId(), command);
		
	}
	//sets the working directory of the batch
	public void setWorkingDir(String Input){
		workingDir=Input;
	}
	
	//gets the working directory of the batch
	public String getWorkingDir(){
		return workingDir;
	}
	//gets the commands from the batch
	public Map<String, Command> getCommands(){
		return commands;
	}
	
}
