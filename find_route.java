import java.io.*;
import java.util.*;

public class find_route
{
    /*  Uniform-Cost Search and print details at each iterations    */
    public static void find_path_uninform_detail(Map<String, LinkedList<City>> list, String start, String goal)
    {
        /*  Variables   */
        int node_expanded = 0;
        Set<String> explored = new HashSet<String>();
        LinkedList<City> frontier = new LinkedList<City>();
        City start_city = new City(start, 0);

        /*  First node into the fringe  */
        frontier.add(start_city);
        System.out.println("Expanding Node: " + node_expanded);
        while ( frontier.size() != 0 )
        {

            /* Display all nodes in frontier    */
            System.out.println("Fringe:");
            Iterator<City> it = frontier.iterator();
            while (it.hasNext())
            {
                City city = it.next();
                System.out.println("\t" + city.get_city() + ": g(n) = " + city.get_c_distance() 
                    + ", d = " + city.get_depth() + " , Parent: " + city.get_parent().get_city()
                    + " g(n) = " + city.get_parent().get_city() + " d: " + city.get_parent().get_depth());                    
            }
    
            /*  Pop the first element (also smallest)   */
            City node = frontier.poll();
            node_expanded++;
            /*  Displaying the node popped  */
            System.out.println("Generating successor to " + node.get_city());
            
            /*  Goal Condition  */
            if (node.get_city().equals(goal))
            {
                /*  Display Results */
                System.out.println("Node Expanded: " + node_expanded);
                System.out.println("Distance: " + node.get_c_distance() + "km");
                System.out.println("Route: ");
                
                LinkedList<City> solution = new LinkedList<City>();

                while ( !node.get_city().equals("None"))
                {
                    solution.addFirst(node);
                    node = node.get_parent();
                }

                Iterator<City> i = solution.iterator();
                City c = i.next();
                while (i.hasNext())
                {
                    System.out.print("\t" + c.get_city() + " to ");
                    c = i.next();
                    System.out.print(c.get_city() + ", " + c.get_distance() + " km");
                    System.out.println();
                }
                return;
            }

            /*  Generating successor to non-visited city    */
            if ( !explored.contains(node.get_city()))
            {
                /*  Add parent to explored list */
                explored.add(node.get_city());

                /*  Displaying all explored nodes   */
                Iterator<String> k = explored.iterator();
                System.out.println("Closed: ");
                System.out.print("\t");
                while (k.hasNext())
                {
                    System.out.print(k.next() + ", ");
                }
                System.out.println();
                
                /*  Generating all successors   */
                Iterator<City> i = list.get(node.get_city()).listIterator();
                while (i.hasNext())
                {
                    City child = i.next();
                    child.set_parent(node);
                    child.set_c_distance(child.get_distance() + node.get_c_distance());
                    child.set_depth(node.get_depth() + 1);
                    frontier.add(child);
                    /*  Sort the fringe */
                    Collections.sort(frontier, City.Comparators.dist);
                }              
            }
            else
            {
                System.out.println(node.get_city() + " already closed. No successor");
            }
            System.out.println();
        }
        /*  Displaying Information for failure  */
        System.out.println("Node Expanded: " + node_expanded);
        System.out.println("Distance: infinity");
        System.out.println("Route: None");        
        return;
    }

    /*  A* Searchand print details at each iterations   */
    public static void find_path_inform_detail(Map<String, LinkedList<City>> list, String start, String goal, Map<String, Integer> list_h)
    {
        /*  Variables   */
        int node_expanded = 0;
        Set<String> explored = new HashSet<String>();
        LinkedList<City> frontier = new LinkedList<City>();
        City start_city = new City(start, 0);
        start_city.set_c_distance(list_h.get(start_city.get_city()));

        /*  First node into the fringe  */
        frontier.add(start_city);
        System.out.println("Expanding Node: " + node_expanded);
        while ( frontier.size() != 0 )
        {

            /* Display all nodes in frontier    */
            System.out.println("Fringe:");
            Iterator<City> it = frontier.iterator();
            while (it.hasNext())
            {
                City city = it.next();
                System.out.println("\t" + city.get_city() + ": g(n) = " + city.get_c_distance() 
                    + ", d = " + city.get_depth() + " , Parent: " + city.get_parent().get_city()
                    + " g(n) = " + city.get_parent().get_city() + " d: " + city.get_parent().get_depth());
            }
    
            /*  Pop the first element (also smallest)   */
            City node = frontier.poll();
            node.set_c_distance(node.get_c_distance() - list_h.get(node.get_city()));
            node_expanded++;

            /*  Displaying the node popped  */
            System.out.println("Generating successor to " + node.get_city());
            
            /*  Goal Condition  */
            if (node.get_city().equals(goal))
            {
                /*  Display Results */
                System.out.println("Node Expanded: " + node_expanded);
                System.out.println("Distance: " + node.get_c_distance() + " km");
                System.out.println("Route: ");
                
                /*  Backtrack adding solution city to a LinkedList  */
                LinkedList<City> solution = new LinkedList<City>();
                while ( !node.get_city().equals("None"))
                {
                    solution.addFirst(node);
                    node = node.get_parent();
                }

                /*  Iterate and display solution    */
                Iterator<City> i = solution.iterator();
                City c = i.next();
                while (i.hasNext())
                {
                    System.out.print("\t" + c.get_city() + " to ");
                    c = i.next();
                    System.out.print(c.get_city() + ", " + c.get_distance() + " km");
                    System.out.println();
                }
                return;
            }

            /*  Generating successor to non-visited city    */
            if ( !explored.contains(node.get_city()))
            {
                /*  Add parent to explored list */
                explored.add(node.get_city());

                /*  Displaying all explored nodes   */
                Iterator<String> k = explored.iterator();
                System.out.println("Closed: ");
                System.out.print("\t");
                while (k.hasNext())
                {
                    System.out.print(k.next() + ", ");
                }
                System.out.println();
                
                /*  Generating all successors   */
                Iterator<City> i = list.get(node.get_city()).listIterator();
                while (i.hasNext())
                {
                    City child = i.next();
                    child.set_parent(node);
                    child.set_c_distance(child.get_distance() + node.get_c_distance() + list_h.get(child.get_city()));
                    child.set_depth(node.get_depth() + 1);
                    frontier.add(child);
                    /*  Sort the fringe */
                    Collections.sort(frontier, City.Comparators.dist);
                }              
            }
            else
            {
                System.out.println(node.get_city() + " already closed. No successor");
            }
            System.out.println();
        }
        /*  Displaying Information for failure  */
        System.out.println("Node Expanded: " + node_expanded);
        System.out.println("Distance: infinity");
        System.out.println("Route: None");        
    }  

    /*  Uniform-Cost Search without print details  */
    public static void find_path_uninform(Map<String, LinkedList<City>> list, String start, String goal)
    {
        /*  Variables   */
        int node_expanded = 0;
        Set<String> explored = new HashSet<String>();
        LinkedList<City> frontier = new LinkedList<City>();
        City start_city = new City(start, 0);

        /*  First node into the fringe  */
        frontier.add(start_city);

        /*  Loop going through nodes in frontier    */
        while ( frontier.size() != 0 )
        {
            /*  Pop the first element (also smallest)   */
            City node = frontier.poll();
            node_expanded++;
            
            /*  Goal Condition  */
            if (node.get_city().equals(goal))
            {
                /*  Display Results */
                System.out.println("Node Expanded: " + node_expanded);
                System.out.println("Distance: " + node.get_c_distance() + "km");
                System.out.println("Route: ");
                
                /*  Backtrack adding solution city to a LinkedList  */
                LinkedList<City> solution = new LinkedList<City>();
                while ( !node.get_city().equals("None"))
                {
                    solution.addFirst(node);
                    node = node.get_parent();
                }

                /*  Iterate and display solution    */
                Iterator<City> i = solution.iterator();
                City c = i.next();
                while (i.hasNext())
                {
                    System.out.print("\t" + c.get_city() + " to ");
                    c = i.next();
                    System.out.print(c.get_city() + ", " + c.get_distance() + " km");
                    System.out.println();
                }
                return;
            }

            /*  Generating successor to non-visited city    */
            if ( !explored.contains(node.get_city()))
            {
                /*  Add parent to explored list */
                explored.add(node.get_city());
                
                /*  Generating all successors   */
                Iterator<City> i = list.get(node.get_city()).listIterator();
                while (i.hasNext())
                {
                    City child = i.next();
                    child.set_parent(node);
                    child.set_c_distance(child.get_distance() + node.get_c_distance());
                    child.set_depth(node.get_depth() + 1);
                    frontier.add(child);
                    /*  Sort the fringe */
                    Collections.sort(frontier, City.Comparators.dist);
                }              
            }
        }

        /*  Displaying Information for failure  */
        System.out.println("Node Expanded: " + node_expanded);
        System.out.println("Distance: infinity");
        System.out.println("Route: None");        
        return;
    }   

    /*  A* Search without print details */
    public static void find_path_inform(Map<String, LinkedList<City>> list, String start, String goal, Map<String, Integer> list_h)
    {
        /*  Variables   */
        int node_expanded = 0;
        Set<String> explored = new HashSet<String>();
        LinkedList<City> frontier = new LinkedList<City>();
        City start_city = new City(start, 0);
        start_city.set_c_distance(list_h.get(start_city.get_city()));

        /*  First node into the fringe  */
        frontier.add(start_city);

        /*  Loop going through nodes in frontier    */
        while ( frontier.size() != 0 )
        {
    
            /*  Pop the first element (also smallest)   */
            City node = frontier.poll();
            node.set_c_distance(node.get_c_distance() - list_h.get(node.get_city()));
            node_expanded++;
   
            /*  Goal Condition  */
            if (node.get_city().equals(goal))
            {
                /*  Display Results */
                System.out.println("Node Expanded: " + node_expanded);
                System.out.println("Distance: " + node.get_c_distance() + " km");
                System.out.println("Route: ");
                
                /*  Backtrack adding solution city to a LinkedList  */
                LinkedList<City> solution = new LinkedList<City>();
                while ( !node.get_city().equals("None"))
                {
                    solution.addFirst(node);
                    node = node.get_parent();
                }

                /*  Iterate and display solution    */
                Iterator<City> i = solution.iterator();
                City c = i.next();
                while (i.hasNext())
                {
                    System.out.print("\t" + c.get_city() + " to ");
                    c = i.next();
                    System.out.print(c.get_city() + ", " + c.get_distance() + " km");
                    System.out.println();
                }
                return;
            }

            /*  Generating successor to non-visited city    */
            if ( !explored.contains(node.get_city()))
            {
                /*  Add parent to explored list */
                explored.add(node.get_city());
                
                /*  Generating all successors   */
                Iterator<City> i = list.get(node.get_city()).listIterator();
                while (i.hasNext())
                {
                    City child = i.next();
                    child.set_parent(node);
                    child.set_c_distance(child.get_distance() + node.get_c_distance() + list_h.get(child.get_city()));
                    child.set_depth(node.get_depth() + 1);
                    frontier.add(child);
                    
                    /*  Sort the fringe */
                    Collections.sort(frontier, City.Comparators.dist);
                }              
            }
        }
        /*  Displaying Information for failure  */
        System.out.println("Node Expanded: " + node_expanded);
        System.out.println("Distance: infinity");
        System.out.println("Route: None");        
    }    

    /*  Storing all variables into data structure   */
    public static void data_import( Map<String, LinkedList<City>> list, Scanner input)
    {
        while (input.hasNextLine())
        {
            String line = input.nextLine();
            if (!line.equals("END OF INPUT") && !line.equals(""))
            {
                LinkedList<City> temp_list;

                /*  Separate each input line by space   */
                String[] cat = line.split("\\s+");

                /*  Add path from City 1 to City 2  */
                City temp_city = new City(cat[1], Integer.parseInt(cat[2]));
                if (!list.containsKey(cat[0]))
                {
                    temp_list = new LinkedList<City>();
                }
                else
                {
                    temp_list = list.get(cat[0]);
                }
                temp_list.add(temp_city);
                list.put(cat[0], temp_list); 

                /*  Add path from City 2 to City 1  */
                temp_city = new City(cat[0], Integer.parseInt(cat[2]));
                if (!list.containsKey(cat[1]))
                {
                    temp_list = new LinkedList<City>();
                }
                else
                {
                    temp_list = list.get(cat[1]);
                }
                temp_list.add(temp_city);
                list.put(cat[1], temp_list);                            
            }
        }        
    }

    /*  Storing all heuristic values to Kassel  */
    public static void import_heuristic( Map<String, Integer> heuristic_list, Scanner input )
    {
        while (input.hasNextLine())
        {
            /*  Separate each input line by space   */
            String line = input.nextLine();
            if (!line.equals("END OF INPUT") && !line.equals(""))
            {
                String[] cat = line.split("\\s+");
                heuristic_list.put(cat[0], Integer.parseInt(cat[1]));
            }
        }
    }

    /*  Main Method */
    public static void main (String[] args) throws Exception
    {
        /*  Variables   */
        Map<String, LinkedList<City>> list = new HashMap<String, LinkedList<City>>();
        Scanner input = new Scanner(new File(args[0]));
        /*  Store input data into HashMap in form of adjacency list */
        data_import(list, input);

        /*  Search for path between two cities  */
        if (args.length == 3)
        {
            /*  Execute function for Uniform-Cost Search    */
            find_path_uninform(list, args[1], args[2]);
        }
        else if (args.length == 4 && args[2].equals("Kassel"))
        {
            /*  Variables   */
            Map<String, Integer> list_h = new HashMap<String, Integer>();

            /*  Reassign scanner    */
            input = new Scanner(new File(args[3]));

            /*  Store heuristc data file into HashMap   */
            import_heuristic(list_h, input);

            /*  Execute function for A* Search to Kassel    */
            find_path_inform(list, args[1], args[2], list_h);
        }
        
    }
}