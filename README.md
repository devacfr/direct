Matcha Framework
======

Matcha is a Java connector implementation for [Ext JS](http://sencha.com) supporting [Ext Direct](http://www.sencha.com/products/extjs/extdirect/) technology and providing utility classes allowing to communicate with [Ext.data.Store](http://docs.sencha.com/extjs/#!/api/Ext.data.Store), [Ext.grid.Panel](http://docs.sencha.com/extjs/#!/api/Ext.form.Panel), [Ext.form.Panel](http://docs.sencha.com/extjs/#!/api/Ext.form.Panel)...

Matcha uses the [JSR-330](https://jcp.org/en/jsr/detail?id=330) dependency injection as basis and, [Guice 3.0](https://code.google.com/p/google-guice/) and [Spring Framework 3.1.x](http://projects.spring.io/spring-framework/) as extension.

Matcha is a Java Maven project and is composed of four main modules:

* **matcha-api** module contains all classes allowing facilitate communication with the Ext JS client-side application.
* **matcha-direct**, **matcha-direct-guice**, **matcha-direct-spring** modules are a implementation of Ext Direct API for Ext JS. It is based on [directjngine](https://code.google.com/p/directjngine/) but proposes a better integration with different DI and HTTP server-side implementation as [Java Servlet API](http://www.oracle.com/technetwork/java/index-jsp-135475.html), [JAX-RS](https://jax-rs-spec.java.net/), [Restlet](http://restlet.org/), and simplify configuration, accepts form parameter multi values for example checkbox and so on.
* **matcha-example** module proposes different use cases and integration facilitating the understanding.
* **matcha-testing** is a module facilitating the unit testing or integration testing  creation.
 

## Contribution Policy

Contributions via GitHub pull requests are gladly accepted from their original author.
Along with any pull requests, please state that the contribution is your original work and 
that you license the work to the project under the project's open source license.
Whether or not you state this explicitly, by submitting any copyrighted material via pull request, 
email, or other means you agree to license the material under the project's open source license and 
warrant that you have the legal authority to do so.

## Licence

	This is free and unencumbered software released into the public domain.
	
	Anyone is free to copy, modify, publish, use, compile, sell, or
	distribute this software, either in source code form or as a compiled
	binary, for any purpose, commercial or non-commercial, and by any
	means.
	
	In jurisdictions that recognize copyright laws, the author or authors
	of this software dedicate any and all copyright interest in the
	software to the public domain. We make this dedication for the benefit
	of the public at large and to the detriment of our heirs and
	successors. We intend this dedication to be an overt act of
	relinquishment in perpetuity of all present and future rights to this
	software under copyright law.
	
	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
	EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
	MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
	IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
	OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
	ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
	OTHER DEALINGS IN THE SOFTWARE.
	
	For more information, please refer to <http://unlicense.org/>
