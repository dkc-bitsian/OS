package batchprocessor;
/** CS 5338.001 Operating Systems Concepts
 * Krishna Chaitanya Reddy Dodda
*/
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Element;


//this class refers to the the cmd subcommand of the main pipe command
public class PipeCmdCommand extends Command 
{
	//describing the pipecmdcommand
	@Override
	public String describe() {
		System.out.println("");
		String s= "PipeCmdCommand execution has started";
		return s;
	}

	//parsing the command
	@Override
	public void parse(Element elem) throws ProcessException  
	{
		String id = elem.getAttribute("id");
		if (id == null || id.isEmpty()) {
			throw new ProcessException("Missing ID in PipeCMD Command");
		}
		System.out.println("ID: " + id);
		

		String path = elem.getAttribute("path");
		if (path == null || path.isEmpty()) {
			throw new ProcessException("Missing PATH in PipeCMD Command");
		}
		System.out.println("Path: " + path);
		comm_path=path;
		comm_id=id;

		// Arguments must be passed as a list of
		// individual strings.
		List<String> cmdArgs = new ArrayList<String>();
		String arg = elem.getAttribute("args");
		if (!(arg == null || arg.isEmpty())) {
			StringTokenizer st = new StringTokenizer(arg);
			while (st.hasMoreTokens()) {
				String tok = st.nextToken();
				cmdArgs.add(tok);
			}
		}
		for (String argi : cmdArgs) {
			System.out.println("Arg " + argi);
		}
		comm_args=cmdArgs;

		String inID = elem.getAttribute("in");
		if (!(inID == null || inID.isEmpty())) 
		{
			System.out.println("inID: " + inID);
			comm_in=inID;
		}

		String outID = elem.getAttribute("out");
		if (!(outID == null || outID.isEmpty())) 
		{
			System.out.println("outID: " + outID);
			comm_out=outID;
		
		}

	}
	@Override
	public void execute(String workingDir, Map<String, Command> batch_CommandList ) 
	{
		
		System.out.println("Executing Cmd command of the pipe with id "+ comm_id);

	}


}
