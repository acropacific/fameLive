<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
                  xmlns:wdm="urn:schemas-themidnightcoders-com:xml-wdm"
                  xmlns:xs="http://www.w3.org/2001/XMLSchema" 
                  xmlns:codegen="xalan://weborb.data.management.codegen.XsltExtention"
                  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   
  <xsl:template name="codegen.server.java.data.oracle.remove">
    <xsl:variable name="table" select="@name" />
    <xsl:variable name="schema" select="@wdm:Schema" />
    <xsl:variable name="serverNS" select="codegen:getServerNamespace($schema)" />
    <xsl:variable name="class-name" select="concat($serverNS, '.', codegen:getClassName($table,$schema))" />
    <xsl:variable name="functionParam" select="codegen:FunctionParameter(@name)" />

    private final String SqlDelete = "Delete From \"<xsl:value-of select="$schema"/>\".\"<xsl:value-of select="$table" />\" "
    <xsl:if test="count(xs:key) != 0">
     + " Where "
      <xsl:for-each select="xs:complexType/xs:attribute[concat('@',@name) = key('pk',$table)/@xpath]">
      + "  \"<xsl:value-of select="@name" />\" = ? <xsl:if test="position() != last()"> and </xsl:if> "
      </xsl:for-each>
    </xsl:if>;

    public DomainObject remove(<xsl:value-of select="$class-name" /><xsl:text> </xsl:text><xsl:value-of select="$functionParam" />, boolean cascade) throws Exception 
    {
      SynchronizationScope syncScope = new SynchronizationScope( getDatabase() );
      DatabaseConnectionMonitor monitor = null;      

      try
      {
            monitor = new DatabaseConnectionMonitor( getDatabase() );     
            PreparedStatement sqlCommand = getDatabase().createCommand( SqlDelete );      

          <xsl:for-each select="xs:complexType/xs:attribute[concat('@',@name) = key('pk',$table)/@xpath]">
            <xsl:variable name="property" select="codegen:getProperty($table,$schema,@name)" />
            sqlCommand.setObject( <xsl:value-of select="position()"/>, <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property" />() );
          </xsl:for-each>
            sqlCommand.execute();
            monitor.commitTransaction();
      }
      catch(Exception e)
      {
        if( monitor != null )     
          monitor.rollbackTransaction();
          
        throw e;
      }   
      finally
      {
        if( monitor != null )
          monitor.close();
      }

      raiseAffected( <xsl:value-of select="$functionParam" />, IDataMapper.delete );
      syncScope.Invoke();
      return (DomainObject)registerRecord( <xsl:value-of select="$functionParam" /> );
    }
    
    public DomainObject remove(DomainObject arg) throws Exception
    {
      return remove((<xsl:value-of select="$class-name" />)arg,true);
    }

  </xsl:template>
  
</xsl:stylesheet>