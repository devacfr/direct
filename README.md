Matcha Framework
======

Matcha is a Java connector implementation for [Ext JS](http://sencha.com) supporting [Ext Direct](http://www.sencha.com/products/extjs/extdirect/) technology and providing utility classes allowing to communicate with [Ext.data.Store](http://docs.sencha.com/extjs/#!/api/Ext.data.Store), [Ext.grid.Panel](http://docs.sencha.com/extjs/#!/api/Ext.form.Panel), [Ext.form.Panel](http://docs.sencha.com/extjs/#!/api/Ext.form.Panel)...

Matcha uses the [JSR-330](https://jcp.org/en/jsr/detail?id=330) dependency injection as basis and, [Guice 3.0](https://code.google.com/p/google-guice/) and [Spring Framework 3.1.x](http://projects.spring.io/spring-framework/) as extension.

Matcha is a Java Maven project and is composed of four main modules:

* **matcha-api** module contains all classes allowing facilitate communication with the Ext JS client-side application.
* **matcha-direct**, **matcha-direct-guice**, **matcha-direct-spring** modules are a implementation of Ext Direct API for Ext JS. It is based on [directjngine](https://code.google.com/p/directjngine/) but proposes a better integration with different DI and HTTP server-side implementation as [Java Servlet API](http://www.oracle.com/technetwork/java/index-jsp-135475.html), [JAX-RS](https://jax-rs-spec.java.net/), [Restlet](http://restlet.org/), and simplify configuration, accepts form parameter multi values for example checkbox and so on.
* **matcha-example** module proposes different use cases and integration facilitating the understanding.
* **matcha-testing** is a module facilitating the unit testing or integration testing  creation.
 
## Continious Intergration

[![Build Status](https://travis-ci.org/devacfr/matcha.png)](https://travis-ci.org/devacfr/matcha)

## Contribution Policy

Contributions via GitHub pull requests are gladly accepted from their original author.
Along with any pull requests, please state that the contribution is your original work and 
that you license the work to the project under the project's open source license.
Whether or not you state this explicitly, by submitting any copyrighted material via pull request, 
email, or other means you agree to license the material under the project's open source license and 
warrant that you have the legal authority to do so.

## Licence

This software uses the [ExtJs library](http://www.sencha.com/products/extjs), which is distributed under the GPL v3 license (see http://www.sencha.com/products/extjs/licensing).

	This software is licensed under the Apache 2 license, quoted below.
	
	Copyright 2014 Christophe Friederich
	
	Licensed under the Apache License, Version 2.0 (the "License"); you may not
	use this file except in compliance with the License. You may obtain a copy of
	the License at http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
	WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
	License for the specific language governing permissions and limitations under
	the License.


