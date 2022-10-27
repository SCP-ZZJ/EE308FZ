import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lab1_2 {

    public static void main(String [] args) throws Exception {
        Scanner scanner=new Scanner(System.in);
        String code="";
        System.out.println("Enter the file path");  // D:/IDEA_workplace/EE308FZ/src/Lab1/SampleC.c
        String fileName =scanner.nextLine();
        System.out.println("Enter the level number: ");
        int level=scanner.nextInt();
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line=bufferedReader.readLine();
        while(line!=null) {
            code+=line;
            line=bufferedReader.readLine();
        }
        String keywords="abstract、assert、boolean、break、byte、case、"
                + "catch、char、class、continue、default、do、double、else、"
                + "enum、extends、final、finally、float、for、if、implements、"
                + "import、int、interface、instanceof、long、native、new、"
                + "package、private、protected、public、return、short、static、"
                + "strictfp、super、switch、synchronized、this、throw、throws、"
                + "transient、try、void、volatile、while";//all keywords
        String []keyArr=keywords.split("、");

        if (level == 1){
            Level1(keyArr, code);
        }else if (level == 2){
            Level2(keyArr, code);
        }else if (level >= 3){
            Level3(keyArr, code);
        }else {
            System.out.println("invalid level number!");
        }
    }

    //Level1 for all keywords
    public static void Level1(String[] keyArr,String code){

        int total_num = 0;
        for(int i=0; i<keyArr.length; i++) {
            Pattern p=Pattern.compile("[^a-z]"+keyArr[i]+"[^a-z]");
            Matcher matcher=p.matcher(code);
            while(matcher.find()) {
                total_num++;
            }
        }
        System.out.println("total num: "+total_num);
    }

    //check switch-case
    public static void Level2(String[] keyArr, String code){
        Level1(keyArr,code);    // add extra function based on level1

        //check switch
        int switch_num = 0;
        Pattern p=Pattern.compile("switch");
        Matcher matcher=p.matcher(code);
        while(matcher.find()) {
            switch_num++;
        }

        //check case
        p=Pattern.compile("switch.*?}");
        matcher=p.matcher(code);
        List case_num=new ArrayList();
        while(matcher.find()) {
            String tempText=matcher.toString();//get one switch section
            Pattern temp_p=Pattern.compile("case");
            Matcher temp_matcher=temp_p.matcher(tempText);
            int temp_case_num=0;
            while(temp_matcher.find()) {
                temp_case_num++;
            }
            case_num.add(temp_case_num);
        }
        System.out.println("switch num: "+switch_num);
        System.out.print("case num: ");
        for(int i=0;i<case_num.size();i++) {
            System.out.print(case_num.get(i)+" ");
        }
        System.out.println();
    }

    //check if-else / if-else if-else structures
    public static void Level3(String[] keyArr, String code) {
        Level2(keyArr, code);
        Pattern p = Pattern.compile("if|else if|else");
        Matcher matcher = p.matcher(code);
        Stack<String> stack = new Stack();
        int ifElseNum = 0;
        int ifElseIfNum = 0;
        boolean checkElseIf = false;
        while (matcher.find()) {
            String string = code.substring(matcher.start(), matcher.end());
            if (string.equals("if")) {
                stack.push(string);
            }
            else if (string.equals("else if")){
                stack.push(string);
            }
            else{  // string == "else"
                while (!stack.isEmpty()){
                    String s= stack.pop();
                    if(s.equals("else if")){
                        checkElseIf = true;
                    }else{ // s == "if"
                        if(checkElseIf == true){
                            ifElseIfNum++;
                            checkElseIf = false;
                        }else{
                            ifElseNum++;
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("if-else num: " + ifElseNum);
        System.out.println("if-elseif-else num: " + ifElseIfNum);
    }

}
