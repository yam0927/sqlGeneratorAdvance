public class AndInterpreter implements Interpreter {
    private String op1;
    private String op2;
    public AndInterpreter(String op1, String op2){
        this.op1 = op1;
        this.op2 = op2;
    }

    @Override
    public String interpret() {
        return "(" + op1 + ")" + " AND " + "(" + op2 + ")";
    }
}
