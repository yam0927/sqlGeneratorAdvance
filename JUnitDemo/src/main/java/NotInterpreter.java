public class NotInterpreter implements Interpreter {
    private String op1;
    public NotInterpreter(String op1){
        this.op1 = op1;
    }
    @Override
    public String interpret() {
        return "NOT " + "(" + op1 + ")";
    }
}
