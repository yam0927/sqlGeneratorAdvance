public class OrInterpreter implements Interpreter {
    private String op1;
    private String op2;
    public OrInterpreter(String op1, String op2){
        this.op1 = op1;
        this.op2 = op2;
    }

    @Override
    public String interpret() {
        return "(" + op1 + ")" + " OR " + "(" + op2 + ")";
    }
}
