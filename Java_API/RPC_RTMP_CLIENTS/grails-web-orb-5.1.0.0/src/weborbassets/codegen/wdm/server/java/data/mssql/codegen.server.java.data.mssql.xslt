<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
                  xmlns:xs="http://www.w3.org/2001/XMLSchema" 
                  xmlns:codegen="xalan://weborb.data.management.codegen.XsltExtention"
                  xmlns:wdm="urn:schemas-themidnightcoders-com:xml-wdm"
                  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  
  <xsl:import href="codegen.server.java.data.mssql.create.xslt"/>
  <xsl:import href="codegen.server.java.data.mssql.update.xslt"/>
  <xsl:import href="codegen.server.java.data.mssql.remove.xslt"/>
  <xsl:import href="codegen.server.java.data.mssql.findAll.xslt"/>
  <xsl:import href="codegen.server.java.data.mssql.load.xslt"/>
  <xsl:import href="codegen.server.java.data.mssql.save.xslt"/>
  <xsl:import href="codegen.server.java.data.mssql.findByPrimaryKey.xslt"/>
  
  <xsl:template name="codegen.server.java.data.mssql.enviroment">
    <xsl:if test="codegen:singleCall('codegen.server.java.data.mssql.enviroment')"></xsl:if>
    import java.util.Hashtable;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.ArrayList;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
  </xsl:template>

  <xsl:template name="codegen.server.java.data.mssql.database">
    import java.sql.ResultSetMetaData;
    <xsl:if test="codegen:CountSchemas(codegen:getCurrentDatabase()) != '1'">
        <xsl:for-each select="/xs:schema/xs:element[@name = codegen:getCurrentDatabase()]">
    import <xsl:value-of select="codegen:getServerNamespace(@wdm:Schema)"/>.*;
        </xsl:for-each>
    </xsl:if>
    
    public class <xsl:value-of select="codegen:FirstLetterToUpper(/xs:schema/xs:element/@name)" />Db extends Database
    {
      public <xsl:value-of select="codegen:FirstLetterToUpper(/xs:schema/xs:element/@name)" />Db() throws Exception
      {
        InitConnectionString( "<xsl:value-of select="/xs:schema/xs:element/@name"/>" );
      }
    <xsl:for-each select="xs:complexType/xs:choice/xs:element[@wdm:DatabaseObjectType='table']">
        <xsl:variable name="class-name" select="codegen:getClassName(@name, @wdm:Schema)" />
        <xsl:variable name="functionParam" select="codegen:FunctionParameter(@name)" />
      public <xsl:value-of select="$class-name"/> create(<xsl:value-of select="$class-name"/><xsl:text> </xsl:text><xsl:value-of select="$functionParam"/>) throws Exception
      {
        <xsl:value-of select="$class-name"/>DataMapper dataMapper = new <xsl:value-of select="$class-name"/>DataMapper((Database)this);
          
        return (<xsl:value-of select="$class-name"/>)dataMapper.create(<xsl:value-of select="$functionParam"/>);
      }

      public <xsl:value-of select="$class-name"/> update(<xsl:value-of select="$class-name"/><xsl:text> </xsl:text><xsl:value-of select="$functionParam"/>) throws Exception
      {
        <xsl:value-of select="$class-name"/>DataMapper dataMapper = new <xsl:value-of select="$class-name"/>DataMapper((Database)this);

        return (<xsl:value-of select="$class-name"/>)dataMapper.update(<xsl:value-of select="$functionParam"/>);
      }

      public <xsl:value-of select="$class-name"/> remove(<xsl:value-of select="$class-name"/><xsl:text> </xsl:text><xsl:value-of select="$functionParam"/>, boolean cascade) throws Exception
      {
        <xsl:value-of select="$class-name"/>DataMapper dataMapper = new <xsl:value-of select="$class-name"/>DataMapper((Database)this);

        return (<xsl:value-of select="$class-name"/>)dataMapper.remove(<xsl:value-of select="$functionParam"/>, cascade);
      }
      </xsl:for-each>

      <xsl:for-each select="xs:complexType/xs:choice/xs:element[@wdm:DatabaseObjectType='storedprocedure']">
      public ArrayList <xsl:value-of select="codegen:getStoredProcedureName(@name, @wdm:Schema)"/>(
        <xsl:for-each select="xs:complexType/xs:attribute">
        <xsl:value-of select="codegen:javaDataType(@type)" /> <xsl:text> </xsl:text> <xsl:value-of select="codegen:getFunctionParameter(@name)" /><xsl:if test="position() != last()">, </xsl:if> <xsl:text>
        </xsl:text>
      </xsl:for-each>) throws Exception
      {
        DatabaseConnectionMonitor databaseConnectionMonitor = null; 
        ArrayList array = new ArrayList();           

        try 
        {        
          databaseConnectionMonitor = new DatabaseConnectionMonitor( this );                 
          PreparedStatement statement = this.getConnection().prepareStatement( "EXEC <xsl:value-of select="@name" /> " <xsl:for-each select="xs:complexType/xs:attribute">
                +  " @<xsl:value-of select="@name"/> = ? <xsl:if test="position() != last()">,</xsl:if> " </xsl:for-each> );
      <xsl:for-each select="xs:complexType/xs:attribute">
          statement.setObject( <xsl:value-of select="position()"/>, <xsl:value-of select="codegen:getFunctionParameter(@name)" /> ); </xsl:for-each>        
          ResultSet result = statement.executeQuery();          
          ResultSetMetaData meta = result.getMetaData();
            
          while( result.next() )
          {
            HashMap hashtable = new HashMap();
                
            for( int i = 0; i &gt; meta.getColumnCount(); i++ )
              hashtable.put( meta.getColumnLabel( i + 1 ), result.getString( i + 1 ) );
                    
            array.add( hashtable );
          } 
                
          result.close();
          statement.close();                
          databaseConnectionMonitor.commitTransaction();
          
          return array;                
        }
        catch( Exception e )
        {
          databaseConnectionMonitor.rollbackTransaction();
              
          throw e;
        }
        finally
        {
          databaseConnectionMonitor.close();
        }            
      }
      </xsl:for-each>
    }
  </xsl:template>
  
  <xsl:template name="codegen.server.java.data.mssql">
    <xsl:variable name="class-name" select="codegen:getClassName(@name, @wdm:Schema)"   />
    <xsl:variable name="table" select="@name"   />
    <xsl:variable name="functionParam" select="codegen:FunctionParameter(@name)" />
    <xsl:variable name="identity" select="boolean(xs:complexType/xs:attribute[@default='identity'])" />
    <xsl:variable name="db-class" select="codegen:getClassName(concat(../../../@name,'Db'), ../../../@wdm:Schema)" />
    import weborb.data.management.mssql.MSSQLCommandBuilder;
    import com.microsoft.sqlserver.jdbc.SQLServerResultSet;
<xsl:for-each select="xs:complexType/xs:attribute">
      <xsl:if test="codegen:javaDataType(@type) = 'BigDecimal'"> 
    import java.math.BigDecimal;</xsl:if>
    </xsl:for-each>

    public abstract class _<xsl:value-of select="$class-name"/>DataMapper extends DataMapper
    {
      public _<xsl:value-of select="$class-name"/>DataMapper() throws Exception 
      {
          super(  new <xsl:value-of select="codegen:getCurrentDatabase()" />Db() );      
      }

      public _<xsl:value-of select="$class-name"/>DataMapper( Database database )
      { 
          super( database ); 
      }

      public Class getDomainObjectClass()
      {
        return <xsl:value-of select="$class-name"/>.class;
      }

      public String getTableName()
      {
          return "[<xsl:value-of select="@wdm:Schema" />].[<xsl:value-of select="$table"/>]";
      }

      public String getSafeName( String name )
      {
          return "[" + name + "]";
      } 

      public Hashtable getRelation( String tableName ) throws Exception
      { 
          throw new Exception( "Not yet implemented" );
      }

      public ICommandBuilder getCommandBuilder()
      {
          return new MSSQLCommandBuilder();
      }
      
      <xsl:call-template name="codegen.server.java.data.mssql.create" />
      <xsl:call-template name="codegen.server.java.data.mssql.findByPrimaryKey" />
      <xsl:call-template name="codegen.server.java.data.mssql.load" />
      <xsl:call-template name="codegen.server.java.data.mssql.remove" />
      <xsl:call-template name="codegen.server.java.data.mssql.save" />
      <xsl:call-template name="codegen.server.java.data.mssql.update" />        
    }
    
  </xsl:template>  
</xsl:stylesheet>