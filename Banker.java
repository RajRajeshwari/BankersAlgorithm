//package hello;
import java.io.*;
public class Banker 
{
	public static void main(String[] args) throws IOException
	{
		int Avail[], Max[][], Allo[][],Need[][];   //declaration
		int n,m,i,j;
		boolean safestate = true; 
		InputStreamReader in = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(in);
		System.out.println("Enter the number of processes:");
		n = Integer.parseInt(br.readLine());
		System.out.println("Enter the number of different resources:");
		m = Integer.parseInt(br.readLine());
		//initializing the matrices
		Avail = new int[m];
		Max = new int[n][m];
		Allo = new int[n][m];
		Need = new int[n][m];
		int check =0,count2 =0;
		int sequence[] = new int[n]; //to indicate the sequence of the processes
		boolean Finish[] = new boolean[n];  //flag variable to indicate whether the job is pending or not
		int counter = 0;
		
		//Avail[] vector is of length m indicating the number of available resources of each type.
		//If Avail[i] = k, then k instances of resource type Ri is available.
		System.out.println("Enter the number of instances,k, for each available resource, Ri");
		for(i = 0 ; i < m; i++)
			Avail[i] = Integer.parseInt(br.readLine());
		
		//Max[n][m] n-no of processes, m-no of resource type; if Max[i][j]=k, process Pi can request maximum k instances of resources type Rj.
		System.out.print("Enter the value in the Max[][] matrix for each of the process Pi indicating the no. of instances,k, of each resource Rj");
		for(i = 0; i < n; i++)
			for(j = 0; j < m; j++)
				Max[i][j] = Integer.parseInt(br.readLine());

		//Allo[n][m] n-no of processes, m-no of resource type; if Allo[i][j]=k, process Pi is currently allocated k instances of resources type Rj.
		System.out.print("Enter the value in the Allo[][] matrix for each of the process Pi indicating the no. of instances,k, of each resource Rj");
		for(i = 0; i < n; i++)
			for(j = 0; j < m; j++)
				Allo[i][j] = Integer.parseInt(br.readLine());
		
		//Need[n][m] n-no of processes, m-no of resource type; if Max[i][j]=k, process Pi may need k instances of resources type Rj.
		//Need[i][j] = Max[i][j] - Allo[i][j];
		System.out.println("Need Martix is as follows:");
		for(i = 0; i < n; i++)
		{
			for(j = 0; j < m; j++)
			{
				Need[i][j] = Max[i][j] - Allo[i][j];
				System.out.print(Need[i][j]+"  ");
			}
			System.out.println();
		}
		
		//////////////////////////////////////////Safety Algorithm section/////////////////////////////////////////////////////////
		//Step 1:
		for(i = 0; i < n; i++)
		{
			Finish[i] = false;
			sequence[i]  = -1;
		}
				
		safestate = false; 
		for(int k = 0; k <= n; k++) // limiting the number of maximum trails so that there is no infinite loop in case safe state does not exist
		{
			if(safestate == false)
			{				
				 
				for(i = 0; i < n; i++)
				{
					for(j = 0; j < m; j++)
						if(Finish[i] == false && Need[i][j] <= Avail[j])    //Step 2:
							check++;
						else 
							break; // to go to next value of i 
					if(check == m) //Step 3:
					{
						Finish[i] = true;
						sequence[counter] = i;
						counter++;
						check = 0;
						for(int x = 0; x < m; x++)
						{
							Avail[x] = Avail[x] + Allo[i][x];
							Allo[i][x] = Max[i][x];
						}
					}
				}
				
				for(i = 0; i < n; i++)
					if(Finish[i] == true) 
						count2++;
				if(count2 == n)
					safestate = true;
				else
				{
					safestate = false;
					count2 = 0;
				}
			}
			else
				break;
		}
		if(safestate == true)
		{
			System.out.println("One of the safe sequence is:");
			for(i = 0; i < n; i++)
				System.out.print("P"+sequence[i]+"\t");
		}
		else
			System.out.println("No safe state");
	}
}