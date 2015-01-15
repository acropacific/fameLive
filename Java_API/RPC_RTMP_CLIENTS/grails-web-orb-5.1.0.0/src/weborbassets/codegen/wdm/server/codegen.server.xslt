<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" 
  xmlns:codegen="xalan://weborb.data.management.codegen.XsltExtention"
  xmlns:xs="http://www.w3.org/2001/XMLSchema" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:import href="java/codegen.server.java.xslt"/>
  
  <xsl:template name="codegen.server">
    <xsl:variable name="lang" select="codegen:getServerLanguage()" />
    <xsl:value-of select="codegen:Progress('Server code generator started')" />
    <xsl:value-of select="codegen:Progress(concat('Server code language is ', $lang))" />
    
    <xsl:choose>
      <xsl:when test="$lang = 'java'">
        <folder name="java">
         <xsl:call-template name="codegen.server.java" />
        </folder> 
      </xsl:when>
      <xsl:when test="$lang = ''">
        <xsl:message terminate="yes">
          Server side language "<xsl:value-of select="$lang" />" not defined
        </xsl:message>
      </xsl:when>
      <xsl:otherwise>
        <xsl:message terminate="yes">Server side language "<xsl:value-of select="$lang" />" not supported</xsl:message>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
</xsl:stylesheet>