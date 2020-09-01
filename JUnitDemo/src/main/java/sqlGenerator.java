import java.util.Stack;

public class sqlGenerator {
    public static String generateSQL(String str){
        String result = "";
        str.trim();
        String _str = "(" + str + ")";//添加首尾括号用于解析

        Stack<String> sqlContents = new Stack<String>();
        Stack<String> logicOperators = new Stack<String>(); //(1)! (2)&& (3)||
        Stack<Character> bracelets = new Stack<Character>();

        boolean isInnerBracelet = false;
        for(int i = 0; i < _str.length(); ){
            if(_str.charAt(i) == '('){
                bracelets.push('(');
                i++;
            }
            else if(_str.charAt(i) == '!'){
                logicOperators.push("NOT");
                i++;
                while(i < _str.length() && _str.charAt(i) == ' ') i++;
            }
            else if(_str.charAt(i) == '|'){
                logicOperators.push("OR");
                i += 2;
                while(i < _str.length() && _str.charAt(i) == ' ') i++;
            }
            else if(_str.charAt(i) == '&'){
                logicOperators.push("AND");
                i += 2;
                while(i < _str.length() && _str.charAt(i) == ' ') i++;
            }
            else if(_str.charAt(i) != ')'){
                String content = "";
                while(_str.charAt(i) != ')'){
                    content += _str.charAt(i);
                    i++;
                }
                sqlContents.push(content);
                isInnerBracelet = true;
            }
            else{ //_str.charAt(i) == ')'
                if(isInnerBracelet){
                    bracelets.pop();
                    isInnerBracelet = false;
                }
                else{ //不是最内层括号
                    if(logicOperators.empty())
                        break;
                    String op = logicOperators.peek();
                    if(op.equals("NOT")){ //一元运算符
                        String op1 = sqlContents.peek();
                        Interpreter sql = new NotInterpreter(op1);
                        sqlContents.pop();
                        sqlContents.push(sql.interpret());
                    }

                    else{
                        String op2 = sqlContents.peek();
                        sqlContents.pop();
                        String op1 = sqlContents.peek();
                        sqlContents.pop();
                        Interpreter sql;
                        if(op.equals("OR")){//"OR"
                            sql = new OrInterpreter(op1, op2);
                        }
                        else{//"AND"
                            sql = new AndInterpreter(op1, op2);
                        }
                        sqlContents.push(sql.interpret());
                    }
                    logicOperators.pop();
                }
                i++;
                while(i < _str.length() && _str.charAt(i) == ' ') i++;
            }
        }
        result = "SELECT * FROM CUSTOMER WHERE " + sqlContents.peek();
        result = result.replace("==", "=");
        System.out.println(result);
        return result;
    }
}
