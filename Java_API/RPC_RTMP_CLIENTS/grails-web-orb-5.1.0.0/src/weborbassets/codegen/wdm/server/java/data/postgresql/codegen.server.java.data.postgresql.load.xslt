<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
                  xmlns:wdm="urn:schemas-themidnightcoders-com:xml-wdm"                  
                  xmlns:xs="http://www.w3.org/2001/XMLSchema" 
                  xmlns:codegen="xalan://weborb.data.management.codegen.XsltExtention"
                  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template name="codegen.server.java.data.postgresql.load">
    <xsl:variable name="table" select="@name" />
    <xsl:variable name="schema" select="@wdm:Schema" />
    <xsl:variable name="serverNS" select="codegen:getServerNamespace($schema)" />
    <xsl:variable name="class-name" select="concat($serverNS, '.', codegen:getClassName( $table,$schema ))" />
    <xsl:variable name="functionParam" select="codegen:FunctionParameter(@name)" />
    <xsl:variable name="pk" select="xs:key/@name" />
    <xsl:variable name="pk-schema" select="xs:key/@wdm:Schema" />

      protected DomainObject doLoad( ResultSet dataReader ) throws Exception 
      {
        <xsl:value-of select="$class-name" /><xsl:text> </xsl:text><xsl:value-of select="$functionParam" /> = new<xsl:text> </xsl:text><xsl:value-of select="$class-name" />();

    <xsl:for-each select="xs:complexType/xs:attribute">
      <xsl:variable name="property" select="codegen:getProperty($table,$schema,@name)" />
        <xsl:if test="@use = 'optional'">
        if( dataReader.getObject( <xsl:value-of select="position()" /> ) != null )
        </xsl:if>                
        <xsl:choose>
            <xsl:when test="codegen:isObjectType(@type)">
          <xsl:value-of select="$functionParam" />.set<xsl:value-of select="$property"/>( <xsl:if test="codegen:isArrayType(@type)">(<xsl:value-of select="codegen:javaDataType(@type)"/>)getTypedArray( </xsl:if>dataReader.<xsl:value-of select="codegen:dataReaderFunctionName(@type)" />( <xsl:value-of select="position()" /> )<xsl:if test="codegen:isArrayType(@type)"> )</xsl:if> );
            </xsl:when>
            <xsl:otherwise>
          <xsl:value-of select="$functionParam" />.set<xsl:value-of select="$property"/>( new <xsl:value-of select="codegen:javaDataType(@type)" />( dataReader.<xsl:value-of select="codegen:dataReaderFunctionName(@type)" />( <xsl:value-of select="position()" /> ) ) );                  
            </xsl:otherwise>
        </xsl:choose>                     
    </xsl:for-each>
       
        return (DomainObject)registerRecord(<xsl:value-of select="$functionParam" />);
      }


      protected DomainObject doLoad( Hashtable hashtable ) throws Exception 
      {
        <xsl:value-of select="$class-name" /><xsl:text> </xsl:text><xsl:value-of select="$functionParam" /> = new<xsl:text> </xsl:text><xsl:value-of select="$class-name" />();
      <xsl:for-each select="xs:complexType/xs:attribute">
      <xsl:variable name="property" select="codegen:getProperty($table,$schema,@name)" />
        if( hashtable.containsKey( "<xsl:value-of select="@name"/>" ) )
        <xsl:choose>
          <xsl:when test="@type='xs:base64Binary' or @type = 'xs:timestamp'">
          <xsl:value-of select="$functionParam" />.set<xsl:value-of select="$property"/>( (<xsl:value-of select="codegen:javaDataType(@type)" />) hashtable.get( "<xsl:value-of select="$property"/>" ) );
          </xsl:when>
          <xsl:when test="@type='xs:nonNegativeInteger' or  @type='xs:long' or @type = 'xs:float'  or @type = 'xs:int'  or @type = 'xs:byte' or @type = 'xs:double' or @type = 'xs:short'">{
            Number num = (Number)  hashtable.get( "<xsl:value-of select="$property"/>" );
            <xsl:value-of select="$functionParam" />.set<xsl:value-of select="$property"/>( (<xsl:value-of select="codegen:javaDataType(@type)" />)num.<xsl:value-of select="codegen:numberObjectConversionMethod(@type)" />() );
            }
          </xsl:when>          
          <xsl:otherwise>
          <xsl:value-of select="$functionParam" />.set<xsl:value-of select="$property"/>( (<xsl:value-of select="codegen:javaDataType(@type)" />)hashtable.get( "<xsl:value-of select="$property"/>" ) );
          </xsl:otherwise>
        </xsl:choose>
      </xsl:for-each>

        return (DomainObject)<xsl:value-of select="$functionParam" />;
      }


      protected ArrayList fill( PreparedStatement sqlCommand, int offset, int limit )
      {
        ArrayList resultList = new ArrayList();
        
        try
        {
          ResultSet result = sqlCommand.executeQuery( );

          if( limit == 0 )
          {
            while( result.next() )
            {
              <xsl:value-of select="$class-name" /> item = new <xsl:value-of select="$class-name" />();              
              <xsl:for-each select="xs:complexType/xs:attribute">
              <xsl:variable name="property" select="codegen:getProperty($table,$schema,@name)" />
                  <xsl:if test="@use = 'optional'">
              if( result.getObject( "<xsl:value-of select="@name"/>" ) != null )
                  </xsl:if>                
                <xsl:choose>
                    <xsl:when test="codegen:isObjectType(@type)">
                item.set<xsl:value-of select="$property"/>( <xsl:if test="codegen:isArrayType(@type)">(<xsl:value-of select="codegen:javaDataType(@type)"/>)getTypedArray( </xsl:if>result.<xsl:value-of select="codegen:dataReaderFunctionName(@type)" />( "<xsl:value-of select="@name"/>" )<xsl:if test="codegen:isArrayType(@type)"> )</xsl:if> );
                    </xsl:when>
                    <xsl:otherwise>
                item.set<xsl:value-of select="$property"/>( new <xsl:value-of select="codegen:javaDataType(@type)" />( result.<xsl:value-of select="codegen:dataReaderFunctionName(@type)" />( "<xsl:value-of select="@name"/>" ) ) );                  
                    </xsl:otherwise>
                </xsl:choose>               
              </xsl:for-each>
                
              registerRecord(item);
              resultList.add( item );
            }
        
            return resultList;
          }

          int currentRow = 0;
            
          for( currentRow = 0; currentRow &lt; offset; currentRow++ )
            result.next();

          while( result.next() <![CDATA[&&]]> ( currentRow &lt; ( offset + limit ) ))
          {
            currentRow++;
            <xsl:value-of select="$class-name" /> item = new <xsl:value-of select="$class-name" />();
              
            <xsl:for-each select="xs:complexType/xs:attribute">
            <xsl:variable name="property" select="codegen:getProperty($table,$schema,@name)" />
                <xsl:if test="@use = 'optional'">
            if( result.getObject( "<xsl:value-of select="@name"/>" ) != null )
                </xsl:if>
                <xsl:choose>
                    <xsl:when test="codegen:isObjectType(@type)">
              item.set<xsl:value-of select="$property"/>( <xsl:if test="codegen:isArrayType(@type)">(<xsl:value-of select="codegen:javaDataType(@type)"/>)getTypedArray( </xsl:if>result.<xsl:value-of select="codegen:dataReaderFunctionName(@type)" />( "<xsl:value-of select="@name"/>" )<xsl:if test="codegen:isArrayType(@type)"> )</xsl:if> );
                    </xsl:when>
                    <xsl:otherwise>
              item.set<xsl:value-of select="$property"/>( new <xsl:value-of select="codegen:javaDataType(@type)" />( result.<xsl:value-of select="codegen:dataReaderFunctionName(@type)" />( "<xsl:value-of select="@name"/>" ) ) );                    
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>
                
            registerRecord(item);
            resultList.add( item );
          }         
        }
        catch( Exception e )
        {
          e.printStackTrace();
        }
         
        return resultList;
      }
    
    <xsl:for-each select="xs:keyref">
      <xsl:variable name="parent-schema" select="@refer-schema" />
      <xsl:variable name="parent-table" select="key('table',@refer)[@wdm:Schema = $parent-schema]/@name" />
      <xsl:variable name="parent-property" select="codegen:getParentProperty($table,$schema,$parent-table,$parent-schema,@name,0)" />
      <xsl:variable name="sql-name" select="concat('SqlFindBy',$parent-property)" />
      private final String <xsl:value-of select="$sql-name"/> = "select * from \"<xsl:value-of select="$schema"/>\".\"<xsl:value-of select="$table" />\" "
      + " where "
      <xsl:for-each select="xs:field">
        <xsl:if test="position() != 1">
        + "  and "
        </xsl:if>
       + " \"<xsl:value-of select="substring(@xpath,2)"/>\" = ? "
      </xsl:for-each>;

      //Note: this function can be called only from server side
      protected ArrayList findBy<xsl:value-of select="$parent-property"/>(<xsl:value-of select="codegen:getServerNamespace($parent-schema)"/>.<xsl:value-of select="codegen:getClassName($parent-table,$parent-schema)"/> domainObject, QueryOptions queryOptions) throws Exception
      {
        ArrayList result = null;       
        PreparedStatement sqlCommand = getDatabase().createCommand(<xsl:value-of select="$sql-name"/>);
        <xsl:for-each select="xs:field">
          <xsl:variable name="pk-field-position" select="position()" />
          <xsl:variable name="parent-pk-field-name" select="key('table',../@refer)/xs:key/xs:field[$pk-field-position]/@xpath" />
        sqlCommand.setObject(<xsl:value-of select="position()"/>,
        domainObject.get<xsl:value-of select="codegen:getPropertyName($parent-table,$parent-schema,substring($parent-pk-field-name,2))" />());
        </xsl:for-each>

        result = fill(sqlCommand,0,0);        
        
        for( int i = 0; i &lt; result.size(); i++ )
          ((<xsl:value-of select="$class-name" />)result.get(i)).set<xsl:value-of select="$parent-property"/>( domainObject );
         <xsl:if test="key('dependent',current()/xs:key/@name)">
        loadRelations(result);
         </xsl:if>

        return result;
      }

    </xsl:for-each>

    <xsl:if test="key('dependent',$pk)[@wdm:Schema = $pk-schema]">
      protected void loadRelations(DomainObject domainObject, QueryOptions queryOptions) throws Exception
      {
        for( int i = 0; i &lt; queryOptions.getRelations(this).size(); i++ )
        {
          String relationName = (String)queryOptions.getRelations(this).get( i );
        <xsl:for-each select="key('dependent',$pk)[@wdm:Schema = $pk-schema]">
          <xsl:variable name="child-table" select="@name" />
          <xsl:variable name="child-schema" select="@wdm:Schema" />
          <xsl:variable name="fk" select="xs:keyref[@refer = $pk and @refer-schema = $pk-schema]/@name" />
          <xsl:choose>
            <xsl:when test="count(xs:key/xs:field[@xpath = key('fkByName',$fk)/@xpath and ../@wdm:Schema = key('fkByName',$fk)/../@refer-schema]) = count(xs:key/xs:field)">
            </xsl:when>
            <xsl:otherwise>
              <xsl:variable name="hidden-property" select="codegen:getChildProperty($table,$schema,@name,@wdm:Schema,$fk,0)" />
              <xsl:variable name="relation-property" select="codegen:getChildProperty($table,$schema,@name,@wdm:Schema,$fk,0)" />
              <xsl:variable name="reverse-property" select="codegen:getParentProperty($child-table,$child-schema,$table,$schema,$fk,0)" />

          if(relationName.equals( "<xsl:value-of select="$relation-property" />" ) )
          {
            <xsl:value-of select="codegen:getServerNamespace($child-schema)"/>.<xsl:value-of select="codegen:getClassName($child-table,$child-schema)" />DataMapper dataMapper = new <xsl:value-of select="codegen:getServerNamespace($child-schema)"/>.<xsl:value-of select="codegen:getClassName($child-table,$child-schema)" />DataMapper(getDatabase());
            ((<xsl:value-of select="$class-name" />)domainObject).set<xsl:value-of select="$hidden-property" />Items( dataMapper.findBy<xsl:value-of select="$reverse-property"/>((<xsl:value-of select="$class-name" />)domainObject,queryOptions) );
          }
            </xsl:otherwise>
          </xsl:choose>
        </xsl:for-each>
        }
      }
    </xsl:if>
    
  </xsl:template>

  
  </xsl:stylesheet>