<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
                  xmlns:wdm="urn:schemas-themidnightcoders-com:xml-wdm"
                  xmlns:xs="http://www.w3.org/2001/XMLSchema"
                  xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"
                  xmlns:codegen="xalan://weborb.data.management.codegen.XsltExtention"
                  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template name="codegen.server.java.data.mssql.update">
    <xsl:variable name="table" select="@name"   />
    <xsl:variable name="schema" select="@wdm:Schema" />
    <xsl:variable name="class-name" select="concat(codegen:getServerNamespace($schema), '.', codegen:getClassName($table,$schema))" />
    <xsl:variable name="functionParam" select="codegen:FunctionParameter(@name)" />
    <xsl:variable name="id" select="@id"   />
    <xsl:variable name="pk" select="xs:key/@name" />
    <xsl:variable name="pk-schema" select="xs:key/@wdm:Schema"/>
    <xsl:variable name="editable" select="boolean(xs:complexType/xs:attribute[not(concat('@',@name) = key('pk',$table)/@xpath) and not(@msdata:ReadOnly='true')])" />
    <xsl:variable name="startPosition" select="count(xs:complexType/xs:attribute[not(concat('@',@name) = key('pk',$table)/@xpath) and not(@msdata:ReadOnly='true')])" />

    <xsl:if test="$editable">
      final String SqlUpdate = "Update [<xsl:value-of select="@wdm:Schema" />].[<xsl:value-of select="@name" />] Set "
      <xsl:for-each select="xs:complexType/xs:attribute[not(concat('@',@name) = key('pk',$table)/@xpath) and not(@msdata:ReadOnly='true')]">
       + " [<xsl:value-of select="@name" />] = ? <xsl:if test="position() != last()">,</xsl:if> "
      </xsl:for-each>
      <xsl:if test="count(xs:key) != 0">
       + " Where "
        <xsl:for-each select="xs:complexType/xs:attribute[concat('@',@name) = key('pk',$table)/@xpath and not(@msdata:ReadOnly='true')]">
         + " [<xsl:value-of select="@name" />] = ? <xsl:if test="position() != last()"> and </xsl:if> "
        </xsl:for-each>
      </xsl:if>;
    </xsl:if>
    <xsl:if test="key('dependent',current()/xs:key/@name)"></xsl:if>
      
      public DomainObject update( DomainObject arg ) throws Exception 
      {
        return updateTyped( (<xsl:value-of select="$class-name" />)arg );
      }
      
      public <xsl:value-of select="$class-name" /> updateTyped( <xsl:value-of select="$class-name" /><xsl:text> </xsl:text><xsl:value-of select="$functionParam" /> ) throws Exception 
      {
        SynchronizationScope syncScope = new SynchronizationScope( getDatabase() );      
      <xsl:if test="$editable">
        DatabaseConnectionMonitor monitor = null;
 
        try
        {   
          monitor = new DatabaseConnectionMonitor( getDatabase() );   
          PreparedStatement sqlCommand = getDatabase().createCommand( SqlUpdate );
        <xsl:for-each select="xs:complexType/xs:attribute[not(concat('@',@name) = key('pk',$table)/@xpath) and not(@msdata:ReadOnly='true')]">
          <xsl:variable name="property" select="codegen:getProperty($table,$schema,@name)" />
          <xsl:choose>
            <xsl:when test="@use  = 'optional'">
              <xsl:choose>
                <xsl:when test="codegen:IsNullable(@type)">
          if(<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>() != null <xsl:if test="@type = 'xs:string'"><![CDATA[&&]]> !<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().equals("")</xsl:if> <xsl:if test="codegen:IsBinary( @type)"><![CDATA[&&]]> <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().length != 0 </xsl:if> )
                </xsl:when>
                <xsl:otherwise>
          if(<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>() != null <xsl:if test="@type = 'xs:string'"><![CDATA[&&]]> !<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().equals("")</xsl:if> <xsl:if test="codegen:IsBinary( @type)"><![CDATA[&&]]> <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().length != 0 </xsl:if> )
                </xsl:otherwise>
              </xsl:choose>
              <xsl:choose>
                <xsl:when test="@type = 'xs:date'">
            sqlCommand.setObject(<xsl:value-of select="position()"/>, new java.sql.Date( <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().getTime() ) );
                </xsl:when>
                <xsl:otherwise>
            sqlCommand.setObject(<xsl:value-of select="position()"/>, <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>());                                
                </xsl:otherwise>
              </xsl:choose>
          else<xsl:choose><xsl:when test="codegen:IsBinary( @type)">
            sqlCommand.setBytes(<xsl:value-of select="position()"/>, null );
                  </xsl:when>
                  <xsl:otherwise>
            sqlCommand.setObject(<xsl:value-of select="position()"/>, null);
                  </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
              <xsl:choose>
                <xsl:when test="@type = 'xs:date'">
          sqlCommand.setObject(<xsl:value-of select="position()"/>, new java.sql.Date( <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().getTime() ) );
                </xsl:when>
                <xsl:otherwise>
          sqlCommand.setObject(<xsl:value-of select="position()"/>, <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>());                                
                </xsl:otherwise>
              </xsl:choose>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:for-each>

        <xsl:for-each select="xs:complexType/xs:attribute[concat('@',@name) = key('pk',$table)/@xpath and not(@msdata:ReadOnly='true')]">
          <xsl:variable name="property" select="codegen:getProperty($table,$schema,@name)" />
          <xsl:choose>
            <xsl:when test="@use  = 'optional'">
              <xsl:choose>
                <xsl:when test="codegen:IsNullable(@type)">
          if(<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>() != null <xsl:if test="@type = 'xs:string'"><![CDATA[&&]]> !<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().equals("")</xsl:if> <xsl:if test="codegen:IsBinary( @type)"><![CDATA[&&]]> <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().length != 0 </xsl:if> )
                </xsl:when>
                <xsl:otherwise>
          if(<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>() != null <xsl:if test="@type = 'xs:string'"><![CDATA[&&]]> !<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().equals("")</xsl:if> <xsl:if test="codegen:IsBinary( @type)"><![CDATA[&&]]> <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().length != 0 </xsl:if> )
                </xsl:otherwise>
              </xsl:choose>
              <xsl:choose>
                <xsl:when test="@type = 'xs:date'">
            sqlCommand.setObject(<xsl:value-of select="position() + $startPosition"/>, new java.sql.Date( <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().getTime() ) );
                </xsl:when>
                <xsl:otherwise>
            sqlCommand.setObject(<xsl:value-of select="position() + $startPosition"/>, <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>());                                
                </xsl:otherwise>
              </xsl:choose>
          else<xsl:choose><xsl:when test="codegen:IsBinary( @type)">
            sqlCommand.setBytes(<xsl:value-of select="position() + $startPosition"/>, null );
                  </xsl:when>
                  <xsl:otherwise>
            sqlCommand.setObject(<xsl:value-of select="position() + $startPosition"/>, null);
                  </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
              <xsl:choose>
                <xsl:when test="@type = 'xs:date'">
          sqlCommand.setObject(<xsl:value-of select="position() + $startPosition"/>, new java.sql.Date( <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>().getTime() ) );
                </xsl:when>
                <xsl:otherwise>
          sqlCommand.setObject(<xsl:value-of select="position() + $startPosition"/>, <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property"/>());                                
                </xsl:otherwise>
              </xsl:choose>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:for-each>
          sqlCommand.execute();     
    <xsl:for-each select="key('dependent',current()/xs:key/@name)[@wdm:Schema = current()/xs:key/@wdm:Schema]">
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
            dataMapper.save((<xsl:value-of select="codegen:getServerNamespace(@wdm:Schema)"/>.<xsl:value-of select="codegen:getClassName(@name,@wdm:Schema)"/>)<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property-name" />());
          }
            </xsl:when> <xsl:otherwise>
              <xsl:variable name="property-name" select="codegen:getChildProperty($table,$schema,@name,@wdm:Schema,$fk,0)" />
          if(<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property-name" />Items() != null
          <![CDATA[&&]]> <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property-name" />Items().size() &gt; 0)
          {
              <xsl:value-of select="codegen:getServerNamespace(@wdm:Schema)"/>.<xsl:value-of select="codegen:getClassName(@name,@wdm:Schema)"/>DataMapper dataMapper = new <xsl:value-of select="codegen:getServerNamespace(@wdm:Schema)"/>.<xsl:value-of select="codegen:getClassName(@name,@wdm:Schema)"/>DataMapper(getDatabase());
            
            for( int i = 0; i &lt; <xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property-name" />Items().size(); i++ )
              dataMapper.save((<xsl:value-of select="codegen:getServerNamespace(@wdm:Schema)"/>.<xsl:value-of select="codegen:getClassName(@name,@wdm:Schema)"/>)<xsl:value-of select="$functionParam" />.get<xsl:value-of select="$property-name" />Items().get(i));
          }
            </xsl:otherwise>
          </xsl:choose>
            </xsl:for-each>
          </xsl:for-each>
        </xsl:for-each>     
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

        raiseAffected(<xsl:value-of select="$functionParam" />,IDataMapper.update);
        </xsl:if>       
        syncScope.Invoke();
        return (<xsl:value-of select="$class-name" />)registerRecord(<xsl:value-of select="$functionParam" />);
      }
</xsl:template>

</xsl:stylesheet>