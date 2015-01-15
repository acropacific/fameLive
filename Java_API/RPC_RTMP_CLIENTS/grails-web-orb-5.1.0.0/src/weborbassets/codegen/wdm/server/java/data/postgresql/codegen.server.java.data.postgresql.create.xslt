<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
                  xmlns:wdm="urn:schemas-themidnightcoders-com:xml-wdm"
                  xmlns:xs="http://www.w3.org/2001/XMLSchema"
                  xmlns:codegen="xalan://weborb.data.management.codegen.XsltExtention"
                  xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"
                  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  
  <xsl:template name="codegen.server.java.data.postgresql.create">
    <xsl:variable name="table" select="@name"   />
    <xsl:variable name="schema" select="@wdm:Schema"   />
    <xsl:variable name="serverNS" select="codegen:getServerNamespace($schema)" />
    <xsl:variable name="class-name" select="concat($serverNS, '.', codegen:getClassName( @name, @wdm:Schema ))"   />
    <xsl:variable name="functionParam" select="codegen:getFunctionParameter( @name)" />
    <xsl:variable name="identity" select="boolean(xs:complexType/xs:attribute[@msdata:AutoIncrement='true'])" />
    <xsl:variable name="id" select="@id"   />
    <xsl:variable name="pk" select="xs:key/@name"   />
    <xsl:variable name="pk-schema" select="xs:key/@wdm:Schema"   />
    <xsl:variable name="timestamp" select="boolean(xs:complexType/xs:attribute[@type='xs:time'])" />
    
    private final String SqlCreate = "Insert Into \"<xsl:value-of select="$schema"/>\".\"<xsl:value-of select="$table" />\" ( "
    <xsl:for-each select="xs:complexType/xs:attribute[not(@msdata:AutoIncrement='true') and not(@msdata:ReadOnly='true')]">
     + " \"<xsl:value-of select="@name" />\" "
     <xsl:if test="position() != last()"> + " , "</xsl:if>
    </xsl:for-each>+ " ) Values ( "
    <xsl:for-each select="xs:complexType/xs:attribute[not(@msdata:AutoIncrement='true') and not(@msdata:ReadOnly='true')]">
      + " ? <xsl:if test="position() != last()">,</xsl:if> "
    </xsl:for-each>+ " ); "
    ;
      public DomainObject create( DomainObject arg ) throws Exception 
      {
        <xsl:value-of select="$class-name" /><xsl:text> </xsl:text><xsl:value-of select="$functionParam" /> = (<xsl:value-of select="$class-name" />) arg;
        return createTyped( <xsl:value-of select="$functionParam" /> );
      }
    
      public <xsl:value-of select="$class-name" /> createTyped( <xsl:value-of select="$class-name" /><xsl:text> </xsl:text><xsl:value-of select="$functionParam" /> ) throws Exception 
      {
        SynchronizationScope syncScope = new SynchronizationScope( getDatabase() );
        DatabaseConnectionMonitor monitor = null;      

        try
        {
          monitor = new DatabaseConnectionMonitor( getDatabase() );
          <xsl:if test="$identity">
          ResultSet result = getDatabase().createCommand( "Select column_default from information_schema.columns where table_name='<xsl:value-of select="$table" />' and column_name='<xsl:value-of select="xs:complexType/xs:attribute[@msdata:AutoIncrement='true']/@name"/>'" ).executeQuery();
          result.next();
          String identity_query = "Select " + result.getString( 1 ).replaceAll( "nextval", "currval" ) + ";";
          result.close();
          </xsl:if>         
          PreparedStatement sqlCommand = getDatabase().createCommand( SqlCreate );    
        <xsl:for-each select="xs:complexType/xs:attribute[not(@msdata:AutoIncrement='true') and not(@msdata:ReadOnly='true')]">
          <xsl:variable name="property" select="codegen:getPropertyName( $table, $schema, @name )" />
            <xsl:choose>
              <xsl:when test="@use  = 'optional'">
                <xsl:choose>
                  <xsl:when test="codegen:IsNullable( @type )">
          if( <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>() != null <xsl:if test="@type = 'xs:string'"><![CDATA[&&]]> !<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().equals("")</xsl:if> <xsl:if test="codegen:IsBinary( @type)"><![CDATA[&&]]> <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().length != 0 </xsl:if> )
                  </xsl:when>
                  <xsl:otherwise>
          if( <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>() != null <xsl:if test="@type = 'xs:string'"><![CDATA[&&]]> !<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().equals("")</xsl:if> <xsl:if test="codegen:IsBinary( @type)"><![CDATA[&&]]> <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().length != 0 </xsl:if> )
                  </xsl:otherwise>
                </xsl:choose>
              <xsl:choose>
                <xsl:when test="@type = 'xs:date' or @type = 'xs:timestamp' or @type = 'xs:time'">
            sqlCommand.setObject( <xsl:value-of select="position()"/>, new java.sql.Date( <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().getTime() ) );
                </xsl:when> 
                <xsl:otherwise>
            sqlCommand.setObject( <xsl:value-of select="position()"/>, <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>()<xsl:if test="@type = 'xs:boolean'">, java.sql.Types.BIT </xsl:if>);
                </xsl:otherwise>
              </xsl:choose>
          else
                <xsl:choose>
                  <xsl:when test="codegen:IsBinary( @type)">
            sqlCommand.setBytes( <xsl:value-of select="position()"/>, null );
                  </xsl:when>
                  <xsl:otherwise>
            sqlCommand.setObject( <xsl:value-of select="position()"/>, null );
                  </xsl:otherwise>
                </xsl:choose>
              </xsl:when>
              <xsl:otherwise>
              <xsl:choose>
                <xsl:when test="@type = 'xs:date' or @type = 'xs:timestamp' or @type = 'xs:time'">
          sqlCommand.setObject( <xsl:value-of select="position()"/>, new java.sql.Date( <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().getTime() ) );
                </xsl:when>
                <xsl:otherwise>
          sqlCommand.setObject( <xsl:value-of select="position()"/>, <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>()<xsl:if test="@type = 'xs:boolean'">, java.sql.Types.BIT </xsl:if>);
                </xsl:otherwise>
              </xsl:choose>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:for-each>

    <xsl:variable name="key-schema" select="current()/xs:key/@wdm:Schema" />
    <xsl:for-each select="key('dependent',current()/xs:key/@name)[xs:keyref/@refer-schema = $key-schema]">
      <xsl:variable name="child-table-pk" select="xs:key/@name" />
      <xsl:variable name="child-table-pk-schema" select="xs:key/@wdm:Schema" />
      <xsl:for-each select="xs:keyref[@refer = $pk and @refer-schema = $pk-schema]">
      <xsl:variable name="fk" select="@name" />
        <xsl:for-each select="key('table',$child-table-pk)[@wdm:Schema = $child-table-pk-schema]">
          <xsl:choose>
            <xsl:when test="count(xs:key/xs:field[@xpath = key('fkByName',$fk)/@xpath and ../@wdm:Schema = key('fkByName',$fk)/../@refer-schema]) = count(xs:key/xs:field)">
              <xsl:variable name="property-name" select="codegen:getChildProperty($table,$schema,@name,@wdm:Schema,$fk,1)" />

          if(<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property-name" />() != null)
          {
            <xsl:value-of select="codegen:getServerNamespace(@wdm:Schema)"/>.<xsl:value-of select="codegen:getClassName(@name,@wdm:Schema)"/>DataMapper dataMapper = new <xsl:value-of select="codegen:getServerNamespace(@wdm:Schema)"/>.<xsl:value-of select="codegen:getClassName(@name,@wdm:Schema)"/>DataMapper(getDatabase());
            dataMapper.create(<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property-name" />());
          }
          </xsl:when>
          <xsl:otherwise>
            <xsl:variable name="property-name" select="codegen:getChildProperty( $table,$schema,@name,@wdm:Schema,$fk,0)" />
          
          if(<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property-name" />Items() != null 
              <![CDATA[&&]]> <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property-name" />Items().size() &gt; 0)
          {
            <xsl:value-of select="$serverNS"/>.<xsl:value-of select="codegen:getClassName(@name,@wdm:Schema)"/>DataMapper dataMapper = new <xsl:value-of select="$serverNS"/>.<xsl:value-of select="codegen:getClassName(@name,@wdm:Schema)"/>DataMapper(getDatabase());
            
            for( int i = 0; i &gt; <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property-name" />Items().size(); i++ )
            {
              <xsl:value-of select="$serverNS"/>.<xsl:value-of select="codegen:getClassName(@name,@wdm:Schema)"/> item = (<xsl:value-of select="$serverNS"/>.<xsl:value-of select="codegen:getClassName(@name,@wdm:Schema)"/>)<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property-name" />Items().get( i );
              dataMapper.create(item);
            }                        
          }
            </xsl:otherwise>
          </xsl:choose>
        </xsl:for-each>
      </xsl:for-each>
    </xsl:for-each>
         
          <xsl:choose>
            <xsl:when test="$identity">
          sqlCommand.execute();
          ResultSet resultSet = getDatabase().createCommand( identity_query ).executeQuery();
          resultSet.next();
          <xsl:value-of select="$functionParam" />.set<xsl:value-of select="codegen:getPropertyName( $table,$schema, xs:complexType/xs:attribute[@msdata:AutoIncrement='true']/@name)"/>( <xsl:value-of select="codegen:getParseFunction( xs:complexType/xs:attribute[@msdata:AutoIncrement='true']/@type)" />( resultSet.getString( 1 ) ) ) ;
          resultSet.close();
            </xsl:when>
            <xsl:otherwise>
          sqlCommand.execute();
            </xsl:otherwise>
          </xsl:choose>
          
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
      
        raiseAffected(<xsl:value-of select="$functionParam" />,IDataMapper.create);
        syncScope.Invoke();
        return (<xsl:value-of select="$class-name" />)registerRecord(<xsl:value-of select="$functionParam" />);
      }

  </xsl:template>
</xsl:stylesheet>