<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:codegen="xalan://weborb.data.management.codegen.XsltExtention"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:import href="mssql/codegen.server.java.data.mssql.xslt"/>
    <xsl:import href="mysql/codegen.server.java.data.mysql.xslt"/>
    <xsl:import href="oracle/codegen.server.java.data.oracle.xslt"/>
    <xsl:import href="postgresql/codegen.server.java.data.postgresql.xslt"/>

    <xsl:template name="codegen.server.java.data.enviroment">
    import weborb.data.management.*;
    import weborb.util.*;

        <xsl:for-each select="/xs:schema/xs:element">
            <xsl:variable name="database" select="codegen:getDatabaseServerType(@name)"/>
            <xsl:value-of select="codegen:setCurrentDatabase(@name)"/>

            <xsl:value-of select="codegen:Progress(concat('Database is ', $database))"/>
            <xsl:choose>
                <xsl:when test="$database = 'mssql'">
                    <xsl:call-template name="codegen.server.java.data.mssql.enviroment"/>
                </xsl:when>
                <xsl:when test="$database = 'mysql'">
                    <xsl:call-template name="codegen.server.java.data.mysql.enviroment"/>
                </xsl:when>
                <xsl:when test="$database = 'oracle'">
                    <xsl:call-template name="codegen.server.java.data.oracle.enviroment"/>
                </xsl:when>
                <xsl:when test="$database = 'postgresql'">
                    <xsl:call-template name="codegen.server.java.data.postgresql.enviroment"/>
                </xsl:when>
            </xsl:choose>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="codegen.server.java.data">
        <xsl:value-of select="codegen:Progress(concat('Generating class ', @name,'DataMapper'))"/>
        <xsl:variable name="database" select="codegen:getDatabaseServerType(current()/../../../@name)"/>
        <xsl:choose>
            <xsl:when test="$database = 'mssql'">
                <xsl:call-template name="codegen.server.java.data.mssql"/>
            </xsl:when>
            <xsl:when test="$database = 'mysql'">
                <xsl:call-template name="codegen.server.java.data.mysql"/>
            </xsl:when>
            <xsl:when test="$database = 'oracle'">
                <xsl:call-template name="codegen.server.java.data.oracle"/>
            </xsl:when>
            <xsl:when test="$database = 'postgresql'">
                <xsl:call-template name="codegen.server.java.data.postgresql"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="codegen.server.java.data.database">
        <xsl:variable name="database" select="codegen:getDatabaseServerType(/xs:schema/xs:element/@name)"/>
        <xsl:value-of select="codegen:setCurrentDatabase(/xs:schema/xs:element/@name)"/>

        <xsl:choose>
            <xsl:when test="$database = 'mssql'">
                <xsl:call-template name="codegen.server.java.data.mssql.database"/>
            </xsl:when>
            <xsl:when test="$database = 'mysql'">
                <xsl:call-template name="codegen.server.java.data.mysql.database"/>
            </xsl:when>
            <xsl:when test="$database = 'oracle'">
                <xsl:call-template name="codegen.server.java.data.oracle.database"/>
            </xsl:when>
            <xsl:when test="$database = 'postgresql'">
                <xsl:call-template name="codegen.server.java.data.postgresql.database"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>