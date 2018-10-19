package goliathoufx;

public class VarIsStupid
{
    public VarIsStupid()
    {
        Person person = new Person("VarIs", "Stupid");
        
        System.out.println(person.getFirstName());
        System.out.println(person.getLastName());
    }
    
    private class Person
    {
        private final String firstName;
        private final String lastName;
        
        public Person(String fN, String lN)
        {
            firstName = fN;
            lastName = lN;
        }
        
        public String getFirstName()
        {
            return firstName;
        }
        
        public String getLastName()
        {
            return lastName;
        }
    }
}
