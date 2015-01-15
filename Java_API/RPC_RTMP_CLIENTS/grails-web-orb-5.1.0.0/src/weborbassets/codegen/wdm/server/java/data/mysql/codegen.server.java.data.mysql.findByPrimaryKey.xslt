<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
                  xmlns:wdm="urn:schemas-themidnightcoders-com:xml-wdm"
                  xmlns:xs="http://www.w3.org/2001/XMLSchema"
                  xmlns:codegen="xalan://weborb.data.management.codegen.XsltExtention"
                  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template name="codegen.server.java.data.mysql.findByPrimaryKey">
    <xsl:variable name="class-name" select="codegen:getClassName(@name,@wdm:Schema)"   />
    <xsl:variable name="table" select="@name"   />
    <xsl:variable name="schema" select="@wdm:Schema" />
    <xsl:variable name="functionParam" select="codegen:FunctionParameter(@name)" />

    private final String SqlSelectByPk = "Select"
    <xsl:for-each select="xs:complexType/xs:attribute">
     + " `<xsl:value-of select="@name" />` <xsl:if test="position() != last()">,</xsl:if> "
    </xsl:for-each>
     + " From `<xsl:value-of select="$table" />` "
    <xsl:if test="count(xs:key) != 0">
     + " Where "
      <xsl:for-each select="xs:complexType/xs:attribute[concat('@',@name) = key('pk',$table)/@xpath]">
     + " `<xsl:value-of select="@name" />` = ? <xsl:if test="position() != last()"> and </xsl:if> "
      </xsl:for-each>
    </xsl:if>
    ;

      public DomainObject findByPrimaryKey(
    <xsl:for-each select="xs:complexType/xs:attribute[concat('@',@name) = key('pk',$table)/@xpath]">
      <xsl:value-of select="codegen:javaDataType(@type)" />
      <xsl:text> </xsl:text>
      <xsl:value-of select="codegen:FunctionParameter(@name)" />
      <xsl:if test="position() != last()">,</xsl:if>
    </xsl:for-each>
      ) throws Exception
      {
        DatabaseConnectionMonitor monitor = null;

        try
        {
		  monitor = new DatabaseConnectionMonitor( getDatabase() );
		  PreparedStatement sqlCommand = getDatabase().createCommand( SqlSelectByPk );
        <xsl:for-each select="xs:complexType/xs:attribute[concat('@',@name) = key('pk',$table)/@xpath]">
		  sqlCommand.setObject( <xsl:value-of select="position()"/> , <xsl:value-of select="codegen:FunctionParameter(@name)" /> );
        </xsl:for-each>
		  ResultSet dataReader = sqlCommand.executeQuery();
		  DomainObject domainObject = null;

		  if( dataReader.next() )
		      domainObject = doLoad( dataReader );

		  monitor.commitTransaction();

		  return domainObject;
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

        //throw new DataNotFoundException("<xsl:value-of select="$table" /> not found, search by primary key");
      }


      public boolean exists( DomainObject arg ) throws Exception
      {
        <xsl:value-of select="$class-name" /><xsl:text> </xsl:text><xsl:value-of select="$functionParam" /> = (<xsl:value-of select="$class-name" />) arg;
        DatabaseConnectionMonitor monitor = null;

        try
        {
		  monitor = new DatabaseConnectionMonitor( getDatabase() );
		  PreparedStatement sqlCommand = getDatabase().createCommand( SqlSelectByPk );
        <xsl:for-each select="xs:complexType/xs:attribute[concat('@',@name) = key('pk',$table)/@xpath]">
		<xsl:choose>
			<xsl:when test="@type = 'xs:date'">
		  sqlCommand.setObject( <xsl:value-of select="position()"/>, new java.sql.Timestamp( <xsl:value-of select="$functionParam" />.get<xsl:value-of select="codegen:getPropertyName($table,$schema,@name,@wdm:Schema)" />().getTime() ) );
		    </xsl:when>
			<xsl:otherwise>
		  sqlCommand.setObject( <xsl:value-of select="position()"/>, <xsl:value-of select="$functionParam" />.get<xsl:value-of select="codegen:getPropertyName($table,$schema,@name,@wdm:Schema)" />() );
			</xsl:otherwise>
		</xsl:choose>
        </xsl:for-each>
		  ResultSet dataReader = sqlCommand.executeQuery();
		  monitor.commitTransaction();

		  return dataReader.next();
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
      }

    private final String CheckInSql = " " <xsl:if test="count(xs:complexType/xs:attribute[concat('@',@name) = key('pk',$table)/@xpath]) > 0">+</xsl:if>
    <xsl:for-each select="xs:complexType/xs:attribute[concat('@',@name) = key('pk',$table)/@xpath]">
    <xsl:if test="position() != 1"> + </xsl:if>" `<xsl:value-of select="$table"/>`.`<xsl:value-of select="@name" />` = ? <xsl:if test="position() != last()"> and </xsl:if>"
    </xsl:for-each>;

      protected PreparedStatement prepareCheckInCommand( DomainObject domainObject, String sqlQuery ) throws Exception
      {
        <xsl:value-of select="$class-name"/> _<xsl:value-of select="$class-name"/> = (<xsl:value-of select="$class-name"/>)domainObject;
        PreparedStatement sqlCommand = getDatabase().createCommand( DataMapper.modifyQueryForCheckIn(sqlQuery,CheckInSql) );

      <xsl:for-each select="xs:complexType/xs:attribute[concat('@',@name) = key('pk',$table)/@xpath]">
        sqlCommand.setObject( <xsl:value-of select="position()"/>, _<xsl:value-of select="$class-name"/>.get<xsl:value-of select="codegen:getPropertyName($table,$schema,@name,@wdm:Schema)" />() );
      </xsl:for-each>

        return sqlCommand;
      }

  </xsl:template>


  </xsl:stylesheet>