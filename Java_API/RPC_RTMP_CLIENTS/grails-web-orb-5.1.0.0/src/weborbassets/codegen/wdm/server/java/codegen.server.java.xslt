<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
  xmlns:codegen="xalan://weborb.data.management.codegen.XsltExtention"
  xmlns:wdm="urn:schemas-themidnightcoders-com:xml-wdm"
  xmlns:xs="http://www.w3.org/2001/XMLSchema"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:import href="codegen.server.java.domain.xslt"/>
  <xsl:import href="data/codegen.server.java.data.xslt"/>
<!--  <xsl:import href="codegen.server.java.project.xslt"/> -->
  <xsl:import href="codegen.server.java.config.xslt"/>
  <xsl:import href="../../import/codegen.import.keys.xslt"/>

  <xsl:template name="codegen.server.java">

  <folder name="{codegen:getServerFolder()}">

      <xsl:call-template name="codegen.server.java.config" />
      <xsl:if test="codegen:singleCall(concat(/xs:schema/xs:element/@name,'Db'))">
          <xsl:value-of select="codegen:FirstLetterToUpper(/xs:schema/xs:element/@name)"/>
          <file name="{codegen:FirstLetterToUpper(/xs:schema/xs:element/@name)}Db.java">
    package <xsl:value-of select="codegen:getServerNamespace()"/>;

              <!--<xsl:call-template name="codegen.server.java.domain.enviroment" />-->
              <xsl:call-template name="codegen.server.java.data.enviroment"/>
              <xsl:call-template name="codegen.server.java.data.database"/>
          </file>
      </xsl:if>

      <xsl:choose>
          <xsl:when test="codegen:CountSchemas(/xs:schema/xs:element/@name) = '1'">

    <xsl:for-each select="/xs:schema/xs:element">
      <xsl:for-each select="xs:complexType/xs:choice/xs:element[@wdm:DatabaseObjectType='table']">
        <xsl:variable name="class-name" select="codegen:getClassName(@name, @wdm:Schema)"   />
        <file name="{$class-name}.java" overwrite="false">
          package <xsl:value-of select="codegen:getServerNamespace()" />;

          import <xsl:value-of select="codegen:getServerNamespace()" />.codegen._<xsl:value-of select="$class-name"/>;

          public class <xsl:value-of select="$class-name"/> extends _<xsl:value-of select="$class-name"/>
          {
          }

          <xsl:value-of select="codegen:Progress(concat('Generating code for table ', $class-name))" />
        </file>
        <file name="{$class-name}DataMapper.java" overwrite="false">
          package <xsl:value-of select="codegen:getServerNamespace()" />;

          import weborb.data.management.*;
          import <xsl:value-of select="codegen:getServerNamespace()" />.codegen._<xsl:value-of select="$class-name"/>DataMapper;

            public class <xsl:value-of select="$class-name"/>DataMapper extends _<xsl:value-of select="$class-name"/>DataMapper
            {
              public <xsl:value-of select="$class-name"/>DataMapper() throws Exception
              {}
              public <xsl:value-of select="$class-name"/>DataMapper( Database database )
              {
                super(database);
              }

        }
      </file>

      </xsl:for-each>
    </xsl:for-each>

  <folder name="codegen" overwrite="true">
    <xsl:for-each select="/xs:schema/xs:element">
    <xsl:value-of select="codegen:setCurrentDatabase(@name)" />

      <xsl:for-each select="xs:complexType/xs:choice/xs:element[@wdm:DatabaseObjectType='table']">
      <xsl:variable name="class-name" select="codegen:getClassName(@name,@wdm:Schema)"   />
       <file name="_{$class-name}DataMapper.java" overwrite="false">
    package <xsl:value-of select="codegen:getServerNamespace()" />.codegen;

    import <xsl:value-of select="codegen:getServerNamespace()" />.*;
        <xsl:call-template name="codegen.server.java.domain.enviroment" />
        <xsl:call-template name="codegen.server.java.data.enviroment" />
        <xsl:call-template name="codegen.server.java.data"/>
       </file>
       <file name="_{$class-name}.java" overwrite="false">
          package <xsl:value-of select="codegen:getServerNamespace()" />.codegen;
          import <xsl:value-of select="codegen:getServerNamespace()" />.*;

          <xsl:call-template name="codegen.server.java.domain.enviroment" />
          <xsl:call-template name="codegen.server.java.data.enviroment" />
          <xsl:call-template name="codegen.server.java.domain" />

        </file>
      </xsl:for-each>
    </xsl:for-each>
  </folder>
               </xsl:when>
      <xsl:otherwise>
                    <xsl:for-each select="/xs:schema/xs:element">
      <xsl:variable name="schemaName" select="@wdm:Schema"/>
                        <folder><xsl:attribute name="name"><xsl:value-of select="@wdm:Schema"/></xsl:attribute>
      <xsl:for-each select="xs:complexType/xs:choice/xs:element[@wdm:DatabaseObjectType='table']">
        <xsl:variable name="class-name" select="codegen:getClassName(@name, @wdm:Schema)"   />
        <file name="{$class-name}.java" overwrite="false">
          package <xsl:value-of select="codegen:getServerNamespace()" />.<xsl:value-of select="@wdm:Schema"/>;

          import <xsl:value-of select="codegen:getServerNamespace()" />.<xsl:value-of select="@wdm:Schema"/>.codegen._<xsl:value-of select="$class-name"/>;

          public class <xsl:value-of select="$class-name"/> extends _<xsl:value-of select="$class-name"/>
          {
          }

          <xsl:value-of select="codegen:Progress(concat('Generating code for table ', $class-name))" />
        </file>
        <file name="{$class-name}DataMapper.java" overwrite="false">
          package <xsl:value-of select="codegen:getServerNamespace()" />.<xsl:value-of select="@wdm:Schema"/>;

          import weborb.data.management.*;
          import <xsl:value-of select="codegen:getServerNamespace()" />.<xsl:value-of select="@wdm:Schema"/>.codegen._<xsl:value-of select="$class-name"/>DataMapper;

            public class <xsl:value-of select="$class-name"/>DataMapper extends _<xsl:value-of select="$class-name"/>DataMapper
            {
              public <xsl:value-of select="$class-name"/>DataMapper() throws Exception
              {}
              public <xsl:value-of select="$class-name"/>DataMapper( Database database )
              {
                super(database);
              }

        }
      </file>

      </xsl:for-each>
                            <folder name="codegen" overwrite="true">
    <xsl:value-of select="codegen:setCurrentDatabase(@name)" />

      <xsl:for-each select="xs:complexType/xs:choice/xs:element[@wdm:DatabaseObjectType='table']">
      <xsl:variable name="class-name" select="codegen:getClassName(@name,@wdm:Schema)"   />
       <file name="_{$class-name}DataMapper.java" overwrite="false">
    package <xsl:value-of select="codegen:getServerNamespace()" />.<xsl:value-of select="@wdm:Schema"/>.codegen;

    import <xsl:value-of select="codegen:getServerNamespace()" />.*;

    import <xsl:value-of select="codegen:getServerNamespace(@wdm:Schema)"/>.*;

        <xsl:call-template name="codegen.server.java.domain.enviroment" />
        <xsl:call-template name="codegen.server.java.data.enviroment" />
        <xsl:call-template name="codegen.server.java.data"/>
       </file>
       <file name="_{$class-name}.java" overwrite="false">
          package <xsl:value-of select="codegen:getServerNamespace(@wdm:Schema)" />.codegen;

          import <xsl:value-of select="codegen:getServerNamespace(@wdm:Schema)"/>.*;

      <!--xsl:variable name="pk" select="string(xs:key/@name)" />
      <!xsl:for-each select="key('dependent',current()/xs:key/@name)">
          <xsl:variable name="child-table-pk" select="xs:key/@name" />
          <xsl:for-each select="xs:keyref[@refer = $pk]">
            <xsl:variable name="fk" select="@name" />
            <xsl:for-each select="key('table',$child-table-pk)">
              import <xsl:value-of select="codegen:getServerNamespace(@wdm:Schema)"/>.codegen._<xsl:value-of select="codegen:getClassName(@name, @wdm:Schema)"/>;
            </xsl:for-each>
          </xsl:for-each>
      </xsl:for-each-->

          <xsl:for-each select="xs:keyref[@refer-schema != $schemaName ]">
              import <xsl:value-of select="codegen:getServerNamespace(@refer-schema)"/>.codegen.*;
          </xsl:for-each>

          <xsl:call-template name="codegen.server.java.domain.enviroment" />
          <xsl:call-template name="codegen.server.java.data.enviroment" />
          <xsl:call-template name="codegen.server.java.domain" />

        </file>
      </xsl:for-each>
  </folder>
                        </folder>
    </xsl:for-each>


          </xsl:otherwise>
      </xsl:choose>
    </folder>
  </xsl:template>
</xsl:stylesheet>