<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
                  xmlns:xs="http://www.w3.org/2001/XMLSchema"
                  xmlns:wdm="urn:schemas-themidnightcoders-com:xml-wdm"
                  xmlns:codegen="xalan://weborb.data.management.codegen.XsltExtention"
                  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:import href="../../import/codegen.import.keys.xslt"/>

  <xsl:template name="codegen.server.java.domain.enviroment"/>
  
  <xsl:template name="codegen.server.java.domain">
    <xsl:variable name="table" select="string(@name)"   />
    <xsl:variable name="schema" select="string(@wdm:Schema)" />
    <xsl:variable name="package" select="codegen:getServerNamespace($schema)" />
    <xsl:variable name="class-name" select="codegen:getClassName(@name, @wdm:Schema)"   />
    <xsl:variable name="full-class-name" select="concat($package, '.', $class-name)"   />
    <xsl:variable name="id" select="string(@id)"   />
    <xsl:variable name="pk" select="string(xs:key/@name)"   />
    <xsl:variable name="pk-schema" select="string(xs:key/@wdm:Schema)" />
    <xsl:value-of select="codegen:Progress(concat('Generating class ', $class-name))" />
    import <xsl:value-of select="codegen:getServerNamespace()" />.*;
    <xsl:call-template name="codegen.server.java.domain.enviroment"/>

    public abstract class _<xsl:value-of select="$class-name"/> extends DomainObject
    {
    <!--xsl:for-each select="xs:complexType/xs:attribute[not(concat('@',@name) = key('fk',$class-name)/@xpath)]"-->
    <xsl:for-each select="xs:complexType/xs:attribute">
      protected <xsl:value-of select="codegen:javaDataType(@type)"/><xsl:text> </xsl:text><xsl:value-of select="codegen:FunctionParameter(codegen:getPropertyName($table,$schema,@name))" /><xsl:if test="@use != 'optional'"> = <xsl:value-of select="codegen:getDefaultValue(@type)"/></xsl:if>;
    </xsl:for-each>

    <xsl:for-each select="xs:keyref">
      <xsl:variable name="parent-schema" select="@refer-schema" />
      <xsl:variable name="parent-table" select="key('table',@refer)[@wdm:Schema = $parent-schema]/@name" />
      <xsl:variable name="parent-property" select="concat('_', codegen:getParentProperty($table,$schema,$parent-table,$parent-schema,@name,1))" />

      // parent tables
      protected <xsl:value-of select="codegen:getServerNamespace($parent-schema)"/>.<xsl:value-of select="codegen:getClassName($parent-table, $parent-schema)"/><xsl:text> </xsl:text><xsl:value-of select="$parent-property" />
      <!--<xsl:if test="xs:field[substring(@xpath,2) = ../../xs:complexType/xs:attribute[@use='required']/@name]">-->
        <!--= new <xsl:value-of select="codegen:getServerNamespace($parent-schema)"/>.<xsl:value-of select="codegen:getClassName($parent-table, $parent-schema)"/>()-->
      <!--</xsl:if>-->;
    </xsl:for-each>

    public _<xsl:value-of select="$class-name"/>(){}

    public _<xsl:value-of select="$class-name"/>(
    <xsl:for-each select="xs:complexType/xs:attribute">
      <xsl:value-of select="codegen:javaDataType(@type)" />
      <xsl:text> 
            </xsl:text>
      <xsl:value-of select="codegen:getFunctionParameter(codegen:getPropertyName($table,$schema,string(@name)))" />
      <xsl:if test="position() != last()">,</xsl:if>
    </xsl:for-each>
    )
    {
    <xsl:for-each select="xs:complexType/xs:attribute">
      this.set<xsl:value-of select="codegen:getPropertyName($table,$schema,string(@name))" />( <xsl:value-of select="codegen:getFunctionParameter(codegen:getPropertyName($table,$schema,string(@name)))" /> );
    </xsl:for-each>
    }

    public boolean contains(Hashtable fields) throws Exception
    {
      int matchCount = 0;
      <xsl:for-each select="xs:complexType/xs:attribute">
        if(fields.containsKey("<xsl:value-of select="codegen:getPropertyName($table,$schema,string(@name))"/>"))
        {
       <xsl:choose>
        <xsl:when test="codegen:isNumericType(@type)">
           Number fieldNum = (Number)fields.get("<xsl:value-of select="codegen:getPropertyName($table,$schema,string(@name))"/>");
           Number thisNum = (Number) this.get<xsl:value-of select="codegen:getPropertyName($table,$schema,string(@name))"/>();
           
           if( fieldNum.doubleValue() != thisNum.doubleValue() ) 
        </xsl:when>
        <xsl:otherwise>
          if(!fields.get("<xsl:value-of select="codegen:getPropertyName($table,$schema,string(@name))"/>").equals(this.get<xsl:value-of select="codegen:getPropertyName($table,$schema,string(@name))"/>()))
        </xsl:otherwise>
       </xsl:choose>
            return false;
          else if(++matchCount == fields.size())
            return true;
        }
      </xsl:for-each>
    
      return matchCount == fields.size();
    }

    public String  uri() throws Exception
    {

    String uri = "<xsl:value-of select="/xs:schema/runtime/@modelName"/>.<xsl:value-of select="../../../@name"/>.<xsl:value-of select="$schema"/>.<xsl:value-of select="@name"/>"
    <xsl:for-each select="xs:key/xs:field">
      + "." + get<xsl:value-of select="codegen:getPropertyName($table,$schema,substring(@xpath,2))" />()
    </xsl:for-each>;

    return uri;
    }

    <xsl:for-each select="xs:complexType/xs:attribute">
      <xsl:variable name="attribute-name" select="string(@name)" />
      
      <xsl:variable name="optional" select="boolean(@use = 'optional')" />
      <xsl:variable name="support-null" select="codegen:IsNullable(@type)" />

        <xsl:choose>
          <xsl:when test="not(concat('@',@name) = key('fkByElementId',$id)/@xpath)">
            public <xsl:value-of select="codegen:javaDataType(@type)" /> get<xsl:value-of select="codegen:getPropertyName($table,$schema,string(@name))" />() { return <xsl:value-of select="codegen:getFunctionParameter(codegen:getPropertyName($table,$schema,string(@name)))" />;}
            public void set<xsl:value-of select="codegen:getPropertyName($table,$schema,string(@name))" />( <xsl:value-of select="codegen:javaDataType(@type)" /> value )
            { 
                <xsl:value-of select="codegen:getFunctionParameter(codegen:getPropertyName($table,$schema,string(@name)))" /> = value;
            }
          </xsl:when>
          <xsl:otherwise>
            public <xsl:value-of select="codegen:javaDataType(@type)" /> get<xsl:value-of select="codegen:getPropertyName($table,$schema,string(@name))" />() throws Exception
            {
            <xsl:for-each select="key('tableById',$id)/xs:keyref">
              <xsl:for-each select="xs:field">
                <xsl:if test="@xpath = concat('@',$attribute-name)">
                  <xsl:variable name="pk-field-position" select="position()" />
                  <xsl:variable name="parent-table-name" select="string(key('table', ../@refer)/@name)" />
                  <xsl:variable name="parent-schema-name" select="string(key('table', ../@refer)/@wdm:Schema)" />
                  <xsl:variable name="parent-pk-field-name" select="key('table',../@refer)/xs:key/xs:field[$pk-field-position]/@xpath" />
                  <xsl:variable name="parent-property" select="concat('_', codegen:getParentProperty($table,$schema,$parent-table-name,$parent-schema-name,../@name,1))" />
                  if(<xsl:value-of select="$parent-property"/> != null)
                    return <xsl:value-of select="$parent-property"/>.get<xsl:value-of select="codegen:getPropertyName($parent-table-name,$parent-schema-name, substring($parent-pk-field-name,2) ) "/>();

                </xsl:if>
              </xsl:for-each>
            </xsl:for-each>

            <xsl:choose>
              <xsl:when test="$optional">
                <xsl:choose>
                  <xsl:when test="@type = 'xs:anyURI'">
                    return "";
                  </xsl:when>
                  <xsl:otherwise>
                    return null;
                  </xsl:otherwise>
                </xsl:choose>
              </xsl:when>
              <xsl:otherwise>throw new Exception("Parent instance not initialized ");</xsl:otherwise>
            </xsl:choose>
            }
            public void set<xsl:value-of select="codegen:getPropertyName($table,$schema,string(@name))" />( <xsl:value-of select="codegen:javaDataType(@type)" /> value )
            {
            <xsl:for-each select="key('tableById',$id)/xs:keyref">
              <xsl:for-each select="xs:field">
                <xsl:if test="@xpath = concat('@',$attribute-name)">
                  <xsl:variable name="pk-field-position" select="position()" />
                  <xsl:variable name="parent-schema-name" select="../@refer-schema" />
                  <xsl:variable name="parent-table-name" select="string(key('table', ../@refer)[@wdm:Schema = $parent-schema-name]/@name)" />
                  <xsl:variable name="parent-pk-field-name" select="key('table',../@refer)/xs:key/xs:field[$pk-field-position]/@xpath" />
                  <xsl:variable name="parent-property" select="concat('_', codegen:getParentProperty($table,$schema,$parent-table-name,$parent-schema-name,../@name,1))" />

                  <xsl:choose>
                    <xsl:when test="$optional and $support-null">
                      if(value == null)
                        <xsl:value-of select="$parent-property"/> = null;
                      else
                      {
                      if(<xsl:value-of select="$parent-property"/> == null)
                        <xsl:value-of select="$parent-property"/> = new <xsl:value-of select="codegen:getServerNamespace($parent-schema-name)"/>.<xsl:value-of select="codegen:getClassName($parent-table-name, $parent-schema-name)"/>();

                        <xsl:value-of select="$parent-property"/>.set<xsl:value-of select="codegen:getPropertyName($parent-table-name, $parent-schema-name, substring($parent-pk-field-name,2)) "/>( value );
                      }
                    </xsl:when>
                    <xsl:otherwise>
                      if(<xsl:value-of select="codegen:getClassName($parent-property, $parent-schema-name)"/> == null)
                        <xsl:value-of select="codegen:getClassName($parent-property, $parent-schema-name)"/> = new <xsl:value-of select="codegen:getServerNamespace($parent-schema-name)"/>.<xsl:value-of select="codegen:getClassName($parent-table-name, $parent-schema-name)"/>();

                      <xsl:value-of select="codegen:getClassName($parent-property, $parent-schema-name)"/>.set<xsl:value-of select="codegen:getPropertyName($parent-table-name, $parent-schema-name, substring($parent-pk-field-name,2)) "/>( value );
                    </xsl:otherwise>
                  </xsl:choose>
                </xsl:if>                
              </xsl:for-each>
            </xsl:for-each>
            }
          </xsl:otherwise>
        </xsl:choose>
    </xsl:for-each>

    <xsl:for-each select="xs:keyref">
      <xsl:variable name="parent-schema" select="@refer-schema" />
      <xsl:variable name="parent-table" select="key('table',@refer)[@wdm:Schema = $parent-schema]/@name" />
      <xsl:variable name="parent-property" select="concat('_', codegen:getParentProperty($table,$schema,$parent-table,$parent-schema,@name,1))" />

      public <xsl:if test="codegen:CountSchemas(codegen:getCurrentDatabase()) != '1'"><xsl:value-of select="codegen:getServerNamespace($parent-schema)"/>.</xsl:if><xsl:value-of select="codegen:getClassName($parent-table, $parent-schema)"/><xsl:text> get</xsl:text><xsl:value-of select="codegen:getParentProperty($table, $schema, $parent-table, $parent-schema, @name, 0)" />()
      {
        return <xsl:value-of select="$parent-property" />;
      }

      public void<xsl:text> set</xsl:text><xsl:value-of select="codegen:getParentProperty($table, $schema, $parent-table, $parent-schema, @name,0)" />( <xsl:if test="codegen:CountSchemas(codegen:getCurrentDatabase()) != '1'"><xsl:value-of select="codegen:getServerNamespace($parent-schema)"/>.</xsl:if><xsl:value-of select="codegen:getClassName($parent-table, $parent-schema)"/> value )
      {
        <xsl:value-of select="$parent-property" /> = value;
      }
      
    </xsl:for-each>

	public DataMapper createDataMapper(Database database)
	{
		return new <xsl:value-of select="$class-name"/><xsl:text>DataMapper(database)</xsl:text>;
	}


    public DomainObject extractSingleObject() throws Exception
    {
      <xsl:value-of select="$class-name"/><xsl:text> </xsl:text><xsl:value-of select="codegen:getFunctionParameter($class-name)"/> = new <xsl:value-of select="$class-name"/>();
      
      <xsl:for-each select="xs:complexType/xs:attribute">
        <xsl:value-of select="codegen:getFunctionParameter($class-name)"/>.set<xsl:value-of select="codegen:getPropertyName($table,$schema,string(@name))" />( this.get<xsl:value-of select="codegen:getPropertyName($table,$schema,string(@name))" />() );
      </xsl:for-each>
        
       <xsl:value-of select="codegen:getFunctionParameter($class-name)"/>.ActiveRecordId = this.ActiveRecordId;
 
      return <xsl:value-of select="codegen:getFunctionParameter($class-name)"/>;
    }

    <xsl:for-each select="key('dependent',current()/xs:key/@name)">
      <xsl:variable name="child-table-pk" select="xs:key/@name" />
      <xsl:variable name="child-table-pk-schema" select="xs:key/@wdm:Schema" />
      <xsl:for-each select="xs:keyref[@refer = $pk and @refer-schema = $pk-schema]">
        <xsl:variable name="fk" select="@name" />
        <xsl:for-each select="key('table',$child-table-pk)[@wdm:Schema = $child-table-pk-schema]">
        <xsl:variable name="child-property" select="concat('_',codegen:getChildProperty($table, $schema, @name, @wdm:Schema, $fk, 1))" />
        <xsl:choose>
        <xsl:when test="count(xs:key/xs:field[@xpath = key('fkByName',$fk)/@xpath and ../@wdm:Schema = key('fkByName',$fk)/../@refer-schema]) = count(xs:key/xs:field)">
          // one to one relation
          _<xsl:value-of select="codegen:getClassName(@name, @wdm:Schema)"/><xsl:text> </xsl:text><xsl:value-of select="$child-property" />;
          public _<xsl:value-of select="codegen:getClassName(@name, @wdm:Schema)"/><xsl:text> </xsl:text>get<xsl:value-of select="codegen:getChildProperty($table, $schema, @name, @wdm:Schema, $fk, 1)" />()
          {
            return this.<xsl:value-of select="$child-property" />;
          }

          public void set<xsl:value-of select="codegen:getChildProperty($table, $schema, @name, @wdm:Schema, $fk, 1)" />( _<xsl:value-of select="codegen:getClassName(@name, @wdm:Schema)"/> value )
          {
            this.<xsl:value-of select="$child-property" /> = value;
          }           
        </xsl:when>
        <xsl:otherwise>
          // one to many relation
          private ArrayList <xsl:value-of select="$child-property" />;

          public ArrayList <xsl:text> get</xsl:text><xsl:value-of select="codegen:getChildProperty($table, $schema, @name, @wdm:Schema,$fk, 0)" />Items()
          {
            return this.<xsl:value-of select="$child-property" />;
          }

          public void <xsl:text> set</xsl:text><xsl:value-of select="codegen:getChildProperty($table,$schema,@name,@wdm:Schema,$fk, 0)" />Items( ArrayList value )
          {         
            this.<xsl:value-of select="$child-property" /> = value; 
          }
          
          
          public <xsl:if test="codegen:CountSchemas(codegen:getCurrentDatabase()) != '1'"><xsl:value-of select="codegen:getServerNamespace()"/>.<xsl:value-of select="@wdm:Schema"/>.</xsl:if><xsl:value-of select="codegen:getClassName(@name,@wdm:Schema)"/><xsl:text> </xsl:text>add<xsl:value-of select="codegen:getChildProperty($table,$schema,@name,@wdm:Schema,$fk, 0)" />Item(
          <xsl:if test="codegen:CountSchemas(codegen:getCurrentDatabase()) != '1'"><xsl:value-of select="codegen:getServerNamespace()"/>.<xsl:value-of select="@wdm:Schema"/>.</xsl:if><xsl:value-of select="codegen:getClassName(@name,@wdm:Schema)"/><xsl:text> </xsl:text><xsl:value-of select="codegen:getFunctionParameter( codegen:getClassName(@name,@wdm:Schema) )"/>)
          {
            <xsl:value-of select="codegen:getFunctionParameter( codegen:getClassName(@name,@wdm:Schema) )"/>.set<xsl:value-of select="codegen:getParentProperty(@name, @wdm:Schema, $table, $schema, $fk, 0)"/>( (<xsl:value-of select="$full-class-name"/>)this );
            
            if( <xsl:value-of select="$child-property" /> == null )
                <xsl:value-of select="$child-property" /> = new ArrayList();
                
            <xsl:value-of select="$child-property" />.add(<xsl:value-of select="codegen:getFunctionParameter( codegen:getClassName(@name, @wdm:Schema) )"/>);
            
            return <xsl:value-of select="codegen:getFunctionParameter( codegen:getClassName(@name, @wdm:Schema) )"/>;
          }
            
        </xsl:otherwise>
        </xsl:choose>
        </xsl:for-each>
      </xsl:for-each>
    </xsl:for-each>
    }
  </xsl:template>
</xsl:stylesheet>