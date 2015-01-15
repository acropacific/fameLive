<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
    xmlns:codegen="urn:cogegen-xslt-lib:xslt"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:import href="codegen.xslt"/>
  
  
   <xsl:import href="codegen.invoke.ajax.xslt"/> 
  


 <xsl:template name="codegen.vo.folder"/>

 <xsl:template name="codegen.process.fullproject">
        <xsl:for-each select="/namespaces">
            <xsl:call-template name="codegen.process.namespace" />
        </xsl:for-each>
  </xsl:template> 

  <xsl:template name="codegen.process.namespace">
        <xsl:for-each select="namespace[count(descendant::service)>=1]">
            <folder name="{@name}">
                <xsl:call-template name="codegen.process.namespace" />
                <xsl:for-each select="service">
                    <xsl:call-template name="codegen.service" />
                </xsl:for-each>
                <xsl:call-template name="codegen.vo.folder" />
            </folder>
        </xsl:for-each>
  </xsl:template>

  <xsl:template name="codegen.invoke.method.name">
    <xsl:value-of select="@name"/>
  </xsl:template>

  <xsl:template name="codegen.service">
    <file name="{@name}.html" type="xml">
<html>
<script language="javascript" src="../WebORB.js"></script>
<script language="javascript" src="weborb/hibernate/{@name}.js"></script>
<script language="javascript" src="weborb/hibernate/QueryDescriptor.js"></script>
<script language="javascript" src="weborb/hibernate/SQLQueryDescriptor.js"></script>
<script language="javascript" src="weborb/hibernate/LockMode.js"></script>
<script language="javascript" src="weborb/hibernate/CacheMode.js"></script>
<script language="javascript" src="weborb/hibernate/FlushMode.js"></script>
<body>  
</body>
</html>
    </file>
<folder name="weborb">
<folder name="hibernate">
    <file name="{@name}.js">
      <xsl:call-template name="codegen.description">
        <xsl:with-param name="file-name" select="concat(@name,'.js')" />
      </xsl:call-template>
       <xsl:call-template name="codegen.code" />
    </file>
    <file name = "HibernateModel.js" >
    <xsl:for-each select='method'>
      function <xsl:value-of select='@name'/>Response( result )
      {
         alert( result );
      }
    </xsl:for-each>

  </file> 
  <file name="LockMode.js">
   
  
  //Instances represent a lock mode for a row of a relational database table.
  
  function LockMode()
  {
    this.level = 0;
    this.name = "";
    this.hashcode = 0;
  
    function LockMode(level, name)
    {
      this.level = level;
      this.name = name; 
      hashcode = (level * 37) ^ (name != null ? name.length : 0);     
    }    
    
    // No lock required. 
    // If an object is requested with this lock mode, a Read lock
    // might be obtained if necessary.
        
    this.None = new LockMode(0, "None");

    // A shared lock.  Objects are loaded in Read mode by default
    
    this.Read = new LockMode(5, "Read");

    // An upgrade lock.  Objects loaded in this lock mode are materialized using anSQL SELECT ... FOR UPDATE
    
    this.Upgrade = new LockMode(10, "Upgrade");

    
    // Attempt to obtain an upgrade lock, using an Oracle-style
    // SELECT ... FOR UPGRADE NOWAIT. 
    // 
    // The semantics of this lock mode, once obtained, are the same as Upgrade
   
    this.UpgradeNoWait = new LockMode(10, "UpgradeNoWait");

    //
    // A Write lock is obtained when an object is updated or inserted.
    // This is not a valid mode for Load() or Lock().
    //
    this.Write = new LockMode(10, "Write");

    //  
    // Similar to  except that, for versioned entities,
    // it results in a forced version increment.
    
    this.Force = new LockMode(15, "Force");   
  
}
  
  </file>
  <file name="FlushMode.js">

  //Represents a flushing strategy. The flush process synchronizes database state with session
  // state by detecting state changes and executing SQL statements.

 function FlushMode()
  {
    //Special value for unspecified flush mode (like  in Java).
    
      this.Unspecified = -1;
    
  
      // The Session is never flushed unless Flush() is explicitly
      // called by the application. This mode is very efficient for read only
      //transactions
       
      this.Never = 0;
      
       
      // The ISession is flushed when Transaction.Commit() is called
      
      this.Commit = 5;
      
      
      // The ISession is sometimes flushed before query execution in order to
      // ensure that queries never return stale state. This is the default flush mode.
       
      this.Auto = 10;
      
       
      //The  is flushed before every query. This is
      //almost always unnecessary and inefficient.
       
      this.Always = 20;
    
  } 
  </file>
  <file name="CacheMode.js">
  // Controls how the session interacts with the second-level cache and query cache.
  
  function CacheMode()
  {
    
    // The session will never interact with the cache, except to invalidate
    // cache items when updates occur
    
    this.Ignore = 0;

    
    // The session will never read items from the cache, but will add items
 
    this.Put = 1;

    // The session may read items from the cache, but will not add items, 
    // except to invalidate items when updates occur
   
    this.Get = 2;

    // The session may read items from the cache, and add items to the cache
    
    this.Normal = this.Put | this.Get;
  
    // The session will never read items from the cache, but will add items
    // to the cache as it reads them from the database. In this mode, the
    // effect of hibernate.cache.use_minimal_puts is bypassed, in
    // order to force a cache refresh
 
    this.Refresh = this.Put | 4; // NH: include Put but have a different value
  } 
</file>
<file name="QueryDescriptor.js">
var parameters;
function QueryDescriptor( proxy, methodName, query)
    {   
  parameters = new Array();  
  
     if (methodName!=undefined) 
     if(query!=undefined)
      parameters.push( [ methodName, query ] );
     this.parameters = parameters; 
   this.proxy = proxy;
  
this.uniqueResult = function (weborbAsyncCall)
      { 
       if( weborbAsyncCall )
        this.proxy.uniqueResult(parameters, new Async( GetResponse ));
       else
        return this.proxy.uniqueResult(parameters);     
      } 
   
this.list = function (weborbAsyncCall)
      {    
       if( weborbAsyncCall )
        this.proxy.list(parameters, new Async( GetResponse ));
       else
        return this.proxy.list(parameters);     
      } 
    
this.executeUpdate = function (weborbAsyncCall)
      {                
       if( weborbAsyncCall )
        this.proxy.executeUpdate(parameters, new Async( GetResponse ));
       else
        return this.proxy.executeUpdate(parameters);     
      }     

this.setMaxResults = function ( maxResult) 
    {
      parameters.push(["setMaxResults",maxResult]);
      return this;
    }   
    
this.setFirstResult = function ( firstResult ) 
    {
      parameters.push(["setFirstResult", firstResult]);
      return this;
    }   
    
this.setReadOnly = function ( readonly ) 
    {
      parameters.push(["setReadOnly", readonly]);
      return this;
    }  
    
this.setFetchSize = function ( fetchSize ) 
    {
      parameters.push(["setFetchSize",fetchSize]);
      return this;
    } 

this.setLockMode = function ( alias, lock ) 
    {
      parameters.push(["setLockMode", alias, lock ]);
      return this;
    }
  
this.setComment = function ( comment ) 
    {
      parameters.push(["setComment", comment]);
      return this;
    }

this.setFlushMode = function ( flush )
    {
      parameters.push(["setFlushMode", flush]);
      return this;
    }    

this.setCacheMode = function( cache )
    {
      parameters.push(["setCacheMode", cache]);
      return this;
    }

this.setNamedParameter = function ( name, val ) 
    {
      parameters.push(["setNamedParameter", name, val ]);
      return this;
    }   

this.setNamedParameterList = function ( name, val ) 
    {
      parameters.push(["setNamedParameterList", name, val ]);
      return this;
    }   

this.setProperties = function ( prop ) 
    {
      parameters.push(["setProperties", prop]);
      return this;
    }

this.setNamedString = function ( name, val ) 
    {
      parameters.push(["setNamedString", name, val ]);
      return this;
    }

this.setNamedBinary = function ( name, val ) 
    {
      parameters.push(["setNamedBinary", name, val ]);
      return this;
    } 
  
this.setNamedBoolean = function ( name, val ) 
    {
      parameters.push(["setNamedBoolean", name, val ]);
      return this;
    }

this.setNamedDate = function ( name, val )  
    {
      parameters.push(["setNamedDate", name, val]);
      return this;
    }
  
this.setNamedEntity = function ( name, val )
    {
      parameters.push(["setNamedEntity", name, val]);
      return this;
    }     
    
this.setNamedInteger = function ( name, val ) 
    {
      parameters.push(["setNamedInteger", name, val]);
      return this;
    }   

this.setCacheable = function( cacheable ) 
    {
      parameters.push(["setCacheable", cacheable]);
      return this;
    }   
  
this.setCacheRegion = function ( cacheRegion )
    {
      parameters.push(["setCacheRegion", cacheRegion]);
      return this;
    } 
    
this.setTimeout = function ( timeout ) 
    {
      parameters.push(["setTimeout", timeout]);
      return this;
    }      
    
this.setParameter = function ( pos, val ) 
    {
      parameters.push(["setParameter", pos, val ]);
      return this;
    }                 
    
this.setParameterList = function ( pos, val ) 
    {
      parameters.push(["setParameterList", pos, val ]);
      return this;
    }        
    
this.setBinary = function ( pos, val )
    {
      parameters.push(["setBinary", pos, val ]);
      return this;
    }

this.setBoolean = function ( pos, val )
    {
      parameters.push(["setBoolean", pos, val ]);
      return this;
    }

this.setDate = function ( pos, val )
    {
      parameters.push(["setDate", pos, val ]);
      return this;
    }

this.setString = function( pos, val )
    {
      parameters.push(["setString", pos, val ]);
      return this;
    }
    
this.setInteger = function ( pos, val )
    {
      parameters.push(["setInteger", pos, val ]);
      return this;
    }

this.setLong = function ( pos, val )
    {
      parameters.push(["setLong", pos, val ]);
      return this;
    }
  
this.setResultTransformer = function ( transformer)
    {
      parameters.push(["setResultTransformer", transformer]);
      return this;
    }   
}
</file>
<file name="SQLQueryDescriptor.js">
SQLQueryDescriptor.prototype = new QueryDescriptor();


function SQLQueryDescriptor ( proxy, methodName, query) {
   parameters = new Array();
   SQLQueryDescriptor.prototype.proxy = proxy; 
   parameters.push( [ methodName, query ] );
   SQLQueryDescriptor.prototype.parameters = parameters;

this.addEntity = function (entityName)
    {
        parameters.push(["addEntity",entityName]);
        return this;
    }
  
this.addJoin = function (alias, path)
    {
      parameters.push(["addJoin",alias, path]);
        return this;
    }   

this.setResultSetMapping = function (name)
    {
      parameters.push(["setResultSetMapping",name]);
        return this;
    }        
} 
</file>      

</folder>
</folder>    
<folder name="model">
<folder name="vo">     
 <xsl:for-each select="//datatype">

 <file name = "{@name}.js">
        function <xsl:value-of select="@name"/>()
        {
          <xsl:for-each select="const">
            this.<xsl:value-of select="@name"/> = <xsl:if test="@type='String'">"</xsl:if><xsl:value-of select="@value"/><xsl:if test="@type='String'">"</xsl:if>;
          </xsl:for-each> 

          <xsl:for-each select="field">
            this.<xsl:value-of select="@name"/> = <xsl:choose>
              <xsl:when test="@type='String'">''</xsl:when>
              <xsl:when test="@type='Boolean'">false</xsl:when>
              <xsl:when test="@type='Date'">new Date()</xsl:when>
              <xsl:when test="@type='int' or @type = 'Number'">0</xsl:when>
              <xsl:when test="@type='Array'">new Array()</xsl:when>
              <xsl:otherwise>null</xsl:otherwise>
            </xsl:choose>;
          </xsl:for-each>
        }
 </file>
      </xsl:for-each>
   
      
</folder>
</folder>
  </xsl:template>
  
  <xsl:template name="codegen.code">
    var proxy = webORB.bind( "<xsl:value-of select='@fullname' />", "<xsl:value-of select='@endpointURL' />" );
    
    <xsl:for-each select='method'>
      function <xsl:value-of select='@name'/>( <xsl:for-each select="arg">
      <xsl:if test="position() != 1">,</xsl:if>
      <xsl:value-of select="@name"/>
    </xsl:for-each><xsl:if test="count(arg) != 0">,</xsl:if> weborbAsyncCall )
      {
        if( weborbAsyncCall )
          proxy.<xsl:value-of select='@name'/>( <xsl:for-each select="arg">
      <xsl:if test="position() != 1">,</xsl:if>
      <xsl:value-of select="@name"/>
    </xsl:for-each><xsl:if test="count(arg) != 0">,</xsl:if> new Async( <xsl:value-of select='@name'/>Response ) );
        else
          return proxy.<xsl:value-of select='@name'/>(<xsl:for-each select="arg">
      <xsl:if test="position() != 1">,</xsl:if>
      <xsl:value-of select="@name"/>
    </xsl:for-each>);
      }
    </xsl:for-each>
      function createQueryDescriptor (queryString)
      {
         return new QueryDescriptor(proxy, "createQuery", queryString);
      }
      function createSQLQueryDescriptor( queryString )
      {
        return new SQLQueryDescriptor(proxy, "createSQLQuery", queryString);
      }
      function getNamedQueryDescriptor(name)
      {
        return new QueryDescriptor(proxy, "getNamedQuery", name);
      }   

    <xsl:for-each select="method[@containsvalues=1]">
      function TestDrive()
      {
        <xsl:call-template name="codegen.invoke.method" />
      }
    </xsl:for-each>

  </xsl:template>

</xsl:stylesheet>
