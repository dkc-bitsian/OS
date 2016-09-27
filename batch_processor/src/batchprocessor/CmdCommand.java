package batchprocessor;
/** CS 5338.001 Operating Systems Concepts
 * Krishna Chaitanya Reddy Dodda
*/
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.io.File;
import java.io.IOException;

import org.w3c.dom.Element;


//this class is used to execute and parse the cmd command
public class CmdCommand extends Command

{

	//method describing what the current command is doing
	@Override
	public String describe() 
	{
		System.out.println("");
		String s= "CmdCommand execution has started";
		return s;
	}

	//method to execute the cmd command
	@Override
	public void execute(String workingDir, Map<String, Command> batch_CommandList ) 

	{
		
		try
		{

			List<String> command = new ArrayList<String>();
			
			command.add(comm_path);
			
			//adding commands (comm_args) to a list which would be fed to the processbuilder 
			for(int i=0;i<comm_args.size();i++)
			{
			command.add(comm_args.get(i));
			}
			
			//building the process builder
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(command);
			builder.directory(new File(workingDir));
			File wd = builder.directory();
								

			// setting the input file to the process 
			try
            {
				if(comm_in==null)
				{
					//Cmd can still not give an exception if In is not given
				}
				else if(!comm_in.isEmpty())
                {
							File inFile = new File(wd, batch_CommandList.get(comm_in).comm_path);
							
                			command.add(batch_CommandList.get(comm_in).comm_path);

					builder.redirectInput(inFile);
                }
            }
			catch(NullPointerException e)
            {
                System.out.println(" ERROR PROCESSING-Cant find input file with ID " + comm_in);
                e.printStackTrace();
            }
			
			System.out.println("The Commands for the process being executed by Cmd command are"+command);

			// setting the output file to the process
			try
			{
                if(!(comm_out.isEmpty()||comm_out==null))
                {
					
                	File outFile = new File(wd, batch_CommandList.get(comm_out).comm_path);
                	builder.redirectOutput(outFile);


                }
                if(comm_out==null)
                {
                    System.out.println(" Missing OUT file in the cmd command ");

                }
			}
			catch(NullPointerException e)
            {
                System.out.println(" ERROR PROCESSING- Cant find output file with ID " + comm_out);
                e.printStackTrace();
            }							
			//executing the process
			
			Process process = builder.start();
			process.waitFor();
            System.out.println("The process is completed");


		}
		catch(IOException e)
        {
            System.out.println("ERROR-CmdCommand IOException");

        }
        catch(InterruptedException e)
        {
            
        }
		System.out.println("Cmd command execution has finished " );

	}

	
	//method to parse the cmd command
	@Override
	public void parse(Element elem) throws ProcessException 
	{
		String id = elem.getAttribute("id");
		if (id == null || id.isEmpty()) {
			throw new ProcessException("Missing ID in CMD Command");
		}
		System.out.println("ID: " + id);

		String path = elem.getAttribute("path");
		if (path == null || path.isEmpty()) {
			throw new ProcessException("Missing PATH in CMD Command");
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
		if (!(inID == null || inID.isEmpty())) {
			System.out.println("inID: " + inID);
			comm_in=inID;
		}

		String outID = elem.getAttribute("out");
		if (!(outID == null || outID.isEmpty())) {
			System.out.println("outID: " + outID);
			comm_out=outID;
		
		}
		
	}
}
