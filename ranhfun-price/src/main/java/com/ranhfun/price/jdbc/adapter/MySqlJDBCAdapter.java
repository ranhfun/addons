package com.ranhfun.price.jdbc.adapter;

import java.util.ArrayList;
import java.util.Arrays;

import com.ranhfun.price.jdbc.Statements;

/**
 * 
 * @org.apache.xbean.XBean element="mysql-jdbc-adapter"
 * 
 */
public class MySqlJDBCAdapter extends DefaultJDBCAdapter {

    // The transactional types..
    public static final String INNODB = "INNODB";
    public static final String NDBCLUSTER = "NDBCLUSTER";
    public static final String BDB = "BDB";

    // The non transactional types..
    public static final String MYISAM = "MYISAM";
    public static final String ISAM = "ISAM";
    public static final String MERGE = "MERGE";
    public static final String HEAP = "HEAP";

    String engineType = INNODB;
    String typeStatement = "ENGINE";

    public void setStatements(Statements statements) {
        String type = engineType.toUpperCase();
        
        String typeClause = typeStatement + "=" + type;
        if( type.equals(NDBCLUSTER) ) {
            // in the NDBCLUSTER case we will create as INNODB and then alter to NDBCLUSTER
            typeClause = typeStatement + "=" + INNODB;
        }
        
        // Update the create statements so they use the right type of engine 
        String[] s = statements.getCreateSchemaStatements();
        for (int i = 0; i < s.length; i++) {
            if( s[i].startsWith("CREATE TABLE")) {
                s[i] = s[i]+ " " + typeClause;
            }
        }
        
        if( type.equals(NDBCLUSTER) ) {
            // Add the alter statements.
            ArrayList<String> l = new ArrayList<String>(Arrays.asList(s));
            l.add("ALTER TABLE "+statements.getFullAgentInfoTableName()+" ENGINE="+NDBCLUSTER);
            l.add("ALTER TABLE "+statements.getFullNumberInfoTableName()+" ENGINE="+NDBCLUSTER);
            l.add("FLUSH TABLES");
            s = l.toArray(new String[l.size()]);
            statements.setCreateSchemaStatements(s);
        }        
        
        super.setStatements(statements);
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getTypeStatement() {
        return typeStatement;
    }

    public void setTypeStatement(String typeStatement) {
        this.typeStatement = typeStatement;
    }
}
