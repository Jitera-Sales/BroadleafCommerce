/*-
 * #%L
 * BroadleafCommerce Common Libraries
 * %%
 * Copyright (C) 2009 - 2024 Broadleaf Commerce
 * %%
 * Licensed under the Broadleaf Fair Use License Agreement, Version 1.0
 * (the "Fair Use License" located  at http://license.broadleafcommerce.org/fair_use_license-1.0.txt)
 * unless the restrictions on use therein are violated and require payment to Broadleaf in which case
 * the Broadleaf End User License Agreement (EULA), Version 1.1
 * (the "Commercial License" located at http://license.broadleafcommerce.org/commercial_license-1.1.txt)
 * shall apply.
 *
 * Alternatively, the Commercial License may be replaced with a mutually agreed upon license (the "Custom License")
 * between you and Broadleaf Commerce. You may not use this file except in compliance with the applicable license.
 * #L%
 */
package org.broadleafcommerce.common.util.sql.importsql;

import org.broadleafcommerce.common.logging.SupportLogManager;
import org.broadleafcommerce.common.logging.SupportLogger;
import org.hibernate.dialect.Dialect;
import org.hibernate.tool.schema.internal.script.SingleLineSqlScriptExtractor;

import java.io.Reader;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a utility class that is only meant to be used for testing the BLC demo on SQL Server. In our current
 * import sql files, there are a number of value declarations that are incompatible with Sql Server. This
 * custom extractor takes care of transforming those values into something SQL Server understands.
 *
 * @author Jeff Fischer
 */
public class DemoSqlServerSingleLineSqlCommandExtractor extends SingleLineSqlScriptExtractor {

    public static final String DOUBLEBACKSLASHMATCH = "(\\\\\\\\)";
    public static final String TRUE = "'TRUE'";
    public static final String FALSE = "'FALSE'";
    public static final String CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP";
    @Serial
    private static final long serialVersionUID = 1L;
    private static final SupportLogger LOGGER = SupportLogManager.getLogger(
            "UserOverride", DemoSqlServerSingleLineSqlCommandExtractor.class
    );
    private static final String BOOLEANTRUEMATCH = "(?i)(true)(?=(?:[^']|'[^']*')*$)";
    private static final String BOOLEANFALSEMATCH = "(?i)(false)(?=(?:[^']|'[^']*')*$)";
    private static final String TIMESTAMPMATCH = "(?i)(current_date)";
    protected boolean alreadyRun = false;

    @Override
    public List<String> extractCommands(Reader reader, Dialect dialect) {
        if (!alreadyRun) {
            alreadyRun = true;
            LOGGER.support("Converting hibernate.hbm2ddl.import_files sql statements for compatibility with SQL Server");
        }

        List<String> statements = super.extractCommands(reader, dialect);
        return handleReplacements(statements);
    }

    protected List<String> handleReplacements(List<String> statements) {
        List<String> result = new ArrayList<>(statements.size());
        for (String statement : statements) {
            String fixed = replaceBoolean(statement);
            // Replace newline characters
            result.add(fixed.replaceAll(
                    DemoPostgresSingleLineSqlCommandExtractor.NEWLINE_REPLACEMENT_REGEX,
                    "' + CHAR(13) + CHAR(10) + '"
            ));
        }
        return result;
    }

    protected String replaceBoolean(String statement) {
        //try start matches
        statement = statement.replaceAll(BOOLEANTRUEMATCH + "\\s*[,]", TRUE + ",");
        statement = statement.replaceAll(BOOLEANFALSEMATCH + "\\s*[,]", FALSE + ",");
        statement = statement.replaceAll(TIMESTAMPMATCH + "\\s*[,]", CURRENT_TIMESTAMP + ",");

        //try middle matches
        statement = statement.replaceAll("[,]\\s*" + BOOLEANTRUEMATCH + "\\s*[,]", "," + TRUE + ",");
        statement = statement.replaceAll("[,]\\s*" + BOOLEANFALSEMATCH + "\\s*[,]", "," + FALSE + ",");
        statement = statement.replaceAll("[,]\\s*" + TIMESTAMPMATCH + "\\s*[,]", "," + CURRENT_TIMESTAMP + ",");

        //try end matches
        statement = statement.replaceAll("[,]\\s*" + BOOLEANTRUEMATCH, "," + TRUE);
        statement = statement.replaceAll("[,]\\s*" + BOOLEANFALSEMATCH, "," + FALSE);
        statement = statement.replaceAll("[,]\\s*" + TIMESTAMPMATCH, "," + CURRENT_TIMESTAMP);

        //try matches for updates
        statement = statement.replaceAll("[=]\\s*" + BOOLEANTRUEMATCH, "=" + TRUE);
        statement = statement.replaceAll("[=]\\s*" + BOOLEANFALSEMATCH, "=" + FALSE);

        //remove the double backslashes - sql server does not honor backslash as an escape character
        statement = statement.replaceAll(DOUBLEBACKSLASHMATCH, "\\\\");

        return statement;
    }

}
