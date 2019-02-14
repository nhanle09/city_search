import java.util.Comparator;
import java.util.LinkedList;

class City
{
    /*  Variables    */
    private String name = "None";
    private int distance = 0;
    private City parent = null;
    private int depth = 0;
    private int c_distance = 0;


    /*  Constructor */
    City(String city, int d)
    {
        name = city;
        distance = d;
        parent = new City();
    }
    City() {}

    /*  Getters */
    String get_city()
    {
        return name;
    }
    int get_distance()
    {
        return distance;
    }
    City get_parent()
    {
        return parent;
    }
    int get_depth()
    {
        return depth;
    }
    int get_c_distance()
    {
        return c_distance;
    }

    /*  Setters */
    void set_parent( City _parent )
    {
        parent = _parent;
    }
    void set_depth( int _depth )
    {
        depth = _depth;
    }
    void set_c_distance(int c)
    {
        c_distance = c;
    }

    /*  Compare method  */
    
    //@Override
    public int compareTo(City city_compare)
    {
        return Comparators.dist.compare(this, city_compare);
    }

    static class Comparators
    {
        static Comparator<City> dist = new Comparator<City>()
        {
            @Override
            public int compare (City c1, City c2)
            {
                return c1.get_c_distance() - c2.get_c_distance();
            }
        };
    }
    
}