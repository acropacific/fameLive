<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
                  xmlns:xs="http://www.w3.org/2001/XMLSchema" 
                  xmlns:codegen="xalan://weborb.data.management.codegen.XsltExtention"
                  xmlns:wdm="urn:schemas-themidnightcoders-com:xml-wdm"
                  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  
  <xsl:template name="codegen.server.java.data.postgresql.save">
    <xsl:variable name="class-name" select="concat(codegen:getServerNamespace(@wdm:Schema), '.', codegen:getClassName(@name,@wdm:Schema))"   />
    <xsl:variable name="functionParam" select="codegen:FunctionParameter(@name)" />

    public DomainObject save( DomainObject <xsl:value-of select="$functionParam" /> )  throws Exception 
    {
        return saveTyped( (<xsl:value-of select="$class-name" />)<xsl:value-of select="$functionParam" /> );
    }

    public <xsl:value-of select="$class-name" /> saveTyped( <xsl:value-of select="$class-name" /><xsl:text> </xsl:text><xsl:value-of select="$functionParam" /> )  throws Exception 
    {
      if(exists(<xsl:value-of select="$functionParam" />))
        return updateTyped(<xsl:value-of select="$functionParam" />);
        return createTyped(<xsl:value-of select="$functionParam" />);
    }

  </xsl:template>
</xsl:stylesheet>