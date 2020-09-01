import org.junit.Assert;
import org.junit.Test;

public class sqlGeneratorTest {
    @Test
    public void testSingleNOT(){//只含有单一的NOT子句
        String str = "!(companyName == 'HTSC')";
        Assert.assertEquals("SELECT * FROM CUSTOMER WHERE NOT (companyName = 'HTSC')",
                sqlGenerator.generateSQL(str));
    }

    @Test
    public void testSingleOR(){//只含有单一的OR子句
        String str = "(companyName == 'HTSC')||(age < 30)";
        Assert.assertEquals("SELECT * FROM CUSTOMER WHERE (companyName = 'HTSC') OR (age < 30)",
                sqlGenerator.generateSQL(str));
    }

    @Test
    public void testSingleAND(){//只含有单一的AND子句
        String str = "(age < 30)&&(gender == 'female')";
        Assert.assertEquals("SELECT * FROM CUSTOMER WHERE (age < 30) AND (gender = 'female')",
                sqlGenerator.generateSQL(str));
    }

    @Test
    public void testAndOr(){//混合And和Or子句，全部为二元逻辑运算符
        String str = "(companyName == 'HTSC')&&((age < 30)||(gender == 'female'))";
        Assert.assertEquals("SELECT * FROM CUSTOMER WHERE (companyName = 'HTSC') AND ((age < 30) OR (gender = 'female'))",
                sqlGenerator.generateSQL(str));
    }

    @Test
    public void testNotAndOr1(){//混合Not和And和Or子句，同时包含二元和一元逻辑运算符,一元嵌套在二元外侧
        String str = "!((companyName == 'HTSC')&&((age < 30)||(gender == 'female')))";
        Assert.assertEquals("SELECT * FROM CUSTOMER WHERE NOT ((companyName = 'HTSC') AND ((age < 30) OR (gender = 'female')))",
                sqlGenerator.generateSQL(str));
    }

    @Test
    public void testNotAndOr2(){//混合Not和And和Or子句，同时包含二元和一元逻辑运算符,一元和二元并列
        String str = "(!(companyName == 'HTSC'))&&((age < 30)||(gender == 'female'))";
        Assert.assertEquals("SELECT * FROM CUSTOMER WHERE (NOT (companyName = 'HTSC')) AND ((age < 30) OR (gender = 'female'))",
                sqlGenerator.generateSQL(str));
    }

    @Test
    public void testSpacesExist(){//输入的逻辑运算符与子句间存在若干空格
        String str = "(companyName == 'HTSC') && ((age < 30) || (gender == 'female'))";
        Assert.assertEquals("SELECT * FROM CUSTOMER WHERE (companyName = 'HTSC') AND ((age < 30) OR (gender = 'female'))",
                sqlGenerator.generateSQL(str));
    }

}
