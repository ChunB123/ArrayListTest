package studentControlSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainPage {
    private ArrayList<Student> arrayStudent;
    private Scanner scanner;
    private BufferedWriter bw;
    private BufferedReader br;
    //主页面屏幕显示
    public MainPage() throws IOException {
        arrayStudent=new ArrayList<>();
        scanner=new Scanner(System.in);
        br=new BufferedReader(new FileReader("information.txt"));

        Read();
    }


    //读取功能输入
    public void Page() throws IOException {

        while(true) {
            System.out.println("---------欢迎来到学生管理系统---------");
            System.out.println("1 查看所有学生");
            System.out.println("2 添加学生");
            System.out.println("3 删除学生");
            System.out.println("4 修改学生");
            System.out.println("5 退出");
            System.out.println("请开始你的表演:");
            //输入的序号
            String input = scanner.next();
            //合理性校验
            if (Check(input)){
                continue;
            }
            //String转化为整形
            switch (Integer.parseInt(input)){
                case 1:
                    getAllStudent();
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    setStudent();
                    break;
                case 5:
                    Write();
                    System.exit(0);
                    default:
            }
        }
    }
    //读取文件中的数据到集合中
    private void Read() throws IOException {
        try {
            String input;
            while ((input=br.readLine())!=null){
                arrayStudent.add(Parse(input));
            }
        } catch (FileNotFoundException e) {
            Page();
        }
    }
    //解析数据为Student对象
    private Student Parse(String data){
        String name=(data.split(","))[0];
        String age=(data.split(","))[1];
        Student s=new Student(name,Integer.parseInt(age));
        return s;
    }

    //保存集合中的数据
    private void Write() throws IOException {
        bw=new BufferedWriter(new FileWriter("information.txt"));
        for (int i=0;i<arrayStudent.size();i++) {
            bw.write(arrayStudent.get(i).getName()+","+arrayStudent.get(i).getAge());
            bw.newLine();
        }
        bw.close();
    }
    //菜单合理性校验
    private boolean Check(String input){
        //使用正则表达式只允许1-5
        Pattern p=Pattern.compile("[12345]");
        Matcher m=p.matcher(input);

        if(m.matches()) {
            //集合为空,不可执行3,4操作
            while(arrayStudent.size()==0){
                int index=Integer.parseInt(input);
                if(index==3||index==4){
                    System.out.println("草泥马");
                    return true;
                }else{
                    return false;
                }
            }
                return false;
            }

            System.out.println("草泥马");

        return true;
    }
    //年龄合理性校验
    private int CheckAge(){
        String age;
        Pattern p=Pattern.compile("\\d{1,2}");
        while(true){
            System.out.println("请输入学生的年龄:");
            age=scanner.next();
            Matcher m=p.matcher(age);
            if(m.matches()){
                break;
            }
            System.out.println("草泥马");
        }
        return Integer.parseInt(age);
    }

    //索引合理性校验
    private int CheckIndex(){
        int arrayIndex=arrayStudent.size();
        int inputInt;

        //检验是否是整数
        Pattern p1=Pattern.compile("\\D+");
        while(true){
            System.out.println("请输入学生的索引");
            String input=scanner.next();
            Matcher m=p1.matcher(input);
            //含有非数字,continue
            if(m.matches()){
                System.out.println("草泥马");
                continue;
            }
            inputInt=Integer.parseInt(input);
            if(inputInt<0||inputInt>arrayIndex-1){
                System.out.println("草泥马");
                continue;
            }
            break;
        }
        return inputInt;
    }
    //返回主菜单
    public void Back() throws IOException {
        while(true) {
            System.out.println("按1-5返回主菜单");
            String input=scanner.next();
            if(Check(input)){
                continue;
            }
            Page();
        }
    }
    //查看所有学生
    public void getAllStudent() throws IOException {
        String name;
        int age;
        for(int i=0;i<arrayStudent.size();i++){
            name=arrayStudent.get(i).getName();
            age=arrayStudent.get(i).getAge();
            System.out.println("学生"+i+"的名字是"+name+",年龄是"+age);
        }
        Back();
    }

    //添加学生
    public void addStudent() throws IOException {
            System.out.println("请输入学生的名字:");
            String name = scanner.next();
            int age=CheckAge();
            arrayStudent.add(new Student(name, age));
            Back();

    }

    //删除学生
    public void deleteStudent() throws IOException {
        int delete=CheckIndex();
        String deleteName=arrayStudent.remove(delete).getName();
        System.out.println("您删除的是学生"+deleteName);
        Back();
    }

    //修改学生
    public void setStudent() throws IOException {
        int set=CheckIndex();
        System.out.println("请输入学生的名字:");
        String name=scanner.next();
        int age=CheckAge();
        arrayStudent.set(set,new Student(name,age));
        Back();
    }
}
