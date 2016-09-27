package batchprocessor;
/** CS 5338.001 Operating Systems Concepts
 * Krishna Chaitanya Reddy Dodda

*/

import java.util.Map;

import org.w3c.dom.Element;


//this class is responsible for parsing the WD command and then setting the workdirectory of the batch
public class WDCommand extends Command 
{

	//method describing what the current command is doing

	@Override
	public String describe() {
		System.out.println("");
		String s= "WDCommand has started";
		return s;
	}

	//method to execute the respective WD command

	@Override
	public void execute(String workingDir, Map<String, Command> Commands ) 
	{
	
		System.out.println("WD command is being executed");

		System.out.println("ID of the WD command being executed is "+comm_id);
		
		System.out.println("The working directory has been set to "+comm_path);
		
		System.out.println("execution of the WD command has finished");

	}
	//method to parse the WD command
	@Override
	public void parse(Element elem) throws ProcessException 
	{
		String id = elem.getAttribute("id");
		if (id == null || id.isEmpty()) {
			throw new ProcessException("Missing ID in WD Command");
		}
		System.out.println("ID: " + id);

		String path = elem.getAttribute("path");
		if (path == null || path.isEmpty()) {
			throw new ProcessException("Missing PATH in WD Command");
		}
		System.out.println("Path: " + path);
		//setting instance variables of the command class after parsing
		//these instance variables will be used for the execution of the command
		comm_id=id;
		comm_path=path;
	}
	
}