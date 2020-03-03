package epam.pratsaunik.tickets.hash;

import com.mysql.cj.x.protobuf.MysqlxExpr;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PasswordHashTest {
    private String stringA;
    private String stringB;

    public PasswordHashTest(String stringA, String stringB) {
        this.stringA = stringA;
        this.stringB = stringB;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> values() {
        return Arrays.asList(new Object[][]{
                {"11", "1 1"},
                {"B", "BB"},
                {"A", "AA"},
                {"", " "},
                {"0","O"}
        });
    }

    @Test
    public void getHashTest(){
        String sA=this.stringA;
        String sB=this.stringB;
        assertFalse(PasswordHash.getHash(sA).equals(PasswordHash.getHash(sB)));
    }

    @Test
    public void getHashTest2(){
        String sA=this.stringA;
        String sB= new String(stringA);
        assertTrue(PasswordHash.getHash(sA).equals(PasswordHash.getHash(sB)));
    }



}