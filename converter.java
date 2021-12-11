import java.util.*;

public class Main {
    static Map<String,List<String>> produrules;
    static List<String>[] gnf;
    static Map<String,String> newVariable;
    static int variables;

    Main(){
        gnf = new ArrayList[50];
        newVariable = new HashMap<>();
        produrules = new HashMap<>();

        produrules.put("A",new ArrayList<>(Arrays.asList("A+B","B")));
        produrules.put("B",new ArrayList<>(Arrays.asList("BS","S")));
        produrules.put("S",new ArrayList<>(Arrays.asList("(A)","a","b","c")));
    }
   public static void replaceVariables(){
       int i=1;
       for (Map.Entry mapElement : produrules.entrySet()) {
           String key = (String) mapElement.getKey();

           if (!newVariable.containsKey(key)) {
               newVariable.put(key, Integer.toString(i));
               gnf[i] = new ArrayList<>();
               i++;

           }

           List<String> ls = (List<String>) mapElement.getValue();
           System.out.println(key + " : " + ls);

           for (int j = 0; j < ls.size(); j++) {

               String s = ls.get(j);
               String val = "";
               for (int l = 0; l < s.length(); l++) {

                   if (Character.isUpperCase(s.charAt(l))) {
                       if (!newVariable.containsKey(String.valueOf(s.charAt(l)))) {
                           newVariable.put(String.valueOf(s.charAt(l)), Integer.toString(i));
                           gnf[i] = new ArrayList<>();
                           i++;
                       }
                       val = val + newVariable.get(String.valueOf(s.charAt(l)));
                       continue;
                   }
                   val = val+String.valueOf(s.charAt(l));

               }
               gnf[Integer.parseInt(newVariable.get(key))].add(val);
           }
       }
       for (Map.Entry mapElement : newVariable.entrySet()) {
           String key = (String)mapElement.getKey();
           String value = (String)mapElement.getValue();


           System.out.println(key + " : " + value);
       }
           variables =i;
       }

       public static void removeTerminal(){
        HashMap<Character,Character> seen = new HashMap<>();

        int len = variables;
        for(int i=1; i<len; i++){

            for(int j=0; j<gnf[i].size();j++){

                String s = gnf[i].get(j);
                if(s.length()>1){

            for(int l=0; l<s.length(); l++) {

                if(!Character.isDigit(s.charAt(l))){

                    if(seen.containsKey(s.charAt(l))){  //aaB

                        char value = seen.get(s.charAt(l));
                        s = s.replace(s.charAt(l),value);



                    }
                    else{
                    int index = variables;

                    gnf[index] = new ArrayList<>(Arrays.asList(String.valueOf(s.charAt(l))));
                            s = s.replace(s.charAt(l),(char)(index+'0'));

                      variables++;}
                            gnf[i].set(j,s);

                }

            }}
            }
        }
       }

       public static void _2NonTerminals(){

           int len = variables;
           for(int i=1; i<len; i++){

               for(int j=0; j<gnf[i].size();j++){

                   String s = gnf[i].get(j);
                   while(s.length()>2){


                           String newString = s.substring(s.length()-2);
                           int index = variables;
                           s = s.substring(0,s.length()-2)+String.valueOf(index);

                           gnf[index] = new ArrayList<>(Arrays.asList(newString));
                           gnf[i].set(j,s);
                       variables++;
                       }
                   }
               }

           for(int i=variables-1; i>0;i--){

               for(int j=0; j<gnf[i].size();j++){

                   String s = gnf[i].get(j);
                   if(s.length()==1 && Character.isDigit(s.charAt(0)) ){

                               gnf[i].remove(j);
                       gnf[i].addAll(gnf[Integer.parseInt(s)]);



                   }
               }




           }
           }




   public static void GNF(){
     int i=1;

      do{
          System.out.println("*******"+i+"********");
          inAscending(i);
          System.out.println("Ascending "+gnf[i]);
         isleftRecursion( i, gnf[i]);
          System.out.println("Left Recursion "+gnf[i]);

      i++;
     }while(i<variables);
     
     System.out.println();
     System.out.println();
     System.out.println();
     System.out.println("Array list after Step 3:");
     System.out.println();
     
     gnfprint();


      gnffinal();



     }

     public static void inAscending(int aindex ) {


         for(int i=0; i<gnf[aindex].size(); i++) {

             if(Character.isDigit(gnf[aindex].get(i).charAt(0)) && aindex > Character.getNumericValue(gnf[aindex].get(i).charAt(0))) {

                 String old = gnf[aindex].get(i).substring(1);

                 int index = Character.getNumericValue(gnf[aindex].get(i).charAt(0));
                 gnf[aindex].remove(i);

                     String[] newstates = new String[gnf[index].size()];


                     for (int j = 0; j < gnf[index].size(); j++) {

                         newstates[j] =gnf[index].get(j)+old;

                     }
                     List<String> list = Arrays.asList(newstates);
                     gnf[aindex].addAll(list);

                    i--;


             }

         }

     }

     public static void isleftRecursion(int x, List<String> list) {


        for(int i=0; i<list.size(); i++) {

            if (Character.isDigit(list.get(i).charAt(0)) && x == Character.getNumericValue(list.get(i).charAt(0))) {

                String alpha = list.get(i).substring(1);
                list.remove(i);
                removerecursion(x,alpha);
            }


        }

     }

     public static void removerecursion(int aindex,String alpha) {

         gnf[variables] = new ArrayList<>();



         gnf[variables].add(alpha);
         gnf[variables].add(alpha+variables);

         variables++;


           String[] newstates = new String[gnf[aindex].size()];
            String newstate = Integer.toString(variables-1);

         for(int i=0; i<gnf[aindex].size(); i++){

             newstates[i] =gnf[aindex].get(i)+newstate;

         }

         List<String> list = Arrays.asList(newstates);
         gnf[aindex].addAll(list);




     }


     public static void gnfprint(){
         
        for(int i=1; i<variables; i++){

            System.out.println("A"+i+" -> "+gnf[i]);
        }




     }


     public static void gnffinal()
     {
         for( int i = 10; i>0; i--)
        {
            ArrayList<String> array1 = new ArrayList<String>();
            int len = gnf[i].size();
            for(int j=0; j<len;j++)
            {
                String x = gnf[i].get(j);
                char ch = x.charAt(0);
                

                if(ch=='a' ||ch=='b' ||ch=='c' ||ch=='(' ||ch==')' ||ch=='+' )
                {
                    array1.add(x);
                    continue;
                }

                else
                {
                    String y = x.substring(1);
                    int temp = Character.getNumericValue(ch);

                    for(int d=0; d<gnf[temp].size(); d++)
                    {
                        String subpart = gnf[temp].get(d);
                        char ch1 = subpart.charAt(0);

                        if(ch1=='a' ||ch1=='b' ||ch1=='c' ||ch1=='(' ||ch1==')' ||ch1=='+' )
                        {
                            array1.add(subpart+y);
                        }
                    }

                    
                }
                
            }
            gnf[i] = array1;
        }
         
         System.out.println();
     System.out.println();
     System.out.println();
     System.out.println("Array list after Step 4:");
     System.out.println();
        gnfprint();
     }


    public static void main(String[] args){

        Main obj = new Main();

        obj.replaceVariables();
        obj.gnfprint();
        obj.removeTerminal();
        obj.gnfprint();
        obj._2NonTerminals();
        obj.GNF();
        



    }


}
